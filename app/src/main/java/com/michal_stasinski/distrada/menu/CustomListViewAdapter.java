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


    private static LayoutInflater inflater = null;
    private ArrayList<MenuItemProduct> arr;
    private Context mContext;
    private int color;
    private boolean sortOption;

    public CustomListViewAdapter(Context context, ArrayList<MenuItemProduct> mListArray, int color, Boolean sort) {

        sortOption = sort;

        Collections.sort(mListArray, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                String s1 = (((MenuItemProduct) o2).getRank());
                String s2 = (((MenuItemProduct) o2).getRank());
                if (sortOption) {
                    int id1 = Integer.parseInt(((MenuItemProduct) o1).getRank());
                    int id2 = Integer.parseInt(((MenuItemProduct) o2).getRank());

                    if (id1 > id2) {
                        return 1;
                    }
                    if (id1 < id2) {
                        return -1;
                    } else {
                        return 0;
                    }

                } else {
                    return s1.compareToIgnoreCase(s2);
                }
                //  return s1.compareToIgnoreCase(s2);
                //return Integer.valueOf(id1).compareTo(Integer.valueOf(id2));
            }
        });


        this.arr = mListArray;
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
        ViewHolderItem viewHolder;

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.menu_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.titleItem);
            viewHolder.textDesc = (TextView) view.findViewById(R.id.txtDesc);
            viewHolder.textPrice = (TextView) view.findViewById(R.id.txtPrice);
            viewHolder.colorShape = (TextView) view.findViewById(R.id.positionInList);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolderItem)  view.getTag();
        }

        /*

        TextView title = (TextView) view.findViewById(R.id.titleItem);
        TextView textDesc = (TextView) view.findViewById(R.id.txtDesc);
        TextView textPrice = (TextView) view.findViewById(R.id.txtPrice);
        TextView colorShape = (TextView) view.findViewById(R.id.positionInList);

        colorShape.setText(arr.get(position).getRank());

        ((GradientDrawable) colorShape.getBackground()).setColor(mContext.getResources().getColor(this.color));
        title.setText(arr.get(position).getNameProduct().toUpperCase());
        textDesc.setText(arr.get(position).getDesc().toUpperCase());
        textPrice.setText(arr.get(position).getPrice().toString().toUpperCase() + " ZŁ");
        */

        viewHolder.title.setText(arr.get(position).getNameProduct().toUpperCase());
        viewHolder.colorShape.setText(arr.get(position).getRank());
        ((GradientDrawable)  viewHolder.colorShape.getBackground()).setColor(mContext.getResources().getColor(this.color));
        viewHolder.textDesc.setText(arr.get(position).getDesc().toUpperCase());
        viewHolder.textPrice.setText(arr.get(position).getPrice().toString().toUpperCase() + " ZŁ");



        return view;
    }

    static class ViewHolderItem {

        TextView title;
        TextView textDesc;
        TextView textPrice;
        TextView colorShape;

    }
}
