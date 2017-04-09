package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class Additions extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_menu);
        currentActivity = 2;
        choicetActivity = 2;
        colorActivity = currentActivity;
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_dodatki);
        View inflated = stub.inflate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText("DODATKI");

        Button addon_button = (Button) findViewById(R.id.addon_button);
        addon_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getBaseContext(), Pizza.class);
                startActivity(intent);
                overridePendingTransition(R.animator.left_in, R.animator.right_out);
            }
        });


        sortByInt = true;
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.pizza_view);
        loadFireBaseData("additions",true);

    }
}