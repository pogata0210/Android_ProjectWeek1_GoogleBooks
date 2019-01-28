package com.example.android_projectweek1_googlebooks;

import java.util.ArrayList;

public class Repo {
    private ArrayList<BookGiver> booksList;

    public void addBook(final BookGiver book) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BookDbDao.addBook(book);
            }
        }).start();
    }

    public ArrayList<BookGiver> readBooks(){
        booksList = BookDbDao.listBooks();
        return booksList;
    }

    public void updateBookRead(BookGiver BookGiver){
        BookDbDao.updateBookRead(BookGiver);
    }

    public void updateBookFavorite(BookGiver BookGiver){
        BookDbDao.updateBookFavorite(BookGiver);
    }

    public void updateBookReview(BookGiver BookGiver){
        BookDbDao.updateBookReview(BookGiver);
    }

    public void deleteBook(BookGiver book) {
        BookDbDao.deleteBook(book);
    }

    public void addBookshelf(BookShelf bookshelf) {
        BookDbDao.addBookshelf(bookshelf);
    }

    public ArrayList<BookShelf> listBookshelves() {
        return BookDbDao.listBookshelves();
    }

    public void deleteBookshelf(BookShelf bookshelf) {

        BookDbDao.deleteBookshelf(bookshelf);
    }

    public void addBookInShelf(BookShelf bookshelf, BookGiver book) {
        BookDbDao.addBookInShelf(bookshelf, book);
    }

    public void deleteBookInShelf(BookShelf bookshelf, BookGiver book) {
        BookDbDao.deleteBookInShelf(bookshelf, book);
    }

    public ArrayList<BookGiver> listBookInShelf(BookShelf bookshelf) {
        return BookDbDao.listBookInShelf(bookshelf);
    }
}

