package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.InProgressEnt;
import com.app.yellowcap.fragments.EditJobTechFragment;
import com.app.yellowcap.fragments.HomeFragment;
import com.app.yellowcap.interfaces.CallUser;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class InProgressBinder extends ViewBinder<InProgressEnt> {

    DockActivity context;
    CallUser callUser;

    public InProgressBinder(CallUser callUser, DockActivity context) {
        super(R.layout.inprogress_item);
        this.callUser=callUser;
        this.context=context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new InProgressBinder.ViewHolder(view);
    }

    @Override
    public void bindView(InProgressEnt entity, int position, int grpPosition, View view, Activity activity) {

        InProgressBinder.ViewHolder viewHolder = (InProgressBinder.ViewHolder) view.getTag();

        setTextStyle(viewHolder);

        viewHolder.btn_callUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUser.CallOnUserNumber("00000");
            }
        });

        viewHolder.iv_editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(EditJobTechFragment.newInstance(), "EditJobTechFragment");
            }
        });

        viewHolder.btn_addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(EditJobTechFragment.newInstance(), "EditJobTechFragment");
            }
        });
        viewHolder.btn_markAsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
            }
        });

    }

    private void setTextStyle(ViewHolder viewHolder) {
        viewHolder.txt_jobNo.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_jobPosted.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_clientName.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_JobTitle.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_Earning.setTypeface(null, Typeface.BOLD);
        viewHolder.txt_Address.setTypeface(null, Typeface.BOLD);
    }

    public static class ViewHolder extends BaseViewHolder {

        private AnyTextView txt_jobNoText;
        private AnyTextView txt_jobPostedText;
        private AnyTextView txt_clientNameText;
        private AnyTextView txt_JobTitleText;
        private AnyTextView txt_EarningText;
        private AnyTextView txt_AddressText;
        private Button btn_callUser;
        private Button btn_addJob;
        private Button btn_markAsComplete;
        private ImageView iv_editBtn;

        private AnyTextView txt_jobNo;
        private AnyTextView txt_jobPosted;
        private AnyTextView txt_clientName;
        private AnyTextView txt_JobTitle;
        private AnyTextView txt_Earning;
        private AnyTextView txt_Address;

        public ViewHolder(View view) {

            txt_jobNoText = (AnyTextView) view.findViewById(R.id.txt_jobNoText);
            txt_jobPostedText = (AnyTextView) view.findViewById(R.id.txt_jobPostedText);
            txt_clientNameText = (AnyTextView) view.findViewById(R.id.txt_clientNameText);
            txt_JobTitleText = (AnyTextView) view.findViewById(R.id.txt_JobTitleText);
            txt_EarningText = (AnyTextView) view.findViewById(R.id.txt_EarningText);
            txt_AddressText = (AnyTextView) view.findViewById(R.id.txt_AddressText);
            btn_callUser = (Button) view.findViewById(R.id.btn_callUser);
            btn_addJob = (Button) view.findViewById(R.id.btn_addJob);
            btn_markAsComplete = (Button) view.findViewById(R.id.btn_markAsComplete);
            iv_editBtn = (ImageView) view.findViewById(R.id.iv_editBtn);

            txt_jobNo = (AnyTextView) view.findViewById(R.id.txt_jobNo);
            txt_jobPosted = (AnyTextView) view.findViewById(R.id.txt_jobPosted);
            txt_clientName = (AnyTextView) view.findViewById(R.id.txt_ClientName);
            txt_JobTitle = (AnyTextView) view.findViewById(R.id.txt_JobTitle);
            txt_Earning = (AnyTextView) view.findViewById(R.id.txt_Earning);
            txt_Address = (AnyTextView) view.findViewById(R.id.txt_Address);

        }
    }
}
