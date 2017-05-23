package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
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

/**
 * Created by saeedhyder on 5/23/2017.
 */

public class NewJobDetail extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    @BindView(R.id.imageSlider)
    SliderLayout imageSlider;
    @BindView(R.id.pagerIndicator)
    PagerIndicator pagerIndicator;
    Unbinder unbinder;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setImageGallery();

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
                    .putString("extra", image+"");

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

        switch (position){


        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
