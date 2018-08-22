package com.example.nilay.booklistingapp;

import android.graphics.Bitmap;

public class Books {
    private String mName;
    private String mAuthor;
    Bitmap mImageResId;

    Books(String name,String author, Bitmap id)
    {
        mName=name;
        mAuthor=author;
        mImageResId=id;
    }

    public Bitmap getmImageResId() {
        return mImageResId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmName() {
        return mName;
    }
}
