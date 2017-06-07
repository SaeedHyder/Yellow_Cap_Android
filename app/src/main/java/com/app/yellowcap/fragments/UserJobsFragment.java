package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.interfaces.SetOrderCounts;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.yellowcap.activities.DockActivity.KEY_FRAG_FIRST;

/**
 * Created on 5/24/2017.
 */

public class UserJobsFragment extends BaseFragment implements View.OnClickListener, SetOrderCounts {
    @BindView(R.id.ll_listView)
    LinearLayout llListView;
    @BindView(R.id.ll_CompletedJobs)
    LinearLayout llCompletedJobs;
    @BindView(R.id.ll_InProgess)
    LinearLayout llInProgess;
    @BindView(R.id.selectedArrowCompletedJobs)
    ImageView selectedArrowCompletedJobs;
    @BindView(R.id.selectedArrowInProgress)
    ImageView selectedArrowInProgress;
    @BindView(R.id.txt_jobCount)
    AnyTextView txtJobCount;
    @BindView(R.id.txt_InProgressCount)
    AnyTextView txtInProgressCount;
    Unbinder unbinder;
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.txt_userName)
    AnyTextView txtUserName;

    public static UserJobsFragment newInstance() {
        return new UserJobsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user_jobs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListners();
        ReplaceListViewFragment(UserInProgressFragment.newInstance());
        setProfileData();
        setInprogressCount(0);
        setcompleteCount(0);
    }

    private void setProfileData() {
        Picasso.with(getDockActivity()).load(prefHelper.getRegistrationResult().getProfileImage()).into(CircularImageSharePop);
        txtUserName.setText(prefHelper.getRegistrationResult().getFullName());
    }


    private void setListners() {
        llInProgess.setOnClickListener(this);
        llCompletedJobs.setOnClickListener(this);
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

    private void ReplaceListViewFragment(UserCompleteJobsFragment frag) {
        frag.setOrderCounts(this);
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_listView, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();

    }
    private void ReplaceListViewFragment(UserInProgressFragment frag) {
        frag.setOrderCounts(this);
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_listView, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        getDockActivity().lockDrawer();
        titleBar.setSubHeading(getString(R.string.jobs));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_CompletedJobs:
                selectedArrowCompletedJobs.setVisibility(View.VISIBLE);
                selectedArrowInProgress.setVisibility(View.GONE);
                txtJobCount.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.yellow));
                txtInProgressCount.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.gray));
                ReplaceListViewFragment(UserCompleteJobsFragment.newInstance());
                break;

            case R.id.ll_InProgess:
                selectedArrowCompletedJobs.setVisibility(View.GONE);
                selectedArrowInProgress.setVisibility(View.VISIBLE);
                txtJobCount.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.gray));
                txtInProgressCount.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.yellow));
                ReplaceListViewFragment(UserInProgressFragment.newInstance());
                break;
        }
    }

    @Override
    public void setcompleteCount(int count) {
        txtJobCount.setText(String.valueOf(count));
    }

    @Override
    public void setInprogressCount(int count) {
        txtInProgressCount.setText(String.valueOf(count));
    }
}
