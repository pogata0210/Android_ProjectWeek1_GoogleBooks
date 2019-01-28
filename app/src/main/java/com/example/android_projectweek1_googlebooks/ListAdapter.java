package com.example.android_projectweek1_googlebooks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private ArrayList<BookGiver> bookGiver;
    private Activity activity;
    Bitmap bitmap = null;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookTitle, bookAuthor;
        CheckBox favoriteButton;
        ImageView bookCover;
        ViewGroup parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);

        }
    }

    private ArrayList<BookGiver> dataList;
    private Context context;

   ListAdapter(ArrayList<BookGiver> dataList, Activity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(
                viewGroup.getContext())
                .inflate(
                        R.layout.element,
                        viewGroup,
                        false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BookGiver data = dataList.get(i);
        final String url = data.getImageUrl();
        Boolean switchState = viewHolder.favoriteButton.isChecked();

        viewHolder.bookTitle.setText(data.getTitle());
        viewHolder.bookAuthor.setText(data.getAuthor());
        if(url != null) {
            new DownloadBookImageTask(viewHolder.bookCover).execute(url);
        }

        viewHolder.favoriteButton.setOnCheckedChangeListener(null);
        viewHolder.favoriteButton.setChecked(data.isSelected());
        viewHolder.favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BookGiver book = new BookGiver(data.getTitle(), data.getAuthor(), data.getImageUrl(), data.getReview(), data.isRead());
                dataList.get(viewHolder.getAdapterPosition()).setSelected(isChecked);
                BookDbDao.addBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class DownloadBookImageTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;


        public DownloadBookImageTask(ImageView bookImageView) {
            this.imageViewWeakReference = new WeakReference<>(bookImageView);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap image;
            InputStream in;
            try {
                URL imageURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();


                in = connection.getInputStream();
                image = BitmapFactory.decodeStream(in);
                in.close();
                return image;
            } catch (IOException e) {
                e.printStackTrace();
                return  null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = imageViewWeakReference.get();
            imageView.setImageBitmap(bitmap);
        }
    }


}