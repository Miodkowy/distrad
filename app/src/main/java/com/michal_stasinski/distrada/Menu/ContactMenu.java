package com.michal_stasinski.distrada.Menu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;


import java.util.ArrayList;
import java.util.Map;

public class ContactMenu extends BaseMenu implements OnMapReadyCallback {
    private DatabaseReference myRef;
    private ArrayList<MenuItemProduct> menuItem;
    private int colorActivity;
    private boolean sortByInt;

    GoogleMap mGoogleMap;
    MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_menu);

        currentActivity = 1;
        colorActivity = currentActivity;
        sortByInt = true;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.contact_view);

        ImageButton button_route = (ImageButton) findViewById(R.id.route_button);
        button_route.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:54.5258318,18.5149058?q=diStrada");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ImageButton button_call = (ImageButton) findViewById(R.id.call_button);
        button_call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:501049315"));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity( callIntent);

            }
        });
        mMapView = (MapView) findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("TEST","_________________________________MAPAAAAAAAAAAAAAAAAAAAAAAAA");
        MapsInitializer.initialize(mMapView.getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(54.5258318,18.5149058)).title("Di Strada").snippet("zapraszamy na pyszną pizzę"));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(54.5258318,18.5149058)).zoom(14).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));


    }
}








