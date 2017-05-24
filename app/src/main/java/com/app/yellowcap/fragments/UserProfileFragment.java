package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyEditTextView;
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
    AnyEditTextView edtnumber;
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
    }

    private void setlistener() {
        btnEditcard.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        CircularImageSharePop.setOnClickListener(this);
        imgGps.setOnClickListener(this);
        edtLocationgps.setAutoCompleteTextListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CircularImageSharePop:
                break;
            case R.id.img_gps:
                if (getMainActivity().statusCheck()){
                    edtLocationgps.setText(getMainActivity().getMyCurrentLocation().getAddress());
                }
                break;
            case R.id.btn_editcard:
                getDockActivity().addDockableFragment(CreditCardFragment.newInstance(),"CreditCardFargment");
                break;
            case R.id.btn_submit:
                UIHelper.showShortToastInCenter(getDockActivity(),"Profile Update SuccessFull");
                break;
        }
    }

    @Override
    public void onTextClear() {

    }

    @Override
    public void onItemSelected(Place selectedPlace) {

    }
}
