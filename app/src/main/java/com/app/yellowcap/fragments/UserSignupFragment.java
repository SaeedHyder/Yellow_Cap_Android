package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.RegistrationResultEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.TokenUpdater;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/23/2017.
 */

public class UserSignupFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.edtname)
    AnyEditTextView edtname;
    @BindView(R.id.edtnumber)
    AnyEditTextView edtnumber;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;


    @BindView(R.id.btn_signup)
    Button btnsignup;
    PhoneNumberUtil phoneUtil;

    public static UserSignupFragment newInstance() {
        return new UserSignupFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user_signup;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistener();
        phoneUtil = PhoneNumberUtil.getInstance();
    }

    private void setlistener() {
        btnsignup.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                if (isvalidated()) {
                    if (isPhoneNumberValid())
                        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                            try {
                                loadingStarted();
                                registerUser(phoneUtil.format(phoneUtil.parse(edtnumber.getText().toString(), getString(R.string.uae_country_code)),
                                        PhoneNumberUtil.PhoneNumberFormat.E164));

                            } catch (Exception e) {

                            }


                        }

                }
                break;
        }
    }

    private boolean isPhoneNumberValid() {


        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(edtnumber.getText().toString(), getString(R.string.uae_country_code));
            if (phoneUtil.isValidNumber(number)) {
                return true;
            } else {
                edtnumber.setError(getString(R.string.enter_valid_number_error));
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            edtnumber.setError(getString(R.string.enter_valid_number_error));
            return false;

        }
    }

    private void registerUser(String number) {

        Call<ResponseWrapper<RegistrationResultEnt>> call = webService.registerUser(edtname.getText().toString()
                , edtEmail.getText().toString(),number + "");
        call.enqueue(new Callback<ResponseWrapper<RegistrationResultEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResultEnt>> call, Response<ResponseWrapper<RegistrationResultEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    prefHelper.putRegistrationResult(response.body().getResult());
                    prefHelper.setUserType("user");
                    prefHelper.setUsrId(String.valueOf(response.body().getResult().getId()));
                    prefHelper.setUsrName(response.body().getResult().getFullName());
                    prefHelper.setPhonenumber(response.body().getResult().getPhoneNo());
                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getUserId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                    getDockActivity().replaceDockableFragment(EntryCodeFragment.newInstance(), "EntryCodeFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResultEnt>> call, Throwable t) {
                loadingFinished();
                Log.e("UserSignupFragment", t.toString());

            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    private boolean isvalidated() {

        if (edtname.getText().toString().isEmpty()) {
            edtname.setError(getString(R.string.enter_name));
            return false;
        }  else if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.enter_email));
            return false;
        }
        else if(edtnumber.getText().toString().equals("") && edtnumber.getText().toString().isEmpty()){
            edtnumber.setError(getString(R.string.enter_number));
            return false;
        }
        else if (edtnumber.getText().toString().length() < 9 || edtnumber.getText().toString().length() > 10) {
            edtnumber.setError(getString(R.string.enter_valid_number_error));
            return false;
        } else {
            return true;
        }

    }
}
