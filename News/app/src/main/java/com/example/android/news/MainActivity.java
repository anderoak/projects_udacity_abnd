package com.example.android.news;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<News>> {

    // URL for news data from The Guardian
    private static final String THE_GUARDIAN_NEWS_REQUEST_URL =
            "https://content.guardianapis.com/search?";

    // Constant value for the news loader ID.
    private static final int NEWS_LOADER_ID = 1;

    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private String mNewsForSearch;
    private String mSearchRequestURL;
    private EditText mSearchField;
    private NetworkInfo mNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Hide loading indicator
        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mLoadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.waiting_search);

        newsSearcher(mNewsForSearch);

        mSearchField = findViewById(R.id.search_field);

        // EditText listener for start the user search
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mNewsForSearch = mSearchField.getText().toString();
                    newsSearcher(mNewsForSearch);
                }
                return false;
            }
        });
    }

    public void newsSearcher(String contentToSearch) {

        String queryParam = getResources().getString(R.string.query_param);

        if (contentToSearch != null && contentToSearch.length() != 0) {
            mSearchRequestURL = THE_GUARDIAN_NEWS_REQUEST_URL + "q=" + contentToSearch + queryParam;
        }
        else
        {
            mSearchRequestURL = THE_GUARDIAN_NEWS_REQUEST_URL + queryParam;
        }

        restartLoader();

        ListView newsListView = findViewById(R.id.list);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);

        // Show more information about the news on browser.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getWebUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        mNetworkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader.
            loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
        } else {
            // Otherwise, display error
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    // Create a new loader for the given URL
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, mSearchRequestURL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            mEmptyStateTextView.setText(R.string.no_news);
        }
        mAdapter.clear();
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    // Loader reset
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    // Restart loader for a new search
    public void restartLoader() {
        mEmptyStateTextView.setText(R.string.empty_state_string);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
    }
}
