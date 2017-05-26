package com.app.yellowcap.fragments;

import android.os.Bundle;
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
import com.app.yellowcap.ui.viewbinder.TechNotificationitemBinder;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.yellowcap.R.id.lv_NewJobs;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class TechNotificationsFragment extends BaseFragment {

    @BindView(R.id.lv_TechNotification)
    ListView lvTechNotification;
    Unbinder unbinder;
    private ArrayListAdapter<NewJobEnt> adapter;
    private ArrayList<NewJobEnt> userCollection = new ArrayList<>();

    public static TechNotificationsFragment newInstance() {
        return new TechNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NewJobEnt>(getDockActivity(), new TechNotificationitemBinder());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tech_notifications;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setNotificationData();
      //  NotificationItemListner();

    }

    private void NotificationItemListner() {

        lvTechNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getDockActivity().addDockableFragment(NewJobDetail.newInstance(), "NewJobDetail");
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
        lvTechNotification.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        getDockActivity().releaseDrawer();
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Notifications));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}