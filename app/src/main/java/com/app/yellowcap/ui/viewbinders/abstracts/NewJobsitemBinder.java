package com.app.yellowcap.ui.viewbinders.abstracts;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.NewJobItem;
import com.app.yellowcap.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class NewJobsitemBinder  extends ViewBinder<NewJobItem> {

    private ImageLoader imageLoader;

    public NewJobsitemBinder() {
        super(R.layout.newjobs_item);

        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new NewJobsitemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(NewJobItem entity, int position, int grpPosition, View view, Activity activity) {

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
