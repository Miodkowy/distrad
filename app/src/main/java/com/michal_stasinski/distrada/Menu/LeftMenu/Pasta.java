package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class Pasta extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_menu);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_header_and_bounce_list_view);
        View inflated = stub.inflate();

        currentActivity = 7;
        choicetActivity = 7;
        colorActivity = currentActivity;
        sortByInt = false;
        specialSign = false;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.pasta_view);
        TextView addonText = (TextView) findViewById(R.id.addonText);
        addonText.setText("Makarony podajemy z parmezanem.");
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        // mToolBar.setBackgroundResource(colorToolBar[colorActivity]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFireBaseData("pasta",true);
    }
}
