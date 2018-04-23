package com.example.android.musically;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumListActivity extends AppCompatActivity {

    private TextView playlist;
    private TextView artists;
    private TextView musics;
    private TextView store;

    private ImageView sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list);

        //Find views to use on listerners
        playlist = findViewById(R.id.playList);
        artists = findViewById(R.id.artists);
        musics = findViewById(R.id.musics);
        store = findViewById(R.id.musicStore);

        sample = findViewById(R.id.sampleAlbum);

        // Set a click listener to PlayList List
        playlist.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent playListIntent = new Intent(AlbumListActivity.this, MainActivity.class);
                startActivity(playListIntent);
            }
        });

        // Set a click listener to Artists List
        artists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent artitistsIntent = new Intent(AlbumListActivity.this, ArtistListActivity.class);
                startActivity(artitistsIntent);
            }
        });

        // Set a click listener to Musics List
        musics.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent musicIntent = new Intent(AlbumListActivity.this, MusicListActivity.class);
                startActivity(musicIntent);
            }
        });

        // Set a click listener to Store
        store.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent storeIntent = new Intent(AlbumListActivity.this, StoreActivity.class);
                startActivity(storeIntent);
            }
        });

        // Set a click listener to Sample
        sample.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(AlbumListActivity.this, MusicListActivity.class);
                startActivity(sampleIntent);
            }
        });
    }
}
