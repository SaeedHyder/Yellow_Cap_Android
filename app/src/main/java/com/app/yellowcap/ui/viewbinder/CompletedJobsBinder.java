package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.CompletedJobsEnt;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.CustomRatingBar;

import static com.app.yellowcap.R.id.txt_Rating;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class CompletedJobsBinder extends ViewBinder<CompletedJobsEnt> {

    public CompletedJobsBinder() {
        super(R.layout.completedjobs_item);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new CompletedJobsBinder.ViewHolder(view);
    }

    @Override
    public void bindView(CompletedJobsEnt entity, int position, int grpPosition, View view, Activity activity) {
        final CompletedJobsBinder.ViewHolder viewHolder = (CompletedJobsBinder.ViewHolder) view.getTag();
       ;
        viewHolder.rbAddRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewHolder. rbAddRating.setFocusable(false);
                return true;
            }
        });

        setTextStyle(viewHolder);

    }

    private void setTextStyle(ViewHolder viewHolder) {
        viewHolder.txt_jobNo.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_jobCompleted.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_JobTitle.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_clientName.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_Rating.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_Earning.setTypeface(null, Typeface.BOLD);
    }

     public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txt_jobNoText;
        private AnyTextView txt_jobCompletedText;
         private AnyTextView txt_JobTitleText;
        private AnyTextView txt_clientNameText;
         private CustomRatingBar rbAddRating;
         private AnyTextView txt_EarningText;

         private AnyTextView txt_jobNo;
         private AnyTextView txt_jobCompleted;
         private AnyTextView txt_JobTitle;
         private AnyTextView txt_clientName;
         private AnyTextView txt_Rating;
         private AnyTextView txt_Earning;

        public ViewHolder(View view) {

            txt_jobNoText = (AnyTextView) view.findViewById(R.id.txt_jobNoText);
            txt_jobCompletedText = (AnyTextView) view.findViewById(R.id.txt_jobCompletedText);
            txt_JobTitleText = (AnyTextView) view.findViewById(R.id.txt_JobTitleText);
            txt_clientNameText = (AnyTextView) view.findViewById(R.id.txt_clientNameText);
            rbAddRating = (CustomRatingBar) view.findViewById(R.id.rbAddRating);
            txt_EarningText = (AnyTextView) view.findViewById(R.id.txt_EarningText);

            txt_jobNo = (AnyTextView) view.findViewById(R.id.txt_jobNo);
            txt_jobCompleted = (AnyTextView) view.findViewById(R.id.txt_jobCompleted);
            txt_JobTitle = (AnyTextView) view.findViewById(R.id.txt_JobTitle);
            txt_clientName = (AnyTextView) view.findViewById(R.id.txt_ClientName);
            txt_Rating = (AnyTextView) view.findViewById(R.id.txt_Rating);
            txt_Earning = (AnyTextView) view.findViewById(R.id.txt_Earning);

        }
    }
}
