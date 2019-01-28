package com.example.android_projectweek1_googlebooks;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

public class BookShelf {

    private String name;

    public BookShelf(String name) {
        this.name = name;
    }
    protected BookShelf(Parcel in) {
        name = in.readString();
    }



    private ArrayList<BookGiver> books;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BookGiver> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookGiver> books) {
        this.books = books;
    }

    public Object getTitle() {
        return name;
    }
}

