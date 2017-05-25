package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.text.Html;
import android.view.View;

import com.app.yellowcap.R;
import com.app.yellowcap.entities.UserComleteJobsEnt;
import com.app.yellowcap.fragments.abstracts.BaseFragment;
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
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.txtJobNoText.setText(entity.getJob());
        viewHolder.txtJobTitleText.setText(entity.getJobTitle());
        viewHolder.txtClientNameText.setText(entity.getTechnicianName());
        viewHolder.txtEarningText.setText(entity.getAmountPaid());
        String sourceString = "<b>" + "Description:" + "</b> " + entity.getDescription();
        viewHolder.txtDescriptionText.setText(Html.fromHtml(sourceString));
       viewHolder.rbAddRating.setScore(entity.getRating());

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
