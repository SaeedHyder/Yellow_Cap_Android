package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NotificationEnt;
import com.app.yellowcap.helpers.BasePreferenceHelper;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class TechNotificationitemBinder extends ViewBinder<NotificationEnt> {

    private ImageLoader imageLoader;
    private BasePreferenceHelper prefhelper;

    public TechNotificationitemBinder(BasePreferenceHelper prefhelper) {
        super(R.layout.notification_item);
        this.prefhelper = prefhelper;
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new TechNotificationitemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final NotificationEnt entity, int position, int grpPosition, View view, Activity activity) {

        TechNotificationitemBinder.ViewHolder viewHolder = (TechNotificationitemBinder.ViewHolder) view.getTag();
        if (prefhelper.isLanguageArabic()) {
           // view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewHolder.txt_jobNotification.setText(entity.getArmessage() + "");
        } else {
           // view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            viewHolder.txt_jobNotification.setText(entity.getMessage() + "");
        }
        viewHolder.iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (entity.getActionType()) {
                    case "job":
                        break;
                }
            }
        });
    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView iv_Notificationlogo;
        private AnyTextView txt_jobNotification;
        private ImageView iv_next;

        public ViewHolder(View view) {
            iv_Notificationlogo = (ImageView) view.findViewById(R.id.iv_Notificationlogo);
            txt_jobNotification = (AnyTextView) view.findViewById(R.id.txt_jobNotification);
            iv_next = (ImageView) view.findViewById(R.id.iv_next);
        }
    }
}
