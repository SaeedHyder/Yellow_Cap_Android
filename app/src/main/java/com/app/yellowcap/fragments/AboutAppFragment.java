package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.StaticPageEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/24/2017.
 */

public class AboutAppFragment extends BaseFragment {
    public static AboutAppFragment newInstance(){
        return new AboutAppFragment();
    }
    @BindView(R.id.scroll_tv1)
    TextView scrollTv1;


    @Override
    protected int getLayout() {
        return R.layout.fragment_about_app;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindTextview();
    }

    private void bindTextview() {
        Call<ResponseWrapper<StaticPageEnt>> call = webService.getTermandAbout(prefHelper.getUserId(),"about");
        call.enqueue(new Callback<ResponseWrapper<StaticPageEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<StaticPageEnt>> call, Response<ResponseWrapper<StaticPageEnt>> response) {
                if (response.body().getResponse().equals("2000")) {
                    settitle(response.body().getResult().getBody());
                }
                else{
                    UIHelper.showShortToastInCenter(getDockActivity(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<StaticPageEnt>> call, Throwable t) {
                Log.e("TermAndCondition", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void settitle(String response) {
        getMainActivity().titleBar.setSubHeading(getString(R.string.about_app));
        getMainActivity().titleBar.invalidate();
        scrollTv1.setText(response);
        scrollTv1.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.about_app));
        titleBar.showBackButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
       ButterKnife.bind(this, rootView);
        return rootView;
    }


}
