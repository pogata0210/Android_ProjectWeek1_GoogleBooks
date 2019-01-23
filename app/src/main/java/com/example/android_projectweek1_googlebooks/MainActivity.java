package com.example.android_projectweek1_googlebooks;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Context context;
    private Activity activity;

    private Button searchButton;
    private Button saveBook;
    private EditText inputText;

    private RecyclerView bookListRecclerView;
    private LinearLayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.search_button);
        saveBook = findViewById(R.id.save_books);
        inputText= findViewById(R.id.input_text);

        context = this;

    }
}
