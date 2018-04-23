package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// Helper methods related to requesting and receiving book data from Google Books.
public final class Query {

    // Tag for the log messages
    private static final String LOG_TAG = Query.class.getSimpleName();

    private Query() {
    }

    // Query the Google Books and return a list of {@link Book} objects.
    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Book}s
        return books;
    }

    // Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    // Make an HTTP request to the given URL and return a String as the response
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, String.valueOf(R.string.error_response_code + urlConnection.getResponseCode()));
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.problem_retiving), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /** Convert the {@link InputStream} into a String which contains the
        whole JSON response from the server. */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /** Return a list of {@link Book} objects that has been built up from
        parsing the given JSON response. */
    private static List<Book> extractFeatureFromJson(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response string.
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                // Extract the value for the key called "smallThumbnail"
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String thumbnail = imageLinks.getString("thumbnail");

                // Extract the value for the key called "title"
                String title = volumeInfo.getString("title");

                // Extract the value for the key called "authors"
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                String author = authorArray.getString(0);

                // Extract the value for the key called "publisher"
                String publisher = volumeInfo.getString("publisher");

                // Extract the value for the key called "infoLink"
                String infolink = volumeInfo.getString("infoLink");

                // Create a new {@link Book} object from the JSON response.
                Book book = new Book(thumbnail, title, author, publisher, infolink);

                // Add the new {@link Book} to the list of books.
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.problem_parsing), e);
        }
        // Return the list of books
        return books;
    }
}