package com.michal_stasinski.distrada.menu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michal_stasinski.distrada.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by win8 on 27.12.2016.
 */

public class CustomListViewAdapter extends BaseAdapter {


    private static LayoutInflater inflater =null;
    private ArrayList<MenuItemProduct> arr;
    private Context mContext;
    private int color;

    public CustomListViewAdapter(Context context, ArrayList<MenuItemProduct> mListArray, int color) {
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


       // Log.d("MyApp","creeeeeeeeee"+mContext );
      //  System.out.println("creeeeeeeee"+mListArray);


        Collections.sort(mListArray, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                int  id1 = Integer.parseInt(((MenuItemProduct) o1).getRank().toString());
                int  id2 = Integer.parseInt(((MenuItemProduct) o2).getRank().toString());

                if(id1 > id2){
                    return 1;
                }  if(id1 < id2){
                    return -1;
                } else{
                    return 0;
                }
            }
        });
        this.arr = mListArray;
        Log.d("MyApp","cos__________________________________________ "+mListArray );
        this.mContext = context;
        this.color = color;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //if(convertView == null){
        view = View.inflate(mContext, R.layout.menu_row,null);

        TextView title =(TextView) view.findViewById(R.id.titleItem);
        TextView textDesc =(TextView) view.findViewById(R.id.txtDesc);
        TextView textPrice =(TextView) view.findViewById(R.id.txtPrice);
        TextView colorShape = (TextView) view.findViewById(R.id.positionInList);

        colorShape.setText(String.valueOf(position+1));

        ((GradientDrawable)colorShape.getBackground()).setColor(mContext.getResources().getColor(this.color));
        title.setText(arr.get(position).getNameProduct().toUpperCase());
        textDesc.setText(arr.get(position).getDesc().toUpperCase());
        textPrice.setText(arr.get(position).getPrice().toString().toUpperCase() + " ZŁ");
        return view;
    }
}
