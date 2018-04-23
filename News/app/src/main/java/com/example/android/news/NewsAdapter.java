package com.example.android.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    private ImageView mThumbnailView;

    /**
     * Constructs a new {@link NewsAdapter}.
     * @param context of the app
     * @param news is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(MainActivity context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        mThumbnailView = listItemView.findViewById(R.id.thumbnail);
        loadImageFromUrl(currentNews.getThumbnail());

        String title = currentNews.getTitle();
        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(title);

        String section = currentNews.getSection();
        TextView authorView = listItemView.findViewById(R.id.section);
        authorView.setText(section);

        String datePubl = currentNews.getDatePubl();
        TextView publisherView = listItemView.findViewById(R.id.date_publ);
        publisherView.setText(datePubl);

        return listItemView;
    }

    // Display the thumbnail of the current news in that ImageView
    private void loadImageFromUrl(String url) {
        Picasso.with(getContext()).load(url)
                .error(R.mipmap.ic_launcher)
                .into(mThumbnailView,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError() {

                    }
                });
    }
}