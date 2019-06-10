package com.app.driverpannel.Constants;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onesignal.NotificationRestoreService;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAppliction extends Application {

    private static final String TAG = MyAppliction.class.getSimpleName();
    SharedPreferences.Editor prefEditor;
    private static MyAppliction mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        prefEditor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new OneSignalNotificationHandler())
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }


    public static synchronized MyAppliction getInstance(){
        return  mInstance;
    }



    class OneSignalNotificationHandler implements  OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;

            if(data != null && data.has("Foo")){

                prefEditor.putString("NotificationData",data.optString("Foo")).apply();
            }
        }
    }
}
