package com.app.yellowcap.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NavigationEnt;
import com.app.yellowcap.entities.RegistrationResultEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.TokenUpdater;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.UpdateNotificationsCount;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.NavigationItemBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SideMenuFragment extends BaseFragment  {

    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.txt_userName)
    AnyTextView txtUserName;
    @BindView(R.id.txt_useremail)
    AnyTextView txtUseremail;
    @BindView(R.id.nav_listview)
    ListView navListview;

    protected BroadcastReceiver broadcastReceiver;
    public boolean isNotificationTap = false;
    int notificationCount;
    UpdateNotificationsCount updateNotificationsCount;
    private ArrayList<NavigationEnt> navigationItemList = new ArrayList<>();
    private ArrayListAdapter<NavigationEnt> madapter;

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        madapter = new ArrayListAdapter<NavigationEnt>(getDockActivity(), new NavigationItemBinder(getDockActivity(), this,prefHelper));


    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_sidemenu;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistItemClickListener();
        binddata();
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            getUserProfile();
        }
        onNotificationReceived();


    }

    public void setInterface(UpdateNotificationsCount notificationsCount) {
        updateNotificationsCount = notificationsCount;
    }

    @Override
    public void onResume() {

        super.onResume();
        TokenUpdater.getInstance().UpdateToken(getDockActivity(), prefHelper.getUserId(), "android", prefHelper.getFirebase_TOKEN());

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
                    notificationCount = prefHelper.getBadgeCount();
                    updateNotificationsCount.updateCount(prefHelper.getBadgeCount());
                    getMainActivity().refreshSideMenu();
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


    private void getUserProfile() {
        if (prefHelper.isLogin() && prefHelper.getUserType().equals("user")) {
            Call<ResponseWrapper<RegistrationResultEnt>> call = webService.getUserProfile(prefHelper.getUserId());
            call.enqueue(new Callback<ResponseWrapper<RegistrationResultEnt>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<RegistrationResultEnt>> call, Response<ResponseWrapper<RegistrationResultEnt>> response) {
                    if (response.body().getResponse().equals("2000")) {
                        setProfileData(response.body().getResult());
                    } else {

                        UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<RegistrationResultEnt>> call, Throwable t) {
                    Log.e("EntryCodeFragment", t.toString());
                  //  UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
                }
            });
        }
    }

    private void setProfileData(RegistrationResultEnt result) {
        prefHelper.putRegistrationResult(result);
        Picasso.with(getDockActivity()).load(result.getProfileImage()).into(CircularImageSharePop);

        txtUserName.setText(result.getFullName());
        txtUseremail.setText(result.getEmail());

    }

    private void setlistItemClickListener() {
        navListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* madapter.notifyDataSetChanged();
                AnyTextView textview = (AnyTextView)view.findViewById(R.id.txt_home);
                textview.setTextColor(getResources().getColor(R.color.text_color));
                ImageView img = (ImageView)view.findViewById(R.id.img_unselected);
                NavigationEnt entity = (NavigationEnt)madapter.getItem(position);
                img.setImageResource(entity.getSelectedDrawable());*/

                if (navigationItemList.get(position).getItem_text().equals(getString(R.string.home))) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                } else if (navigationItemList.get(position).getItem_text().equals(getString(R.string.notifications))) {
                    getDockActivity().replaceDockableFragment(UserNotificationsFragment.newInstance(), "UserHomeFragment");
                } else if (navigationItemList.get(position).getItem_text().equals(getString(R.string.my_job))) {
                    getDockActivity().replaceDockableFragment(UserJobsFragment.newInstance(), "UserjobsFragment");
                } else if (navigationItemList.get(position).getItem_text().equals(getString(R.string.profile))) {
                    getDockActivity().replaceDockableFragment(UserProfileFragment.newInstance(), "UserProfileFragment");
                } else if (navigationItemList.get(position).getItem_text().equals(getString(R.string.about_app))) {
                    getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(), "UserAboutFragment");
                }


            }
        });
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }



    private void binddata() {
        navigationItemList.add(new NavigationEnt(R.drawable.home_yellow, R.drawable.home, getString(R.string.home)));
        navigationItemList.add(new NavigationEnt(R.drawable.profile_yellow, R.drawable.profile, getString(R.string.profile)));
        navigationItemList.add(new NavigationEnt(R.drawable.notification_yellow, R.drawable.notification,
                getString(R.string.notifications), notificationCount));
        navigationItemList.add(new NavigationEnt(R.drawable.jobs_yellow, R.drawable.jobs, getString(R.string.my_job)));
        navigationItemList.add(new NavigationEnt(R.drawable.about_yellow, R.drawable.about, getString(R.string.about_app)));
        bindview();
    }

    private void bindview() {
        madapter.clearList();
        navListview.setAdapter(madapter);
        madapter.addAll(navigationItemList);
        madapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
         ButterKnife.bind(this, rootView);
        return rootView;
    }

   /* @Override
    public void updateCount(int count, int position) {

        if (notificationCount != count) {

            NavigationEnt updatedItem = (NavigationEnt) madapter.getItem(position);
            updatedItem.setNotificationCount(notificationCount);

            navigationItemList.remove(position);
            navigationItemList.add(position, updatedItem);
            madapter.clearList();
            madapter.addAll(navigationItemList);
            madapter.notifyDataSetChanged();

        }

    }*/
}
