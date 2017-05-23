package com.app.yellowcap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yellowcap.R;
import com.app.yellowcap.fragments.abstracts.BaseFragment;

/**
 * Created on 5/22/2017.
 */


public class UserSelectionFragment extends BaseFragment {
    public static UserSelectionFragment newInstance() {
        return new UserSelectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_userselection;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
