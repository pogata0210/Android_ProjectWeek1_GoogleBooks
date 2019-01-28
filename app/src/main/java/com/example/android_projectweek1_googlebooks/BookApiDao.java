package com.example.android_projectweek1_googlebooks;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class   BookApiDao {
    private static final String SEARCH_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String BOOKS_ARRAY = "items";



    public static ArrayList<BookGiver> searchBooks(final String searchString) {

        final ArrayList<BookGiver> books = new ArrayList<>();
        final String result = NetworkAdapter.httpRequest(SEARCH_URL + searchString + "&maxResults=40&startIndex=0", NetworkAdapter.GET);

        try {

            JSONObject dataObject = new JSONObject(result);
            JSONArray bookArray = dataObject.getJSONArray("items");

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject bookObject = bookArray.getJSONObject(i);
                JSONObject volumeInfo = bookObject.getJSONObject("volumeInfo");
                JSONObject saleInfo = bookObject.getJSONObject("saleInfo");
                JSONObject imageUrls = volumeInfo.getJSONObject("imageLinks");
                String authors = "";



                BookGiver book = new BookGiver(bookArray.getJSONObject(i));
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }

}
