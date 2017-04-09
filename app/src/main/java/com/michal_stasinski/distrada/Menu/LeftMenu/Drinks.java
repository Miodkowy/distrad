package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class Drinks extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        specialSign = true;
        currentActivity = 9;
        choicetActivity = 9;
        colorActivity = currentActivity;
        sortByInt = true;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.deser_view);
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFireBaseData("drinks",true);
    }
}
