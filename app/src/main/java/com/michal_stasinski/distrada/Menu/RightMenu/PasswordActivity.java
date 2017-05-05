package com.michal_stasinski.distrada.Menu.RightMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.Menu.LeftMenu.News;
import com.michal_stasinski.distrada.R;

import java.util.ArrayList;

public class PasswordActivity extends BaseMenu {
    private DatabaseReference mDatabase;
    private String top ;
    private String bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.base_menu);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.right_password);
        View inflated = stub.inflate();

        Button login_button = (Button) findViewById(R.id.login_button);
        Button back_button = (Button) findViewById(R.id.back_button);

        final TextView wrongText = (TextView) findViewById(R.id.wrong_login);
        final EditText loginText = (EditText) findViewById(R.id.login_text);
        final EditText password = (EditText) findViewById(R.id.password_text);

        wrongText.setAlpha(0);
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);


        background.setBackgroundResource(R.mipmap.piec_view);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginText.getText().toString().equals(top) && password.getText().toString().equals(bot)) {
                    Intent intent = new Intent();
                    Config.ISREGISTER = true;
                    intent.setClass(PasswordActivity.this, News.class);
                    startActivity(intent);
                    overridePendingTransition(R.animator.right_in, R.animator.left_out);
                } else {
                    wrongText.setAlpha(1);
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PasswordActivity.this, News.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("news&not");
        mDatabase.addValueEventListener(new ValueEventListener() {
            ArrayList<String> menuItem = new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    String txt = (String) item.getValue();
                    menuItem.add(txt);

                }
                top = menuItem.get(1);
                bot = menuItem.get(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
