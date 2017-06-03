package com.app.yellowcap.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.MainActivity;
import com.app.yellowcap.entities.LocationModel;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.CameraHelper;
import com.app.yellowcap.helpers.DateHelper;
import com.app.yellowcap.helpers.DatePickerHelper;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.helpers.TimePickerHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.onDeleteImage;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.adapters.RecyclerViewAdapterImages;
import com.app.yellowcap.ui.viewbinder.SelectedJobBinder;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;
import com.jota.autocompletelocation.AutoCompleteLocation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/22/2017.
 */

public class RequestServiceFragment extends BaseFragment implements View.OnClickListener, MainActivity.ImageSetter, onDeleteImage, AutoCompleteLocation.AutoCompleteLocationListener {
    public static String TYPE = "TYPE";
    @BindView(R.id.spn_jobtype)
    Spinner spnJobtype;
    @BindView(R.id.spn_jobdescription)
    Spinner spnJobdescription;
    @BindView(R.id.listView_jobselected)
    ListView listViewJobselected;
    @BindView(R.id.edt_locationgps)
    AutoCompleteLocation edtLocationgps;
    @BindView(R.id.edt_locationspecific)
    AnyEditTextView edtLocationspecific;
    @BindView(R.id.btn_preferreddate)
    AnyTextView btnPreferreddate;
    @BindView(R.id.btn_preferredtime)
    AnyTextView btnPreferredtime;
    @BindView(R.id.btn_addimage)
    LinearLayout btnAddimage;
    @BindView(R.id.addimages)
    RecyclerView addimages;
    @BindView(R.id.btn_cc)
    Button btnCc;
    @BindView(R.id.btn_cod)
    Button btnCod;
    @BindView(R.id.btn_request)
    Button btnRequest;
    Unbinder unbinder;
    ArrayList<String> images = new ArrayList<>();
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.img_cc_check)
    ImageView imgCcCheck;
    @BindView(R.id.img_cod_check)
    ImageView imgCodCheck;
    private RecyclerViewAdapterImages mAdapter;
    private ArrayList<ServiceEnt> selectedJobs;
    private ArrayListAdapter<ServiceEnt> selectedJobsadapter;
    private ServiceEnt jobtype;
    private String paymentType = "";
    private ServiceEnt homeSelectedService;
    private ArrayList<ServiceEnt> jobcollection;
    private ArrayList<ServiceEnt> jobChildcollection;


    private String preferreddate = "preferreddate";
    private String preferredtime = "preferredtime";

    public static RequestServiceFragment newInstance() {
        return new RequestServiceFragment();
    }

    public static RequestServiceFragment newInstance(ServiceEnt type) {
        Bundle args = new Bundle();
        args.putString(TYPE, new Gson().toJson(type));
        RequestServiceFragment fragment = new RequestServiceFragment();
        fragment.setArguments(args);
        return new RequestServiceFragment();
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = getArguments().getString(TYPE);
            String jsonString = getArguments().getString(TYPE);
            if (jsonString != null)
                homeSelectedService = new Gson().fromJson(jsonString, ServiceEnt.class);
        }
        selectedJobsadapter = new ArrayListAdapter<ServiceEnt>(getDockActivity(), new SelectedJobBinder(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_service, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    private void initListViewAdapter() {
      /*  selectedJobs = new ArrayList<>();
        selectedJobs.add("Total Electricity Failure/Break down");
        selectedJobs.add("No Electricity in some Room");
        selectedJobs.add("Repair Ac");
        selectedJobs.add("Ac Not Working");*/

        bindSelectedJobview(selectedJobs);
    }

    private void bindSelectedJobview(ArrayList<ServiceEnt> jobs) {
        selectedJobsadapter.clearList();
        listViewJobselected.setAdapter(selectedJobsadapter);
        selectedJobsadapter.addAll(jobs);
        selectedJobsadapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listViewJobselected);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        setDataInAdapter(images);
        initJobTypeSpinner(TYPE);


    }

    private void setDataInAdapter(ArrayList<String> ImageArray) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false);
        addimages.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapterImages(ImageArray, getDockActivity(), this);
       /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());*/
        addimages.setLayoutManager(layoutManager);
        addimages.setItemAnimator(new DefaultItemAnimator());
        addimages.setAdapter(mAdapter);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_request_service;
    }

    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }*/

    private void initJobTypeSpinner(String type) {
        jobcollection = new ArrayList<>();
        Call<ResponseWrapper<ArrayList<ServiceEnt>>> call = webService.getHomeServices();
        call.enqueue(new Callback<ResponseWrapper<ArrayList<ServiceEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Response<ResponseWrapper<ArrayList<ServiceEnt>>> response) {
                if (response.body().getResponse().equals("2000")) {
                    jobcollection.clear();
                    jobcollection.addAll(response.body().getResult());
                    setJobtypeSpinner();

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Throwable t) {
                Log.e("TermAndCondition", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });


    }

    private void setJobtypeSpinner() {
        final ArrayList<String> jobtypearraylist = new ArrayList<String>();
        for (ServiceEnt item : jobcollection
                ) {
            jobtypearraylist.add(item.getTitle());
        }


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobtypearraylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobtype.setAdapter(categoryAdapter);
        if (homeSelectedService != null && jobcollection.size() > 0) {
            if (jobcollection.contains(homeSelectedService)) {
                spnJobtype.setSelection(jobcollection.indexOf(homeSelectedService));
                jobtype = jobcollection.get(jobcollection.indexOf(homeSelectedService));
                initJobDescriptionSpinner(jobtype);
            } else {
                spnJobtype.setSelection(0);
                jobtype = jobcollection.get(0);
                initJobDescriptionSpinner(jobtype);
            }
        }

        spnJobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobtype = jobcollection.get(position);
                initJobDescriptionSpinner(jobtype);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                initJobDescriptionSpinner(jobtype);
            }
        });
    }

    private void initJobDescriptionSpinner(ServiceEnt selectedService) {
        if (selectedService != null) {
            jobChildcollection = new ArrayList<>();
            Call<ResponseWrapper<ArrayList<ServiceEnt>>> call = webService.getchildServices(String.valueOf(selectedService.getId()));
            call.enqueue(new Callback<ResponseWrapper<ArrayList<ServiceEnt>>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Response<ResponseWrapper<ArrayList<ServiceEnt>>> response) {
                    if (response.body().getResponse().equals("2000")) {
                        jobChildcollection.clear();
                        jobChildcollection.addAll(response.body().getResult());
                        setJobDescriptionSpinner();
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<ArrayList<ServiceEnt>>> call, Throwable t) {
                    Log.e("TermAndCondition", t.toString());
                    UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
                }
            });

        }
    }

    private void setJobDescriptionSpinner() {
        final ArrayList<String> jobdescriptionarraylist = new ArrayList<String>();
        for (ServiceEnt item : jobChildcollection
                ) {
            jobdescriptionarraylist.add(item.getTitle());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobdescriptionarraylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobdescription.setAdapter(categoryAdapter);
        spnJobdescription.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           //     selectedJobs.add(jobChildcollection.get(position));
               // bindSelectedJobview(selectedJobs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setListener() {
        btnPreferreddate.setOnClickListener(this);
        btnPreferredtime.setOnClickListener(this);
        btnAddimage.setOnClickListener(this);
        btnCc.setOnClickListener(this);
        btnCod.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
        getMainActivity().setImageSetter(this);
        edtLocationgps.setAutoCompleteTextListener(this);
        imgGps.setOnClickListener(this);
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
            case R.id.btn_preferreddate:
                initDatePicker(btnPreferreddate);
                break;
            case R.id.btn_preferredtime:
                initTimePicker(btnPreferredtime);
                break;
            case R.id.btn_addimage:
                CameraHelper.uploadMedia(getMainActivity());
                break;
            case R.id.btn_cc:
                paymentType = "cc";
                setCCCheck();
                CreditCardFragment fragment = CreditCardFragment.newInstance();
                getDockActivity().addDockableFragment(fragment, "CreditCardFragment");
                break;

            case R.id.btn_cod:
                paymentType = "cod";
                setCODCheck();
                break;
            case R.id.btn_request:
                if (validate()) {
                    final DialogHelper RequestSend = new DialogHelper(getDockActivity());
                    RequestSend.initRequestSendDialog(R.layout.request_send_dialog, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RequestSend.hideDialog();
                            getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                        }
                    });
                    RequestSend.setCancelable(false);
                    RequestSend.showDialog();
                }

                break;
            case R.id.img_gps:
               /* if (getMainActivity().statusCheck()) {
                    LocationModel locationModel = getMainActivity().getMyCurrentLocation();
                    if (locationModel != null)
                        edtLocationgps.setText(locationModel.getAddress());
                }*/
                getLocation(edtLocationgps);

                break;
        }
    }

    private void setCCCheck() {

        imgCcCheck.invalidate();
        imgCcCheck.setVisibility(View.VISIBLE);
        imgCodCheck.setVisibility(View.GONE);
    }

    private void setCODCheck() {
        imgCcCheck.setVisibility(View.GONE);
        imgCodCheck.setVisibility(View.VISIBLE);
    }

    private boolean validate() {
        if (edtLocationgps.getText().toString().isEmpty()) {
            edtLocationgps.setError("Enter Address");
            return false;
        } else if (btnPreferreddate.getText().toString().isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Date");
            return false;
        } else if (btnPreferredtime.getText().toString().isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Time");
            return false;
        } else if (paymentType.isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Payment Type");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.request_setvice));
    }

    private void initTimePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final TimePickerHelper timePicker = new TimePickerHelper();
        timePicker.initTimeDialog(getDockActivity(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Date date = new Date();
                if (!DateHelper.isTimeAfter(date.getHours(), date.getMinutes(), hourOfDay, minute)) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.less_time_error));
                } else {
                    textView.setText(timePicker.getTime(hourOfDay, minute));
                }

            }
        }, DateFormat.is24HourFormat(getMainActivity()));

        timePicker.showTime();
    }

    private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
                        Date dateSpecified = c.getTime();
                        if (dateSpecified.before(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.date_before_error));
                        } else {
                            textView.setText(datePickerHelper.getStringDate(year, month, dayOfMonth));
                        }

                    }
                }, "PreferredDate");

        datePickerHelper.showDate();
    }

    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {
            images.add(imagePath);
            mAdapter.notifyItemInserted(images.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(preferreddate, btnPreferreddate.getText().toString());
        outState.putString(preferredtime, btnPreferredtime.getText().toString());

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            btnPreferredtime.setText(savedInstanceState.getString(preferredtime));
            btnPreferreddate.setText(savedInstanceState.getString(preferreddate));
        }

    }

    private void refreshListview() {
        selectedJobsadapter.clearList();
        listViewJobselected.setAdapter(selectedJobsadapter);
        selectedJobsadapter.addAll(selectedJobs);
        selectedJobsadapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listViewJobselected);
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath) {

    }

    @Override
    public void onDelete(int position) {
        images.remove(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnDeleteJobs(int position) {
       /* selectedJobs.remove(position);*/
        // refreshListview();
    }

    @Override
    public void onTextClear() {

    }

    @Override
    public void onItemSelected(Place selectedPlace) {
        System.out.println(selectedPlace);
    }


}
