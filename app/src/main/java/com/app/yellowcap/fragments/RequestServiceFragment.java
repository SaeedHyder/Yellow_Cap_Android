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
import com.app.yellowcap.entities.ImageDetailEnt;
import com.app.yellowcap.entities.LocationModel;
import com.app.yellowcap.entities.RequestEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.ServiceChildEnt;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.entities.UserInProgressEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.global.AppConstants;
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

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/22/2017.
 */

public class RequestServiceFragment extends BaseFragment implements View.OnClickListener, MainActivity.ImageSetter, onDeleteImage, AutoCompleteLocation.AutoCompleteLocationListener {
    public static String TYPE = "TYPE";
    public static String SCREENFROM = "SCREENFROM";
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
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.img_cc_check)
    ImageView imgCcCheck;
    @BindView(R.id.img_cod_check)
    ImageView imgCodCheck;
    Unbinder unbinder;
    @BindView(R.id.edt_addtional_job)
    AnyEditTextView edtAddtionalJob;
    @BindView(R.id.txt_job_posted)
    AnyTextView txtJobPosted;
    private ArrayList<String> images = new ArrayList<>();
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
    private String predate = "", preTime = "";
    private UserInProgressEnt previousRequestData;
    private Boolean isEdit = false;

    public static RequestServiceFragment newInstance() {
        return new RequestServiceFragment();
    }

    public static RequestServiceFragment newInstance(ServiceEnt type, UserInProgressEnt editData) {
        Bundle args = new Bundle();
        args.putString(TYPE, new Gson().toJson(type));
        args.putString(SCREENFROM, new Gson().toJson(editData));
        RequestServiceFragment fragment = new RequestServiceFragment();
        fragment.setArguments(args);
        return fragment;
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
            SCREENFROM = getArguments().getString(SCREENFROM);
            if (TYPE != null)
                homeSelectedService = new Gson().fromJson(TYPE, ServiceEnt.class);
            if (SCREENFROM != null)
                previousRequestData = new Gson().fromJson(SCREENFROM, UserInProgressEnt.class);

        }
        selectedJobsadapter = new ArrayListAdapter<ServiceEnt>(getDockActivity(), new SelectedJobBinder(this));
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

    @Override
    protected int getLayout() {
        return R.layout.fragment_request_service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_service, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedJobs = new ArrayList<>();
        setListener();
        if (previousRequestData != null) {
            isEdit = true;
            editCurrentService();
        } else {
            setDataInAdapter(images);
            initJobTypeSpinner(TYPE);
            txtJobPosted.setText(getString(R.string.job_posted_label) +
                    new SimpleDateFormat("dd-MM-yy").format(Calendar.getInstance().getTime()));
        }


    }

    private void initListViewAdapter() {
      /*
        selectedJobs.add("Total Electricity Failure/Break down");
        selectedJobs.add("No Electricity in some Room");
        selectedJobs.add("Repair Ac");
        selectedJobs.add("Ac Not Working");*/

        bindSelectedJobview(selectedJobs);
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
                if (images.size() > 4) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.imagelimit_error));
                } else {
                    CameraHelper.uploadMedia(getMainActivity());
                }

                break;
            case R.id.btn_cc:
                paymentType = "credit";
                setCCCheck();
                CreditCardFragment fragment = CreditCardFragment.newInstance();
                getDockActivity().addDockableFragment(fragment, "CreditCardFragment");
                break;

            case R.id.btn_cod:
                paymentType = "cash";
                setCODCheck();
                break;
            case R.id.btn_request:
                if (validate()) {
                    loadingStarted();
                    CreateRequest();
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
            if (jobtypearraylist.contains(homeSelectedService.getTitle())) {
                spnJobtype.setSelection(jobtypearraylist.indexOf(homeSelectedService.getTitle()));
                jobtype = jobcollection.get(jobtypearraylist.indexOf(homeSelectedService.getTitle()));
                initJobDescriptionSpinner(jobtype);
            } else {
                spnJobtype.setSelection(0);
                jobtype = jobcollection.get(0);
                initJobDescriptionSpinner(jobtype);
            }
        }
        spnJobtype.setEnabled(false);
        spnJobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              /*  jobtype = jobcollection.get(position);
                initJobDescriptionSpinner(jobtype);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //   initJobDescriptionSpinner(jobtype);
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

    private void editCurrentService() {
        List<ImageDetailEnt> imagesDetail = previousRequestData.getImageDetail();
        for (ImageDetailEnt item : imagesDetail
                ) {
            images.add(item.getFileLink());
        }
        setDataInAdapter(images);
        for (ServiceChildEnt item : previousRequestData.getServicsList()
                ) {
            selectedJobs.add(item.getServiceEnt());
        }

        refreshListview();
        if (previousRequestData.getServiceDetail() != null)
            homeSelectedService = previousRequestData.getServiceDetail();
        edtLocationgps.setText(previousRequestData.getAddress());
        edtLocationspecific.setText(previousRequestData.getFullAddress());
        edtAddtionalJob.setText(previousRequestData.getDiscription());
        initJobTypeSpinner(TYPE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
          /*  Date d  = sdf.parse(previousRequestData.getDate());
            Date d1 = sdf.parse(previousRequestData.getTime());*/
            Date d2 = sdf.parse(previousRequestData.getCreatedAt());
            predate = previousRequestData.getDate();
            preTime = previousRequestData.getTime();
          /*  btnPreferreddate.setText(new SimpleDateFormat("yyyy MMM dd", Locale.ENGLISH).format(d));
            btnPreferredtime.setText( new SimpleDateFormat("h:mm a").format(d1));*/
            btnPreferreddate.setText(previousRequestData.getDate());
            btnPreferredtime.setText(previousRequestData.getTime());
            txtJobPosted.setText(getString(R.string.job_posted_label) + new SimpleDateFormat("dd-MM-yy").format(d2));
        } catch (Exception ex) {
            Logger.getLogger(RequestServiceFragment.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (previousRequestData.getPaymentType().equals("credit")) {
            paymentType = "credit";
            setCCCheck();
        } else {
            paymentType = "cash";
            setCODCheck();
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
                if (!selectedJobs.contains(jobChildcollection.get(position))) {
                    selectedJobs.add(jobChildcollection.get(position));
                }


                refreshListview();
                // bindSelectedJobview(selectedJobs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
              /*  if (!selectedJobs.contains(jobChildcollection.get(0)))
                    selectedJobs.add(jobChildcollection.get(0));

                refreshListview();*/
            }
        });
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

    private void CreateRequest() {
        String serviceID = String.valueOf(jobcollection.get(spnJobtype.getSelectedItemPosition()).getId());
        StringBuilder sb = new StringBuilder(selectedJobs.size());
        ArrayList<Integer> selectedIDS = new ArrayList<>();
        for (ServiceEnt item : selectedJobs
                ) {
            selectedIDS.add(item.getId());
        }
        String serviceIDS = StringUtils.join(selectedIDS, ",");
        ArrayList<MultipartBody.Part> files = new ArrayList<>();
        int index = 0;
        for (String item : images
                ) {
            if (!item.contains("http://")) {

                File file = new File(item);
                files.add(MultipartBody.Part.createFormData("images[]",
                        file.getName(), RequestBody.create(MediaType.parse("image/*"), file)));

            }
            index++;
        }
        //MultipartBody.Part[] part = files.toArray();
        Call<ResponseWrapper<RequestEnt>> call;
        if (!isEdit) {
            call = webService.createRequest(
                    RequestBody.create(MediaType.parse("text/plain"), prefHelper.getUserId()),
                    RequestBody.create(MediaType.parse("text/plain"), serviceID),
                    RequestBody.create(MediaType.parse("text/plain"), serviceIDS),
                    RequestBody.create(MediaType.parse("text/plain"), edtAddtionalJob.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtLocationgps.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtLocationspecific.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), predate),
                    RequestBody.create(MediaType.parse("text/plain"), preTime),
                    RequestBody.create(MediaType.parse("text/plain"), paymentType),
                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(AppConstants.CREATE_REQUEST)), files);
        } else {
            call = webService.editUserRequest(
                    RequestBody.create(MediaType.parse("text/plain"), prefHelper.getUserId()),
                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(previousRequestData.getId())),
                    RequestBody.create(MediaType.parse("text/plain"), serviceID),
                    RequestBody.create(MediaType.parse("text/plain"), serviceIDS),
                    RequestBody.create(MediaType.parse("text/plain"), edtAddtionalJob.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtLocationgps.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), edtLocationspecific.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"), predate),
                    RequestBody.create(MediaType.parse("text/plain"), preTime),
                    RequestBody.create(MediaType.parse("text/plain"), paymentType),
                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(AppConstants.CREATE_REQUEST)), files);
        }
        call.enqueue(new Callback<ResponseWrapper<RequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RequestEnt>> call, Response<ResponseWrapper<RequestEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
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
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RequestEnt>> call, Throwable t) {
                loadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private boolean validate() {
        if (edtLocationgps.getText().toString().isEmpty()) {
            edtLocationgps.setError("Enter Address");
            return false;
        } else if (edtLocationspecific.getText().toString().isEmpty()) {
            edtLocationspecific.setError("Enter Address");
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
        } else if (selectedJobs.isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select a Job");
            return false;
        } else {
            return true;
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

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.request_setvice));
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

                            predate = new SimpleDateFormat("dd-MM-yy").format(c.getTime());

                            textView.setText(predate);
                        }

                    }
                }, "PreferredDate");

        datePickerHelper.showDate();
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
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    c.set(year, month, day, hourOfDay, minute);
                    preTime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                    textView.setText(timePicker.getTime(hourOfDay, minute));
                }

            }
        }, DateFormat.is24HourFormat(getMainActivity()));

        timePicker.showTime();
    }

    private void bindSelectedJobview(ArrayList<ServiceEnt> jobs) {
        selectedJobsadapter.clearList();
        listViewJobselected.setAdapter(selectedJobsadapter);
        selectedJobsadapter.addAll(jobs);
        selectedJobsadapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listViewJobselected);
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

    private void refreshListview() {
        selectedJobsadapter.clearList();
        listViewJobselected.setAdapter(selectedJobsadapter);
        selectedJobsadapter.addAll(selectedJobs);
        selectedJobsadapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listViewJobselected);
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
        if (selectedJobs.size() > position)
            selectedJobs.remove(position);


        refreshListview();

    }

    @Override
    public void onTextClear() {

    }

    @Override
    public void onItemSelected(Place selectedPlace) {
        System.out.println(selectedPlace);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}
