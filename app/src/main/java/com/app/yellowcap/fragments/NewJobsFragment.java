package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NewJobEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.NewJobsitemBinder;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class NewJobsFragment extends BaseFragment {

    @BindView(R.id.lv_NewJobs)
    ListView lv_NewJobs;

    private ArrayListAdapter<NewJobEnt> adapter;
    private ArrayList<NewJobEnt> userCollection = new ArrayList<>();

    Unbinder unbinder;

    public static NewJobsFragment newInstance() {
        return new NewJobsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NewJobEnt>(getDockActivity(), new NewJobsitemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newjobs, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_newjobs;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setNotificationData();
        selectNewJobsListItem();


    }

    private void selectNewJobsListItem() {

        lv_NewJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getDockActivity().replaceDockableFragment(NewJobDetail.newInstance(), "NewJobDetail");
            }
        });

    }

    private void setNotificationData() {

        userCollection.add(new NewJobEnt("drawable://" + R.drawable.itemlogo, "Failure In Ac Unit", "drawable://" + R.drawable.next));
        userCollection.add(new NewJobEnt("drawable://" + R.drawable.itemlogo, "Failure In Ac Unit", "drawable://" + R.drawable.next));
        userCollection.add(new NewJobEnt("drawable://" + R.drawable.itemlogo, "Failure In Ac Unit", "drawable://" + R.drawable.next));

        bindData(userCollection);
    }

    private void bindData(ArrayList<NewJobEnt> userCollection) {

        adapter.clearList();
        lv_NewJobs.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().lockDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.New_Jobs));

    }
}
