package com.example.nilay.booklistingapp;

import android.graphics.Bitmap;

public class Books {
    private String mName;
    private String mAuthor;
    String mImageResId;

    Books(String name,String author,String id)
    {
        mName=name;
        mAuthor=author;
        mImageResId=id;
    }

    public String getmImageResId() {
        return mImageResId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmName() {
        return mName;
    }
}
