package com.ingic.yellowcap.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ingic.yellowcap.R;
import com.ingic.yellowcap.entities.RequestDetail;
import com.ingic.yellowcap.entities.ResponseWrapper;
import com.ingic.yellowcap.entities.ServiceEnt;
import com.ingic.yellowcap.entities.serviceList;
import com.ingic.yellowcap.fragments.abstracts.BaseFragment;
import com.ingic.yellowcap.helpers.InternetHelper;
import com.ingic.yellowcap.helpers.UIHelper;
import com.ingic.yellowcap.interfaces.onDeleteImage;
import com.ingic.yellowcap.ui.adapters.ArrayListAdapter;
import com.ingic.yellowcap.ui.viewbinder.SelectedJobBinder;
import com.ingic.yellowcap.ui.views.AnyEditTextView;
import com.ingic.yellowcap.ui.views.AnySpinner;
import com.ingic.yellowcap.ui.views.AnyTextView;
import com.ingic.yellowcap.ui.views.TitleBar;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ingic.yellowcap.R.id.txt_totalPriceText;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class EditJobTechFragment extends BaseFragment implements onDeleteImage {
    public static String TYPE = "TYPE";
    public static String PARENTID = "0";
    public static String CLIENTNAME = "NAME";
    public static String ISEDIT = "isEdit";
    @BindView(R.id.txt_jobNo)
    AnyTextView txtJobNoText;
    @BindView(R.id.txt_jobNoText)
    AnyTextView txtJobNo;
    @BindView(R.id.ll_job)
    LinearLayout llJob;
    @BindView(R.id.txt_ClientName)
    AnyTextView txtClientName;
    @BindView(R.id.txt_clientNameText)
    AnyTextView txtClientNameText;
    @BindView(R.id.ll_clientName)
    LinearLayout llClientName;
    @BindView(R.id.txt_jobtype)
    AnyTextView txtJobtype;
    @BindView(R.id.spn_jobtype)
    AnySpinner spnJobtype;
    @BindView(R.id.txt_jobdescription)
    AnyTextView txtJobdescription;
    @BindView(R.id.spn_jobdescription)
    AnySpinner spnJobdescription;
    @BindView(R.id.txt_jobselected)
    AnyTextView txtJobselected;
    @BindView(R.id.listView_jobselected)
    ListView listViewJobselected;
    @BindView(R.id.txt_jobadditional)
    AnyTextView txtJobadditional;
    @BindView(R.id.txt_pricing)
    AnyTextView txtPricing;
    @BindView(R.id.txt_Total)
    AnyTextView txtTotal;
    @BindView(txt_totalPriceText)
    AnyTextView txtTotalPriceText;
    @BindView(R.id.ll_TotalPrice)
    LinearLayout llTotalPrice;

    ArrayList<String> images = new ArrayList<>();
    @BindView(R.id.txt_additionDescription)
    AnyEditTextView mTxtAdditionDescription;
    @BindView(R.id.edt_total)
    AnyEditTextView mEdtTotal;
    private ArrayList<ServiceEnt> selectedJobs;
    private ArrayListAdapter<ServiceEnt> selectedJobsadapter;
    private ServiceEnt jobtype;
    private RequestDetail previousData;
    private ArrayList<ServiceEnt> jobcollection;
    private ArrayList<ServiceEnt> jobChildcollection;
    private Boolean isEdit = false;

    public static EditJobTechFragment newInstance() {
        return new EditJobTechFragment();
    }

    public static EditJobTechFragment newInstance(RequestDetail editData) {
        Bundle args = new Bundle();
        args.putString(TYPE, new Gson().toJson(editData));
        EditJobTechFragment fragment = new EditJobTechFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static EditJobTechFragment newInstance(String parentID, String ClientName) {
        Bundle args = new Bundle();
        args.putString(PARENTID, parentID);
        args.putString(CLIENTNAME, ClientName);
        EditJobTechFragment fragment = new EditJobTechFragment();
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
            PARENTID = getArguments().getString(PARENTID);
            CLIENTNAME = getArguments().getString(CLIENTNAME);
            isEdit = getArguments().getBoolean(ISEDIT);
            if (TYPE != null)
                previousData = new Gson().fromJson(TYPE, RequestDetail.class);


        }
        selectedJobsadapter = new ArrayListAdapter<ServiceEnt>(getDockActivity(), new SelectedJobBinder(this, prefHelper));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_editjob_technician;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initListViewAdapter();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedJobs = new ArrayList<>();
        if (prefHelper.isLanguageArabic()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
        if (previousData != null) {
            isEdit = true;
            PARENTID = String.valueOf(previousData.getId());
            editCurrentService();
        } else {
            if (PARENTID != null) {
                txtJobNo.setText(String.valueOf(PARENTID));
            }
            if (CLIENTNAME != null) {
                txtClientNameText.setText(CLIENTNAME);
            }
            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                initJobTypeSpinner("");
            }
        }
        txtTotalPriceText.setTypeface(null, Typeface.BOLD);

        setListners();


    }

    private void setListners() {
        mTxtAdditionDescription.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    private void initListViewAdapter() {
        /*selectedJobs.add("Total Electricity Failure/Break down");
        selectedJobs.add("No Electricity in some Room");
        selectedJobs.add("Repair Ac");
        selectedJobs.add("Ac Not Working");*/


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
                // UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });


    }

    private void setJobtypeSpinner() {
        final ArrayList<String> jobtypearraylist = new ArrayList<String>();
        for (ServiceEnt item : jobcollection
                ) {
            jobtypearraylist.add(prefHelper.isLanguageArabic() ? item.getArTitle() : item.getTitle());
        }


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobtypearraylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobtype.setAdapter(categoryAdapter);
        if (previousData != null) {
            if (jobcollection.size() > 0) {
                if (previousData.getService_detail() != null) {
                    if (prefHelper.isLanguageArabic()) {
                        if (jobtypearraylist.contains(previousData.getService_detail().getAr_title())) {
                            spnJobtype.setSelection(jobtypearraylist.indexOf(previousData.getService_detail().getAr_title()));
                            jobtype = jobcollection.get(jobtypearraylist.indexOf(previousData.getService_detail().getAr_title()));
                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                initJobDescriptionSpinner(jobtype);
                            }
                        } else {
                            spnJobtype.setSelection(0);
                            jobtype = jobcollection.get(0);
                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                initJobDescriptionSpinner(jobtype);
                            }
                        }
                    } else {
                        if (jobtypearraylist.contains(previousData.getService_detail().getTitle())) {
                            spnJobtype.setSelection(jobtypearraylist.indexOf(previousData.getService_detail().getTitle()));
                            jobtype = jobcollection.get(jobtypearraylist.indexOf(previousData.getService_detail().getTitle()));
                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                initJobDescriptionSpinner(jobtype);
                            }
                        } else {
                            spnJobtype.setSelection(0);
                            jobtype = jobcollection.get(0);
                            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                initJobDescriptionSpinner(jobtype);
                            }
                        }
                    }
                }
            }
            spnJobtype.setEnabled(false);
        } else {
            spnJobtype.setSelection(0);
            jobtype = jobcollection.get(0);
            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                initJobDescriptionSpinner(jobtype);
            }
        }

        spnJobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isEdit) {
                    selectedJobs.clear();
                    refreshListview();
                    jobtype = jobcollection.get(position);
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        initJobDescriptionSpinner(jobtype);
                    }
                }
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
                        if (!isEdit) {
                            selectedJobs.clear();
                        }
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
                    //  UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
                }
            });

        }
    }

    private void setJobDescriptionSpinner() {
        final ArrayList<String> jobdescriptionarraylist = new ArrayList<String>();
        for (ServiceEnt item : jobChildcollection
                ) {
            jobdescriptionarraylist.add(prefHelper.isLanguageArabic() ? item.getArTitle() : item.getTitle());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobdescriptionarraylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobdescription.setAdapter(categoryAdapter);
        spnJobdescription.setOnItemSelectedEvenIfUnchangedListener(new AdapterView.OnItemSelectedListener() {
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

    private void editCurrentService() {

        for (serviceList item : previousData.getServics_list()
                ) {
            if (item.getService_detail() != null) {
                selectedJobs.add(new ServiceEnt(item.getService_detail().getId(),
                        item.getService_detail().getTitle(),
                        item.getService_detail().getImage(),
                        item.getService_detail().getParent_id(),
                        item.getService_detail().getCreated_at(),
                        item.getService_detail().getUpdated_at(),
                        item.getService_detail().getDeleted_at(),
                        item.getService_detail().getService_image(),
                        item.getService_detail().getAr_title()));
            }
        }

        refreshListview();
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            initJobTypeSpinner("");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        txtJobNo.setText(String.valueOf(previousData.getId()));
        txtClientNameText.setText(previousData.getUser_detail().getFull_name());
        mTxtAdditionDescription.setText(previousData.getDiscription());
        mEdtTotal.setText(previousData.getTotal());

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
        //MultipartBody.Part[] part = files.toArray();
        Call<ResponseWrapper> call;
        if (!isEdit) {
            call = webService.addTechJob(prefHelper.getUserId(),
                    serviceID,
                    PARENTID,
                    serviceIDS,
                    mTxtAdditionDescription.getText().toString(),
                    mEdtTotal.getText().toString());
        } else {
            call = webService.editTechJob(prefHelper.getUserId(),
                    serviceID,
                    PARENTID,
                    serviceIDS,
                    mTxtAdditionDescription.getText().toString(),
                    mEdtTotal.getText().toString());

        }
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                //   UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });
    }

    private void refreshListview() {
        selectedJobsadapter.clearList();
        listViewJobselected.setAdapter(selectedJobsadapter);
        selectedJobsadapter.addAll(selectedJobs);
        selectedJobsadapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listViewJobselected);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        getDockActivity().lockDrawer();
        titleBar.showsaveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    CreateRequest();
                }
            }
        });
        titleBar.showBackButton();
        if (isEdit) {
            titleBar.setSubHeading(getString(R.string.edit_job));
        } else {
            titleBar.setSubHeading(getString(R.string.add_job));
        }
    }
 /*   private void initJobTypeSpinner() {
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
    }*/


    @Override
    public void onDelete(int position) {

    }

    @Override
    public void OnDeleteJobs(int position) {
        if (selectedJobs.size() > position)
            selectedJobs.remove(position);


        refreshListview();
    }
}
