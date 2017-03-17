package com.michal_stasinski.distrada.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.app.Config;
import com.michal_stasinski.distrada.utils.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.android.gms.internal.zzs.TAG;


public class NotificationFragment extends Fragment {

    View myView;
    private TextView txtRegId, txtMessage;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private OkHttpClient mClient;
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
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



        Button sendNoty = (Button) myView.findViewById(R.id.sendNotification);

        sendNoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();//add your user refresh tokens who are logged in with firebase.

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(refreshedToken);

                sendMessage( jsonArray,"Hello","dział mi wysyłanie z androida jupi","Http:\\google.com","My Name is Vishal");
            }
        });


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

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {
        Log.d("MyApp","fragementColor________________seeennnnnnnnnnnnnnnnnnnnn___________________________________________ " );
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    root.put("notification", notification);
                    root.put("data", data);
                   // root.put("registration_ids", recipients);
                    root.put("to","/topics/swift_fans_distrada64");
                    String result = postToFCM(root.toString());
                    Log.d("MyApp","fragementColor________________poszlooooooooooooooooooooooo____________________________________________ " );
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Log.d("MyApp","fragementColor________________dupa____________________________________________ " );
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    Toast.makeText(myView.getContext(), "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(myView.getContext(), "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        Log.d("postToFCM", "fmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm: ");
         mClient = new OkHttpClient();

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=AIzaSyDYOkZ1lZLeLvOcproToZioVGHwQ5qgQjw")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

}
