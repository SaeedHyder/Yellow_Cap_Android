package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.UserInProgressEnt;
import com.app.yellowcap.fragments.RequestServiceFragment;
import com.app.yellowcap.fragments.UserHomeFragment;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.interfaces.CallUser;
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

    public UserInProgressBinder(CallUser callUser, DockActivity context) {
        super(R.layout.row_item_user_inprogress);
        this.callUser = callUser;
        this.context = context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new InProgressViewHolder(view);
    }

    @Override
    public void bindView(final UserInProgressEnt entity, int position, int grpPosition, View view, Activity activity) {
        InProgressViewHolder viewHolder = (InProgressViewHolder) view.getTag();
        viewHolder.txtTechNameText.setText(entity.getName());
        viewHolder.txtJobTitleText.setText(entity.getTitle());
        viewHolder.txtAmountText.setText(entity.getQuoteamount());
        viewHolder.txtNumberText.setText(entity.getNumber());
        if (entity.getCallNumber()) {
            viewHolder.btnCallUser.setBackground(context.getResources().getDrawable(R.drawable.button_background));
        } else {
            viewHolder.btnCallUser.setBackground(context.getResources().getDrawable(R.drawable.button_blackbackground));
        }
        viewHolder.ivEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addDockableFragment(RequestServiceFragment.newInstance(), "RequestServiceFragment");
            }
        });
        viewHolder.btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogHelper dialogHelper = new DialogHelper(context);
                dialogHelper.initCancelJobDialog(R.layout.cancle_job_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.addDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                        dialogHelper.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogHelper.hideDialog();
                    }
                });
            }
        });
        viewHolder.btnCallUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getCallNumber()) {
                    callUser.CallOnUserNumber();
                } else {
                    UIHelper.showShortToastInCenter(context, "We will soon assign you a Technician");
                }
            }
        });

    }

    public  static class InProgressViewHolder extends BaseViewHolder {
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

        public  InProgressViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
