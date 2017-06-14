package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.UserComleteJobsEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.SetOrderCounts;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.UserCompleteJobsBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/24/2017.
 */

public class UserCompleteJobsFragment extends BaseFragment {
   /* @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;*/
    @BindView(R.id.CompletedJobs_ListView)
    ListView CompletedJobsListView;
    SetOrderCounts orderCounts;
    private ArrayListAdapter<UserComleteJobsEnt> adapter;
    private ArrayList<UserComleteJobsEnt> userCollection = new ArrayList<>();

    public static UserCompleteJobsFragment newInstance() {
        return new UserCompleteJobsFragment();
    }

    public SetOrderCounts getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(SetOrderCounts orderCounts) {
        this.orderCounts = orderCounts;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_completedjobs;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<UserComleteJobsEnt>(getDockActivity(), new UserCompleteJobsBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completedjobs, container, false);
      ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            getCompletedJobs();
        }
    }

    private void getCompletedJobs() {
        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<ArrayList<UserComleteJobsEnt>>> call = webService.getUserCompleted(prefHelper.getUserId());

        call.enqueue(new Callback<ResponseWrapper<ArrayList<UserComleteJobsEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserComleteJobsEnt>>> call, Response<ResponseWrapper<ArrayList<UserComleteJobsEnt>>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    setCompletedJobsData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserComleteJobsEnt>>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                //UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });

    }


    private void setCompletedJobsData(ArrayList<UserComleteJobsEnt> result) {
        userCollection = new ArrayList<>();

      /*  if (result.size() <= 0) {
            UIHelper.showShortToastInCenter(getDockActivity(), "No Data to Show");
        } else {
            CompletedJobsListView.setVisibility(View.GONE);
        }
*/
        userCollection.addAll(result);
        orderCounts.setcompleteCount(result.size());
      /*  userCollection.add(new UserComleteJobsEnt("01", "24-3-17", "Al Musa", "Electrical", 4, "AED 55.00",getString(R.string.dummy_desciption)));
        userCollection.add(new UserComleteJobsEnt("02", "25-3-17", "Al Musa", "Plumbing", 3, "AED 55.00",getString(R.string.dummy_desciption)));
        userCollection.add(new UserComleteJobsEnt("03", "26-3-17", "Al Musa", "Cleaning", 5, "AED 55.00",getString(R.string.dummy_desciption)));*/

        bindData(userCollection);
    }

    private void bindData(ArrayList<UserComleteJobsEnt> userCollection) {

        adapter.clearList();
        CompletedJobsListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }




}
