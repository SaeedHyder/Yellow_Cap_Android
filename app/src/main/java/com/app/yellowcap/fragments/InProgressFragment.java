package com.app.yellowcap.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.CompletedJobsEnt;
import com.app.yellowcap.entities.InProgressEnt;
import com.app.yellowcap.entities.JobRequestEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.UserComleteJobsEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.CallUser;
import com.app.yellowcap.interfaces.MarkAsComplete;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.InProgressBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class InProgressFragment extends BaseFragment implements CallUser{

    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.InProgress_ListView)
    ListView InProgressListView;
    Unbinder unbinder;

    private ArrayListAdapter<InProgressEnt> adapter;

    private ArrayList<InProgressEnt> userCollection = new ArrayList<>();

    public static InProgressFragment newInstance() {
        return new InProgressFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_inprogress;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter = new ArrayListAdapter<InProgressEnt>(getDockActivity(), new InProgressBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInProgressJobsData();
    }

    private void setInProgressJobsData() {

        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));

        bindData(userCollection);
    }

    private void bindData(ArrayList<InProgressEnt> userCollection) {

        adapter.clearList();
        InProgressListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void CallOnUserNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }


}

