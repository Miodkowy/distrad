package com.michal_stasinski.distrada;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CustomDrawerAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] smallTxtArr;
    private final String[] largeTxtArr;
    private final Integer[] imgid;

    public  CustomDrawerAdapter(Activity context, String[] largeTextItem, String[] smallTextItem,Integer[] imgid) {
       super(context, R.layout.custom_drawer_row, largeTextItem);

        this.context = context;
        this.largeTxtArr = largeTextItem;
        this.smallTxtArr = smallTextItem;
        this.imgid = imgid;
    }

    public View getView(int position,  View convertView ,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View view = convertView;
        View rowView;

        if(position == 0){
             rowView = inflater.inflate(R.layout.drawer_header, null, true);
        }else {

            rowView = inflater.inflate(R.layout.custom_drawer_row, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            CustomTextView txtTitle = (CustomTextView) rowView.findViewById(R.id.txtTitleDrawer);
            CustomTextView smallTxt = (CustomTextView) rowView.findViewById(R.id.txtDescDrawer);

            txtTitle.setText(largeTxtArr[position]);
            imageView.setImageResource(imgid[position]);
            smallTxt.setText(smallTxtArr[position]);
        }
        return rowView;

    };
}