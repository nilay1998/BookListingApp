package com.example.nilay.booklistingapp;

public class Books {
    private String mName;
    private String mAuthor;
    int mImageResId;

    Books(String name,String author, int id)
    {
        mName=name;
        mAuthor=author;
        mImageResId=id;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmName() {
        return mName;
    }
}
