package com.example.android.riotourguide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anderson on 28/02/2018.
 *
 * Create a list of Turistic Cards
 *
 */

public class CardAdapter extends ArrayAdapter<Card> {

    private static final String LOG_TAG = CardAdapter.class.getSimpleName();

    public CardAdapter(Activity context, ArrayList<Card> cards) {
        super(context, 0, (List<Card>) cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.card, parent,
                    false);
        }

        // Place photo.
        Card currentCard = (Card) getItem(position);
        ImageView defaultImageView = (ImageView) listItemView.findViewById(R.id.image_view);
        defaultImageView.setImageResource(currentCard.getCardImageSource());

        // Place name.
        TextView titleTextView = listItemView.findViewById(R.id.title_view);
        titleTextView.setText(currentCard.getmCardTitle());

        // Place address.
        TextView addressTextView = listItemView.findViewById(R.id.address_view);
        addressTextView.setText(currentCard.getmCardAddress());

        // Place site address.
        TextView siteTextView = listItemView.findViewById(R.id.site_view);
        siteTextView.setText(currentCard.getmCardSite());

        // Place phone number.
        TextView phoneTextView = listItemView.findViewById(R.id.phone_view);
        phoneTextView.setText(currentCard.getmCardPhone());

        return listItemView;
    }
}
