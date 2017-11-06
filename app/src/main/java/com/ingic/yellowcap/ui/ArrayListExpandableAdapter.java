package com.ingic.yellowcap.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.ingic.yellowcap.ui.viewbinders.abstracts.ExpandableListViewBinder;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayListExpandableAdapter<T, E> extends BaseExpandableListAdapter {

    protected Activity mContext;

    protected ExpandableListViewBinder<T, E> viewBinder;

    protected ExpandableListView elvInprogress;


    private ArrayList<T> headerCollection = new ArrayList<>();
    private HashMap<T, ArrayList<E>> ChildCollection = new HashMap<>();

    public ArrayListExpandableAdapter(Activity context, ArrayList<T> headerCollection, HashMap<T, ArrayList<E>> listDataChild,
                                      ExpandableListViewBinder<T, E> viewBinder, ExpandableListView elvInprogress) {
        mContext = context;
        this.headerCollection = headerCollection;
        this.ChildCollection = listDataChild;
        this.viewBinder = viewBinder;
        this.elvInprogress=elvInprogress;

    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.ChildCollection.get(this.headerCollection.get(groupPosition)).size();
    }



    @Override
    public int getGroupCount() {
        return headerCollection.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerCollection.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.ChildCollection.get(this.headerCollection.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {

        View convertView = view;
        if (convertView == null) {
            convertView = viewBinder.createChildView(mContext);
        }

        final E childItem = (E) getChild(groupPosition, childPosition);

        viewBinder.bindChildView(childItem, childPosition, groupPosition,this.ChildCollection.get(this.headerCollection.get(groupPosition)).size(), convertView, mContext);

        return convertView;


    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup parent) {

        View convertView = view;

        if (convertView == null) {
            convertView = viewBinder.createGroupView(mContext);
        }

        T groupItem = (T) getGroup(groupPosition);

        elvInprogress.expandGroup(groupPosition);
        viewBinder.bindGroupView(groupItem, groupPosition, 0, this.ChildCollection.get(this.headerCollection.get(groupPosition)).size(),  convertView, mContext);

        return convertView;


    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
