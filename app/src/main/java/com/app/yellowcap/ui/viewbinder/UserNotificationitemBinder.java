package com.app.yellowcap.ui.viewbinder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.DockActivity;
import com.app.yellowcap.entities.NotificationEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.fragments.QuotationFragment;
import com.app.yellowcap.fragments.UserHomeFragment;
import com.app.yellowcap.fragments.UserJobsFragment;
import com.app.yellowcap.helpers.BasePreferenceHelper;
import com.app.yellowcap.helpers.DialogHelper;
import com.app.yellowcap.helpers.InternetHelper;
import com.app.yellowcap.helpers.UIHelper;
import com.app.yellowcap.retrofit.WebService;
import com.app.yellowcap.ui.viewbinders.abstracts.ViewBinder;
import com.app.yellowcap.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 5/24/2017.
 */

public class UserNotificationitemBinder extends ViewBinder<NotificationEnt> {

    private WebService service;
    private ImageLoader imageLoader;
    private DockActivity dockActivity;
    private BasePreferenceHelper prefhelper;

    public UserNotificationitemBinder(DockActivity activity, WebService webservice, BasePreferenceHelper prefhelper) {
        super(R.layout.newjobs_item);
        this.dockActivity = activity;
        this.service = webservice;
        this.prefhelper = prefhelper;
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new UserNotificationitemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final NotificationEnt entity, int position, int grpPosition, View view, Activity activity) {

        UserNotificationitemBinder.ViewHolder viewHolder = (UserNotificationitemBinder.ViewHolder) view.getTag();
        viewHolder.txt_jobNotification.setText(entity.getMessage());
        viewHolder.mainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (entity.getActionType()) {
                    case "Job":
                        dockActivity.popBackStackTillEntry(1);
                        dockActivity.replaceDockableFragment(UserJobsFragment.newInstance(), "UserJobsFragment");
                        break;
                    case "Quotation":
                        dockActivity.replaceDockableFragment(QuotationFragment.newInstance(entity), "QuotationFragment");
                        break;
                    case "feedback":
                        openRatingPopup(entity);
                        break;
                }
            }
        });
    }

    private void openRatingPopup(final NotificationEnt entity) {
        String message="";
        String title="";
        if (entity.getRequestDetail().getServicsList().size()>0){
            if(!prefhelper.isLanguageArabic()){
            message = entity.getRequestDetail().getServicsList().get(0).getServiceEnt().getTitle();}
            else{
                message = entity.getRequestDetail().getServicsList().get(0).getServiceEnt().getArTitle();
            }
        }
        if(!prefhelper.isLanguageArabic()){
            title=entity.getRequestDetail().getServiceDetail().getTitle();
        }
        else {
            title=entity.getRequestDetail().getServiceDetail().getArTitle();
        }
        final DialogHelper dialogHelper = new DialogHelper(dockActivity);
        dialogHelper.initRatingDialog(R.layout.rating_pop_up_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dialogHelper.hideDialog();
                if (InternetHelper.CheckInternetConectivityandShowToast(dockActivity)) {
                    submitFeedback(entity, dialogHelper);
                }
            }
        }, title,message
                );
        dialogHelper.setCancelable(true);
        dialogHelper.showDialog();
    }

    private void submitFeedback(NotificationEnt ent, final DialogHelper helper) {
        Call<ResponseWrapper> call =  service.sendFeedback(prefhelper.getUserId(),
                String.valueOf(ent.getRequestDetail().getId()),
               String.valueOf( ent.getRequestDetail().getAssign_technician_details().getTechnicianId()),
                Math.round(helper.getRating(R.id.rbAddRating)),
                helper.getEditText(R.id.txt_feedback),
                helper.getEditText(R.id.txt_tip));

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                helper.hideDialog();
                if (response.body().getResponse().equals("2000")) {
                    dockActivity.popBackStackTillEntry(0);
                   dockActivity.replaceDockableFragment(UserHomeFragment.newInstance(), "UserHomeFragment");
                } else {
                    UIHelper.showShortToastInCenter(dockActivity, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                helper.hideDialog();
                Log.e("EntryCodeFragment", t.toString());
               // UIHelper.showShortToastInCenter(dockActivity, t.toString());
            }
        });
    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView iv_Notificationlogo;
        private AnyTextView txt_jobNotification;
        private ImageView iv_next;
        private LinearLayout mainFrame;

        public ViewHolder(View view) {
            iv_Notificationlogo = (ImageView) view.findViewById(R.id.iv_Notificationlogo);
            txt_jobNotification = (AnyTextView) view.findViewById(R.id.txt_jobNotification);
            iv_next = (ImageView) view.findViewById(R.id.iv_next);
            mainFrame=(LinearLayout) view.findViewById(R.id.ll_mainFrame);
        }
    }
}
