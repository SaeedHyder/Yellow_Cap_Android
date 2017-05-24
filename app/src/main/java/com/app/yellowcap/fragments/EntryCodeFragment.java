package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.PinEntryEditText;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/23/2017.
 */

public class EntryCodeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.btn_submit_code)
    Button btnSubmitCode;
    private Boolean isValidate = false;
    Unbinder unbinder;

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
        initPinEnrty();
    }

    private void setlistener() {
        btnSubmitCode.setOnClickListener(this);
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
            case R.id.btn_submit_code:
                if (isValidate){
                    getDockActivity().addDockableFragment(TermAndConditionFragment.newInstance(),"Terms And conditon Fragment");
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.incorrect_code));
                }
                break;
        }
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }
    private void initPinEnrty() {
        txtPinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                if (str.toString().equals("1234")) {
                   // txtPinEntry.setText(null);
                    isValidate = true;
                } else {
                    txtPinEntry.setError(true);
                    isValidate = false;

                    txtPinEntry.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtPinEntry.setText(null);
                        }
                    }, 1000);
                }
            }
        });
    }
}
