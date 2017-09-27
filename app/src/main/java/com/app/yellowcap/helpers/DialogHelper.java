package com.app.yellowcap.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.ui.views.AnyEditTextView;
import com.app.yellowcap.ui.views.AnyTextView;
import com.app.yellowcap.ui.views.CustomRatingBar;

/**
 * Created on 5/24/2017.
 */

public class DialogHelper {
    private Dialog dialog;
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
    }

    public Dialog initForgotPasswordDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }

    public Dialog initJobRefusalDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }

    public Dialog initCancelQuotationDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        AnyEditTextView msg = (AnyEditTextView) dialog.findViewById(R.id.ed_msg);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }

    public AnyEditTextView getEditTextView(int ResID) {
        return (AnyEditTextView) dialog.findViewById(ResID);

    }

    public Dialog logoutDialoge(int layoutID, View.OnClickListener yes, View.OnClickListener no) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button Yes = (Button) dialog.findViewById(R.id.btn_yes);
        Yes.setOnClickListener(yes);
        Button No = (Button) dialog.findViewById(R.id.btn_no);
        No.setOnClickListener(no);
        return this.dialog;
    }

    public Dialog deteleAccountDialoge(int layoutID, View.OnClickListener yes, View.OnClickListener no) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button Yes = (Button) dialog.findViewById(R.id.btn_yes);
        Yes.setOnClickListener(yes);
        Button No = (Button) dialog.findViewById(R.id.btn_no);
        No.setOnClickListener(no);
        return this.dialog;
    }

    public Dialog initCancelJobDialog(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancle);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initRequestSendDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }

    public Dialog initSignUpDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }


    public Dialog initJobDetailDialog(int layoutID, View.OnClickListener onclicklistener, String title, String person_name,
                                      View.OnClickListener arriveclickListener, View.OnClickListener completeclickListener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        AnyTextView txttitle = (AnyTextView) dialog.findViewById(R.id.txt_problem_name);
        txttitle.setText(title);
        AnyTextView txtperson = (AnyTextView) dialog.findViewById(R.id.txt_personname);
        txtperson.setText(person_name);
        AnyTextView arrive = (AnyTextView) dialog.findViewById(R.id.txt_arrval_time);
        arrive.setOnClickListener(arriveclickListener);
        AnyTextView complete = (AnyTextView) dialog.findViewById(R.id.txt_complete_time);
        complete.setOnClickListener(completeclickListener);
        return this.dialog;
    }

    public AnyTextView getTimeTextview(int ID) {
        return (AnyTextView) dialog.findViewById(ID);

    }

    public Dialog initRatingDialog(int layoutID, View.OnClickListener onclicklistener, String title, String message) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        AnyTextView txttitle = (AnyTextView) dialog.findViewById(R.id.txtHeader);
        txttitle.setText(title);
        AnyTextView txtmessage = (AnyTextView) dialog.findViewById(R.id.notwell_tv);
        txtmessage.setText(message);
        final CustomRatingBar rating = (CustomRatingBar) dialog.findViewById(R.id.rbAddRating);
        rating.setOnScoreChanged(new CustomRatingBar.IRatingBarCallbacks() {
            @Override
            public void scoreChanged(float score) {
                if(score<1.0f)
                    rating.setScore(1.0f);
            }
        });
        return this.dialog;
    }

    public float getRating(int ratingBarID) {
        CustomRatingBar ratingBar = (CustomRatingBar) dialog.findViewById(ratingBarID);
        return ratingBar.getScore();
    }

    public String getEditText(int editTextID) {
        AnyEditTextView editTextView = (AnyEditTextView) dialog.findViewById(editTextID);
        KeyboardHide.hideSoftKeyboard(dialog.getContext(), editTextView);
        return editTextView.getText().toString();
    }

    public void showDialog() {

        dialog.show();
    }

    public void setCancelable(boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        dialog.dismiss();
    }
}
