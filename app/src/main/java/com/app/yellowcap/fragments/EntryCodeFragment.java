package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.app.yellowcap.ui.views.PinEntryEditText;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/23/2017.
 */

public class EntryCodeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.btn_submit_code)
    Button btnSubmitCode;

    private Boolean isValidate = false;
    private String pinCode = "";

    public static EntryCodeFragment newInstance() {
        return new EntryCodeFragment();
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_entry_code;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistener();
       // insertPin();
        // initPinEnrty();
    }

    private void insertPin() {
        txtPinEntry.setText(prefHelper.getRegistrationResult().getCode());
    }

    private void setlistener() {
        btnSubmitCode.setOnClickListener(this);
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
            case R.id.btn_submit_code:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    loadingStarted();
                    verifyCode();
                }
                break;
        }
    }

    private void verifyCode() {

        if (!(txtPinEntry.getText().toString().equals("") && txtPinEntry.getText().toString().length() < 4)) {
            Call<ResponseWrapper<RegistrationResultEnt>> call = webService.verifyCode(prefHelper.getUserId(),
                    txtPinEntry.getText().toString());
            call.enqueue(new Callback<ResponseWrapper<RegistrationResultEnt>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<RegistrationResultEnt>> call, Response<ResponseWrapper<RegistrationResultEnt>> response) {
                   loadingFinished();
                    if (response.body().getResponse().equals("2000")) {
                        TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                                prefHelper.getUserId(),
                                AppConstants.Device_Type,
                                prefHelper.getFirebase_TOKEN());
                        prefHelper.setLoginStatus(true);
                        //getDockActivity().popBackStackTillEntry(0);
                        prefHelper.putRegistrationResult(response.body().getResult());
                        getDockActivity().replaceDockableFragment(TermAndConditionFragment.newInstance(), "Terms And conditon Fragment");
                    } else {
                        txtPinEntry.setText(null);
                        UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<RegistrationResultEnt>> call, Throwable t) {
                    loadingFinished();
                    Log.e("EntryCodeFragment", t.toString());
                   // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
                }
            });
        } else {
            loadingFinished();
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.valid_code_error));
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    private void initPinEnrty() {
        if (txtPinEntry != null) {
            txtPinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1234")) {
                        // txtPinEntry.setText(null);
                        isValidate = true;
                    } else {
                        txtPinEntry.setError(true);
                        isValidate = false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.incorrect_code));
                        txtPinEntry.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (txtPinEntry != null)
                                    txtPinEntry.setText(null);
                            }
                        }, 1000);
                    }
                }
            });
        }
    }
}
