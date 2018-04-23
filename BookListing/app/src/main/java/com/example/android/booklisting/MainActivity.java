package com.example.android.booklisting;

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
        implements LoaderCallbacks<List<Book>> {

    // URL for books data from the Google Books
    private static final String GOOGLE_BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    // Constant value for the book loader ID.
    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter mAdapter;
    private TextView emptyStateTextView;
    private View loadingIndicator;
    private String bookForSearch;
    private String searchRequestURL;
    private EditText searchField;

    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Hide loading indicator
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView = findViewById(R.id.empty_view);
        emptyStateTextView.setText(R.string.waiting_search);

        searchField = findViewById(R.id.search_field);

        // EditText listener for start the user search
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    bookForSearch = searchField.getText().toString();
                    searchRequestURL = GOOGLE_BOOKS_REQUEST_URL + bookForSearch;

                    restartLoader();

                    ListView bookListView = findViewById(R.id.list);
                    emptyStateTextView = findViewById(R.id.empty_view);
                    bookListView.setEmptyView(emptyStateTextView);
                    mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());
                    bookListView.setAdapter(mAdapter);

                    // Show more information about the book on browser.
                    bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Book currentBook = mAdapter.getItem(position);
                            Uri bookUri = Uri.parse(currentBook.getInfoLink());
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                            startActivity(websiteIntent);
                        }
                    });

                    // Get a reference to the ConnectivityManager to check state of network connectivity
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);

                    // Get details on the currently active default data network
                    networkInfo = connMgr.getActiveNetworkInfo();

                    // If there is a network connection, fetch data
                    if (networkInfo != null && networkInfo.isConnected()) {
                        LoaderManager loaderManager = getLoaderManager();

                        // Initialize the loader.
                        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    } else {
                        // Otherwise, display error
                        View loadingIndicator = findViewById(R.id.loading_indicator);
                        loadingIndicator.setVisibility(View.GONE);
                        emptyStateTextView.setText(R.string.no_internet_connection);
                    }
                }
                return false;
            }
        });
    }

    // Create a new loader for the given URL
    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, searchRequestURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        if (networkInfo != null && networkInfo.isConnected()) {
            emptyStateTextView.setText(R.string.no_books);
        }
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    // Loader reset
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    // Restart loader for a new search
    public void restartLoader() {
        emptyStateTextView.setText(R.string.empty_state_string);
        loadingIndicator.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }
}
