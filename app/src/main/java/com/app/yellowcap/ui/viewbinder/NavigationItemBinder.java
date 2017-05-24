package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.NavigationEnt;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/24/2017.
 */

public class NavigationItemBinder extends ViewBinder<NavigationEnt> {
    DockActivity activity;

    public NavigationItemBinder(DockActivity activity) {
        super(R.layout.row_item_nav);
        this.activity = activity;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new NavViewHolder(view);
    }

    @Override
    public void bindView(NavigationEnt entity, int position, int grpPosition, View view, Activity activity) {
        NavViewHolder viewHolder = (NavViewHolder)view.getTag();
        viewHolder.txtHome.setText(entity.getItem_text());
        viewHolder.imgUnselected.setImageResource(entity.getUnselectedDrawable());

    }

   public static class NavViewHolder extends BaseViewHolder {
        @BindView(R.id.img_selected)
        ImageView imgSelected;
        @BindView(R.id.img_unselected)
        ImageView imgUnselected;
        @BindView(R.id.txt_home)
        AnyTextView txtHome;
        @BindView(R.id.txt_line)
        View txtLine;
       @BindView(R.id.ll_item_container)
       LinearLayout container;

        NavViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
