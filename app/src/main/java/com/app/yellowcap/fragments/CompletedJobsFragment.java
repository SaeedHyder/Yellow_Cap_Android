package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.CompletedJobsEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.CompletedJobsBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class CompletedJobsFragment extends BaseFragment {

    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.CompletedJobs_ListView)
    ListView CompletedJobsListView;
    Unbinder unbinder;

    private ArrayListAdapter<CompletedJobsEnt> adapter;

    private ArrayList<CompletedJobsEnt> userCollection = new ArrayList<>();

    public static CompletedJobsFragment newInstance() {

        return new CompletedJobsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<CompletedJobsEnt>(getDockActivity(), new CompletedJobsBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completedjobs, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_completedjobs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCompletedJobsData();
    }

    private void setCompletedJobsData() {

        userCollection.add(new CompletedJobsEnt("02","23-3-17","Al Musa","Plumbing","5","AED 55.00"));
        userCollection.add(new CompletedJobsEnt("02","23-3-17","Al Musa","Plumbing","5","AED 55.00"));
        userCollection.add(new CompletedJobsEnt("02","23-3-17","Al Musa","Plumbing","5","AED 55.00"));


        bindData(userCollection);
    }

    private void bindData(ArrayList<CompletedJobsEnt> userCollection) {

        adapter.clearList();
        CompletedJobsListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
