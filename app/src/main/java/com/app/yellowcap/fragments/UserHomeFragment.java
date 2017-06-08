package com.app.yellowcap.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.adapters.HomeServiceAdapter;
import com.app.yellowcap.ui.viewbinder.HomeServiceBinder;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/23/2017.
 */

public class UserHomeFragment extends BaseFragment implements View.OnClickListener {
    /* @BindView(R.id.ll_ac)
     LinearLayout llAc;
     @BindView(R.id.ll_electrical)
     LinearLayout llElectrical;
     @BindView(R.id.ll_plumbing)
     LinearLayout llPlumbing;
     @BindView(R.id.ll_furniture)
     LinearLayout llFurniture;
     @BindView(R.id.ll_pest)
     LinearLayout llPest;
     @BindView(R.id.ll_cleaning)
     LinearLayout llCleaning;
     @BindView(R.id.ll_move)
     LinearLayout llMove;
     @BindView(R.id.ll_custom)
     LinearLayout llCustom;*/
    public boolean isNotification = false;
    protected BroadcastReceiver broadcastReceiver;
    Unbinder unbinder;
    @BindView(R.id.filter_subtypes)
    GridView filterSubtypes;
    private ArrayList<ServiceEnt> userservices;
    private HomeServiceAdapter madapter;
    private ArrayListAdapter<ServiceEnt>mServiceAdapter;
    private int TOTAL_CELLS_PER_ROW = 3;

    public static UserHomeFragment newInstance() {
        return new UserHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceAdapter = new ArrayListAdapter<ServiceEnt>(getDockActivity(),new HomeServiceBinder(getDockActivity()));

    }

    @Override
    protected int getLayout() {
        return R.layout.home_user;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingStarted();
        if (getMainActivity().isNotification) {
            getMainActivity().isNotification = false;
            showNotification();
        }
        setListener();
        gethomeData();
        onNotificationReceived();

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
                    isNotification = true;
                    System.out.println(prefHelper.getFirebase_TOKEN());
                }
            }
        };
    }

    private void showNotification() {
        getDockActivity().addDockableFragment(UserNotificationsFragment.newInstance(), "UserNotificationsFragment");
    }

    private void gethomeData() {
        Call<ResponseWrapper<ArrayList<ServiceEnt>>> call = webService.getHomeServices();
        call.enqueue(new Callback<ResponseWrapper<ArrayList<ServiceEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Response<ResponseWrapper<ArrayList<ServiceEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    bindData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e("UserHome", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void bindData(ArrayList<ServiceEnt> result) {
        userservices = new ArrayList<>();
        userservices.addAll(result);
        bindview(userservices);
    }

    private void bindview(ArrayList<ServiceEnt> userservices) {
        mServiceAdapter.clearList();
        filterSubtypes.setAdapter(mServiceAdapter);
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
      /*  llAc.setOnClickListener(this);
        llCleaning.setOnClickListener(this);
        llCustom.setOnClickListener(this);
        llElectrical.setOnClickListener(this);
        llFurniture.setOnClickListener(this);
        llMove.setOnClickListener(this);
        llPest.setOnClickListener(this);
        llPlumbing.setOnClickListener(this);*/


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
        });
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.requesr_service_home));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addRequestServiceFragment(ServiceEnt type) {
        getDockActivity().replaceDockableFragment(RequestServiceFragment.newInstance(type,null), "RequestServiceFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.ll_ac:
                addRequestServiceFragment("ac");
                break;
            case R.id.ll_electrical:
                addRequestServiceFragment("electrical");
                break;
            case R.id.ll_plumbing:
                addRequestServiceFragment("plumbing");
                break;
            case R.id.ll_furniture:
                addRequestServiceFragment("furniture");
                break;
            case R.id.ll_pest:
                addRequestServiceFragment("pest");
                break;
            case R.id.ll_cleaning:
                addRequestServiceFragment("cleaning");
                break;
            case R.id.ll_move:
                addRequestServiceFragment("move");
                break;
            case R.id.ll_custom:
                addRequestServiceFragment("custom");
                break;*/
        }
    }
}
/* private void bindviewRecyler(final ArrayList<ServiceEnt> userservices) {
        final GridLayoutManager mng_layout = new GridLayoutManager(getDockActivity(), TOTAL_CELLS_PER_ROW*//*In your case 4*//*);
        madapter = new HomeServiceAdapter(getDockActivity(), userservices);
        mng_layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int remainder = userservices.size() % TOTAL_CELLS_PER_ROW;
                int secondlastposition = -1;
                int lastposition = userservices.size() - remainder;
                if (remainder !=0) {
                    if (remainder > 1) {
                        secondlastposition = userservices.size() - remainder - 1;
                    }
                    if (lastposition == position) {
                        return remainder;
                    } else if (secondlastposition == position) {
                        return remainder - 1;
                    } else {
                        return 1;
                    }
                }else {
                    return 1;
                }

            }
        });
        madapter.notifyDataSetChanged();
        filterSubtypes.setLayoutManager(mng_layout);
        filterSubtypes.setItemAnimator(new DefaultItemAnimator());
        filterSubtypes.setAdapter(madapter);

        madapter.notifyDataSetChanged();

    }*/