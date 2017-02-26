package com.michal_stasinski.distrada.menu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
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
    private HashMap<String , String> pizzaMap;
    private DatabaseReference myRef;
    private int fragementColor;
    private ArrayList<MenuItemProduct> pizzzaItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.menu_layout, container ,false);

        //referencja do bazy w firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        int strtext = getArguments().getInt("position");
        fragementColor = getArguments().getInt("colorFragement");
        Log.d("MyApp","fragementColor____________________________________________________________ "+fragementColor );
        //TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolBarTitle);

        if (strtext == 1) {
            myRef = database.getReference("pizzas");


        }
        if (strtext == 2) {
            myRef = database.getReference("pizzas");
            //myView.setBackgroundColor(Color.YELLOW);
        }
        if (strtext == 3) {
            myRef = database.getReference("starters");
            //myView.setBackgroundColor(Color.RED);
        }
        if (strtext == 4) {
            myRef = database.getReference("insalates");
            //toolBarTitle.setText("SALATKI");
        }
        if (strtext == 5) {
            myRef = database.getReference("zuppas");
           // toolBarTitle.setText("ZUPY");
        }
        if (strtext == 6) {
            myRef = database.getReference("pasta");
           // toolBarTitle.setText("MAKARONY");
        }
        if (strtext == 7) {
            myRef = database.getReference("alfornos");
            //toolBarTitle.setText("MAKARONY ZAPIEKANE");
        }
        if (strtext == 8) {
            myRef = database.getReference("secondis");
            //toolBarTitle.setText("MAKARONY ZAPIEKANE");
        }
        if (strtext == 9) {
            myRef = database.getReference("drinks");
            //toolBarTitle.setText("NAPOJE I DESERY");
        }
        mListView = (ListView) myView.findViewById(R.id.mListView_FirstLayout);





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 pizzzaItem = new ArrayList<MenuItemProduct>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot desc = item;

                    Map<String, Object> map = (Map<String, Object>) desc.getValue();
                    String descrip = (String) map.get("name");
                    Number rank = (Number) map.get("rank");
                    MenuItemProduct pizza = new MenuItemProduct();

                    pizza.setNameProduct(descrip);
                    pizza.setRank(rank);

                    pizzzaItem.add(pizza);
                }

                CustomListViewAdapter carrayAdapter = new CustomListViewAdapter(myView.getContext(), pizzzaItem, fragementColor);
                mListView.setAdapter(carrayAdapter);


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
