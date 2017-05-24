package com.app.yellowcap.ui.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;

import berlin.volders.badger.BadgeShape;
import berlin.volders.badger.Badger;
import berlin.volders.badger.CountBadge;

/**
 * Created on 5/24/2017.
 */

public class BadgeHelper {
    ImageView imgNotificationCounter ;
    DockActivity dockActivity;
    private CountBadge badge;
    public BadgeHelper(ImageView imgNotificationCounter, DockActivity activity) {
        this.imgNotificationCounter = imgNotificationCounter;
        dockActivity = activity;
    }

    public void hideBadge() {
        imgNotificationCounter.setVisibility(View.GONE);
    }

    public ImageView getImgNotificationCounter(){
        return imgNotificationCounter;
    }

    public CountBadge initBadge(Context mcontext) {
        imgNotificationCounter.setVisibility(View.VISIBLE);
        CountBadge.Factory circle = new CountBadge.Factory(BadgeShape.circle(.7f, Gravity.CENTER_VERTICAL ),
                dockActivity. getResources().getColor(R.color.text_color),dockActivity. getResources().getColor(R.color.white));

        Badger<CountBadge> badger = Badger.sett(dockActivity.getResources().getDrawable(R.drawable.ic_badge), circle);
        imgNotificationCounter.setImageDrawable(badger.drawable);
        badge = badger.badge;
        badge.setCount(0);
        return badge;

    }

    public void addtoBadge(int count) {
        try {
            if (count > 99) {
                CountBadge.Factory circle = new CountBadge.Factory(BadgeShape.oval(1f, 2f, Gravity.BOTTOM),
                        dockActivity.  getResources().getColor(R.color.text_color),dockActivity. getResources().getColor(R.color.white));

                Badger<CountBadge> badger = Badger.sett(dockActivity.getResources().getDrawable(R.drawable.ic_badge), circle);
                imgNotificationCounter.setImageDrawable(badger.drawable);
                badge = badger.badge;
                badge.setCount(count);
            } else
                badge.setCount(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addtoBadge(int count,CountBadge countBadge) {
        try {
            if (count > 99) {
                CountBadge.Factory circle = new CountBadge.Factory(BadgeShape.oval(1f, 2f, Gravity.BOTTOM),
                        dockActivity. getResources().getColor(R.color.text_color),dockActivity. getResources().getColor(R.color.white));

                Badger<CountBadge> badger = Badger.sett(dockActivity.getResources().getDrawable(R.drawable.ic_badge), circle);
                imgNotificationCounter.setImageDrawable(badger.drawable);
                countBadge = badger.badge;
                countBadge.setCount(count);
            } else
                countBadge.setCount(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isBadgeVisible(){
        return imgNotificationCounter.getVisibility()==View.VISIBLE;
    }
    public void showBadge(){
        imgNotificationCounter.setVisibility(View.VISIBLE);
    }
    public int getBadgeCount() {
        try {
            return badge.getCount();
        } catch (Exception e) {

        }
        return -1;
    }
}
