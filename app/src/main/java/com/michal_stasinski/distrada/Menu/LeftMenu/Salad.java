package com.michal_stasinski.distrada.Menu.LeftMenu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.michal_stasinski.distrada.Menu.BaseMenu;
import com.michal_stasinski.distrada.R;
import com.michal_stasinski.distrada.Utils.BounceListView;

public class Salad extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_menu);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_header_and_bounce_list_view);
        View inflated = stub.inflate();

        currentActivity = 4;
        choicetActivity = 4;
        colorActivity = currentActivity;
        sortByInt = true;
        RelativeLayout background = (RelativeLayout) findViewById(R.id.main_frame_pizza);
        background.setBackgroundResource(R.mipmap.salad_view);
        TextView addonText = (TextView) findViewById(R.id.addonText);
        addonText.setText("Sałatki komponowane na bazie sałaty lodowej\ni różnego rodzaju sałat włoskich(rucola insalatina,roszponka lub inne) podawane z sosem vinegrette lub jogurtowo - czosnkowym.");
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFireBaseData("insalates",true);
    }
}
