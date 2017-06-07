package com.app.yellowcap.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.JobRequestEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.DateHelper;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.helpers.TimePickerHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.yellowcap.R.id.btn_accept;

/**
 * Created by saeedhyder on 5/23/2017.
 */

public class NewJobDetail extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.imageSlider)
    SliderLayout imageSlider;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    Unbinder unbinder;
    @BindView(R.id.viewPager)
    LinearLayout viewPager;
    @BindView(R.id.txt_jobNameHeading)
    AnyTextView txtJobNameHeading;
    @BindView(R.id.txt_jobName)
    AnyTextView txtJobName;
    @BindView(R.id.ll_job)
    LinearLayout llJob;
    @BindView(R.id.txt_customerNameHeading)
    AnyTextView txtCustomerNameHeading;
    @BindView(R.id.txt_customerName)
    AnyTextView txtCustomerName;
    @BindView(R.id.ll_CustomerName)
    LinearLayout llCustomerName;
    @BindView(R.id.txt_estimatedQuoteHeading)
    AnyTextView txtEstimatedQuoteHeading;
    @BindView(R.id.txt_estimatedQuote)
    AnyTextView txtEstimatedQuote;
    @BindView(R.id.ll_EstimatedQuote)
    LinearLayout llEstimatedQuote;
    @BindView(R.id.txt_serviceHeading)
    AnyTextView txtServiceHeading;
    @BindView(R.id.txt_service)
    AnyTextView txtService;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.txt_AddressHeading)
    AnyTextView txtAddressHeading;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.ll_Address)
    LinearLayout llAddress;
    @BindView(R.id.txt_descriptionHeading)
    AnyTextView txtDescriptionHeading;
    @BindView(R.id.txt_description)
    AnyTextView txtDescription;
    @BindView(R.id.ll_description)
    LinearLayout llDescription;
    @BindView(R.id.txt_preferred_dateTimeHeading)
    AnyTextView txtPreferredDateTimeHeading;
    @BindView(R.id.txt_preferred_dateTime)
    AnyTextView txtPreferredDateTime;
    @BindView(R.id.ll_preferred_dateTime)
    LinearLayout llPreferredDateTime;
    @BindView(R.id.ll_JobDetail)
    LinearLayout llJobDetail;
    @BindView(btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    private AnyTextView arriveTime;
    private AnyTextView completeTime;

    public static NewJobDetail newInstance() {
        return new NewJobDetail();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newjobs_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_newjobs_detail;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtPreferredDateTime.setText("22 Feb 2017"+"    "+"  02:30 PM");
        setListners();
        setImageGallery();
        setTextStyle();

    }

    private void setListners() {
        btnReject.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
    }

    private void setTextStyle() {
        txtJobNameHeading.setTypeface(null, Typeface.BOLD);
        txtCustomerNameHeading.setTypeface(null, Typeface.BOLD);
        txtEstimatedQuoteHeading.setTypeface(null, Typeface.BOLD);
        txtServiceHeading.setTypeface(null, Typeface.BOLD);
        txtAddressHeading.setTypeface(null, Typeface.BOLD);
        txtDescriptionHeading.setTypeface(null, Typeface.BOLD);
        txtPreferredDateTimeHeading.setTypeface(null, Typeface.BOLD);
    }

    private void setImageGallery() {

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(0, R.drawable.plumber1);
        arrayList.add(1, R.drawable.plumber2);
        arrayList.add(2, R.drawable.plumber3);
        arrayList.add(3, R.drawable.plumber4);
        arrayList.add(4, R.drawable.plumber2);

        for (Integer image : arrayList) {
            DefaultSliderView textSliderView = new DefaultSliderView(getDockActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(image)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", image + "");

            imageSlider.addSlider(textSliderView);
        }

        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.addOnPageChangeListener(this);
        imageSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        imageSlider.stopAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {


        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initTimePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final TimePickerHelper timePicker = new TimePickerHelper();
        timePicker.initTimeDialog(getDockActivity(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                try {
                    if (arriveTime != null) {
                        if (arriveTime.getId() == textView.getId()) {
                            Date date = new Date();
                            if (!DateHelper.isTimeAfter(date.getHours(), date.getMinutes(), hourOfDay, minute)) {
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.less_time_error));
                            } else {
                                textView.setText(timePicker.getTime(hourOfDay, minute));
                            }
                        }
                     else {
                        if (!arriveTime.getText().toString().isEmpty()) {
                            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                                Date date = parseFormat.parse(arriveTime.getText().toString());
                                if (!DateHelper.isTimeAfter(date.getHours(), date.getMinutes(), hourOfDay, minute)) {
                                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.time_error));
                                } else {
                                    textView.setText(timePicker.getTime(hourOfDay, minute));
                                }
                        } else {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.arrive_time_error));
                        }
                    }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, DateFormat.is24HourFormat(getMainActivity()));
        timePicker.showTime();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case btn_accept:

                final DialogHelper JobDetailDialog = new DialogHelper(getDockActivity());
              Dialog dialog = JobDetailDialog.initJobDetailDialog(R.layout.new_job_detail_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arriveTime != null && completeTime != null) {
                            if (arriveTime.getText().toString().isEmpty()) {
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.arrive_time_error));

                            } else if (completeTime.getText().toString().isEmpty()) {
                                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.complete_time_error));
                            } else {
                                //jobAccept(arriveTime,completeTime,JobDetailDialog);

                            }
                        }



                    }
                }, "Sink Broken", "Mohammad Ali", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        initTimePicker(arriveTime);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        initTimePicker(completeTime);
                    }
                });

                arriveTime = JobDetailDialog.getTimeTextview(R.id.txt_arrval_time);
                completeTime = JobDetailDialog.getTimeTextview(R.id.txt_complete_time);
                JobDetailDialog.setCancelable(true);
                JobDetailDialog.showDialog();

                break;

            case R.id.btn_reject:

                final DialogHelper RefusalDialog = new DialogHelper(getDockActivity());
                RefusalDialog.initJobRefusalDialog(R.layout.job_refusal_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jobReject(RefusalDialog.getEditText(R.id.ed_msg),RefusalDialog);

                        //RefusalDialog.hideDialog();
                    }
                });
                RefusalDialog.setCancelable(false);
                RefusalDialog.showDialog();

                break;


        }

    }

    private void jobReject(String reason, final DialogHelper refusalDialog) {

        if(!reason.equals("")){
        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<JobRequestEnt>> call= webService.rejectJob("2", prefHelper.getUserId()
                ,"43", AppConstants.TECH_REJECT_JOB,reason);
        call.enqueue(new Callback<ResponseWrapper<JobRequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<JobRequestEnt>> call, Response<ResponseWrapper<JobRequestEnt>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    refusalDialog.hideDialog();
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<JobRequestEnt>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });}
        else
        {
            UIHelper.showShortToastInCenter(getDockActivity(),"Enter the Reason");
        }

    }

    private void jobAccept(AnyTextView arriveTime, AnyTextView completeTime, final DialogHelper jobDetailDialog) {
        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<JobRequestEnt>> call= webService.acceptJob("2", prefHelper.getUserId(),
                "43",AppConstants.TECH_ACCEPT_JOB,arriveTime.getText().toString(),completeTime.getText().toString());
        call.enqueue(new Callback<ResponseWrapper<JobRequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<JobRequestEnt>> call, Response<ResponseWrapper<JobRequestEnt>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    jobDetailDialog.hideDialog();
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<JobRequestEnt>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.plumbing));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
