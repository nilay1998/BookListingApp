package com.example.nilay.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.net.URL;

public class BooksLoader extends AsyncTaskLoader<String>
{
    private String mUrl;
    private String LOG_TAG="BooksLoader";
    public BooksLoader(Context context, String url)
    {
        super(context);
        mUrl=url;
    }
    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"onStartLoader");
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        Log.e(LOG_TAG,"loadInBackground");
        URL url= QueryUtils.createUrl(mUrl);
        String jsonResponse="";
        try {
            jsonResponse=QueryUtils.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return jsonResponse;
    }
}

