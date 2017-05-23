package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LoginFragment extends BaseFragment implements OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.txtForgotPass)
    AnyTextView txtForgotPass;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setListeners();

    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_login:
                prefHelper.setLoginStatus(true);
                getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragmnet");
                break;

            case R.id.txtForgotPass:
                final DialogFragment successPopUp = DialogFragment.newInstance();
                successPopUp.show(getDockActivity().getSupportFragmentManager(), "forgotPasswordPopUp");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
