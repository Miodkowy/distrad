package com.michal_stasinski.distrada.InfoPanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.RestaurantManager.PasswordActivity;
import com.michal_stasinski.distrada.RestaurantManager.RestaurantManager;

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
                intent.setClass(InfoActivity.this, PasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in,R.animator.left_out);
            }
        });
    }

}
