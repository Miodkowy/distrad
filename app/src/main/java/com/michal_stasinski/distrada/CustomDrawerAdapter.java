package com.michal_stasinski.distrada;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CustomDrawerAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public  CustomDrawerAdapter(Activity context, String[] itemname, Integer[] imgid) {
       super(context, R.layout.custom_drawer_row, itemname);

        this.context = context;
        this.itemname = itemname;
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
            CustomTextView txtTitle = (CustomTextView) rowView.findViewById(R.id.titleItem);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            CustomTextView extratxt = (CustomTextView) rowView.findViewById(R.id.txtPrice);

            txtTitle.setText(itemname[position]);
            imageView.setImageResource(imgid[position]);
            extratxt.setText(itemname[position]);
        }
        return rowView;

    };
}