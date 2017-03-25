package com.michal_stasinski.distrada.Menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michal_stasinski.distrada.CustomDrawerAdapter;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class BaseMenu extends AppCompatActivity {

    public BounceListView mListViewDrawer;
    public BounceListView mListViewMenu;
    public RelativeLayout mtoolBarLayout;
    public DrawerLayout mDrawerLayout;
    private ImageView imageDrawer;
    public ActionBarDrawerToggle mToggle;
    public Toolbar mToolBar;

    private ImageView imgBackground;
    private LinearLayout content;
    private int color;
    private Boolean choiceActivity;
    public int currentActivity = 2;

    public String[] largeTextArr = {
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

    public String[] smallTextArr = {
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
    public int[] colorToolBar = {
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

    public Integer[] imgid = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_menu);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG", "START");
        choiceActivity = false;
        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        mToolBar.setBackgroundResource(colorToolBar[currentActivity]);

        imageDrawer = (ImageView) findViewById(R.id.pizza_element_back);
        setSupportActionBar(mToolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mDrawerLayout.setDrawerShadow(R.drawable.custom_drawer_shape, Gravity.START);
        //mDrawerLayout.openDrawer(GravityCompat.START,true);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        content = (LinearLayout) findViewById(R.id.content_frame);
        imgBackground = (ImageView) findViewById(R.id.pizza_element_back);
        imgBackground.setAlpha(0.f);
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText((largeTextArr[currentActivity]).toString());
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                imgBackground.setAlpha(slideOffset);
                mListViewMenu.setAlpha(1 - slideOffset);
                imageDrawer.setAlpha(slideOffset);
               // mtoolBarLayout.setAlpha(1 - slideOffset);
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
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

                mToggle = new ActionBarDrawerToggle(BaseMenu.this, mDrawerLayout, R.string.open, R.string.close) {

                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);

                        if (currentActivity != position) {

                            Intent intent = new Intent();
                            currentActivity = position;

                            if (position == 2) {
                                intent.setClass(getBaseContext(), PizzaMenu.class);
                            }
                            if (position == 3) {
                                intent.setClass(getBaseContext(), StartersMenu.class);
                            }
                            if (position == 4) {
                                intent.setClass(getBaseContext(), SaladMenu.class);
                            }
                            startActivity(intent);
                            overridePendingTransition(R.animator.right_in, R.animator.left_out);
                        }

                    }


                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);

                    }
                };

                mDrawerLayout.addDrawerListener(mToggle);
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
