package com.app.yellowcap.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.yellowcap.R;
import com.app.yellowcap.activities.MainActivity;
import com.app.yellowcap.entities.ResponseWrapper;

import com.app.yellowcap.entities.countEnt;
import com.app.yellowcap.global.AppConstants;
import com.app.yellowcap.global.WebServiceConstants;
import com.app.yellowcap.helpers.BasePreferenceHelper;
import com.app.yellowcap.helpers.NotificationHelper;
import com.app.yellowcap.retrofit.WebService;
import com.app.yellowcap.retrofit.WebServiceFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //handleNotification(remoteMessage.getNotification().getBody());
            handleDataMessage(remoteMessage);
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
              /*  JSONObject json = new JSONObject(remoteMessage.getData().toString());
                Log.e(TAG, "DATA: " + json);*/
               handleDataMessage(remoteMessage);

        }
    }

    private void handleNotification(String message) {
        if (!NotificationHelper.getInstance().isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationHelper.getInstance().playNotificationSound(getApplicationContext());
        } else {

            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(final RemoteMessage messageBody) {


            webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(getApplicationContext(),
                    WebServiceConstants.SERVICE_URL);
            preferenceHelper = new BasePreferenceHelper(getApplicationContext());
            Call<ResponseWrapper<countEnt>> callback = webservice.getNotificationCount(preferenceHelper.getUserId());
            callback.enqueue(new Callback<ResponseWrapper<countEnt>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<countEnt>> call, Response<ResponseWrapper<countEnt>> response) {
                    ;
                    preferenceHelper.setBadgeCount(response.body().getResult().getCount());
                    try {


                        String title = getString(R.string.App_Name);
                        String message = messageBody.getData().get("message");


                        Log.e(TAG, "message: " + message);

                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        resultIntent.putExtra("message", message);
                        resultIntent.putExtra("tapped", true);
                        Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
                        pushNotification.putExtra("message", message);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);
                        showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);

                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e.getMessage());
                    }

                    Log.e(TAG,"aasd"+preferenceHelper.getUserId()+response.body().getResult().getCount());
                    //  SendNotification(response.body().getResult().getCount(), json);
                }

                @Override
                public void onFailure(Call<ResponseWrapper<countEnt>> call, Throwable t) {
                    Log.e(TAG,t.toString());
                    System.out.println(t.toString());
                }
            });



    }

    private void SendNotification(int count, JSONObject json) {

    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationHelper.getInstance().showNotification(context,
                R.drawable.ic_launcher,
                title,
                message,
                timeStamp,
                intent);
    }


}
