package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.LocationModel;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.google.android.gms.location.places.Place;
import com.jota.autocompletelocation.AutoCompleteLocation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on 5/24/2017.
 */

public class UserProfileFragment extends BaseFragment implements View.OnClickListener, AutoCompleteLocation.AutoCompleteLocationListener {
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.edtname)
    AnyEditTextView edtname;
    @BindView(R.id.edtnumber)
    AnyEditTextView edtemail;
    @BindView(R.id.edt_locationgps)
    AutoCompleteLocation edtLocationgps;
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.edt_locationspecific)
    AnyEditTextView edtLocationspecific;
    @BindView(R.id.btn_editcard)
    Button btnEditcard;
    @BindView(R.id.btn_submit)
    Button btnsubmit;
    Unbinder unbinder;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistener();
        edtname.setText("Khalid Howladar");
        edtemail.setText("khalidhowladar@sample.com");
        edtLocationgps.setText(getString(R.string.dummyAddress));
        edtLocationspecific.setText(getString(R.string.dummyAddress));
    }

    private void setlistener() {
        btnEditcard.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        CircularImageSharePop.setOnClickListener(this);
        imgGps.setOnClickListener(this);
        edtLocationgps.setAutoCompleteTextListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        getDockActivity().lockDrawer();
        titleBar.showBackButton();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

private void getLocation(AutoCompleteTextView textView){
    if (getMainActivity().statusCheck()) {
        LocationModel locationModel = getMainActivity().getMyCurrentLocation();
        if (locationModel != null)
            textView.setText(locationModel.getAddress());
        else {
            getLocation(edtLocationgps);
        }
    }
}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CircularImageSharePop:
                break;
            case R.id.img_gps:
               getLocation(edtLocationgps);
                break;
            case R.id.btn_editcard:
                getDockActivity().replaceDockableFragment(CreditCardFragment.newInstance(), "CreditCardFargment");
                break;
            case R.id.btn_submit:
                if (validate()) {
                    getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                }
                break;
        }
    }

    private boolean validate() {
        if (edtname.getText().toString().isEmpty()) {
            edtname.setError(getString(R.string.empty_name_error));
            return false;
        } else if (edtemail.getText().toString().isEmpty()) {
            edtname.setError(getString(R.string.empty_email_error));
            return false;
        } else if (edtLocationgps.getText().toString().isEmpty()) {
            edtLocationgps.setError(getString(R.string.address_empty_error));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onTextClear() {

    }

    @Override
    public void onItemSelected(Place selectedPlace) {

    }
}
