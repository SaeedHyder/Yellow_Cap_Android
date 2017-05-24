package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NewJobEnt;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class UserNotificationitemBinder extends ViewBinder<NewJobEnt> {

    private ImageLoader imageLoader;

    public UserNotificationitemBinder() {
        super(R.layout.newjobs_item);

        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new UserNotificationitemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(NewJobEnt entity, int position, int grpPosition, View view, Activity activity) {

        UserNotificationitemBinder.ViewHolder viewHolder = (UserNotificationitemBinder.ViewHolder) view.getTag();

    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView iv_Notificationlogo;
        private AnyTextView txt_jobNotification;
        private ImageView iv_next;

        public ViewHolder(View view) {
            iv_Notificationlogo= (ImageView) view.findViewById(R.id.iv_Notificationlogo);
            txt_jobNotification = (AnyTextView) view.findViewById(R.id.txt_jobNotification);
            iv_next=(ImageView)view.findViewById(R.id.iv_next);
        }
    }
}
