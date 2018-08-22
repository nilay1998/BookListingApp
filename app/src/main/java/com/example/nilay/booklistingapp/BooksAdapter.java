package com.example.nilay.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends ArrayAdapter<Books> {

    BooksAdapter(Context context, ArrayList<Books> books)
    {
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Books item=getItem(position);
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_main, parent, false);
        ImageView image=convertView.findViewById(R.id.image);
        TextView title=convertView.findViewById(R.id.title);
        TextView author=convertView.findViewById(R.id.author);

        image.setImageResource(item.getmImageResId());
        title.setText(item.getmName());
        author.setText(item.getmAuthor());

        return convertView;
    }
}
