package com.michal_stasinski.distrada.Menu.MenuActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.InfoPanel.InfoActivity;
import com.michal_stasinski.distrada.Menu.Adapters.CustomDrawerAdapter;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.RestaurantManager.NewsActivity;
import com.michal_stasinski.distrada.RestaurantManager.NotificationActivity;
import com.michal_stasinski.distrada.RestaurantManager.PasswordActivity;
import com.michal_stasinski.distrada.RestaurantManager.RestaurantManager;
import com.michal_stasinski.distrada.Utils.BounceListView;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BaseMenu extends AppCompatActivity {

    public BounceListView mListViewDrawer;
    public BounceListView mListViewMenu;
    public RelativeLayout mtoolBarLayout;
    public DrawerLayout mDrawerLayout;
    private ImageView imageDrawer; //obrazek pod drawerem
    public ActionBarDrawerToggle mToggle;
    public Toolbar mToolBar;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ImageView imgBackground;
    private LinearLayout content;
    private int color;
    private Boolean choiceActivity;
    public int currentActivity = 0;
    public int choicetActivity = 0;
    public int badgeCount = 0;
    private Boolean loadActivity;

    private Button gotoRestauratManager;
    private Button notificationCreator;
    private Button postCreator;
    private Button logout;

    public String[] largeTextArr = {
            "AKTUALNOŚCI",
            "KONTAKT",
            "PIZZA",
            "STARTERY",
            "SAŁATKI",
            "ZUPY",
            "MAKARONY ZAPIEKANE",
            "MAKARONY",
            "DRUGIE DANIA",
            "NAPOJE i DESERY"
    };

    public String[] smallTextArr = {
            "NOTIZIE",
            "CONTATTO",
            "PIZZA",
            "ANTIPASTI",
            "SALATE",
            "ZUPPE",
            "PASTE AL FORNO",
            "PASTE",
            "SECONDI PIATTI",
            "DOLCE E BEVANDE",
            "POWIADOMIENIA"
    };
    public int[] colorToolBar = {
            R.color.color_AKTUALNOSCI,
            R.color.color_KONTAKTY,
            R.color.color_PIZZA,
            R.color.color_STARTERY,
            R.color.color_SALATKI,
            R.color.color_ZUPY,
            R.color.color_ALFORNO,
            R.color.color_MAKARONY,
            R.color.color_DRUGIE_DANIE,
            R.color.color_DESERY,
    };

    public Integer[] imgid = {
            R.mipmap.news_icon,
            R.mipmap.contact_icon,
            R.mipmap.pizza_icon,
            R.mipmap.starter_icon,
            R.mipmap.salatka_icon,
            R.mipmap.zupa_icon,
            R.mipmap.alforno_icon,
            R.mipmap.pasta_icon,
            R.mipmap.drugie_danie_icon,
            R.mipmap.drink_icon


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_menu);

        badgeCount = 0;
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                badgeCount += 1;
                ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();


        loadActivity = false;
        choiceActivity = false;
        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        mToolBar.setBackgroundResource(colorToolBar[currentActivity]);

        gotoRestauratManager = (Button) findViewById(R.id.open_manager);
        notificationCreator = (Button) findViewById(R.id.notificationCreator);
        postCreator = (Button) findViewById(R.id.postCreator);
        logout = (Button) findViewById(R.id.logout);
        RegisterButtonVisible(Config.ISREGISTER);


        notificationCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), NotificationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });

        postCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), NewsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Config.ISREGISTER = false;
                RegisterButtonVisible(Config.ISREGISTER);
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), NewsMenu.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });


        imageDrawer = (ImageView) findViewById(R.id.pizza_element_back);
        setSupportActionBar(mToolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


        BounceListView v = (BounceListView) findViewById(R.id.left_drawer);
        v.setEnabled(true);
        mDrawerLayout.setEnabled(true);
        // mDrawerLayout.setDrawerShadow(R.drawable.custom_drawer_shape, Gravity.START);
        //mDrawerLayout.openDrawer(GravityCompat.START,true);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        content = (LinearLayout) findViewById(R.id.content_frame);
        imgBackground = (ImageView) findViewById(R.id.pizza_element_back);
        imgBackground.setAlpha(0.f);
        //mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText((largeTextArr[currentActivity]).toString());


        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                imgBackground.setAlpha(slideOffset);
                // mListViewMenu.setAlpha(1 - slideOffset);
                content.setAlpha(1 - slideOffset);
                imageDrawer.setAlpha(slideOffset);
                // mtoolBarLayout.setAlpha(1 - slideOffset);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                if (currentActivity != choicetActivity) {
                    mDrawerLayout.setEnabled(false);

                    Intent intent = new Intent();
                    if (choicetActivity == 0) {
                        intent.setClass(getBaseContext(), NewsMenu.class);
                    }
                    if (choicetActivity == 1) {
                        intent.setClass(getBaseContext(), ContactMenu.class);
                    }
                    if (choicetActivity == 2) {
                        intent.setClass(getBaseContext(), PizzaMenu.class);
                    }
                    if (choicetActivity == 3) {
                        intent.setClass(getBaseContext(), StartersMenu.class);
                    }
                    if (choicetActivity == 4) {
                        intent.setClass(getBaseContext(), SaladMenu.class);
                    }
                    if (choicetActivity == 5) {
                        intent.setClass(getBaseContext(), SoupMenu.class);
                    }
                    if (choicetActivity == 6) {
                        intent.setClass(getBaseContext(), AlfornoMenu.class);
                    }
                    if (choicetActivity == 7) {
                        intent.setClass(getBaseContext(), PastaMenu.class);
                    }
                    if (choicetActivity == 8) {
                        intent.setClass(getBaseContext(), MainCourseMenu.class);
                    }
                    if (choicetActivity == 9) {
                        intent.setClass(getBaseContext(), DrinksMenu.class);
                    }


                    startActivity(intent);
                    overridePendingTransition(R.animator.right_in, R.animator.left_out);
                }


            }
        };

        mDrawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle.syncState();
        mListViewDrawer = (BounceListView) findViewById(R.id.left_drawer);
        mListViewDrawer.setScrollingCacheEnabled(false);

        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, largeTextArr, smallTextArr, imgid);
        mListViewDrawer.setAdapter(adapter);


        mListViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {
                choicetActivity = position;
                if (currentActivity != choicetActivity) {
                    BounceListView v = (BounceListView) findViewById(R.id.left_drawer);
                    v.setEnabled(false);
                    mDrawerLayout.setEnabled(false);
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
                mDrawerLayout.closeDrawer(GravityCompat.START, true);

            }

        });

        Button openManager = (Button) findViewById(R.id.open_manager);


        openManager.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), PasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.right_menu) {
            mDrawerLayout.openDrawer(GravityCompat.END, true);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START, true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void RegisterButtonVisible(boolean isVisible) {
        if (Config.ISREGISTER) {
            gotoRestauratManager.setVisibility(View.INVISIBLE);
            notificationCreator.setVisibility(View.VISIBLE);
            postCreator.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        } else {
            gotoRestauratManager.setVisibility(View.VISIBLE);
            notificationCreator.setVisibility(View.INVISIBLE);
            postCreator.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
        }
    }
}
