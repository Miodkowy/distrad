package com.michal_stasinski.distrada.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.app.Config;
import com.michal_stasinski.distrada.utils.NotificationUtils;

import static com.google.android.gms.internal.zzs.TAG;


public class NotificationFragment extends Fragment {

    View myView;
    private TextView txtRegId, txtMessage;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public NotificationFragment() {

        Log.e(TAG, "constructor _______________________________________ ");
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e(TAG, "onReceive________________________________________ ");
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(myView.getContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "oncreate _______________________________________ ");

    }

    private void displayFirebaseRegId() {
        Log.e(TAG, "displayFirebaseRegId_______________________________________ " + myView);
        SharedPreferences pref = myView.getContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        txtRegId = (TextView) myView.findViewById(R.id.txt_reg_id1);
        txtMessage = (TextView) myView.findViewById(R.id.txt_push_message1);
        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            Log.e(TAG, "Firebase reg id: " + regId);
            txtRegId.setText("Firebase Reg Id: " + regId);
        } else {
            txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // checking for type intent filter
        myView = inflater.inflate(R.layout.fragment_notification, container, false);

        Log.e(TAG, "create view_______________________________________ " + myView);
        displayFirebaseRegId();
        return myView;
    }


    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(myView.getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(myView.getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(myView.getContext());
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(myView.getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
