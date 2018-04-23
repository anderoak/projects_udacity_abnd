package com.example.android.news;

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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

// Helper methods related to requesting and receiving news data from The Guardian.
public final class Query {

    // Tag for the log messages
    private static final String LOG_TAG = Query.class.getSimpleName();
    private static final int READ_TIMEOUT_TIME = 10000;  /* milliseconds */
    private static final int CONNECT_TIMEOUT_TIME = 10000;  /* milliseconds */
    private static final int RESPONSE_CODE = 200;

    private Query() {
    }

    // Query The Guardian and return a list of {@link News} objects.
    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.problem_http), e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}s
        return news;
    }

    // Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.problem_bulid_url), e);
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
            urlConnection.setReadTimeout(READ_TIMEOUT_TIME);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT_TIME);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == RESPONSE_CODE) {
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

    /** Return a list of {@link News} objects that has been built up from
        parsing the given JSON response. */
    private static List<News> extractFeatureFromJson(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> news = new ArrayList<>();

        // Try to parse the JSON response string.
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray newsArray = responseObject.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {

                // Get a single news at position i within the list of news
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract the value for the key called "title"
                String title = currentNews.getString("webTitle");

                // Extract the value for the key called "sectionName"
                String section = currentNews.getString("sectionName");

                // Extract the value for the key called "webPublicationDate"
                String date = currentNews.getString("webPublicationDate");

                // Convert date
                String dateConverted = dateConverter(date);

                // Extract the value for the key called "webUrl"
                String webUrl = currentNews.getString("webUrl");

                // Extract the value for the object called "fields"
                JSONObject fields = currentNews.getJSONObject("fields");

                // Extract the value for the key called "thumbnail"
                String thumbnail = fields.getString("thumbnail");

                // Create a new {@link News} object from the JSON response.
                News aNews = new News(thumbnail, title, section, dateConverted, webUrl);

                // Add a New {@link News} to the list of news.
                news.add(aNews);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.problem_parsing), e);
        }
        // Return the list of news
        return news;
    }

    public static String dateConverter(String date) {
        String substr=date.substring(0,10);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date dateConverted = null;
        try {
            dateConverted = fmt.parse(substr);
        } catch (ParseException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.problem_date), e);
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
        return fmtOut.format(dateConverted);
    }
}