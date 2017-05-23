package com.app.yellowcap.ui.viewbinders.abstracts;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.CompletedJobsItem;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.CustomRatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class CompletedJobsBinder extends ViewBinder<CompletedJobsItem> {

    public CompletedJobsBinder() {
        super(R.layout.completedjobs_item);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new CompletedJobsBinder.ViewHolder(view);
    }

    @Override
    public void bindView(CompletedJobsItem entity, int position, int grpPosition, View view, Activity activity) {

    }

     public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txt_jobNoText;
        private AnyTextView txt_jobCompletedText;
        private AnyTextView txt_clientNameText;
         private CustomRatingBar rbAddRating;
         private AnyTextView txt_EarningText;

        public ViewHolder(View view) {

            txt_jobNoText = (AnyTextView) view.findViewById(R.id.txt_jobNoText);
            txt_jobCompletedText = (AnyTextView) view.findViewById(R.id.txt_jobCompletedText);
            txt_clientNameText = (AnyTextView) view.findViewById(R.id.txt_clientNameText);
            rbAddRating = (CustomRatingBar) view.findViewById(R.id.rbAddRating);
            txt_EarningText = (AnyTextView) view.findViewById(R.id.txt_EarningText);

        }
    }
}
