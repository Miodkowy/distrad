package com.michal_stasinski.distrada.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.Menu.CustomListViewAdapter;
import com.michal_stasinski.distrada.Menu.MenuItemProduct;
import com.michal_stasinski.distrada.R;

import java.util.ArrayList;
import java.util.Map;

public class PizzaMenuActivity extends BaseMenuActivity {
    private DatabaseReference myRef;
    private ArrayList<MenuItemProduct> menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("pizzas");

        LinearLayout background = (LinearLayout) findViewById(R.id.main_frame);
        background.setBackgroundResource(R.mipmap.pizza_view);

        mToolBar.setBackgroundResource(colorToolBar[2]);


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


                CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), menuItem, colorToolBar[2], true);
                mListViewMenu.setAdapter(arrayAdapter);
                mListViewMenu.setScrollingCacheEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
