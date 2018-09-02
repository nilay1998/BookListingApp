package com.example.nilay.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    BooksAdapter booksAdapter;
    boolean isConnected;
    int i=1;
    private TextView mEmptyStateTextView;
    String search="";
    LoaderManager loaderManager;
    private View circleProgressBar;
    ListView booksListView;
    EditText editText;
    ConnectivityManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        checkConnection(cm);
        booksListView=findViewById(R.id.listview);
        circleProgressBar = findViewById(R.id.loading_spinner);
        editText=findViewById(R.id.album_description_view);
        booksAdapter=new BooksAdapter(this,new ArrayList<Books>());
        booksListView.setAdapter(booksAdapter);
        search=editText.getText().toString();
        mEmptyStateTextView = findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyStateTextView);
        if(checkConnection(cm))
        {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(i, null,this);
        }
        else {
            circleProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
        }
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new BooksLoader(MainActivity.this,QueryUtils.booksURL+editText.getText().toString().replaceAll(" ", "+"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        booksAdapter.clear();
        ArrayList<Books> mnew=QueryUtils.extractFeatureFromJson(s);
        circleProgressBar.setVisibility(View.GONE);
        if(mnew !=null && !mnew.isEmpty())
            booksAdapter.addAll(mnew);
        else {
            booksAdapter.clear();
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("NO BOOKS FOUND");
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        booksAdapter.clear();
    }

    public void click(View view)
    {
        if(editText.getText().toString().equals(search)){
            return;
        }
        search=editText.getText().toString();
        booksAdapter.clear();
        if(checkConnection(cm))
        {
            restartLoader();
        }
        else {
            booksAdapter.clear();
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("NO INTERNET CONNECTION");
        }
    }

    public void restartLoader() {
        mEmptyStateTextView.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(i, null, MainActivity.this);
    }

    public boolean checkConnection(ConnectivityManager connectivityManager) {
        // Status of internet connection
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            return true;

            //Log.i("NETWORK", "INTERNET connection status: " + String.valueOf(isConnected) + ". It's time to play with LoaderManager :)");

        } else {
            return false;

        }
    }
}
