package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.Menu.Models.MenuItemProduct;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

public class Additions extends BaseMenu {
    private DatabaseReference myRef;
    private ArrayList<MenuItemProduct> menuItem;
    private int colorActivity;
    private boolean sortByInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_menu);

        currentActivity = 2;
        choicetActivity = 2;
        colorActivity = currentActivity;

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_dodatki);
        View inflated = stub.inflate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText("DODATKI");

        Button addon_button = (Button) findViewById(R.id.addon_button);
        addon_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getBaseContext(), Pizza.class);
                startActivity(intent);
                overridePendingTransition(R.animator.left_in, R.animator.right_out);
            }
        });


        myRef = database.getReference("additions");

        sortByInt = true;
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.pizza_view);


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

                CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), menuItem, colorToolBar[colorActivity], sortByInt, false);
                mListViewMenu.setAdapter(arrayAdapter);
                mListViewMenu.setScrollingCacheEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}