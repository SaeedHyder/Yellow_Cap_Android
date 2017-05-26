package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.interfaces.onDeleteImage;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.SelectedJobBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.yellowcap.fragments.RequestServiceFragment.setListViewHeightBasedOnChildren;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class EditJobTechFragment extends BaseFragment implements onDeleteImage {

    @BindView(R.id.txt_jobNo)
    AnyTextView txtJobNo;
    @BindView(R.id.txt_jobNoText)
    AnyTextView txtJobNoText;
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
    Spinner spnJobtype;
    @BindView(R.id.txt_jobdescription)
    AnyTextView txtJobdescription;
    @BindView(R.id.spn_jobdescription)
    Spinner spnJobdescription;
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
    @BindView(R.id.txt_totalPriceText)
    AnyTextView txtTotalPriceText;
    @BindView(R.id.ll_TotalPrice)
    LinearLayout llTotalPrice;
    Unbinder unbinder;
    ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> selectedJobs = new ArrayList<>();
    private ArrayListAdapter<String> selectedJobsadapter;
    private String jobtype;

    public static EditJobTechFragment newInstance() {
        return new EditJobTechFragment();
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
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
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
        listViewJobselected.setAdapter(selectedJobsadapter);
        setListViewHeightBasedOnChildren(listViewJobselected);
        initJobTypeSpinner();
        initJobDescriptionSpinner();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        getDockActivity().lockDrawer();
        titleBar.showsaveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().addDockableFragment(OrderHistoryFragment.newInstance(), "OrderHistoryFragment");
            }
        });
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.edit_job));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDelete(int position) {

    }

    @Override
    public void OnDeleteJobs(int position) {

    }
}
