package com.example.android.musically;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView artists;
    private TextView albums;
    private TextView musics;
    private TextView store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_list);

        artists = findViewById(R.id.artists);
        albums = findViewById(R.id.albums);
        musics = findViewById(R.id.musics);
        store = findViewById(R.id.musicStore);

        // Set a click listener to Artists List
        artists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(MainActivity.this, ArtistListActivity.class);
                startActivity(artistsIntent);
            }
        });

        // Set a click listener to Albums List
        albums.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent albumsIntent = new Intent(MainActivity.this, AlbumListActivity.class);
                startActivity(albumsIntent);
            }
        });

        // Set a click listener to Musics List
        musics.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent musicIntent = new Intent(MainActivity.this, MusicListActivity.class);
                startActivity(musicIntent);
            }
        });

        // Set a click listener to Store
        store.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent storeIntent = new Intent(MainActivity.this, StoreActivity.class);
                startActivity(storeIntent);
            }
        });

    }
}
