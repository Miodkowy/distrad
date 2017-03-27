package com.michal_stasinski.distrada.Activity;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.michal_stasinski.distrada.Blog.BlogFragment;
import com.michal_stasinski.distrada.Contact.ContactFragment;
import com.michal_stasinski.distrada.Menu.Adapters.CustomDrawerAdapter;
import com.michal_stasinski.distrada.InfoPanel.InfoActivity;
import com.michal_stasinski.distrada.Menu.MenuActivity.PizzaMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Menu.MenuActivity.MenuFragment;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private BounceListView mListView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;

    private String[] largeTextArr = {

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
            R.color.color_AKTUALNOSCI,
            R.color.color_KONTAKTY,
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

                Log.e("TAG", "onReceive  maina________________________________________ ");
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                }
            }
        };




        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.custom_drawer_shape, Gravity.START);
       // mDrawerLayout.setScrimColor(getResources().getColor(R.color.color_DRAWER_SHADOW));
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
             //  shadowDrawable.setAlpha((1/255)*slideoffset);
               // mDrawerLayout.setDrawerShadow(shadowDrawable);
                //view.setAlpha((1/255)*slideOffset);
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }


        };

        mDrawerLayout.addDrawerListener(mToggle);


        // mDrawerLayout.openDrawer(GravityCompat.START,true);
        //mDrawerLayout.setBackgroundResource(R.mipmap.piec_view);
        //wyłaczenie shadow

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToggle.syncState();

        mListView = (BounceListView) findViewById(R.id.left_drawer);
        mListView.setScrollingCacheEnabled(false);


        /*LayoutInflater inflaterHeader = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflaterHeader.inflate(
                R.layout.drawer_header, mListView, false);
        mListView.addHeaderView(header);*/

        Integer[] imgid = {
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
            public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {


                mToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.open, R.string.close) {

                    /** Called when a drawer has settled in a completely closed state. */
                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);
                        FragmentManager fragmentManager = getFragmentManager();
                        mDrawerLayout.removeDrawerListener(mToggle);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        bundle.putInt("colorFragement", colorToolBar[position]);
                        mToolBar.setBackgroundResource(colorToolBar[position]);
                        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
                        TextView colorShape = (TextView) findViewById(R.id.positionInList);

                        toolBarTitle.setText((largeTextArr[position]).toString());
                        if (position != 0 && position != 1 && position != 10) {

                            MenuFragment fragobj = new MenuFragment();
                            fragobj.setArguments(bundle);
                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out).replace(R.id.content_frame, fragobj).commit();

                        }

                        if (position == 0) {

                            // NotificationFragment notiObj = new NotificationFragment();
                            // notiObj.setArguments(bundle);

                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                                    .replace(R.id.content_frame, new BlogFragment()).commit();
                            // fragmentManager.beginTransaction().replace(R.id.content_frame, notiObj).commit();

                        }
                        if (position == 1) {

                            // NotificationFragment notiObj = new NotificationFragment();
                            // notiObj.setArguments(bundle);
                            fragmentManager.beginTransaction().setCustomAnimations(
                                    R.animator.card_flip_right_in,
                                    R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in,
                                    R.animator.card_flip_left_out)
                                    .replace(R.id.content_frame, new ContactFragment()).commit();


                        }

                        if (position == 10) {

                            // NotificationFragment notiObj = new NotificationFragment();
                            // notiObj.setArguments(bundle);
                            // fragmentManager.beginTransaction().replace(R.id.content_frame, notiObj).commit();

                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, InfoActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            //fragmentManager.beginTransaction().replace(R.id.content_frame, new NotificationFragment()).commit();

                        }


                    }

                    /** Called when a drawer has settled in a completely open state. */
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);

                    }
                };

                mDrawerLayout.addDrawerListener(mToggle);
                mDrawerLayout.closeDrawer(GravityCompat.START, true);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    protected void onStart() {
        super.onStart();
        Button openManager = (Button) findViewById(R.id.button_to_pizza_menu);

        openManager.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PizzaMenu.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });
    }
}