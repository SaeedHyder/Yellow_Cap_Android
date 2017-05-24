package com.app.yellowcap.helpers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.app.yellowcap.R;
import com.app.yellowcap.ui.views.AnyEditTextView;

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
    public Dialog initForgotPasswordDialog(int layoutID ,View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button closeButton =(Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
    public Dialog initJobRefusalDialog(int layoutID ,View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button closeButton =(Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
    public Dialog initCancelQuotationDialog(int layoutID ,View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button closeButton =(Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
    public Dialog initCancelJobDialog(int layoutID , View.OnClickListener onokclicklistener ,View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button okbutton =(Button) dialog.findViewById(R.id.btn_ok);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton =(Button) dialog.findViewById(R.id.btn_cancle);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }
    public Dialog initRequestSendDialog(int layoutID ,View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button closeButton =(Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
    public Dialog initJobDetailDialog(int layoutID ,View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button closeButton =(Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
    public Dialog initRatingDialog(int layoutID ,View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(layoutID);
        Button closeButton =(Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
    public String getEditText(int editTextID){
        AnyEditTextView editTextView = (AnyEditTextView)dialog.findViewById(editTextID);
        return  editTextView.getText().toString();
    }
    public void showDialog(){
        dialog.show();
    }
    public void hideDialog(){
        dialog.dismiss();
    }
}
