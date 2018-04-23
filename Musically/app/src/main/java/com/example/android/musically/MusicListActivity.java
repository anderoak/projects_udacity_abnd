package com.example.android.musically;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MusicListActivity extends AppCompatActivity {

    private TextView playlist;
    private TextView artists;
    private TextView albums;
    private TextView store;

    private TextView sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

        //Find views to use on listerners
        playlist = findViewById(R.id.playList);
        artists = findViewById(R.id.artists);
        albums = findViewById(R.id.albums);
        store = findViewById(R.id.musicStore);

        sample = findViewById(R.id.sampleMusic);

        // Set a click listener to PlayList List
        playlist.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent playListIntent = new Intent(MusicListActivity.this, MainActivity.class);
                startActivity(playListIntent);
            }
        });

        // Set a click listener to Artists List
        artists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(MusicListActivity.this, ArtistListActivity.class);
                startActivity(artistsIntent);
            }
        });

        // Set a click listener to Albums List
        albums.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent albumsIntent = new Intent(MusicListActivity.this, AlbumListActivity.class);
                startActivity(albumsIntent);
            }
        });

        // Set a click listener to Store
        store.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent storeIntent = new Intent(MusicListActivity.this, StoreActivity.class);
                startActivity(storeIntent);
            }
        });

        // Set a click listener to sample
        sample.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(MusicListActivity.this, MainActivity.class);
                startActivity(sampleIntent);
            }
        });
    }
}
