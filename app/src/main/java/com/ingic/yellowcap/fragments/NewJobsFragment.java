package com.ingic.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.yellowcap.R;
import com.ingic.yellowcap.entities.NewJobsEnt;
import com.ingic.yellowcap.entities.ResponseWrapper;
import com.ingic.yellowcap.fragments.abstracts.BaseFragment;
import com.ingic.yellowcap.helpers.InternetHelper;
import com.ingic.yellowcap.helpers.UIHelper;
import com.ingic.yellowcap.ui.adapters.ArrayListAdapter;
import com.ingic.yellowcap.ui.viewbinder.NewJobsitemBinder;
import com.ingic.yellowcap.ui.views.AnyTextView;
import com.ingic.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class NewJobsFragment extends BaseFragment {

    @BindView(R.id.lv_NewJobs)
    ListView lv_NewJobs;

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private ArrayListAdapter<NewJobsEnt> adapter;
    private ArrayList<NewJobsEnt> userCollection ;



    public static NewJobsFragment newInstance() {
        return new NewJobsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NewJobsEnt>(getDockActivity(), new NewJobsitemBinder(prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newjobs, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_newjobs;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            getNewJobs();
        }
        selectNewJobsListItem();



    }

    private void getNewJobs() {
        loadingStarted();
        Call<ResponseWrapper<ArrayList<NewJobsEnt>>> call = webService.newJobs(Integer.valueOf(prefHelper.getUserId()));

        call.enqueue(new Callback<ResponseWrapper<ArrayList<NewJobsEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<NewJobsEnt>>> call, Response<ResponseWrapper<ArrayList<NewJobsEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    setNewJobsData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<NewJobsEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e("UserSignupFragment", t.toString());
               // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });

    }


    private void setNewJobsData(ArrayList<NewJobsEnt> result) {
        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lv_NewJobs.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lv_NewJobs.setVisibility(View.VISIBLE);
        }
        userCollection = new ArrayList<>();
        userCollection.addAll(result);

        bindData(userCollection);
    }

    private void bindData(ArrayList<NewJobsEnt> userCollection) {

        adapter.clearList();
        lv_NewJobs.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void selectNewJobsListItem() {

        lv_NewJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               getDockActivity().addDockableFragment(NewJobDetail.newInstance(userCollection.get(position)), "NewJobDetail");
            }
        });

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.New_Jobs));

    }
}
