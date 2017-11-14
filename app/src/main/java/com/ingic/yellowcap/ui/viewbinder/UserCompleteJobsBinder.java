package com.ingic.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.yellowcap.R;
import com.ingic.yellowcap.entities.UserComleteJobsEnt;
import com.ingic.yellowcap.global.AppConstants;
import com.ingic.yellowcap.helpers.BasePreferenceHelper;
import com.ingic.yellowcap.helpers.DateHelper;
import com.ingic.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.yellowcap.ui.views.AnyTextView;
import com.ingic.yellowcap.ui.views.CustomRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/25/2017.
 */

public class UserCompleteJobsBinder extends ViewBinder<UserComleteJobsEnt> {
    private BasePreferenceHelper preferenceHelper;

    public UserCompleteJobsBinder(BasePreferenceHelper prefhelper) {
        super(R.layout.row_item_user_complete_jobs);
        preferenceHelper = prefhelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UserComleteJobsEnt entity, int position, int grpPosition, View view, Activity activity) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        if(preferenceHelper.isLanguageArabic()){
            viewHolder.root_layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        else
            viewHolder.root_layout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        viewHolder.txtJobNoText.setText(String.valueOf(position + 1));
        if (entity.getServicsList().size() > 0) {
            if (preferenceHelper.isLanguageArabic()) {
                viewHolder.txtJobTitleText.setText(entity.getServicsList().get(0).getServiceEnt().getArTitle()+"");
            } else {
                viewHolder.txtJobTitleText.setText(entity.getServicsList().get(0).getServiceEnt().getTitle()+"");
            }
        }
        if (!preferenceHelper.isLanguageArabic()) {
            viewHolder.txtJobCompletedText.setText(DateHelper.dateFormat(entity.getDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
        } else {
            viewHolder.txtJobCompletedText.setText(entity.getDate()+ "");
        }
        if (entity.getAssign_technician_details() != null)
            viewHolder.txtClientNameText.setText(entity.getAssign_technician_details().getTechnician_details().getFullName());
        viewHolder.txtEarningText.setText(entity.getTotal_amount());
        String sourceString = "<b>" + view.getContext().getResources().getString(R.string.description) + "</b> " +"   "+ entity.getDiscription();
        viewHolder.txtDescriptionText.setText(Html.fromHtml(sourceString));
        if (entity.getFeedbackdetail() != null)
            viewHolder.rbAddRating.setScore(entity.getFeedbackdetail().getRate());
        viewHolder.rbAddRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewHolder.rbAddRating.setFocusable(false);
                return true;
            }
        });

    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_jobNoText)
        AnyTextView txtJobNoText;
        @BindView(R.id.txt_jobCompletedText)
        AnyTextView txtJobCompletedText;
        @BindView(R.id.txt_clientNameText)
        AnyTextView txtClientNameText;
        @BindView(R.id.txt_JobTitleText)
        AnyTextView txtJobTitleText;
        @BindView(R.id.rbAddRating)
        CustomRatingBar rbAddRating;
        @BindView(R.id.txt_EarningText)
        AnyTextView txtEarningText;
        @BindView(R.id.txt_description_text)
        AnyTextView txtDescriptionText;
        @BindView(R.id.root_layout)
        LinearLayout root_layout;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
