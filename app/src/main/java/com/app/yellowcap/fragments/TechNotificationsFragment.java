package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NotificationEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.TechNotificationitemBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class TechNotificationsFragment extends BaseFragment {

    @BindView(R.id.lv_TechNotification)
    ListView lvTechNotification;

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private ArrayListAdapter<NotificationEnt> adapter;
    private ArrayList<NotificationEnt> userCollection;

    public static TechNotificationsFragment newInstance() {
        return new TechNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NotificationEnt>(getDockActivity(), new TechNotificationitemBinder());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tech_notifications;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            getNotification();
        }
        NotificationItemListner();

    }

    private void NotificationItemListner() {

        lvTechNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented in beta version");
                  getDockActivity().replaceDockableFragment(NewJobsFragment.newInstance(), "NewJobDetail");
            }
        });

    }

    private void getNotification() {
        Call<ResponseWrapper<ArrayList<NotificationEnt>>> call = webService.getNotification(prefHelper.getUserId());
        call.enqueue(new Callback<ResponseWrapper<ArrayList<NotificationEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<NotificationEnt>>> call, Response<ResponseWrapper<ArrayList<NotificationEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    setNotificationData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<NotificationEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e("UserSignupFragment", t.toString());
              //  UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void setNotificationData(ArrayList<NotificationEnt> result) {
        userCollection = new ArrayList<>();
        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvTechNotification.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvTechNotification.setVisibility(View.VISIBLE);

        }
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<NotificationEnt> userCollection) {

        adapter.clearList();
        lvTechNotification.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Notifications));

    }

}
