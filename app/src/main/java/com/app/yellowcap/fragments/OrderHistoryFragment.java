package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.yellowcap.activities.DockActivity.KEY_FRAG_FIRST;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class OrderHistoryFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_listView)
    LinearLayout llListView;
    Unbinder unbinder;
    @BindView(R.id.ll_CompletedJobs)
    LinearLayout llCompletedJobs;

    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderhistory, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_orderhistory;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReplaceListViewFragment(CompletedJobsFragment.newInstance());
    }



    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().releaseDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Jobs");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_CompletedJobs:


                break;
        }

    }
    private void ReplaceListViewFragment(CompletedJobsFragment frag) {

        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_listView, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
