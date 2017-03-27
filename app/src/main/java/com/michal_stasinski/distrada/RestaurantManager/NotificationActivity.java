package com.michal_stasinski.distrada.RestaurantManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Utils.NotificationUtils;

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

public class NotificationActivity extends AppCompatActivity {
    private TextView txtRegId , txtMessage;
    private EditText eTitle , eMessage;
    private OkHttpClient mClient;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        setContentView(R.layout.activity_notification);
        displayFirebaseRegId();
        Log.i(TAG, "NotificationActivity  -  onCreate");
        // checking for type intent filter

        Button  not = (Button) findViewById(R.id.send_notification);
         eTitle = (EditText) findViewById(R.id.editText_title);
         eMessage = (EditText) findViewById(R.id.editText_message);

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();//add your user refresh tokens who are logged in with firebase.

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(refreshedToken);

                sendMessage(jsonArray, eTitle.getText().toString(),  eMessage.getText().toString(), "Http:\\google.com", "My Name is Vishal");
            }
        });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(NotificationActivity.this, "Push notification: " + message, Toast.LENGTH_LONG).show();
                    txtMessage.setText(message);
                }
            }
        };
    }

    private void displayFirebaseRegId() {

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        txtRegId = (TextView) findViewById(R.id.text_device_id);
        txtMessage = (TextView) findViewById(R.id.text_reciver);
        if (!TextUtils.isEmpty(regId)) {
            txtRegId.setText("Firebase Reg Id: " + regId);
        } else {
            txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(NotificationActivity.this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(NotificationActivity.this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(NotificationActivity.this);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(NotificationActivity.this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon",R.mipmap.app_icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    root.put("notification", notification);
                    root.put("data", data);

                    root.put("to", "/topics/swift_fans_distrada64");
                    String result = postToFCM(root.toString());
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    Toast.makeText(NotificationActivity.this, "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NotificationActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        mClient = new OkHttpClient();

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("Authorization", "key=AAAAYRSopWU:APA91bFCG1dR9l_JsJtN_ZQp2QA8Bnn2xqws4-lOwNrIW96yck2E9lz8psPT97IxHKdVcqBwB0tus_3bAqipDF35CcXuMn730mcCefIwxNdXMKTeNDny3B-jRt9aqb0-V7vEWdr6l-Bf")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


}
