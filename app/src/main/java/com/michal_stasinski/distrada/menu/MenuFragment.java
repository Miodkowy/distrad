package com.michal_stasinski.distrada.menu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.utils.BounceListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by win8 on 26.12.2016.
 */

public class MenuFragment extends Fragment {

    View myView;
    private Button mSendData;
    private TextView message;
    private ListView mListView;
    private HashMap<String, String> pizzaMap;
    private DatabaseReference myRef;
    private int fragementColor;
    private ArrayList<MenuItemProduct> pizzzaItem;
    private PercentRelativeLayout mListView_Menu;
    private Boolean sortByInt = true;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.menu_layout, container, false);

        //referencja do bazy w firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        int strtext = getArguments().getInt("position");
        fragementColor = getArguments().getInt("colorFragement");
        Log.d("MyApp", "fragementColor____________________________________________________________ " + fragementColor);
        //TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolBarTitle);

        mListView = (BounceListView) myView.findViewById(R.id.mListView_FirstLayout);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_view_header, mListView, false);

        mListView.addHeaderView(header, null, false);


        mListView_Menu = (PercentRelativeLayout) myView.findViewById(R.id.mListView_Menu);

        if (strtext == 1) {
            //  myRef = database.getReference("pizzas");
            // mListView_Menu.setBackgroundResource(R.mipmap.pizza_view);
        }
        if (strtext == 2) {
            //  myRef = database.getReference("pizzas");
            /// mListView_Menu.setBackgroundResource(R.mipmap.pizza_view);
        }
        if (strtext == 3) {
            myRef = database.getReference("pizzas");
            mListView_Menu.setBackgroundResource(R.mipmap.pizza_view);
        }
        if (strtext == 4) {
            myRef = database.getReference("starters");
            //  mListView_Menu.setBackgroundResource(R.mipmap.starters_view);
        }
        if (strtext == 5) {
            myRef = database.getReference("insalates");
            mListView_Menu.setBackgroundResource(R.mipmap.salad_view);
        }
        if (strtext == 6) {
            myRef = database.getReference("zuppas");
            mListView_Menu.setBackgroundResource(R.mipmap.zupa_view);
        }
        if (strtext == 7) {
            sortByInt = false;
            myRef = database.getReference("pasta");
            //  mListView_Menu.setBackgroundResource(R.mipmap.pasta_view);
        }
        if (strtext == 8) {
            myRef = database.getReference("alfornos");
            mListView_Menu.setBackgroundResource(R.mipmap.alforno_view);
        }
        if (strtext == 9) {
            myRef = database.getReference("secondis");
            mListView_Menu.setBackgroundResource(R.mipmap.drugie_view);
        }
        if (strtext == 10) {
            myRef = database.getReference("drinks");
            mListView_Menu.setBackgroundResource(R.mipmap.deser_view);
        }
        if (strtext == 11) {
            myRef = database.getReference("drinks");
            mListView_Menu.setBackgroundResource(R.mipmap.deser_view);
        }


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pizzzaItem = new ArrayList<MenuItemProduct>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;

                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String name = (String) map.get("name");
                    String rank = (String) map.get("rank").toString();
                    String desc = (String) map.get("desc");
                    Number price = (Number) map.get("price");

                    MenuItemProduct pizza = new MenuItemProduct();

                    pizza.setNameProduct(name);
                    pizza.setRank(rank);
                    pizza.setDesc(desc);
                    pizza.setDesc(desc);
                    pizza.setPrice(price);
                    pizzzaItem.add(pizza);
                }

                CustomListViewAdapter carrayAdapter = new CustomListViewAdapter(myView.getContext(), pizzzaItem, fragementColor, sortByInt);
                mListView.setAdapter(carrayAdapter);
                mListView.setScrollingCacheEnabled(false);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return myView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


}
