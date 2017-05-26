package com.app.yellowcap.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.yellowcap.R.id.btn_reject;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class QuotationFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.txt_jobNumber)
    AnyTextView txtJobNumber;
    @BindView(R.id.ll_job)
    LinearLayout llJob;
    @BindView(R.id.txt_JobTitle)
    AnyTextView txtJobTitle;
    @BindView(R.id.ll_CustomerName)
    LinearLayout llCustomerName;
    @BindView(R.id.txt_paymentMode)
    AnyTextView txtPaymentMode;
    @BindView(R.id.ll_paymentMode)
    LinearLayout llPaymentMode;
    @BindView(R.id.txt_userAddress)
    AnyTextView txtUserAddress;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.txt_quotationValid)
    AnyTextView txtQuotationValid;
    @BindView(R.id.ll_Address)
    LinearLayout llAddress;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;
    @BindView(R.id.txt_EstimatedQuote)
    AnyTextView txtEstimatedQuote;
    @BindView(R.id.ll_EstimatedQuote)
    LinearLayout llEstimatedQuote;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(btn_reject)
    Button btnReject;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    Unbinder unbinder;
    @BindView(R.id.txt_jobNumberHeading)
    AnyTextView txtJobNumberHeading;
    @BindView(R.id.txt_JobTitleHeading)
    AnyTextView txtJobTitleHeading;
    @BindView(R.id.txt_paymentModeHeading)
    AnyTextView txtPaymentModeHeading;
    @BindView(R.id.txt_userAddressHeading)
    AnyTextView txtUserAddressHeading;
    @BindView(R.id.txt_quotationValidHeading)
    AnyTextView txtQuotationValidHeading;
    @BindView(R.id.txt_EstimatedQuoteHeading)
    AnyTextView txtEstimatedQuoteHeading;


    public static QuotationFragment newInstance() {
        return new QuotationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_quotation;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListners();
        setTextStyle();
    }

    private void setListners() {
        btnReject.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
    }

    private void setTextStyle() {
        txtJobNumberHeading.setTypeface(null, Typeface.BOLD);
        txtJobTitleHeading.setTypeface(null, Typeface.BOLD);
        txtPaymentModeHeading.setTypeface(null, Typeface.BOLD);
        txtUserAddressHeading.setTypeface(null, Typeface.BOLD);
        txtQuotationValidHeading.setTypeface(null, Typeface.BOLD);
        txtEstimatedQuoteHeading.setTypeface(null, Typeface.BOLD);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reject:
                final DialogHelper cancelJobDialog = new DialogHelper(getDockActivity());
                cancelJobDialog.initCancelQuotationDialog(R.layout.cancle_quatation_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelJobDialog.hideDialog();
                        getMainActivity().popBackStackTillEntry(1);
                        getDockActivity().addDockableFragment(UserHomeFragment.newInstance(), "UserJobsFragment");
                    }
                });
                cancelJobDialog.showDialog();

                break;
            case R.id.btn_accept:
                getMainActivity().popBackStackTillEntry(1);
                getDockActivity().addDockableFragment(UserJobsFragment.newInstance(), "UserJobsFragment");
                break;

        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Electrical));

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

}
