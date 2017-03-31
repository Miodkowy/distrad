package com.michal_stasinski.distrada.Menu.RightMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.Menu.LeftMenu.News;
import com.michal_stasinski.distrada.R;

public class PasswordActivity extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_activity_password);

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
            //On click function
            public void onClick(View view) {

                if (loginText.getText().toString().equals("a") && password.getText().toString().equals("a")) {
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
}
