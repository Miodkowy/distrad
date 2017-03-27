package com.michal_stasinski.distrada.RestaurantManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michal_stasinski.distrada.InfoPanel.InfoActivity;
import com.michal_stasinski.distrada.Menu.MenuActivity.NewsMenu;
import com.michal_stasinski.distrada.R;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Button login_button = (Button) findViewById(R.id.login_button);
        Button back_button = (Button) findViewById(R.id.back_button);
        final TextView wrongText = (TextView) findViewById(R.id.wrong_login);
        final EditText loginText = (EditText) findViewById(R.id.login_text);
        final EditText password = (EditText) findViewById(R.id.password_text);
        wrongText.setAlpha(0);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {

                if (loginText.getText().toString().equals("a") && password.getText().toString().equals("a")) {
                    Intent intent = new Intent();
                    intent.setClass(PasswordActivity.this, RestaurantManager.class);
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
                intent.setClass(PasswordActivity.this, NewsMenu.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });
    }
}
