package com.app.yellowcap.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.CameraHelper;
import com.app.yellowcap.helpers.DatePickerHelper;
import com.app.yellowcap.helpers.TimePickerHelper;
import com.app.yellowcap.interfaces.onDeleteImage;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.adapters.RecyclerViewAdapterImages;
import com.app.yellowcap.ui.viewbinder.SelectedJobBinder;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;
import com.google.android.gms.location.places.Place;
import com.jota.autocompletelocation.AutoCompleteLocation;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/22/2017.
 */

public class RequestServiceFragment extends BaseFragment implements View.OnClickListener, MainActivity.ImageSetter, onDeleteImage, AutoCompleteLocation.AutoCompleteLocationListener {
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
    private ArrayList<String> selectedJobs = new ArrayList<>();
    private ArrayListAdapter<String> selectedJobsadapter;
    private String jobtype;

    public static RequestServiceFragment newInstance() {
        return new RequestServiceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_service, container, false);
        initListViewAdapter();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initListViewAdapter() {
        selectedJobs.add("Total Electricity Failure/Break down");
        selectedJobs.add("No Electricity in some Room");
        selectedJobs.add("Repair Ac");
        selectedJobs.add("Ac Not Working");
        selectedJobsadapter = new ArrayListAdapter<String>(getDockActivity(), selectedJobs, new SelectedJobBinder(this));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        setDataInAdapter(images);
        listViewJobselected.setAdapter(selectedJobsadapter);
        setListViewHeightBasedOnChildren(listViewJobselected);
        initJobTypeSpinner();
        initJobDescriptionSpinner();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initJobTypeSpinner() {
        final List<String> jobtypearraylist = new ArrayList<String>();
        jobtypearraylist.add("Electrical");
        jobtypearraylist.add("Plumbing");
        jobtypearraylist.add("AC");
        jobtypearraylist.add("Furniture Fixture");
        jobtypearraylist.add("Pest Control");
        jobtypearraylist.add("Cleaning");
        jobtypearraylist.add("Move in/Move Out");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobtypearraylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobtype.setAdapter(categoryAdapter);
        spnJobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobtype = jobtypearraylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initJobDescriptionSpinner() {
        final ArrayList<String> jobdescriptionarraylist = new ArrayList<String>();
        jobdescriptionarraylist.add("Total Electricity Failure/Break down");
        jobdescriptionarraylist.add("No Electricity in some Room");
        jobdescriptionarraylist.add("Repair Ac");
        jobdescriptionarraylist.add("Ac Not Working");
        jobdescriptionarraylist.add("Move out to different city");
        jobdescriptionarraylist.add("Fix Furniture");
        jobdescriptionarraylist.add("Clean Lawn");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobdescriptionarraylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobdescription.setAdapter(categoryAdapter);
        spnJobdescription.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //jobtype = jobdescriptionarraylist.get(position);
                //selectedJobs.add(jobdescriptionarraylist.get(position));
                // refreshListview();
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
                imgCcCheck.setVisibility(View.VISIBLE);
                imgCodCheck.setVisibility(View.GONE);
                getDockActivity().addDockableFragment(CreditCardFragment.newInstance(),"CreditCardFragment");
                break;

            case R.id.btn_cod:

                imgCcCheck.setVisibility(View.GONE);
                imgCodCheck.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_request:
                break;
            case R.id.img_gps:
                if (getMainActivity().statusCheck()){
                    edtLocationgps.setText(getMainActivity().getMyCurrentLocation().getAddress());
                }

                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Request Service");
    }

    private void initTimePicker(final TextView textView){
        Calendar calendar = Calendar.getInstance();
        final TimePickerHelper timePicker = new TimePickerHelper();
        timePicker.initTimeDialog(getDockActivity(),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(timePicker.getTime(hourOfDay,minute));
            }
        },DateFormat.is24HourFormat(getMainActivity()));

        timePicker.showTime();
    }
    private void initDatePicker(final TextView textView){
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
                        textView.setText(datePickerHelper.getStringDate(year,month,dayOfMonth));
                    }
                },"PreferredDate");

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
        selectedJobs.remove(position);
        // refreshListview();
    }

    @Override
    public void onTextClear() {

    }

    @Override
    public void onItemSelected(Place selectedPlace) {

    }
}
