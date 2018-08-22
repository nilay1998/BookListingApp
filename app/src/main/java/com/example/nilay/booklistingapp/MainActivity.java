package com.example.nilay.booklistingapp;

import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Loader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    BooksAdapter booksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView booksListView=findViewById(R.id.listview);
        booksAdapter=new BooksAdapter(this,new ArrayList<Books>());
        booksListView.setAdapter(booksAdapter);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null,this);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new BooksLoader(MainActivity.this,QueryUtils.booksURL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        booksAdapter.clear();
        ArrayList<Books> mnew=QueryUtils.extractFeatureFromJson(s);
        booksAdapter.addAll(mnew);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        booksAdapter.clear();
    }

    public void click(View view)
    {

    }
}
