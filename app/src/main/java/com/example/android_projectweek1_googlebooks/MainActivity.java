package com.example.android_projectweek1_googlebooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;
    private Activity activity;

    private Button searchButton;
    private Button saveBook;
    private EditText inputText;
    private ListAdapter listAdapter;

    private RecyclerView bookListRecyclerView;
    private LinearLayoutManager layoutManager;

    private  ArrayList<BookGiver> books;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.search_button);
        saveBook = findViewById(R.id.save_books);
        inputText= findViewById(R.id.input_text);

        context = this;

        books = new ArrayList<>();


        bookListRecyclerView = findViewById(R.id.book_list_recyclerView);
        bookListRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        bookListRecyclerView.setLayoutManager(layoutManager);

        listAdapter = new ListAdapter(books, activity);
        bookListRecyclerView.setAdapter(listAdapter);

        BookDbDao.initializeInstance(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new searchBooksTask().execute(inputText.getText().toString());
            }
        });

        saveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookShelfActivity.class);
                startActivity(intent);
            }
        });

    }

        public class searchBooksTask extends AsyncTask<String, Integer, ArrayList<BookGiver>> {
        @Override
        protected void onPostExecute(ArrayList<BookGiver> bookList){
            super.onPostExecute(bookList);
            books.clear();
            books.addAll(bookList);
            listAdapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<BookGiver> doInBackground(String... strings) {
            return BookApiDao.searchBooks(strings[0]);
        }
    }
}


