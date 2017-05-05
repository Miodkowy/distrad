package com.michal_stasinski.distrada.Menu.RightMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.michal_stasinski.distrada.App.Config;
import com.michal_stasinski.distrada.Menu.LeftMenu.News;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.CustomTextView;

/**
 * Created by win8 on 04.05.2017.
 */

public class SharePopUp extends Activity {
    private Button gotoRestauratManager;
    private Button notificationCreator;
    private Button postCreator;
    private Button logout;
    private ImageButton gotoUri;
    private Button share_btn;
    private ImageButton icon;
    private static Context contex;
    private CustomTextView version_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.share_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().getAttributes().windowAnimations = R.style.Animation;
        getWindow().setLayout((int) (width * 1.0), (int) (height * 1.2));

        getWindow().setGravity(Gravity.BOTTOM);
        gotoRestauratManager = (Button) findViewById(R.id.admin);

        notificationCreator = (Button) findViewById(R.id.notificationCreator);
        postCreator = (Button) findViewById(R.id.postCreator);
        logout = (Button) findViewById(R.id.logout);
        gotoUri = (ImageButton) findViewById(R.id.urlButton);
        icon = (ImageButton) findViewById(R.id.icon_in_info);
        share_btn = (Button) findViewById(R.id.share_btn);
        version_txt = (CustomTextView) findViewById(R.id.version_txt);
        RegisterButtonVisible(Config.ISREGISTER);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Zapraszamy na pyszną pizzę";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DISTRADA");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            }
        });

        gotoUri.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                openWebURL("http://monimaxprojekt.com");
            }
        });

        notificationCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), NotificationCreator.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });

        postCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), NewsCreator.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //On click function
            public void onClick(View view) {
                Config.ISREGISTER = false;
                RegisterButtonVisible(Config.ISREGISTER);
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), News.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });

        Button openManager = (Button) findViewById(R.id.admin);
        openManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), PasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);

            }
        });
    }

    private void RegisterButtonVisible(boolean isVisible) {
        gotoRestauratManager.setVisibility(View.INVISIBLE);
        icon.setVisibility(View.VISIBLE);
        version_txt.setVisibility(View.VISIBLE);
        if (Config.ISREGISTER) {
            notificationCreator.setVisibility(View.VISIBLE);
            postCreator.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            icon.setVisibility(View.INVISIBLE);
            version_txt.setVisibility(View.INVISIBLE);
        } else {
            if (Config.ISADMINENABLED == true) {
                gotoRestauratManager.setVisibility(View.VISIBLE);
            }
            notificationCreator.setVisibility(View.INVISIBLE);
            postCreator.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
        }
    }

    public void openWebURL(String inURL) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));
        startActivity(browse);
    }

}
