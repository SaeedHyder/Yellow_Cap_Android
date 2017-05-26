package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.TitleBar;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/23/2017.
 */

public class UserSignupFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.edtname)
    AnyEditTextView edtname;
    @BindView(R.id.edtnumber)
    AnyEditTextView edtnumber;



    @BindView(R.id.btn_signup)
    Button btnsignup;
    Unbinder unbinder;

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
    }

    private void setlistener() {
        btnsignup.setOnClickListener(this);
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
            case R.id.btn_signup:
                if (isvalidated()){
                    getDockActivity().addDockableFragment(EntryCodeFragment.newInstance(),"EntryCodeFragment");
                }
                break;
        }
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    private boolean isvalidated() {
        if (edtname.getText().toString().isEmpty()){
            edtname.setError(getString(R.string.enter_name));
            return false;
        }
       else if (edtnumber.getText().toString().isEmpty()){
            edtnumber.setError(getString(R.string.enter_number));
            return false;
        }
        else if (edtnumber.getText().toString().length()<10||edtnumber.getText().toString().length()>12){
            edtnumber.setError(getString(R.string.enter_valid_number_error));
            return false;
        }
        else {
            return true;
        }
    }
}
