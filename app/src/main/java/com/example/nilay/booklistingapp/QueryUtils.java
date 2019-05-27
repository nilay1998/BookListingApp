package com.example.nilay.booklistingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QueryUtils {
    private static final String LOG_TAG = "QueryUtils" ;
    public static final String booksURL="https://www.googleapis.com/books/v1/volumes?maxResults=20&q=";

    public static URL createUrl(String stringUrl)
    {
        URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException exception){
            Log.e(LOG_TAG,"Error with creating URL",exception);
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Books> extractFeatureFromJson(String booksJson)
    {
        if (TextUtils.isEmpty(booksJson)) {
            return null;
        }
        ArrayList<Books> books = new ArrayList<>();
        try {
            JSONObject root= new JSONObject(booksJson);
            JSONArray items=root.getJSONArray("items");

            for(int i=0;i<items.length();i++)
            {
                JSONObject object=items.getJSONObject(i);
                JSONObject title=object.getJSONObject("volumeInfo");
                JSONArray author=title.getJSONArray("authors");
                String name= title.getString("title");
                String writer=author.getString(0);
                JSONObject image=title.getJSONObject("imageLinks");
                StringBuilder stringBuilder = new StringBuilder();
                String coverImageUrl=image.getString("smallThumbnail");
//                Pattern p = Pattern.compile("id=(.*?)&");
//                Matcher m = p.matcher(coverImageUrl);
//                if (m.matches()) {
//                    String id = m.group(1);
//                    coverImageUrl = String.valueOf(stringBuilder.append("https://books.google.com/books/content/images/frontcover/").append(id).append("?fife=w300"));
//                } else {
//                    Log.i(LOG_TAG, "Issue with cover");
//                }
                books.add(new Books(name,writer,coverImageUrl));
            }
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }
}
