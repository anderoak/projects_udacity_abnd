package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/* Loads a list of books by using an AsyncTaskLoader to perform the
   network request to the given URL. */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    // Query URL
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Book> books = Query.fetchBookData(mUrl);
        return books;
    }
}