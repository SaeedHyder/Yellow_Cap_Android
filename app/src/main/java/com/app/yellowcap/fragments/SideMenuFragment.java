package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NavigationEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
import com.app.yellowcap.ui.adapters.ArrayListAdapter;
import com.app.yellowcap.ui.viewbinder.NavigationItemBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SideMenuFragment extends BaseFragment {

    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.txt_userName)
    AnyTextView txtUserName;
    @BindView(R.id.txt_useremail)
    AnyTextView txtUseremail;
    @BindView(R.id.nav_listview)
    ListView navListview;
    Unbinder unbinder;
    private ArrayList<NavigationEnt> navigationItemList = new ArrayList<>();
    private ArrayListAdapter<NavigationEnt> madapter;
    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binddata();
        madapter = new ArrayListAdapter<NavigationEnt>(getDockActivity(),new NavigationItemBinder(getDockActivity()));
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_sidemenu;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistItemClickListener();

    }

    private void setlistItemClickListener() {
        navListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madapter.notifyDataSetChanged();
                AnyTextView textview = (AnyTextView)view.findViewById(R.id.txt_home);
                textview.setTextColor(getResources().getColor(R.color.text_color));
                ImageView img = (ImageView)view.findViewById(R.id.img_unselected);
                NavigationEnt entity = (NavigationEnt)madapter.getItem(position);
                img.setImageResource(entity.getSelectedDrawable());

            }
        });
    }




    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private void binddata() {
        navigationItemList.add(new NavigationEnt(R.drawable.home_yellow,R.drawable.home,getString(R.string.home)));
        navigationItemList.add(new NavigationEnt(R.drawable.profile_yellow,R.drawable.profile,getString(R.string.profile)));
        navigationItemList.add(new NavigationEnt(R.drawable.notification_yellow,R.drawable.notification,
                getString(R.string.notifications)));
        navigationItemList.add(new NavigationEnt(R.drawable.jobs_yellow,R.drawable.jobs,getString(R.string.my_job)));
        navigationItemList.add(new NavigationEnt(R.drawable.about_yellow,R.drawable.about,getString(R.string.about_app)));
        bindview();
    }
    private void bindview(){
        madapter.clearList();
        navListview.setAdapter(madapter);
        madapter.addAll(navigationItemList);
        madapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
