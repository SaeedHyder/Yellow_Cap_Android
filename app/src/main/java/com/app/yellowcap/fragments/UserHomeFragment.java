package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 5/23/2017.
 */

public class UserHomeFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.ll_ac)
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
    LinearLayout llCustom;
    Unbinder unbinder;

    public static UserHomeFragment newInstance() {
        return new UserHomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.home_user;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }
    private void setListener(){
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
        titleBar.setSubHeading("Home");
        titleBar.hideButtons();
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        titleBar.showMenuButton();

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

    private void addRequestServiceFragment(String type){
        getDockActivity().addDockableFragment(RequestServiceFragment.newInstance(),"RequestServiceFragment");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ac:
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
                break;
        }
    }
}
