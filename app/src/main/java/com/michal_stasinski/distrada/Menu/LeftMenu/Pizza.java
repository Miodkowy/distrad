package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class Pizza extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_menu);
        currentActivity = 2;
        choicetActivity = 2;
        colorActivity = currentActivity;
        specialSign = false;
        sortByInt = true;
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_pizza);
        View inflated = stub.inflate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.pizza_view);

        Button addon_button = (Button) findViewById(R.id.addon_button);
        addon_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), Additions.class);
                startActivity(intent);
                overridePendingTransition(R.animator.right_in, R.animator.left_out);
            }
        });
        loadFireBaseData("pizzas",true);
    }
}
