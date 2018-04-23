package com.example.android.booklisting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private ImageView thumbnailView;

    /**
     * Constructs a new {@link BookAdapter}.
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BookAdapter(MainActivity context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        thumbnailView = listItemView.findViewById(R.id.thumbnail);
        loadImageFromUrl(currentBook.getThumbnail());

        String title = currentBook.getTitle();
        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(title);

        String author = currentBook.getAuthors();
        TextView authorView = listItemView.findViewById(R.id.author);
        authorView.setText(author);

        String publisher = currentBook.getPublisher();
        TextView publisherView = listItemView.findViewById(R.id.publisher);
        publisherView.setText(publisher);

        return listItemView;
    }

    // Display the thumbnail of the current book in that ImageView
    private void loadImageFromUrl(String url) {
        Picasso.with(getContext()).load(url)
                .error(R.mipmap.ic_launcher)
                .into(thumbnailView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError() {

                    }
                });
    }
}