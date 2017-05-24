package com.app.yellowcap.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.DatePickerHelper;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/23/2017.
 */

public class CreditCardFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.edt_cc_number)
    AnyEditTextView edtCcNumber;
    @BindView(R.id.edt_cc_expiredate)
    AnyTextView edtCcExpiredate;
    @BindView(R.id.edt_cc_cvv)
    AnyEditTextView edtCcCvv;
    @BindView(R.id.edt_cc_name)
    AnyEditTextView edtCcName;
    @BindView(R.id.btn_update)
    Button btnupdate;
    Unbinder unbinder;

    public static CreditCardFragment newInstance() {
        return new CreditCardFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_credit_card;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {
        edtCcExpiredate.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
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
            case R.id.btn_update:
                if (validate()){
                    getMainActivity().onBackPressed();
                }
                break;
            case R.id.edt_cc_expiredate:
                initDatePicker(edtCcExpiredate);
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Add Payment");
    }

    private void initDatePicker(final TextView textView){
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(datePickerHelper.getStringDate(year,month,dayOfMonth));
                    }
                },"PreferredDate");

        datePickerHelper.showDate();
    }
    private boolean validate() {
        if (edtCcNumber.getText().toString().isEmpty()){
            edtCcNumber.setError(getString(R.string.enter_cc_number));
        return false;
        }
        else if (edtCcExpiredate.getText().toString().isEmpty()){
            edtCcExpiredate.setError(getString(R.string.expiredate_error));
            return false;
        }
        else if (edtCcCvv.getText().toString().isEmpty()){
            edtCcCvv.setError(getString(R.string.cvv_error));
            return false;
        }
        else if (edtCcName.getText().toString().isEmpty()){
            edtCcName.setError(getString(R.string.cc_name_error));
            return false;
        }
        else {
            return true;
        }
    }
}
