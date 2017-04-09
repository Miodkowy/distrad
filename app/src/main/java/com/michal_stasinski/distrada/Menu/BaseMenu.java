package com.michal_stasinski.distrada.Menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Menu.Adapters.CustomDrawerAdapter;
import com.michal_stasinski.distrada.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.distrada.Menu.LeftMenu.Alforno;
import com.michal_stasinski.distrada.Menu.LeftMenu.Contact;
import com.michal_stasinski.distrada.Menu.LeftMenu.Drinks;
import com.michal_stasinski.distrada.Menu.LeftMenu.MainCourse;
import com.michal_stasinski.distrada.Menu.LeftMenu.News;
import com.michal_stasinski.distrada.Menu.LeftMenu.Pasta;
import com.michal_stasinski.distrada.Menu.LeftMenu.Pizza;
import com.michal_stasinski.distrada.Menu.LeftMenu.Salad;
import com.michal_stasinski.distrada.Menu.LeftMenu.Soup;
import com.michal_stasinski.distrada.Menu.LeftMenu.Starters;
import com.michal_stasinski.distrada.Menu.Models.MenuItemProduct;
import com.michal_stasinski.distrada.Menu.RightMenu.NewsCreator;
import com.michal_stasinski.distrada.Menu.RightMenu.NotificationCreator;
import com.michal_stasinski.distrada.Menu.RightMenu.PasswordActivity;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BaseMenu extends AppCompatActivity {

    protected BounceListView mListViewDrawer;
    protected BounceListView mListViewMenu;
    protected RelativeLayout mtoolBarLayout;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;
    protected Toolbar mToolBar;
    protected int currentActivity = 0;
    protected int choicetActivity = 0;
    protected int badgeCount = 0;
    protected int colorActivity = 1;
    protected boolean sortByInt;
    protected boolean specialSign = false;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ImageView imgBackground;
    private LinearLayout content;
    private int color;
    private ImageView imageDrawer; //obrazek pod drawerem
    private Boolean choiceActivity;
    private Boolean loadActivity;
    private Button gotoRestauratManager;
    private Button notificationCreator;
    private Button postCreator;
    private Button logout;
    private DatabaseReference myRef;
    private ArrayList<MenuItemProduct> menuItem;


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
        setContentView(R.layout.base_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_bounce_list_view);
        View inflated = stub.inflate();

        badgeCount = 0;
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
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
                intent.setClass(getBaseContext(), NotificationCreator.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });

        postCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), NewsCreator.class);
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
                intent.setClass(getBaseContext(), News.class);
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
                        intent.setClass(getBaseContext(), News.class);
                    }
                    if (choicetActivity == 1) {
                        intent.setClass(getBaseContext(), Contact.class);
                    }
                    if (choicetActivity == 2) {
                        intent.setClass(getBaseContext(), Pizza.class);
                    }
                    if (choicetActivity == 3) {
                        intent.setClass(getBaseContext(), Starters.class);
                    }
                    if (choicetActivity == 4) {
                        intent.setClass(getBaseContext(), Salad.class);
                    }
                    if (choicetActivity == 5) {
                        intent.setClass(getBaseContext(), Soup.class);
                    }
                    if (choicetActivity == 6) {
                        intent.setClass(getBaseContext(), Alforno.class);
                    }
                    if (choicetActivity == 7) {
                        intent.setClass(getBaseContext(), Pasta.class);
                    }
                    if (choicetActivity == 8) {
                        intent.setClass(getBaseContext(), MainCourse.class);
                    }
                    if (choicetActivity == 9) {
                        intent.setClass(getBaseContext(), Drinks.class);
                    }
                    startActivity(intent);
                    //overridePendingTransition(R.animator.right_in, R.animator.left_out);
                    overridePendingTransition(R.anim.flip_inn, R.anim.flip_out);
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
            return true;
        }
        if (id == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Your message goes here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            return true;
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

    public void loadFireBaseData(String databaseReference, Boolean loadData) {
        if (loadData == true) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference(databaseReference);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    menuItem = new ArrayList<MenuItemProduct>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        DataSnapshot dataitem = item;
                        Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                        String name = (String) map.get("name");
                        String rank = (String) map.get("rank").toString();
                        String desc = (String) map.get("desc");
                        Number price = (Number) map.get("price");

                        MenuItemProduct menuItemProduct = new MenuItemProduct();

                        menuItemProduct.setNameProduct(name);
                        menuItemProduct.setRank(rank);
                        menuItemProduct.setDesc(desc);
                        menuItemProduct.setDesc(desc);
                        menuItemProduct.setPrice(price);
                        menuItem.add(menuItemProduct);

                    }
                    CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), menuItem, colorToolBar[colorActivity], sortByInt, specialSign);
                    mListViewMenu.setAdapter(arrayAdapter);
                    mListViewMenu.setScrollingCacheEnabled(false);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

}
