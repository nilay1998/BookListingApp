package com.example.nilay.booklistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Books> arrayList=new ArrayList<>();
        BooksAdapter booksAdapter=new BooksAdapter(this,arrayList);
        ListView booksListView=findViewById(R.id.listview);
        booksListView.setAdapter(booksAdapter);
    }
}
