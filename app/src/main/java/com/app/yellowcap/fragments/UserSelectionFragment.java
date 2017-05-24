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
import butterknife.Unbinder;

/**
 * Created on 5/22/2017.
 */


public class UserSelectionFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.usercontainer)
    LinearLayout usercontainer;
    @BindView(R.id.techniciancontainer)
    LinearLayout techniciancontainer;
    Unbinder unbinder;

    public static UserSelectionFragment newInstance() {
        return new UserSelectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_userselection;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistener();
    }

    private void setlistener() {
        usercontainer.setOnClickListener(this);
        techniciancontainer.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.usercontainer:
                prefHelper.setUserType("user");
                getDockActivity().addDockableFragment(UserSignupFragment.newInstance(),"UserSignUp Fragment");
                break;
            case R.id.techniciancontainer:
                prefHelper.setUserType("technician");
                getDockActivity().addDockableFragment(LoginFragment.newInstance(),"Login Fragment");
                break;
        }
    }
}
