package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.yellowcap.R.id.ll_history;
import static com.app.yellowcap.R.id.ll_logout;
import static com.app.yellowcap.R.id.ll_notification;
import static com.app.yellowcap.R.id.ll_profile;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_newJobs)
    LinearLayout ll_newJobs;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.iv_newJobs)
    ImageView ivNewJobs;
    @BindView(R.id.ll_ItemsFirstRow)
    LinearLayout llItemsFirstRow;
    @BindView(R.id.iv_history)
    ImageView ivHistory;
    @BindView(ll_history)
    LinearLayout llHistory;
    @BindView(R.id.iv_notification)
    ImageView ivNotification;
    @BindView(ll_notification)
    LinearLayout llNotification;
    @BindView(R.id.ll_ItemsSecondtRow)
    LinearLayout llItemsSecondtRow;
    @BindView(R.id.iv_logout)
    ImageView ivLogout;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.ll_ItemsThirdRow)
    LinearLayout llItemsThirdRow;
    @BindView(R.id.ll_ItemsParent)
    LinearLayout llItemsParent;
    Unbinder unbinder;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listners();
    }

    private void listners() {

        ll_newJobs.setOnClickListener(this);
        llHistory.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llLogout.setOnClickListener(this);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(TechNotificationsFragment.newInstance(), "TechNotificationsFragment");
            }
        });
        titleBar.setSubHeading(getString(R.string.home));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_newJobs:
                getDockActivity().replaceDockableFragment(NewJobsFragment.newInstance(), "NewJobsFragment");
                break;

            case R.id.ll_history:
                getDockActivity().replaceDockableFragment(OrderHistoryFragment.newInstance(), "OrderHistoryFragment");
                break;

            case ll_notification:
                getDockActivity().replaceDockableFragment(TechNotificationsFragment.newInstance(), "TechNotificationsFragment");
                break;

            case ll_profile:
                getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), "ProfileFragment");
                break;

            case ll_logout:
                prefHelper.setLoginStatus(false);
               getDockActivity().replaceDockableFragment(UserSelectionFragment.newInstance(), "ProfileFragment");
                break;


        }
    }
}
