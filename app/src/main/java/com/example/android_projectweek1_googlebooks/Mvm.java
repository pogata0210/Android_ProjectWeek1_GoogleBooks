package com.example.android_projectweek1_googlebooks;

import java.util.ArrayList;

public class Mvm {
    private static Repo repo = new Repo();;
    public static void addBook(BookGiver BookGiver){
        repo.addBook(BookGiver);
    }

    public static ArrayList<BookGiver> readBooks(){
        return repo.readBooks();
    }

    public static void updateBookHasRead(BookGiver BookGiver){
        repo.updateBookRead(BookGiver);
    }

    public static void updateBookIsFavorite(BookGiver BookGiver){
        repo.updateBookFavorite(BookGiver);
    }

    public static void updateBookUserReview(BookGiver BookGiver){
        repo.updateBookReview(BookGiver);
    }

    public static void deleteBook(BookGiver BookGiver){
        repo.deleteBook(BookGiver);
    }

    public static void addBookShelf(BookShelf BookShelf){
        repo.addBookshelf(BookShelf);
    }

    public static ArrayList<BookShelf> readBookshelves(){
        return repo.listBookshelves();
    }

    public static void addBookShelfBookRelation(BookShelf BookShelf, BookGiver BookGiver){
        repo.addBookInShelf(BookShelf, BookGiver);
    }

}