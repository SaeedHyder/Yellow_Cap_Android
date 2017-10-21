package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.TechInProgressEnt;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.helpers.BasePreferenceHelper;
import com.app.yellowcap.helpers.DateHelper;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.CustomRatingBar;

/**
 * Created by saeedhyder on 5/22/2017.
 */

public class CompletedJobsBinder extends ViewBinder<TechInProgressEnt> {
    private BasePreferenceHelper prefHelper;

    public CompletedJobsBinder(BasePreferenceHelper preferenceHelper) {
        super(R.layout.completedjobs_item);
        this.prefHelper = preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new CompletedJobsBinder.ViewHolder(view);
    }

    @Override
    public void bindView(TechInProgressEnt entity, int position, int grpPosition, View view, Activity activity) {
        final CompletedJobsBinder.ViewHolder viewHolder = (CompletedJobsBinder.ViewHolder) view.getTag();
        if (prefHelper.isLanguageArabic()) {
            viewHolder.root_layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            viewHolder.root_layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        viewHolder.rbAddRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewHolder.rbAddRating.setFocusable(false);
                return true;
            }
        });
        viewHolder.txt_jobNoText.setText(String.valueOf(position + 1));
       // viewHolder.txt_jobCompletedText.setText(entity.getRequest_detail().getDate());
        if (!prefHelper.isLanguageArabic()) {
            viewHolder.txt_jobCompletedText.setText(DateHelper.dateFormat(entity.getRequest_detail().getDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
        } else {
            viewHolder.txt_jobCompletedText.setText(entity.getRequest_detail().getDate() + "");
        }
        if (entity.getRequest_detail().getServics_list().size() > 0) {
            if (prefHelper.isLanguageArabic()) {
                viewHolder.txt_JobTitleText.setText(entity.getRequest_detail().getServics_list().get(0).getService_detail().getAr_title());
            } else {
                viewHolder.txt_JobTitleText.setText(entity.getRequest_detail().getServics_list().get(0).getService_detail().getTitle());
            }
        }
        if (entity.getRequest_detail().getUser_detail() != null) {
            viewHolder.txt_clientNameText.setText(entity.getRequest_detail().getUser_detail().getFull_name());
        }
        if (entity.getRequest_detail().getFeedbackdetail() != null) {
            viewHolder.rbAddRating.setScore(entity.getRequest_detail().getFeedbackdetail().getRate());
        }
        viewHolder.txt_EarningText.setText(view.getContext().getResources().getString(R.string.aed) + " " + entity.getRequest_detail().getTotal_amount());

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
        private LinearLayout root_layout;

        public ViewHolder(View view) {

            txt_jobNoText = (AnyTextView) view.findViewById(R.id.txt_jobNoText);
            txt_jobCompletedText = (AnyTextView) view.findViewById(R.id.txt_jobCompletedText);
            txt_JobTitleText = (AnyTextView) view.findViewById(R.id.txt_JobTitleText);
            txt_clientNameText = (AnyTextView) view.findViewById(R.id.txt_clientNameText);
            rbAddRating = (CustomRatingBar) view.findViewById(R.id.rbAddRating);
            txt_EarningText = (AnyTextView) view.findViewById(R.id.txt_EarningText);
            root_layout = (LinearLayout) view.findViewById(R.id.root_layout);
            txt_jobNo = (AnyTextView) view.findViewById(R.id.txt_jobNo);
            txt_jobCompleted = (AnyTextView) view.findViewById(R.id.txt_jobCompleted);
            txt_JobTitle = (AnyTextView) view.findViewById(R.id.txt_JobTitle);
            txt_clientName = (AnyTextView) view.findViewById(R.id.txt_ClientName);
            txt_Rating = (AnyTextView) view.findViewById(R.id.txt_Rating);
            txt_Earning = (AnyTextView) view.findViewById(R.id.txt_Earning);

        }
    }
}
