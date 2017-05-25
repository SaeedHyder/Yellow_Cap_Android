package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.CompletedJobsEnt;
import com.app.yellowcap.entities.UserComleteJobsEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.CompletedJobsBinder;
import com.app.yellowcap.ui.viewbinder.UserCompleteJobsBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/24/2017.
 */

public class UserCompleteJobs extends BaseFragment {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.CompletedJobs_ListView)
    ListView CompletedJobsListView;
    Unbinder unbinder;
    private ArrayListAdapter<UserComleteJobsEnt> adapter;
    private ArrayList<UserComleteJobsEnt> userCollection = new ArrayList<>();

    public static UserCompleteJobs newInstance() {
        return new UserCompleteJobs();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_completedjobs;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<UserComleteJobsEnt>(getDockActivity(), new UserCompleteJobsBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completedjobs, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCompletedJobsData();
    }

    private void setCompletedJobsData() {

        userCollection.add(new UserComleteJobsEnt("01", "24-3-17",
                "Al Musa", "Electrical", 4,
                "AED 55.00",getString(R.string.dummy_desciption)));
        userCollection.add(new UserComleteJobsEnt("02", "25-3-17",
                "Al Musa", "Plumbing", 3,
                "AED 55.00",getString(R.string.dummy_desciption)));
        userCollection.add(new UserComleteJobsEnt("03", "26-3-17",
                "Al Musa", "Cleaning", 5,
                "AED 55.00",getString(R.string.dummy_desciption)));

        bindData(userCollection);
    }

    private void bindData(ArrayList<UserComleteJobsEnt> userCollection) {

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
