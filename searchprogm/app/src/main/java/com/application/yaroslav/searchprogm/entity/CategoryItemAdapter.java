package com.application.yaroslav.searchprogm.entity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.application.yaroslav.searchprogm.ItemView;

import java.util.List;

/**
 * Created by Yaroslav on 11.05.2015.
 */
public class CategoryItemAdapter extends ArrayAdapter<CategoryItem> {

    public CategoryItemAdapter(Context c, List<CategoryItem> items) {
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView = (ItemView) convertView;
        if (itemView == null) {
            itemView = ItemView.inflate(parent);
        }
        itemView.setItem(getItem(position));
        return itemView;
    }
}
