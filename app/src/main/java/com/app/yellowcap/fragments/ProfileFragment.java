package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.TechProfileEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.edtFirstName)
    AnyTextView edtFirstName;
    @BindView(R.id.edtLastName)
    AnyTextView edtLastName;
    @BindView(R.id.edtEmailId)
    AnyTextView edtEmailId;
    @BindView(R.id.edtPhoneNo)
    AnyTextView edtPhoneNo;
    @BindView(R.id.edtRegistrationType)
    AnyTextView edtRegistrationType;
    @BindView(R.id.edtRegDate)
    AnyTextView edtRegDate;
    @BindView(R.id.ll_profileItems)
    LinearLayout llProfileItems;
    @BindView(R.id.ll_profileDetail)
    LinearLayout llProfileDetail;
    Unbinder unbinder;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    ImageLoader imageLoader;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_technicianprofile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageLoader=ImageLoader.getInstance();
        mainFrame.setVisibility(View.GONE);
        getTechProfile();
    }

    private void getTechProfile() {
        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<TechProfileEnt>> call = webService.techProfile(Integer.parseInt(prefHelper.getUserId()));

        call.enqueue(new Callback<ResponseWrapper<TechProfileEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<TechProfileEnt>> call, Response<ResponseWrapper<TechProfileEnt>> response) {
                getDockActivity().onLoadingFinished();
                mainFrame.setVisibility(View.VISIBLE);
                if (response.body().getResponse().equals("2000")) {
                    setProfileData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<TechProfileEnt>> call, Throwable t) {
                Log.e("EntryCodeFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });

    }

    private void setProfileData(TechProfileEnt result) {

        imageLoader.displayImage(result.getProfile_image(),CircularImageSharePop);
        edtFirstName.setText(result.getFirst_name());
        edtLastName.setText(result.getLast_name());
        edtEmailId.setText(result.getEmail());
        edtPhoneNo.setText(result.getPhone_no());
        edtRegistrationType.setText(result.getRegistration_type());
        edtRegDate.setText(result.getRegistration_date());

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_technicianprofile;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.my_profile));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
