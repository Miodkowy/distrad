package com.michal_stasinski.distrada.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.michal_stasinski.distrada.CustomDrawerAdapter;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class BaseMenuActivity extends AppCompatActivity {

    public BounceListView mListViewDrawer;
    public BounceListView mListViewMenu;
    public  LinearLayout mtoolBarLayout;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    public Toolbar mToolBar;
    private ImageView imgBackground;
    private LinearLayout content;
    private int color;
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
        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        mtoolBarLayout =(LinearLayout) findViewById(R.id.main_frame);
        setSupportActionBar(mToolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // mDrawerLayout.setDrawerShadow(R.drawable.custom_drawer_shape, Gravity.START);
        //mDrawerLayout.openDrawer(GravityCompat.START,true);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        content = (LinearLayout) findViewById(R.id.content_frame);
        imgBackground = (ImageView) findViewById(R.id.pizza_element_back);
        imgBackground.setAlpha(0.f);
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                imgBackground.setAlpha(slideOffset);
               mListViewMenu.setAlpha(1 - slideOffset);
               // content.setAlpha(1-slideOffset);
                mtoolBarLayout.setAlpha(1-slideOffset);
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
                Log.i("TAG", "position" + position);


                Intent intent = new Intent();

             if (position == 2 ){
                    intent.setClass(getBaseContext(),PizzaMenuActivity.class);
                }
                if (position == 3 ){
                    intent.setClass(getBaseContext(),StartersMenuActivity.class);
                }
               startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
               /* mToggle = new ActionBarDrawerToggle(BaseMenuActivity.this, mDrawerLayout, R.string.open, R.string.close) {

                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);


                    }


                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);

                    }
                };*/

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
