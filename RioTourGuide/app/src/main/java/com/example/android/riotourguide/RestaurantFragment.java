package com.example.android.riotourguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {

    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_list, container, false);
        final ArrayList<Card> cards = new ArrayList<Card>();

        // Loads data in ArrayList.
        cards.add(new Card(R.drawable.r01,
                getString(R.string.restaurant01_name),
                getString(R.string.restaurant01_address),
                getString(R.string.restaurant01_site),
                getString(R.string.restaurant01_phone)));
        cards.add(new Card(R.drawable.r02,
                getString(R.string.restaurant02_name),
                getString(R.string.restaurant02_address),
                getString(R.string.restaurant02_site),
                getString(R.string.restaurant02_phone)));
        cards.add(new Card(R.drawable.r03,
                getString(R.string.restaurant03_name),
                getString(R.string.restaurant03_address),
                getString(R.string.restaurant03_site),
                getString(R.string.restaurant03_phone)));
        cards.add(new Card(R.drawable.r04,
                getString(R.string.restaurant04_name),
                getString(R.string.restaurant04_address),
                getString(R.string.restaurant04_site),
                getString(R.string.restaurant04_phone)));
        cards.add(new Card(R.drawable.r05,
                getString(R.string.restaurant05_name),
                getString(R.string.restaurant05_address),
                getString(R.string.restaurant05_site),
                getString(R.string.restaurant05_phone)));
        cards.add(new Card(R.drawable.r06,
                getString(R.string.restaurant06_name),
                getString(R.string.restaurant06_address),
                getString(R.string.restaurant06_site),
                getString(R.string.restaurant06_phone)));

        CardAdapter adapter = new CardAdapter(getActivity(), cards);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // Opens the browser with the site indicated on the card.
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String cardSite = cards.get(arg2).getmCardSite();
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + cardSite));
                startActivity(launchBrowser);
            }
        } );
        return rootView;
    }
}
