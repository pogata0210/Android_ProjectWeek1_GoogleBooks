package com.example.android_projectweek1_googlebooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;


public class BookShelfActivity extends AppCompatActivity {

    private LinearLayout book_list;
    private EditText shelfText;
    private Button addButton, deleteButton;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_shelf);

        context = this;
        shelfText = findViewById(R.id.bookshelf_name);
        addButton = findViewById(R.id.add_shelf_button);
        book_list = findViewById(R.id.books_list);
        deleteButton = findViewById(R.id.delete_shelf_button);


        final ArrayList<BookShelf> bookshelf = BookDbDao.listBookshelves();



        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BookShelf addShelf = new BookShelf(shelfText.getText().toString());
                BookDbDao.addBookshelf(addShelf);
            }
        });


        for(int i = 0; i < bookshelf.size(); i++) {
            final TextView bookList = new TextView(context);
            final BookShelf getShelf = bookshelf.get(i);

            bookList.setText(getShelf.getName());
            bookList.setTextSize(18);
            bookList.setTypeface(null, Typeface.BOLD);
            bookList.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    BookShelf deleteShelf = new BookShelf(getShelf.getName());
                    BookDbDao.deleteBookshelf(deleteShelf);
                    return false;

                }
            });
            book_list.addView(bookList);

        }
    }
}

