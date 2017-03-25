package com.michal_stasinski.distrada.Menu;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.R;

import java.util.ArrayList;
import java.util.Map;

public class StartersMenu extends BaseMenu {
    private DatabaseReference myRef;
    private ArrayList<MenuItemProduct> menuItem;
    private int colorActivity;
    private boolean sortByInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("starters");
        currentActivity = 3;
        colorActivity = currentActivity;
        sortByInt = true;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.starters_view);

        //mToolBar.setBackgroundResource(colorToolBar[colorActivity]);


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

                CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), menuItem, colorToolBar[colorActivity], sortByInt);
                mListViewMenu.setAdapter(arrayAdapter);
                mListViewMenu.setScrollingCacheEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
