package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.UserComleteJobsEnt;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.CustomRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/25/2017.
 */

public class UserCompleteJobsBinder extends ViewBinder<UserComleteJobsEnt> {
    public UserCompleteJobsBinder() {
        super(R.layout.row_item_user_complete_jobs);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UserComleteJobsEnt entity, int position, int grpPosition, View view, Activity activity) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txtJobNoText.setText(String.valueOf(position+1));
        if (entity.getServicsList().size() > 0)
            viewHolder.txtJobTitleText.setText(entity.getServicsList().get(0).getServiceEnt().getTitle());
        if (entity.getAssignTechnician()!=null)
            viewHolder.txtClientNameText.setText(entity.getAssignTechnician().getTechnicianDetail().getFullName());
        viewHolder.txtEarningText.setText(entity.getTotal_amount());
        String sourceString = "<b>" + "Description:" + "</b> " + entity.getDiscription();
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
