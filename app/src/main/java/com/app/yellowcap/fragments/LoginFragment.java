package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.RegistrationResultEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.helpers.TokenUpdater;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends BaseFragment implements OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.txtForgotPass)
    AnyTextView txtForgotPass;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    Unbinder unbinder1;

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
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_login:
                if (isvalidate()) {
                    loginTechnician();
                }
                break;

            case R.id.txtForgotPass:
                final DialogHelper forgetDialog = new DialogHelper(getDockActivity());
                forgetDialog.initForgotPasswordDialog(R.layout.forgot_password_dialog, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgetDialog.hideDialog();
                    }
                });
                forgetDialog.setCancelable(false);
                forgetDialog.showDialog();
                break;

        }
    }
    private void loginTechnician(){
        Call<ResponseWrapper<RegistrationResultEnt>> call = webService.loginTechnician(edtEmail.getText().toString(),
                edtPassword.getText().toString());
        call.enqueue(new Callback<ResponseWrapper<RegistrationResultEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResultEnt>> call, Response<ResponseWrapper<RegistrationResultEnt>> response) {
                if (response.body().getResponse().equals("2000")) {
                    prefHelper.setUserType("technician");
                    prefHelper.setUsrId(String.valueOf(response.body().getResult().getId()));
                    prefHelper.setUsrName(response.body().getResult().getFullName());
                    prefHelper.setPhonenumber(response.body().getResult().getPhoneNo());
                    prefHelper.putRegistrationResult(response.body().getResult());
                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getUserId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                    prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragmnet");
                }
                else{
                    UIHelper.showShortToastInCenter(getDockActivity(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResultEnt>> call, Throwable t) {
                Log.e("UserSignupFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean isvalidate() {

        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.valid_email));
            return false;
        } else if (edtPassword.getText() == null || (edtPassword.getText().toString().isEmpty())||edtPassword.getText().toString().length()<6) {
                edtPassword.setError(getString(R.string.valid_password));
            return false;
        } else
            return true;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
