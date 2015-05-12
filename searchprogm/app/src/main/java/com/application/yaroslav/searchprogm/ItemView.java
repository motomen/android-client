package com.application.yaroslav.searchprogm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.yaroslav.searchprogm.entity.CategoryItem;

/**
 * Created by Yaroslav on 11.05.2015.
 */
public class ItemView extends RelativeLayout {

    public static ItemView inflate(ViewGroup parent) {
        ItemView itemView = (ItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_view, parent, false);
        return itemView;
    }

    public ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.category_item_view_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        mTitleTextView = (TextView) findViewById(R.id.category_item_titleTextView);
        mDescriptionTextView = (TextView) findViewById(R.id.category_item_descriptionTextView);
        mImageView = (ImageView) findViewById(R.id.category_item_imageView);
    }

    public void setItem(CategoryItem item) {
        mTitleTextView.setText(item.getName());
        mDescriptionTextView.setText(item.getDescription());
        // TODO: set up image URL
        byte[] decodedString = Base64.decode(item.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        mImageView.setImageBitmap(decodedByte);
    }
    private TextView mTitleTextView;

    private TextView mDescriptionTextView;

    private ImageView mImageView;

    public TextView getmTitleTextView() {
        return mTitleTextView;
    }

    public void setmTitleTextView(TextView mTitleTextView) {
        this.mTitleTextView = mTitleTextView;
    }

    public TextView getmDescriptionTextView() {
        return mDescriptionTextView;
    }

    public void setmDescriptionTextView(TextView mDescriptionTextView) {
        this.mDescriptionTextView = mDescriptionTextView;
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public void setmImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }
}
