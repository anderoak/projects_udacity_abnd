package com.example.android.musically;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ArtistListActivity extends AppCompatActivity {

    private TextView playlist;
    private TextView albums;
    private TextView musics;
    private TextView store;

    private TextView sample;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_list);

        //Find views to use on listerners
        playlist = findViewById(R.id.playList);
        albums = findViewById(R.id.albums);
        musics = findViewById(R.id.musics);
        store = findViewById(R.id.musicStore);

        sample = findViewById(R.id.sampleArtist);

        // Set a click listener to Artists List
        playlist.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(ArtistListActivity.this, MainActivity.class);
                startActivity(artistsIntent);
            }
        });

        // Set a click listener to Albums List
        albums.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent albumsIntent = new Intent(ArtistListActivity.this, AlbumListActivity.class);
                startActivity(albumsIntent);
            }
        });

        // Set a click listener to Musics List
        musics.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent musicIntent = new Intent(ArtistListActivity.this, MusicListActivity.class);
                startActivity(musicIntent);
            }
        });

        // Set a click listener to Store
        store.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent storeIntent = new Intent(ArtistListActivity.this, StoreActivity.class);
                startActivity(storeIntent);
            }
        });

        // Set a click listener to Sample
        sample.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(ArtistListActivity.this, AlbumListActivity.class);
                startActivity(sampleIntent);
            }
        });
    }
}
