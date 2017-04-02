package com.michal_stasinski.distrada.Menu.RightMenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationCreator extends BaseMenu {
    private TextView txtRegId, txtMessage;
    private EditText eTitle, eMessage;
    private OkHttpClient mClient;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private TextView switchStatus;
    private Switch mySwitch;
    private Boolean messageWithSound =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_menu);
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.right_notification_creator);
        View inflated = stub.inflate();

        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

        displayFirebaseRegId();
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.piec_view);

        Button not = (Button) findViewById(R.id.send_notification);
        eTitle = (EditText) findViewById(R.id.editText_title);
        eMessage = (EditText) findViewById(R.id.editText_message);

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(TextUtils.isEmpty(eTitle.getText()) || TextUtils.isEmpty(eMessage.getText())) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NotificationCreator.this);
                    alertDialogBuilder.setTitle("UWAGA");
                    alertDialogBuilder
                            .setMessage("Nie wypełniłeś wszystkich wymaganych pół")
                            .setCancelable(false)
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NotificationCreator.this);
                    alertDialogBuilder.setTitle("UWAGA");
                    alertDialogBuilder
                            .setMessage("Na pewno chcesz wysłać wiadomość?")
                            .setCancelable(false)
                            .setPositiveButton("TAK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();//add your user refresh tokens who are logged in with firebase.
                                    JSONArray jsonArray = new JSONArray();
                                    jsonArray.put(refreshedToken);
                                    sendMessage(jsonArray, eTitle.getText().toString(), eMessage.getText().toString(), "Wiadomość");
                                    eTitle.setText("");
                                    eMessage.setText("");
                                }
                            })
                            .setNegativeButton("NIE",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ShortcutBadger.applyCount(getApplicationContext(), 1);
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {

                    String message = intent.getStringExtra("message");
                    Toast.makeText(NotificationCreator.this, "Push notification: " + message, Toast.LENGTH_LONG).show();
                    // txtMessage.setText(message);
                }
            }
        };
        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.switch_button);

        //set the switch to ON
        mySwitch.setChecked(false);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                messageWithSound = isChecked;
                if(isChecked){
                   // switchStatus.setText("Switch is currently ON");
                }else{
                    //switchStatus.setText("Switch is currently OFF");
                }

            }
        });
        switchStatus.setText("");
        //check the current state before we display the screen
        if(mySwitch.isChecked()){
           // switchStatus.setText("Switch is currently ON");
        }
        else {
            //switchStatus.setText("Switch is currently OFF");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText("POWIADOMIENIA");
    }

    private void displayFirebaseRegId() {

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        txtRegId = (TextView) findViewById(R.id.text_device_id);
        // txtMessage = (TextView) findViewById(R.id.editText_message);
        if (!TextUtils.isEmpty(regId)) {
            //txtRegId.setText("Firebase Reg Id: " + regId);
        } else {
           // txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(NotificationCreator.this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(NotificationCreator.this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(NotificationCreator.this);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(NotificationCreator.this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String message) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {

                String badge = ("1");
                String sound = ("default");
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", R.mipmap.app_icon);
                    if(messageWithSound) {
                         notification.put("sound",sound);
                    }
                    notification.put("badge", badge);
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
                    Toast.makeText(NotificationCreator.this, "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(NotificationCreator.this, "Wiadomośc nie została wsyłana, Unknown error occurred.", Toast.LENGTH_LONG).show();
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
                .addHeader("Authorization", "key=AIzaSyDYOkZ1lZLeLvOcproToZioVGHwQ5qgQjw")
                //.addHeader("Authorization", "key=AAAAYRSopWU:APA91bFCG1dR9l_JsJtN_ZQp2QA8Bnn2xqws4-lOwNrIW96yck2E9lz8psPT97IxHKdVcqBwB0tus_3bAqipDF35CcXuMn730mcCefIwxNdXMKTeNDny3B-jRt9aqb0-V7vEWdr6l-Bf")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


}
