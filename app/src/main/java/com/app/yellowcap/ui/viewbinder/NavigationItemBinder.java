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
import com.app.yellowcap.ui.views.BadgeHelper;
import com.google.android.gms.plus.model.people.Person;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/24/2017.
 */

public class NavigationItemBinder extends ViewBinder<NavigationEnt> {
    DockActivity activity;
    BadgeHelper badgeHelper;
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
        badgeHelper = new BadgeHelper(viewHolder.imgNotificationCount, (DockActivity) activity);
        if  (entity.getItem_text().equals(activity.getString(R.string.home))){
            viewHolder.txtHome.setText(entity.getItem_text());
            viewHolder.imgUnselected.setImageResource(entity.getSelectedDrawable());
            viewHolder.txtHome.setTextColor(activity.getResources().getColor(R.color.text_color));
            badgeHelper.hideBadge();
        }else {
            viewHolder.txtHome.setText(entity.getItem_text());
            viewHolder.imgUnselected.setImageResource(entity.getUnselectedDrawable());
            badgeHelper.hideBadge();
            if (entity.getItem_text().equals(activity.getString(R.string.notifications))) {

                badgeHelper.initBadge(activity);
                badgeHelper.addtoBadge(3);
                badgeHelper.showBadge();
            }
        }

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
       @BindView(R.id.imgNotificationCount)
       ImageView imgNotificationCount;
        NavViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
