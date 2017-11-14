package com.ingic.yellowcap.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ingic.yellowcap.R;
import com.ingic.yellowcap.entities.RequestEnt;
import com.ingic.yellowcap.entities.ResponseWrapper;
import com.ingic.yellowcap.entities.UserInProgressEnt;
import com.ingic.yellowcap.fragments.abstracts.BaseFragment;
import com.ingic.yellowcap.global.AppConstants;
import com.ingic.yellowcap.helpers.DialogHelper;
import com.ingic.yellowcap.helpers.InternetHelper;
import com.ingic.yellowcap.helpers.UIHelper;
import com.ingic.yellowcap.interfaces.CallUser;
import com.ingic.yellowcap.interfaces.SetOrderCounts;
import com.ingic.yellowcap.interfaces.onCancelJobListner;
import com.ingic.yellowcap.ui.adapters.ArrayListAdapter;
import com.ingic.yellowcap.ui.viewbinder.UserInProgressBinder;
import com.ingic.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/24/2017.
 */

public class UserInProgressFragment extends BaseFragment implements CallUser, onCancelJobListner {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.InProgress_ListView)
    ListView InProgressListView;

    SetOrderCounts orderCounts;
    private ArrayListAdapter<UserInProgressEnt> adapter;
    private ArrayList<UserInProgressEnt> userCollection;

    public static UserInProgressFragment newInstance() {
        return new UserInProgressFragment();
    }


    public SetOrderCounts getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(SetOrderCounts orderCounts) {
        this.orderCounts = orderCounts;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_inprogress;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<UserInProgressEnt>(getDockActivity(), new UserInProgressBinder(this, getDockActivity(), this,prefHelper));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getDockActivity().onLoadingStarted();
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            getInprogressJobs();
        }

    }

    private void getInprogressJobs() {
        Call<ResponseWrapper<ArrayList<UserInProgressEnt>>> call = webService.getUserInprogress(prefHelper.getUserId());
        call.enqueue(new Callback<ResponseWrapper<ArrayList<UserInProgressEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserInProgressEnt>>> call, Response<ResponseWrapper<ArrayList<UserInProgressEnt>>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    setInProgressJobsData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserInProgressEnt>>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                //UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void setInProgressJobsData(ArrayList<UserInProgressEnt> result) {
        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        orderCounts.setInprogressCount(result.size());
      /*  userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 XXX XXX XXX",false));
        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 123 456 789",true));
        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 789 123 465",true));
        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 XXX XXX XXX",false));*/

        bindData(userCollection);
    }

    private void bindData(ArrayList<UserInProgressEnt> userCollection) {

        adapter.clearList();
        InProgressListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }




    @Override
    public void CallOnUserNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    public void onCancelJob(final int position) {

        final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
        dialogHelper.initCancelJobDialog(R.layout.cancle_job_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().onLoadingStarted();
                if (userCollection.size()>position) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        cancelJob(userCollection.get(position).getId(), dialogHelper);
                    }
                }

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelper.hideDialog();
                getDockActivity().onLoadingFinished();
            }
        });
        dialogHelper.setCancelable(false);
        dialogHelper.showDialog();


    }

    private void cancelJob(Integer requestID, final DialogHelper dialogHelper) {

        Call<ResponseWrapper<RequestEnt>> call = webService.changeStatus(prefHelper.getUserId(), requestID, "", AppConstants.CANCEL_JOB);
        call.enqueue(new Callback<ResponseWrapper<RequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RequestEnt>> call, Response<ResponseWrapper<RequestEnt>> response) {
                getDockActivity().onLoadingFinished();
                dialogHelper.hideDialog();
                if (response.body().getResponse().equals("2000")) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RequestEnt>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("EntryCodeFragment", t.toString());
               // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }
}