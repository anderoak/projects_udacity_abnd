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
public class ShoppingFragment extends Fragment {

    public ShoppingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_list, container, false);
        final ArrayList<Card> cards = new ArrayList<Card>();

        // Loads data in ArrayList.
        cards.add(new Card(R.drawable.s01,
                getString(R.string.shopping01_name),
                getString(R.string.shopping01_address),
                getString(R.string.shopping01_site),
                getString(R.string.shopping01_phone)));
        cards.add(new Card(R.drawable.s02,
                getString(R.string.shopping02_name),
                getString(R.string.shopping02_address),
                getString(R.string.shopping02_site),
                getString(R.string.shopping02_phone)));
        cards.add(new Card(R.drawable.s03,
                getString(R.string.shopping03_name),
                getString(R.string.shopping03_address),
                getString(R.string.shopping03_site),
                getString(R.string.shopping03_phone)));
        cards.add(new Card(R.drawable.s04,
                getString(R.string.shopping04_name),
                getString(R.string.shopping04_address),
                getString(R.string.shopping04_site),
                getString(R.string.shopping04_phone)));
        cards.add(new Card(R.drawable.s05,
                getString(R.string.shopping05_name),
                getString(R.string.shopping05_address),
                getString(R.string.shopping05_site),
                getString(R.string.shopping05_phone)));
        cards.add(new Card(R.drawable.s06,
                getString(R.string.shopping06_name),
                getString(R.string.shopping06_address),
                getString(R.string.shopping06_site),
                getString(R.string.shopping06_phone)));

        CardAdapter adapter = new CardAdapter(getActivity(), cards);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // Opens the browser with the site indicated on the card.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String cardSite = cards.get(arg2).getmCardSite();
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + cardSite));
                startActivity(launchBrowser);
            }
        });
        return rootView;
    }
}
