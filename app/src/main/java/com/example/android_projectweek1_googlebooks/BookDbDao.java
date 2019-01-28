package com.example.android_projectweek1_googlebooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class BookDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        if (db == null) {
            BookDbHelper helper = new BookDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static void addBook(BookGiver book) {
        if (db != null) {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM books WHERE title='%s'",
                    book.getTitle()),
                    null);

            if (cursor.getCount() == 0) {
                ContentValues values = new ContentValues();
                
                values.put(BookDbContract.BookEntry.COLUMN_AUTHOR, book.getAuthor());
              
                values.put(BookDbContract.BookEntry.COLUMN_REVIEW, book.getReview());
                values.put(BookDbContract.BookEntry.COLUMN_READ, book.isRead());
                values.put(BookDbContract.BookEntry.COLUMN_TITLE, book.getTitle());
                values.put(BookDbContract.BookEntry.COLUMN_BOOK_DESC, book.getDesc());
                values.put(BookDbContract.BookEntry.COLUMN_IMAGE_URL, book.getImageUrl());
                values.put(BookDbContract.BookEntry.COLUMN_SALE_LINK, book.getSaleLink());
                values.put(BookDbContract.BookEntry.COLUMN_PUBLISHED_DATE, book.getPublishedDate());
                values.put(BookDbContract.BookEntry.COLUMN_PAGES, book.getPages());
                db.insert(BookDbContract.BookEntry.BOOK_TABLE_NAME, null, values);
            }
            cursor.close();
        }
    }

    public static void updateBookRead(BookGiver book){
        if (db != null) {
            String whereClause = String.format
                    ("%s = '%s'", BookDbContract.BookEntry.COLUMN_TITLE,
                            book.getTitle());
            final Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s",
                    BookDbContract.BookEntry.BOOK_TABLE_NAME, whereClause),
                    null);
            if (cursor.getCount() == 1) {
                ContentValues values = new ContentValues();
                values.put(BookDbContract.BookEntry.COLUMN_READ, book.isRead());
                db.update(BookDbContract.BookEntry.BOOK_TABLE_NAME, values, whereClause,
                        null);
            }
            cursor.close();
        }
    }
    public static void updateBookReview(BookGiver book){
        if (db != null) {
            String whereClause = String.format
                    ("%s = '%s'", BookDbContract.BookEntry.COLUMN_TITLE,
                            book.getTitle());
            final Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s",
                    BookDbContract.BookEntry.BOOK_TABLE_NAME, whereClause),
                    null);
            if (cursor.getCount() == 1) {
                ContentValues values = new ContentValues();
                values.put(BookDbContract.BookEntry.COLUMN_REVIEW, book.getReview());
                db.update(BookDbContract.BookEntry.BOOK_TABLE_NAME, values, whereClause,
                        null);
            }
            cursor.close();
        }
    }

    public static ArrayList<BookGiver> listBooks(){
        String getBooks = String.format("SELECT * FROM %s;", BookDbContract.BookEntry.BOOK_TABLE_NAME);
        Cursor cursor = db.rawQuery(getBooks, null);
        int index;
        ArrayList<BookGiver> books = new ArrayList<>();
        while(cursor.moveToNext()){
            index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_TITLE);
            String title = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_AUTHOR);
            String author = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_IMAGE_URL);
            String imageUrl = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_REVIEW);
            String review = cursor.getString(index);
            index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_READ);
            int read = cursor.getInt(index);
            BookGiver book = new BookGiver(title, author, imageUrl, review, read);
            books.add(book);
        }
        cursor.close();
        return books;
    }

    public static void deleteBook(BookGiver book){
        if(db != null){
            String where = String.format("%s = '%s'", BookDbContract.BookEntry.COLUMN_TITLE, book.getTitle());
            db.delete(BookDbContract.BookEntry.BOOK_TABLE_NAME, where, null);
        }
    }

    public static void addBookshelf(BookShelf bookshelf) {
        if (db != null) {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM bookshelf WHERE shelf_name='%s'",
                    bookshelf.getName()),
                    null);

            if (cursor.getCount() == 0) {

                ContentValues values = new ContentValues();
                values.put(BookDbContract.BookEntry.COLUMN_SHELF_NAME, bookshelf.getName());
                db.insert(BookDbContract.BookEntry.SHELF_TABLE_NAME, null, values);
            }
        }
    }

    public static ArrayList<BookShelf> listBookshelves(){
        String getBookshelf = String.format("SELECT * FROM %s;", BookDbContract.BookEntry.SHELF_TABLE_NAME);
        Cursor cursor = db.rawQuery(getBookshelf, null);
        int index;
        ArrayList<BookShelf> bookshelves = new ArrayList<>();
        while(cursor.moveToNext()){
            index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_SHELF_NAME);
            String name = cursor.getString(index);
            BookShelf bookshelf = new BookShelf(name);
            bookshelves.add(bookshelf);
        }
        cursor.close();
        return bookshelves;
    }

    public static void deleteBookshelf(BookShelf bookshelf){
        if(db != null){
            String where = String.format("%s = '%s'",
                    BookDbContract.BookEntry.COLUMN_SHELF_NAME,
                    bookshelf.getName());
            db.delete(BookDbContract.BookEntry.SHELF_TABLE_NAME, where, null);
        }
    }

    public static void addBookInShelf(BookShelf bookshelf, BookGiver book){
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(BookDbContract.BookEntry.COLUMN_SHELF_ID, bookshelf.getName());
            values.put(BookDbContract.BookEntry.COLUMN_BOOK_ID, book.getTitle());
            db.insert(BookDbContract.BookEntry.BOOKINSHELF_TABLE_NAME, null, values);
        }
    }

    public static void deleteBookInShelf(BookShelf bookshelf, BookGiver book){
        if(db != null){
            String where = String.format("%s = '%s' AND %s='%s'",
                    BookDbContract.BookEntry.COLUMN_SHELF_ID, bookshelf.getName(),
                    BookDbContract.BookEntry.COLUMN_BOOK_ID, book.getTitle());
            db.delete(BookDbContract.BookEntry.BOOKINSHELF_TABLE_NAME, where, null);
        }
    }
    public static ArrayList<BookGiver> listBookInShelf(BookShelf bookshelf){
        if(db != null){
            Cursor cursor = db.rawQuery(String.format("SELECT %s.%s FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s=\"%s\"",
                    BookDbContract.BookEntry.BOOK_TABLE_NAME, BookDbContract.BookEntry.COLUMN_TITLE,
                    BookDbContract.BookEntry.BOOK_TABLE_NAME,
                    BookDbContract.BookEntry.BOOKINSHELF_TABLE_NAME,
                    BookDbContract.BookEntry.BOOK_TABLE_NAME, BookDbContract.BookEntry.COLUMN_TITLE,
                    BookDbContract.BookEntry.BOOKINSHELF_TABLE_NAME, BookDbContract.BookEntry.COLUMN_BOOK_ID,
                    BookDbContract.BookEntry.BOOKINSHELF_TABLE_NAME, BookDbContract.BookEntry.COLUMN_SHELF_ID,
                    bookshelf.getTitle()), null);
            ArrayList<String> bookTitles = new ArrayList<>();
            int index;
            while(cursor.moveToNext()){
                index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_TITLE);
                String title = cursor.getString(index);
                bookTitles.add(title);
            }
            cursor.close();
            ArrayList<BookGiver> volumes = new ArrayList<>();
            for(String title : bookTitles){
                cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s=\"%s\"",
                        BookDbContract.BookEntry.BOOK_TABLE_NAME,
                        BookDbContract.BookEntry.COLUMN_TITLE, title),
                        null);
                while(cursor.moveToNext()){
                    BookGiver Book = getBookData(cursor);
                    volumes.add(Book);
                }
            }
            return volumes;
        }else{
            return new ArrayList<>();
        }
    }


    @NonNull
    private static BookGiver getBookData(Cursor cursor) {
        int index;
        index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_TITLE);
        String title = cursor.getString(index);
        index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_IMAGE_URL);
        String imageUrl = cursor.getString(index);
        index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_REVIEW);
        String userReview = cursor.getString(index);
        index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_AUTHOR);
        String authors = cursor.getString(index);
        index = cursor.getColumnIndexOrThrow(BookDbContract.BookEntry.COLUMN_READ);
        int read = cursor.getInt(index);
        return new BookGiver(title, imageUrl, userReview, authors, read);
    }

    public static void updateBookFavorite(BookGiver bookGiver) {
    }
}