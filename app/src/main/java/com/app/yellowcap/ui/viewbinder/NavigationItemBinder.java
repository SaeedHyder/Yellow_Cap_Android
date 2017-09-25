package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.NavigationEnt;
import com.app.yellowcap.fragments.SideMenuFragment;
import com.app.yellowcap.helpers.BasePreferenceHelper;
import com.app.yellowcap.helpers.ClickableSpanHelper;
import com.app.yellowcap.interfaces.UpdateNotificationsCount;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.BadgeHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/24/2017.
 */

public class NavigationItemBinder extends ViewBinder<NavigationEnt> implements UpdateNotificationsCount {
    DockActivity dockActivity;
    BadgeHelper badgeHelper;
    BasePreferenceHelper prefHelper;
    UpdateNotificationsCount count;
    int badgeCount;
    ImageView countView;


    public NavigationItemBinder(DockActivity activity, SideMenuFragment fragment, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_nav);
        this.dockActivity = activity;
        this.prefHelper = prefHelper;

        fragment.setInterface(this);

    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new NavViewHolder(view);
    }

    @Override
    public void bindView(NavigationEnt entity, int position, int grpPosition, View view, Activity activity) {
        NavViewHolder viewHolder = (NavViewHolder) view.getTag();
        if (prefHelper.isLanguageArabic()){
            viewHolder.rootLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        else  viewHolder.rootLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        badgeHelper = new BadgeHelper(viewHolder.imgNotificationCount, (DockActivity) activity);
        if (entity.getItem_text().equals(activity.getString(R.string.home)) && !entity.getItem_text().equals(activity.getString(R.string.english))) {
            viewHolder.txtHome.setText(entity.getItem_text());
            viewHolder.imgUnselected.setImageResource(entity.getSelectedDrawable());
            viewHolder.txtHome.setTextColor(activity.getResources().getColor(R.color.text_color));
            badgeHelper.hideBadge();
        } else {
            viewHolder.txtHome.setText(entity.getItem_text());
            viewHolder.txtHome.setTextColor(activity.getResources().getColor(R.color.black));
            viewHolder.imgUnselected.setImageResource(entity.getUnselectedDrawable());
            badgeHelper.hideBadge();
            if (entity.getItem_text().equals(activity.getString(R.string.notifications))) {
                //badgeHelper = new BadgeHelper(viewHolder.imgNotificationCount, (DockActivity) activity);
                badgeHelper.initBadge(activity);
                badgeHelper.addtoBadge(prefHelper.getBadgeCount());
                /*if (prefHelper.isLanguageArabic()) {
                    //changeBadgeSide(RelativeLayout.ALIGN_PARENT_START,viewHolder.imgNotificationCount);
                    badgeHelper.changeBadgeGravity(RelativeLayout.ALIGN_PARENT_START);
                } else {
                    //changeBadgeSide(RelativeLayout.ALIGN_PARENT_END,viewHolder.imgNotificationCount);
                    badgeHelper.changeBadgeGravity(RelativeLayout.ALIGN_PARENT_END);
                }*/
                badgeHelper.showBadge();
            } else if (entity.getItem_text().equals(activity.getString(R.string.english)) && !entity.getItem_text().equals(activity.getString(R.string.home))) {
                //LanguageChange(view);
                if (prefHelper.isLanguageArabic()) {
                    String sourceString = "<b> <font color='#fcc739'>" + dockActivity.getResources().getString(R.string.arabic) + "</font> </b> " + " - " +
                            dockActivity.getResources().getString(R.string.english);
                    viewHolder.txtHome.setText(Html.fromHtml(sourceString));
                    // viewHolder.txtHome.setText(dockActivity.getResources().getString(R.string.arabic_english));
                    // setSignupSpan(dockActivity.getResources().getString(R.string.arabic_english), dockActivity.getResources().getString(R.string.arabic), viewHolder.txtHome);
                    viewHolder.txtHome.setGravity(Gravity.RIGHT);
                } else {
                    // viewHolder.txtHome.setText(dockActivity.getResources().getString(R.string.arabic_english));
                    String sourceString = "<b><font color='#fcc739'>" + dockActivity.getResources().getString(R.string.english) + "</font></b> " + " - " +
                            dockActivity.getResources().getString(R.string.arabic);
                    viewHolder.txtHome.setText(Html.fromHtml(sourceString));
                    // setSignupSpan(dockActivity.getResources().getString(R.string.arabic_english), dockActivity.getResources().getString(R.string.english), viewHolder.txtHome);
                    viewHolder.txtHome.setGravity(Gravity.LEFT);

                }

            }

        }
        //count.updateCount(entity.getNotificationCount(),position);


    }

    private void changeBadgeSide(int GravitySide, ImageView imgBadge) {
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(imgBadge.getWidth(),
                        imgBadge.getHeight());
        params.addRule(GravitySide);

        imgBadge.setLayoutParams(params);
    }

    private void setSignupSpan(String text, String spanText, AnyTextView txtview) {

        SpannableStringBuilder stringBuilder = ClickableSpanHelper.initSpan(text);
        ClickableSpanHelper.setSpan(stringBuilder, text, spanText, new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                //  ds.setColor(getResources().getColor(R.color.white));    // you can use custom color
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);    // this remove the underline
            }

            @Override
            public void onClick(View widget) {
                if (prefHelper.isLanguageArabic()) {
                    prefHelper.putLang(dockActivity, "en");

                } else {
                    prefHelper.putLang(dockActivity, "ar");

                }
            }
        });
        ClickableSpanHelper.setColor(stringBuilder, text, spanText, "#fcc739");

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);

    }

    private void LanguageChange(final View txtHome) {
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefHelper.isLanguageArabic()) {
                    prefHelper.putLang(dockActivity, "en");

                } else {
                    prefHelper.putLang(dockActivity, "ar");

                }
            }
        });
    }


    @Override
    public void updateCount(int count) {
        badgeCount = count;
        badgeHelper.addtoBadge(count);
        badgeHelper.getImgNotificationCounter().invalidate();
        badgeHelper.showBadge();
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
        @BindView(R.id.root_layout)
        LinearLayout rootLayout;
        NavViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
