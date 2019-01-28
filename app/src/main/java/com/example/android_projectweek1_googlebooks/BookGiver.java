package com.example.android_projectweek1_googlebooks;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookGiver implements Parcelable {
    private String id, title, author, imageUrl, review, desc, saleLink,publishedDate;
    private int read;
    private int pages;
    private boolean Favorite;

    public BookGiver(String title, String author, String imageUrl, String review, int read) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.review = review;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.read = read;
        this.saleLink = saleLink;
        this.Favorite = Favorite;
    }

    public BookGiver(JSONObject jsonObject) {

        try {
            this.id = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject json = jsonObject.getJSONObject("volumeInfo");

            try {
                this.title = json.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray authorsJsonArray = json.getJSONArray("authors");
                this.author = parseAuthors(authorsJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                this.author = "";
            }
            try {
                JSONObject links = json.getJSONObject("imageLinks");
                this.imageUrl = links.getString("smallThumbnail");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.review = "";
        this.Favorite= false;
        this.read = 0;
    }
    private static String parseAuthors(JSONArray authorsAry) {

        StringBuilder authorsString = new StringBuilder();

        for (int i = 0; i < authorsAry.length(); i++) {
            try {
                if (i == 0) {
                    authorsString.append(authorsAry.getString(i));
                } else {
                    authorsString.append(" - ").append(authorsAry.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return authorsString.toString();
    }

    protected BookGiver(Parcel in) {
        title = in.readString();
        author = in.readString();
        review = in.readString();
        imageUrl = in.readString();
        saleLink = in.readString();
        read = in.readInt();
        pages = in.readInt();
        desc = in.readString();
        publishedDate = in.readString();
        Favorite= in.readByte() != 0;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(review);
        dest.writeString(imageUrl);
        dest.writeString(saleLink);
        dest.writeString(desc);
        dest.writeString(publishedDate);
        dest.writeInt(read);
        dest.writeInt(pages);
        dest.writeByte((byte)(Favorite ? 1 : 0));

    }

    public static final Creator<BookGiver> CREATOR = new Creator<BookGiver>() {
        @Override
        public BookGiver createFromParcel(Parcel in) {

            return new BookGiver(in);
        }

        @Override
        public BookGiver[] newArray(int size) {

            return new BookGiver[size];
        }
    };


    public String getId() {
        return id;
    }

    public String getSaleLink() {
        return saleLink;
    }

    public void setSaleLink(String saleLink) {
        this.saleLink = saleLink;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    public String getDesc() {
        return desc;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int isRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public boolean isSelected() {
        return Favorite;
    }

    public void setSelected(boolean selected) {
        this.Favorite= selected;
    }
}

