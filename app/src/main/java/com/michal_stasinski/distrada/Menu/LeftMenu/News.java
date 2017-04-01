package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.Menu.Adapters.CustomNewsListViewAdapter;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.Menu.Models.NewsItem;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

public class News extends BaseMenu {
    private DatabaseReference myRef;
    private ArrayList<NewsItem> menuItem;
    private int colorActivity;
    private boolean sortByInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_menu);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_news);
        View inflated = stub.inflate();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("blogs");
        currentActivity = 0;
        choicetActivity = 0;
        colorActivity = currentActivity;
        sortByInt = false;

        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.piec_view);

        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuItem = new ArrayList<NewsItem>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String date = (String) map.get("date");
                    String news = (String) map.get("news");
                    String title = (String) map.get("title");
                    String rank = (String) map.get("rank");
                    String url = (String) map.get("imageUrl");

                    NewsItem newsItem = new NewsItem();

                    newsItem.setDate(date);
                    newsItem.setNews(news);
                    newsItem.setTitle(title);
                    newsItem.setRank(rank);
                    newsItem.setUrl(url);

                    menuItem.add(newsItem);
                }

                CustomNewsListViewAdapter arrayAdapter = new CustomNewsListViewAdapter(getApplicationContext(), menuItem, colorToolBar[colorActivity]);
                mListViewMenu.setAdapter(arrayAdapter);
                mListViewMenu.setScrollingCacheEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
