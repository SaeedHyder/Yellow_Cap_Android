package com.app.yellowcap.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.TokenUpdater;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.adapters.HomeServiceAdapter;
import com.app.yellowcap.ui.viewbinder.HomeServiceBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/23/2017.
 */

public class UserHomeFragment extends BaseFragment implements View.OnClickListener {

    public boolean isNotification = false;
    protected BroadcastReceiver broadcastReceiver;

    @BindView(R.id.filter_subtypes)
    GridView filterSubtypes;
    @BindView(R.id.iv_ac)
    ImageView ivAc;
    @BindView(R.id.ll_ac)
    LinearLayout llAc;
    @BindView(R.id.iv_electrical)
    ImageView ivElectrical;
    @BindView(R.id.ll_electrical)
    LinearLayout llElectrical;
    @BindView(R.id.iv_plumbing)
    ImageView ivPlumbing;
    @BindView(R.id.ll_plumbing)
    LinearLayout llPlumbing;
    @BindView(R.id.ll_ItemsFirstRow)
    LinearLayout llItemsFirstRow;
    @BindView(R.id.iv_furniture)
    ImageView ivFurniture;
    @BindView(R.id.ll_furniture)
    LinearLayout llFurniture;
    @BindView(R.id.iv_pest)
    ImageView ivPest;
    @BindView(R.id.ll_pest)
    LinearLayout llPest;
    @BindView(R.id.iv_cleaning)
    ImageView ivCleaning;
    @BindView(R.id.ll_cleaning)
    LinearLayout llCleaning;
    @BindView(R.id.ll_ItemsSecondtRow)
    LinearLayout llItemsSecondtRow;
    @BindView(R.id.iv_move)
    ImageView ivMove;
    @BindView(R.id.ll_move)
    LinearLayout llMove;
    @BindView(R.id.iv_custom)
    ImageView ivCustom;
    @BindView(R.id.ll_custom)
    LinearLayout llCustom;
    @BindView(R.id.ll_ItemsThirdRow)
    LinearLayout llItemsThirdRow;
    @BindView(R.id.ll_ItemsParent)
    LinearLayout llItemsParent;
    @BindView(R.id.ll_ItemsParent1)
    LinearLayout llItemsParent1;
    @BindView(R.id.txt_ac)
    AnyTextView txtAc;
    @BindView(R.id.txt_electrical)
    AnyTextView txtElectrical;
    @BindView(R.id.txt_plumbing)
    AnyTextView txtPlumbing;
    @BindView(R.id.txt_furniture)
    AnyTextView txtFurniture;
    @BindView(R.id.txt_pest)
    AnyTextView txtPest;
    @BindView(R.id.txt_cleaning)
    AnyTextView txtCleaning;
    @BindView(R.id.txt_move)
    AnyTextView txtMove;
    @BindView(R.id.txt_jobs)
    AnyTextView txtJobs;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;
    private ArrayList<ServiceEnt> userservices;
    private HomeServiceAdapter madapter;
    private ArrayListAdapter<ServiceEnt> mServiceAdapter;
    private int TOTAL_CELLS_PER_ROW = 3;
    private ImageLoader imageLoader;

    public static UserHomeFragment newInstance() {
        return new UserHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceAdapter = new ArrayListAdapter<ServiceEnt>(getDockActivity(), new HomeServiceBinder(getDockActivity()));

    }

    @Override
    protected int getLayout() {
        return R.layout.home_user;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageLoader = ImageLoader.getInstance();

        mainFrame.setVisibility(View.GONE);
        onNotificationReceived();
        getMainActivity().refreshSideMenuWithnewFragment();
        if (getMainActivity().isNotification) {
            getMainActivity().isNotification = false;
            getDockActivity().addDockableFragment(UserNotificationsFragment.newInstance(), "UserNotificationsFragment");
        }

        setListener();
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            gethomeData();
        }


    }
    @Override
    public void onResume() {

        super.onResume();
       // TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));

    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    System.out.println("registration complete");
                    // FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    // getMainActivity().isNotification = true;
                    getMainActivity().refreshSideMenu();
                    getMainActivity().titleBar.invalidate();
                    getMainActivity().titleBar.getImageView().invalidate();
                   /* getMainActivity().notificationIntent();
                    if (getMainActivity().isNotification) {
                       getMainActivity().isNotification = false;
                       getDockActivity().addDockableFragment(UserNotificationsFragment.newInstance(), "UserNotificationsFragment");
                    }*/

                    System.out.println(prefHelper.getFirebase_TOKEN());


                }
            }
        };
    }




    private void gethomeData() {
        Call<ResponseWrapper<ArrayList<ServiceEnt>>> call = webService.getHomeServices();
        call.enqueue(new Callback<ResponseWrapper<ArrayList<ServiceEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Response<ResponseWrapper<ArrayList<ServiceEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    bindData(response.body().getResult());
                    setHomedata(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e("UserHome", t.toString());
                //   UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void setHomedata(ArrayList<ServiceEnt> result) {
        if (result.size() > 0) {
            try {
                if (!prefHelper.isLanguageArabic()) {
                    imageLoader.displayImage(result.get(0).getServiceImage(), ivAc);
                    txtAc.setText(result.get(0).getTitle());
                    txtAc.setTag(result.get(0));

                    imageLoader.displayImage(result.get(1).getServiceImage(), ivElectrical);
                    txtElectrical.setText(result.get(1).getTitle());
                    txtElectrical.setTag(result.get(1));

                    imageLoader.displayImage(result.get(2).getServiceImage(), ivPlumbing);
                    txtPlumbing.setText(result.get(2).getTitle());
                    txtPlumbing.setTag(result.get(2));

                    imageLoader.displayImage(result.get(3).getServiceImage(), ivFurniture);
                    txtFurniture.setText(result.get(3).getTitle());
                    txtFurniture.setTag(result.get(3));

                    imageLoader.displayImage(result.get(4).getServiceImage(), ivPest);
                    txtPest.setText(result.get(4).getTitle());
                    txtPest.setTag(result.get(4));

                    imageLoader.displayImage(result.get(5).getServiceImage(), ivCleaning);
                    txtCleaning.setText(result.get(5).getTitle());
                    txtCleaning.setTag(result.get(5));

                    imageLoader.displayImage(result.get(6).getServiceImage(), ivMove);
                    txtMove.setText(result.get(6).getTitle());
                    txtMove.setTag(result.get(6));

                    imageLoader.displayImage(result.get(7).getServiceImage(), ivCustom);
                    txtJobs.setText(result.get(7).getTitle());
                    txtJobs.setTag(result.get(7));

                } else {

                    imageLoader.displayImage(result.get(0).getServiceImage(), ivAc);
                    txtAc.setText(result.get(0).getArTitle());
                    txtAc.setTag(result.get(0));

                    imageLoader.displayImage(result.get(1).getServiceImage(), ivElectrical);
                    txtElectrical.setText(result.get(1).getArTitle());
                    txtElectrical.setTag(result.get(1));

                    imageLoader.displayImage(result.get(2).getServiceImage(), ivPlumbing);
                    txtPlumbing.setText(result.get(2).getArTitle());
                    txtPlumbing.setTag(result.get(2));

                    imageLoader.displayImage(result.get(3).getServiceImage(), ivFurniture);
                    txtFurniture.setText(result.get(3).getArTitle());
                    txtFurniture.setTag(result.get(3));

                    imageLoader.displayImage(result.get(4).getServiceImage(), ivPest);
                    txtPest.setText(result.get(4).getArTitle());
                    txtPest.setTag(result.get(4));

                    imageLoader.displayImage(result.get(5).getServiceImage(), ivCleaning);
                    txtCleaning.setText(result.get(5).getArTitle());
                    txtCleaning.setTag(result.get(5));

                    imageLoader.displayImage(result.get(6).getServiceImage(), ivMove);
                    txtMove.setText(result.get(6).getArTitle());
                    txtMove.setTag(result.get(6));

                    imageLoader.displayImage(result.get(7).getServiceImage(), ivCustom);
                    txtJobs.setText(result.get(7).getArTitle());
                    txtJobs.setTag(result.get(7));
                }

                mainFrame.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void bindData(ArrayList<ServiceEnt> result) {

        userservices = new ArrayList<>();
        userservices.addAll(result);
        bindview(userservices);
    }

    private void bindview(ArrayList<ServiceEnt> userservices) {
        mServiceAdapter.clearList();
        if (filterSubtypes != null) {
            //filterSubtypes.setLayoutParams(new GridLayoutManager.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, GridLayoutManager.LayoutParams.FILL_PARENT));
          /*  filterSubtypes.setNumColumns(GridView.AUTO_FIT);
            filterSubtypes.setColumnWidth(GridView.AUTO_FIT);
            filterSubtypes.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);*/
            filterSubtypes.setAdapter(mServiceAdapter);
        }
        mServiceAdapter.addAll(userservices);
        mServiceAdapter.notifyDataSetChanged();

    }


    private void setListener() {
        filterSubtypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addRequestServiceFragment(userservices.get(position));
            }
        });

        llAc.setOnClickListener(this);
        llCleaning.setOnClickListener(this);
        llCustom.setOnClickListener(this);
        llElectrical.setOnClickListener(this);
        llFurniture.setOnClickListener(this);
        llMove.setOnClickListener(this);
        llPest.setOnClickListener(this);
        llPlumbing.setOnClickListener(this);


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().releaseDrawer();

        titleBar.hideButtons();
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(UserNotificationsFragment.newInstance(), "UserNotificationsFragment");

            }
        },prefHelper);
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.requesr_service_home));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void addRequestServiceFragment(ServiceEnt type) {
        getDockActivity().replaceDockableFragment(RequestServiceFragment.newInstance(type, null), "RequestServiceFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ac:
                addRequestServiceFragment((ServiceEnt) txtAc.getTag());
                break;
            case R.id.ll_electrical:
                addRequestServiceFragment((ServiceEnt) txtElectrical.getTag());
                break;
            case R.id.ll_plumbing:
                addRequestServiceFragment((ServiceEnt) txtPlumbing.getTag());
                break;
            case R.id.ll_furniture:
                addRequestServiceFragment((ServiceEnt) txtFurniture.getTag());
                break;
            case R.id.ll_pest:
                addRequestServiceFragment((ServiceEnt) txtPest.getTag());
                break;
            case R.id.ll_cleaning:
                addRequestServiceFragment((ServiceEnt) txtCleaning.getTag());
                break;
            case R.id.ll_move:
                addRequestServiceFragment((ServiceEnt) txtMove.getTag());
                break;
            case R.id.ll_custom:
                addRequestServiceFragment((ServiceEnt) txtJobs.getTag());
                break;
        }
    }

}