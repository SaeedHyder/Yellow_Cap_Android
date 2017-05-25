package com.app.yellowcap.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.InProgressEnt;
import com.app.yellowcap.entities.UserInProgressEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.interfaces.CallUser;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.InProgressBinder;
import com.app.yellowcap.ui.viewbinder.UserInProgressBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/24/2017.
 */

public class UserInProgressFragment extends BaseFragment implements CallUser {
    public static UserInProgressFragment newInstance(){return new UserInProgressFragment();}

    @Override
    protected int getLayout() {
        return R.layout.fragment_inprogress;
    }
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.InProgress_ListView)
    ListView InProgressListView;
    Unbinder unbinder;

    private ArrayListAdapter<UserInProgressEnt> adapter;

    private ArrayList<UserInProgressEnt> userCollection = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<UserInProgressEnt>(getDockActivity(), new UserInProgressBinder(this,getDockActivity()));
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

        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 XXX XXX XXX",false));
        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 123 456 789",true));
        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 789 123 465",true));
        userCollection.add(new UserInProgressEnt("Al Musa","Ac not colling well","AED 55.00","+971 XXX XXX XXX",false));

        bindData(userCollection);
    }

    private void bindData(ArrayList<UserInProgressEnt> userCollection) {

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
    public void CallOnUserNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
}
