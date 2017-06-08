package com.app.yellowcap.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.InProgressChildEnt;
import com.app.yellowcap.entities.InProgressParentEnt;
import com.app.yellowcap.entities.JobRequestEnt;
import com.app.yellowcap.entities.RequestDetail;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.ServiceDetail;
import com.app.yellowcap.entities.TechInProgressEnt;
import com.app.yellowcap.entities.subRequest;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.CallUser;
import com.app.yellowcap.interfaces.MarkAsComplete;
import com.app.yellowcap.interfaces.SetOrderCounts;
import com.app.yellowcap.ui.ArrayListExpandableAdapter;
import com.app.yellowcap.ui.viewbinder.InprogressExpandBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/6/2017.
 */

public class InProgressExpendFragment extends BaseFragment implements MarkAsComplete,CallUser {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.elv_inprogress)
    ExpandableListView elvInprogress;
    Unbinder unbinder;
    SetOrderCounts orderCounts;
/*<<<<<<< HEAD
    private ArrayListExpandableAdapter<InProgressParentEnt, InProgressChildEnt> adapter;
    private ArrayList<InProgressParentEnt> collectionGroup;
    private ArrayList<InProgressChildEnt> collectionChild ;
    private ArrayList<InProgressChildEnt> collectionChild1 ;
=======*/

    private ArrayListExpandableAdapter<RequestDetail, RequestDetail> adapter;
    private ArrayList<RequestDetail> collectionGroup;
    private ArrayList<RequestDetail> collectionChild ;
//>>>>>>> 29e8a3f026c4bd0d24fe2c96a2e5c2c96e670704

    private HashMap<RequestDetail, ArrayList<RequestDetail>> listDataChild;

    public static InProgressExpendFragment newInstance() {
        return new InProgressExpendFragment();
    }

    public SetOrderCounts getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(SetOrderCounts orderCounts) {
        this.orderCounts = orderCounts;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_inprogress_expand;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        getInProgressJobsData();
    }

    private void getInProgressJobsData() {

        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<ArrayList<TechInProgressEnt>>> call = webService.techCompleteJobs(Integer.valueOf(prefHelper.getUserId()));

        call.enqueue(new Callback<ResponseWrapper<ArrayList<TechInProgressEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<TechInProgressEnt>>> call, Response<ResponseWrapper<ArrayList<TechInProgressEnt>>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    setInProgressJobsData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<TechInProgressEnt>>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("UserSignupFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });


    }

    private void setInProgressJobsData(ArrayList<TechInProgressEnt> result) {

     /*   userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        userCollection.add(new InProgressEnt("02","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));

        bindData(userCollection);*/

        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();

        listDataChild = new HashMap<>();
        for (TechInProgressEnt item:result
             ) {
            collectionGroup.add(item.getRequest_detail());
            if(item.getRequest_detail().getSub_request()!=null){
            collectionChild.addAll(item.getRequest_detail().getSub_request());}
            listDataChild.put(item.getRequest_detail(),collectionChild);
        }
          /*  collectionGroup.addAll(result);

        //collectionGroup.add(new InProgressParentEnt("re","23-3-17","Al Musa","Plumbing","AED 55.00","Dubai Marina,NearMarina"));
        for(ServiceDetail)
        collectionChild.add(new InProgressChildEnt("Your order is successfully made "+"\n"+"and the details have been stored.","AED 110","AED 200"));
        collectionChild.add(new InProgressChildEnt("Your order is successfully made "+"\n"+"and the details have been stored.","AED 110","AED 200"));
            listDataChild.put(collectionGroup.get(0), collectionChild);
            listDataChild.put(collectionGroup.get(1), collectionChild);
             listDataChild.put(collectionGroup.get(2), collectionChild);
          listDataChild.put(collectionGroup.get(3), collectionChild);*/
        orderCounts.setInprogressCount(collectionGroup.size());

        bindData();
    }

    private void bindData() {

        adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new InprogressExpandBinder(getDockActivity(),this,this),elvInprogress);

        elvInprogress.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void markAsComplete() {

        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<JobRequestEnt>> call = webService.markComplete(
                2,33,43,1);

        call.enqueue(new Callback<ResponseWrapper<JobRequestEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<JobRequestEnt>> call, Response<ResponseWrapper<JobRequestEnt>> response) {

                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals("2000")) {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                    getDockActivity().addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<JobRequestEnt>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e("EntryCodeFragment", t.toString());
                UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });


    }
    @Override
    public void CallOnUserNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
}
