package com.michal_stasinski.distrada.InfoPanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.activity.RestaurantManager;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Button openManager= (Button) findViewById(R.id.open_manager);


        openManager.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(InfoActivity.this, RestaurantManager.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
    }

}
