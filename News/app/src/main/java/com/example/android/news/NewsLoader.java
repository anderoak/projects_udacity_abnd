package com.example.android.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/* Loads a list of news by using an AsyncTaskLoader to perform the
   network request to the given URL. */
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    // Query URL
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<News> news = Query.fetchNewsData(mUrl);
        return news;
    }
}