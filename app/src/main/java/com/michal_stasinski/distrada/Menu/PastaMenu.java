package com.michal_stasinski.distrada.Menu;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

public class PastaMenu extends BaseMenu {
    private DatabaseReference myRef;
    private ArrayList<MenuItemProduct> menuItem;
    private int colorActivity;
    private boolean sortByInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta_menu);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        myRef = database.getReference("pasta");
        currentActivity = 7;
        choicetActivity = 7;
        colorActivity = currentActivity;
        sortByInt = false;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.pasta_view);
        TextView addonText = (TextView) findViewById(R.id.addonText);
        addonText.setText("Makarony podajemy z parmezanem.");
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        // mToolBar.setBackgroundResource(colorToolBar[colorActivity]);


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

                CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), menuItem, colorToolBar[colorActivity], sortByInt,false);
                mListViewMenu.setAdapter(arrayAdapter);
                mListViewMenu.setScrollingCacheEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
