package com.app.yellowcap.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.yellowcap.R.id.txt;

/**
 * Created by saeedhyder on 5/23/2017.
 */

public class NewJobDetail extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener ,View.OnClickListener{

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
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;

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

        setListners();
        setImageGallery();
        setTextStyle();

    }

    private void setListners() {
        btnReject.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_reject:

                final DialogHelper RefusalDialog = new DialogHelper(getDockActivity());
                RefusalDialog.initJobRefusalDialog(R.layout.job_refusal_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RefusalDialog.hideDialog();
                    }
                });
                RefusalDialog.showDialog();

                break;


        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().releaseDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Plumbing");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
