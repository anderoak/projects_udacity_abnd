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
public class AttractionsFragment extends Fragment {

    public AttractionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_list, container, false);
        final ArrayList<Card> cards = new ArrayList<Card>();

        // Loads data in Array.
        cards.add(new Card(R.drawable.a01,
                getString(R.string.attraction01_name),
                getString(R.string.attraction01_address),
                getString(R.string.attraction01_site),
                getString(R.string.attraction01_phone)));
        cards.add(new Card(R.drawable.a02,
                getString(R.string.attraction02_name),
                getString(R.string.attraction02_address),
                getString(R.string.attraction02_site),
                getString(R.string.attraction02_phone)));
        cards.add(new Card(R.drawable.a03,
                getString(R.string.attraction03_name),
                getString(R.string.attraction03_address),
                getString(R.string.attraction03_site),
                getString(R.string.attraction03_phone)));
        cards.add(new Card(R.drawable.a04,
                getString(R.string.attraction04_name),
                getString(R.string.attraction04_address),
                getString(R.string.attraction04_site),
                getString(R.string.attraction04_phone)));
        cards.add(new Card(R.drawable.a05,
                getString(R.string.attraction05_name),
                getString(R.string.attraction05_address),
                getString(R.string.attraction05_site),
                getString(R.string.attraction05_phone)));
        cards.add(new Card(R.drawable.a06,
                getString(R.string.attraction06_name),
                getString(R.string.attraction06_address),
                getString(R.string.attraction06_site),
                getString(R.string.attraction06_phone)));

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
