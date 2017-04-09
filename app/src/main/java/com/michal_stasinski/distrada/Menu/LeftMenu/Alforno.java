package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class Alforno extends BaseMenu {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = 6;
        colorActivity = currentActivity;
        sortByInt = true;
        specialSign = false;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.alforno_view);
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFireBaseData("alfornos",true);
    }
}