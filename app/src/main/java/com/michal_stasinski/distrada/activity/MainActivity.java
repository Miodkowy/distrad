package com.michal_stasinski.distrada.activity;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.Blog.BlogFragment;
import com.michal_stasinski.distrada.Contact.ContactFragment;
import com.michal_stasinski.distrada.CustomDrawerAdapter;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.app.Config;
import com.michal_stasinski.distrada.menu.MenuFragment;
import com.michal_stasinski.distrada.utils.BounceListView;
import com.michal_stasinski.distrada.utils.NotificationUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private BounceListView mListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;

    private String[] largeTextArr = {
            "head",
            "AKTUALNOŚCI",
            "KONTAKT",
            "PIZZA",
            "STARTERY",
            "SAŁATKI",
            "ZUPY",
            "MAKARONY",
            "MAKARONY ZAPIEKANE",
            "DRUGIE DANIA",
            "NAPOJE i DESERY",
            "POWIADOMIENIA"
    };

    private String[] smallTextArr = {
            "head",
            "NOTIZIE",
            "CONTATTO",
            "PIZZA",
            "ANTIPASTI",
            "SALATE",
            "ZUPPE",
            "PASTE",
            "PASTE AL FORNO",
            "SECONDI PIATTI",
            "DOLCE E BEVANDE",
            "POWIADOMIENIA"
    };
    private int[] colorToolBar = {
            R.color.color_PIZZA,
            R.color.color_AKTUALNOSCI,
            R.color.color_AKTUALNOSCI,
            R.color.color_PIZZA,
            R.color.color_STARTERY,
            R.color.color_SALATKI,
            R.color.color_ZUPY,
            R.color.color_MAKARONY,
            R.color.color_ALFORNO,
            R.color.color_DRUGIE_DANIE,
            R.color.color_DESERY,
            R.color.color_DESERY
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // orientacia w pione
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e(TAG, "onReceive  maina________________________________________ ");
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                }
            }
        };
/*
        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e(TAG, "onReceive________________________________________ ");
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    //displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };
*/
        //displayFirebaseRegId();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);


        // mDrawerLayout.openDrawer(GravityCompat.START,true);
        mDrawerLayout.setBackgroundResource(R.mipmap.pizza_view);
        //wyłaczenie shadow
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.color_DRAWER_SHADOW));
        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToggle.syncState();

        mListView = (BounceListView) findViewById(R.id.left_drawer);
        Integer[] imgid = {
                R.mipmap.news_icon,
                R.mipmap.news_icon,
                R.mipmap.contact_icon,
                R.mipmap.pizza_icon,
                R.mipmap.starter_icon,
                R.mipmap.salatka_icon,
                R.mipmap.zupa_icon,
                R.mipmap.pasta_icon,
                R.mipmap.alforno_icon,
                R.mipmap.drugie_danie_icon,
                R.mipmap.drink_icon,
                R.mipmap.drink_icon

        };

        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, largeTextArr, smallTextArr, imgid);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                FragmentManager fragmentManager = getFragmentManager();
                Log.d("MyApp", "positionr____________________________________________________________ " + position);
                if (position > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putInt("colorFragement", colorToolBar[position]);
                    mToolBar.setBackgroundResource(colorToolBar[position]);
                    TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
                    TextView colorShape = (TextView) findViewById(R.id.positionInList);

                    toolBarTitle.setText((largeTextArr[position]).toString());

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START, true);

                    if (position != 1 && position != 2 && position != 11) {

                        MenuFragment fragobj = new MenuFragment();
                        fragobj.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragobj).commit();

                    }

                    if (position == 2) {

                        // NotificationFragment notiObj = new NotificationFragment();
                        // notiObj.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
                        // fragmentManager.beginTransaction().replace(R.id.content_frame, notiObj).commit();

                    }

                    if (position == 1) {

                        // NotificationFragment notiObj = new NotificationFragment();
                        // notiObj.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new BlogFragment()).commit();
                        // fragmentManager.beginTransaction().replace(R.id.content_frame, notiObj).commit();

                    }

                    if (position == 11) {

                        // NotificationFragment notiObj = new NotificationFragment();
                        // notiObj.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, new NotificationFragment()).commit();
                        // fragmentManager.beginTransaction().replace(R.id.content_frame, notiObj).commit();

                    }
                }
            }
        });

    }

    // to przycisk powrotu  actionbar do głownego menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen

    /*private void displayFirebaseRegId() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}