package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.MainActivity;
import com.app.yellowcap.entities.LocationModel;
import com.app.yellowcap.entities.RegistrationResultEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.CameraHelper;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.google.android.gms.location.places.Place;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.jota.autocompletelocation.AutoCompleteLocation;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/24/2017.
 */

public class UserProfileFragment extends BaseFragment implements View.OnClickListener, AutoCompleteLocation.AutoCompleteLocationListener, MainActivity.ImageSetter {
    public File profilePic;
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
    @BindView(R.id.edtPhoneNo)
    AnyEditTextView edtPhoneNo;
    private PhoneNumberUtil phoneUtil;


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
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistener();
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            getUserProfile();
        }
        phoneUtil = PhoneNumberUtil.getInstance();
        getMainActivity().setImageSetter(this);
    }

    private void getUserProfile() {
        Call<ResponseWrapper<RegistrationResultEnt>> call = webService.getUserProfile(prefHelper.getUserId());
        call.enqueue(new Callback<ResponseWrapper<RegistrationResultEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResultEnt>> call, Response<ResponseWrapper<RegistrationResultEnt>> response) {
                if (response.body().getResponse().equals("2000")) {
                    setProfileData(response.body().getResult());
                } else {

                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResultEnt>> call, Throwable t) {
                Log.e("EntryCodeFragment", t.toString());
                // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void setProfileData(RegistrationResultEnt result) {
        prefHelper.putRegistrationResult(result);
        Picasso.with(getDockActivity()).load(result.getProfileImage()).
                placeholder(R.drawable.profileimage).into(CircularImageSharePop);
        edtname.setText(result.getFullName());
        edtemail.setText(result.getEmail());
        edtPhoneNo.setText(getNationalPhoneNumber(result.getPhoneNo() + "", PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
        edtLocationgps.setText(result.getAddress());
        edtLocationspecific.setText(result.getFullAddress());

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


    private void getLocation(AutoCompleteTextView textView) {
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
                CameraHelper.uploadMedia(getMainActivity());
                break;
            case R.id.img_gps:
                getLocation(edtLocationgps);
                break;
            case R.id.btn_editcard:
                UIHelper.showShortToastInCenter(getDockActivity(), "Will be Implemented Later");
                //getDockActivity().replaceDockableFragment(CreditCardFragment.newInstance(), "CreditCardFargment");
                break;
            case R.id.btn_submit:
                if (validate()) {
                    if (isPhoneNumberValid())
                        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                            loadingStarted();
                            try {
                                updateProfile(getNationalPhoneNumber(edtPhoneNo.getText().toString(),
                                        PhoneNumberUtil.PhoneNumberFormat.E164));
                            } catch (Exception e) {
                                updateProfile("");
                            }

                        }
                }
                break;
        }
    }

    private void updateProfile(String format) {
        MultipartBody.Part filePart;
        if (profilePic != null) {
            filePart = MultipartBody.Part.createFormData("profile_picture",
                    profilePic.getName(), RequestBody.create(MediaType.parse("image/*"), profilePic));
        } else {
            filePart = MultipartBody.Part.createFormData("profile_picture", "",
                    RequestBody.create(MediaType.parse("*/*"), ""));
        }
        Call<ResponseWrapper<RegistrationResultEnt>> call = webService.updateProfile(
                RequestBody.create(MediaType.parse("text/plain"), prefHelper.getUserId()),
                RequestBody.create(MediaType.parse("text/plain"), edtname.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtemail.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtLocationgps.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtLocationspecific.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), format),
                filePart);
        call.enqueue(new Callback<ResponseWrapper<RegistrationResultEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RegistrationResultEnt>> call, Response<ResponseWrapper<RegistrationResultEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    prefHelper.putRegistrationResult(response.body().getResult());
                    getMainActivity().refreshSideMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RegistrationResultEnt>> call, Throwable t) {
                loadingFinished();
                Log.e("UserProfileDFragment", t.toString());
                // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private boolean validate() {
        if (edtname.getText().toString().isEmpty()) {
            edtname.setError(getString(R.string.empty_name_error));
            return false;
        } else if (edtemail.getText().toString().isEmpty()) {
            edtemail.setError(getString(R.string.empty_email_error));
            return false;
        } else if (edtLocationgps.getText().toString().isEmpty()) {
            edtLocationgps.setError(getString(R.string.address_empty_error));
            return false;
        } else if (edtPhoneNo.getText().toString().length() < 9 || edtPhoneNo.getText().toString().length() > 10) {
            edtPhoneNo.setError(getString(R.string.enter_valid_number_error));
            return false;
        } else {
            return true;
        }
    }

    private boolean isPhoneNumberValid() {


        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(edtPhoneNo.getText().toString(), getString(R.string.uae_country_code));
            if (phoneUtil.isValidNumber(number)) {
                return true;
            } else {
                edtPhoneNo.setError(getString(R.string.enter_valid_number_error));
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            edtPhoneNo.setError(getString(R.string.enter_valid_number_error));
            return false;

        }
    }

    private String getNationalPhoneNumber(String Phonenumber, PhoneNumberUtil.PhoneNumberFormat numberFormat) {


        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(Phonenumber, getString(R.string.uae_country_code));
            if (phoneUtil.isValidNumber(number)) {
                return phoneUtil.format(number,
                        numberFormat).replaceAll("\\s","");
            } else {
                //edtPhoneNo.setError(getString(R.string.enter_valid_number_error));
                return "";
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            //edtPhoneNo.setError(getString(R.string.enter_valid_number_error));
            return "";

        }
    }

    @Override
    public void onTextClear() {

    }

    @Override
    public void onItemSelected(Place selectedPlace) {

    }

    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {
            profilePic = new File(imagePath);
            ImageLoader.getInstance().displayImage(
                    "file:///" + imagePath, CircularImageSharePop);
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath) {

    }

}
