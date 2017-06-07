package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.RequestTechnicianEnt;
import com.app.yellowcap.entities.UserInProgressEnt;
import com.app.yellowcap.fragments.RequestServiceFragment;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.CallUser;
import com.app.yellowcap.interfaces.onCancelJobListner;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/25/2017.
 */

public class UserInProgressBinder extends ViewBinder<UserInProgressEnt> {
    DockActivity context;
    CallUser callUser;
    onCancelJobListner onCancelJobListner;

    public UserInProgressBinder(CallUser callUser, DockActivity context, onCancelJobListner onCancelJobListner) {
        super(R.layout.row_item_user_inprogress);
        this.callUser = callUser;
        this.context = context;
        this.onCancelJobListner = onCancelJobListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new InProgressViewHolder(view);
    }

    @Override
    public void bindView(final UserInProgressEnt entity, final int position, int grpPosition, View view, Activity activity) {
        InProgressViewHolder viewHolder = (InProgressViewHolder) view.getTag();
        RequestTechnicianEnt technicianEnt = null;
        if (entity.getAssignTechnician().size() > 0) {
            technicianEnt = entity.getAssignTechnician().get(0);
        }
        if (technicianEnt != null) {
            viewHolder.ivEditBtn.setVisibility(View.GONE);
            viewHolder.txtTechNameText.setText(technicianEnt.getTechnicianDetail().getFullName());
            viewHolder.txtNumberText.setText(technicianEnt.getTechnicianDetail().getPhoneNo());
            viewHolder.btnCallUser.setBackground(context.getResources().getDrawable(R.drawable.button_background));
        } else {
            viewHolder.ivEditBtn.setVisibility(View.VISIBLE);
            viewHolder.txtTechNameText.setText(context.getString(R.string.no_technician_error));
            viewHolder.txtNumberText.setText(context.getString(R.string.no_number_tech));
            viewHolder.btnCallUser.setBackground(context.getResources().getDrawable(R.drawable.button_blackbackground));

        }
        if (entity.getServicsList().size() > 0)
            viewHolder.txtJobTitleText.setText(entity.getServicsList().get(0).getServiceEnt().getTitle());
        if (entity.getTotal().equals("")) {
            viewHolder.txtAmountText.setText(context.getString(R.string.aed) + " " + entity.getEstimateFrom());
        } else {
            viewHolder.txtAmountText.setText(context.getString(R.string.aed) + " " + entity.getTotal());

        }


        viewHolder.ivEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getServiceDetail() != null)
                    context.replaceDockableFragment(RequestServiceFragment.newInstance(entity.getServiceDetail(), entity), "RequestServiceFragment");
            }
        });
        viewHolder.btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelJobListner.onCancelJob(position);

            }
        });
        final RequestTechnicianEnt finalTechnicianEnt = technicianEnt;
        viewHolder.btnCallUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalTechnicianEnt != null) {
                    callUser.CallOnUserNumber(finalTechnicianEnt.getTechnicianDetail().getPhoneNo());
                } else {
                    UIHelper.showShortToastInCenter(context, context.getString(R.string.assignText));
                }
            }
        });

    }

    public static class InProgressViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_tech_name_Text)
        AnyTextView txtTechNameText;
        @BindView(R.id.iv_editBtn)
        ImageView ivEditBtn;
        @BindView(R.id.txt_JobTitleText)
        AnyTextView txtJobTitleText;
        @BindView(R.id.txt_amountText)
        AnyTextView txtAmountText;
        @BindView(R.id.txt_number_text)
        AnyTextView txtNumberText;
        @BindView(R.id.btn_callUser)
        Button btnCallUser;
        @BindView(R.id.btn_cancelJob)
        Button btnCancelJob;

        public InProgressViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
