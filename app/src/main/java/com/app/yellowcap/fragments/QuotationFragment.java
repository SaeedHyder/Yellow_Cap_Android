package com.app.yellowcap.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NotificationEnt;
import com.app.yellowcap.entities.RequestEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.yellowcap.R.id.btn_reject;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class QuotationFragment extends BaseFragment implements View.OnClickListener {

    public static String SCREENFROM = "SCREENFROM";
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
    private NotificationEnt QuotationData;

    public static QuotationFragment newInstance() {
        return new QuotationFragment();
    }

    public static QuotationFragment newInstance(NotificationEnt ent) {

        Bundle args = new Bundle();

        args.putString(SCREENFROM, new Gson().toJson(ent));
        QuotationFragment fragment = new QuotationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            SCREENFROM = getArguments().getString(SCREENFROM);
            if (SCREENFROM != null)
                QuotationData = new Gson().fromJson(SCREENFROM, NotificationEnt.class);

        }
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
        setData();
    }

    private void setData() {
        if (QuotationData != null) {
            txtJobNumber.setText(String.valueOf(QuotationData.getRequestDetail().getId()));
            txtJobTitle.setText(QuotationData.getRequestDetail().getServiceDetail().getTitle());
            txtPaymentMode.setText(QuotationData.getRequestDetail().getPaymentType());
            txtUserAddress.setText(QuotationData.getRequestDetail().getAddress() + " " + QuotationData.getRequestDetail().getFullAddress());
            txtEstimatedQuote.setText("Between AED " + QuotationData.getRequestDetail().getEstimateFrom()
                    + " to " + QuotationData.getRequestDetail().getEstimateTo());

            getMainActivity().titleBar.setSubHeading(QuotationData.getRequestDetail().getServiceDetail().getTitle());
            getMainActivity().titleBar.getTxtTitle().invalidate();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reject:
                final DialogHelper cancelJobDialog = new DialogHelper(getDockActivity());
                cancelJobDialog.initCancelQuotationDialog(R.layout.cancle_quatation_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                            if (cancelJobDialog.getEditTextView(R.id.ed_msg).getText().toString().trim().equals("")){
                                cancelJobDialog.getEditTextView(R.id.ed_msg).setError(getDockActivity().getResources().getString(R.string.enter_message));
                            }else{
                                cancelQuotation(cancelJobDialog);
                            }
                        }
                    }
                });
                cancelJobDialog.setCancelable(true);
                cancelJobDialog.showDialog();

                break;
            case R.id.btn_accept:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    getDockActivity().onLoadingStarted();
                    acceptQuotation();
                }
                break;


        }

    }

    private void cancelQuotation(final DialogHelper dialog) {
        Call<ResponseWrapper<RequestEnt>> call = webService.changeStatus(prefHelper.getUserId(), QuotationData.getRequestDetail().getId(),
                dialog.getEditText(R.id.ed_msg), AppConstants.CANCEL_QUOTATION);
        call.enqueue(new Callback<ResponseWrapper<RequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RequestEnt>> call, Response<ResponseWrapper<RequestEnt>> response) {
                getDockActivity().onLoadingFinished();
                dialog.hideDialog();
                if (response.body().getResponse().equals("2000")) {
                    getMainActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(), "UserJobsFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RequestEnt>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("UserSignupFragment", t.toString());
                // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void acceptQuotation() {
        Call<ResponseWrapper<RequestEnt>> call = webService.changeStatus(prefHelper.getUserId(), QuotationData.getRequestDetail().getId(),
                "", AppConstants.ACCEPT_QUOTATION);
        call.enqueue(new Callback<ResponseWrapper<RequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RequestEnt>> call, Response<ResponseWrapper<RequestEnt>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    getDockActivity().popBackStackTillEntry(1);
                    getDockActivity().replaceDockableFragment(UserJobsFragment.newInstance(), "UserJobsFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RequestEnt>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("UserSignupFragment", t.toString());
                //  UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        if (QuotationData != null)
            if (!prefHelper.isLanguageArabic())
                titleBar.setSubHeading(QuotationData.getRequestDetail().getServiceDetail().getTitle() + "");
            else {
                titleBar.setSubHeading(QuotationData.getRequestDetail().getServiceDetail().getArTitle() + "");
            }
        else
            titleBar.setSubHeading(getString(R.string.Quotation));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


}
