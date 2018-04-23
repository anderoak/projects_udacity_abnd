package com.example.android.musically;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class StoreActivity extends AppCompatActivity {

    private TextView playlist;
    private TextView artists;
    private TextView albums;
    private TextView musics;

    private TextView musicPayment;
    private TextView albumPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_store);

        //Find views to use on listerners
        playlist = findViewById(R.id.playList);
        artists = findViewById(R.id.artists);
        albums = findViewById(R.id.albums);
        musics = findViewById(R.id.musics);

        musicPayment = findViewById(R.id.buyMusic);
        albumPayment = findViewById(R.id.buyAlbum);

        // Set a click listener to PlayList List
        playlist.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent playListIntent = new Intent(StoreActivity.this, MainActivity.class);
                startActivity(playListIntent);
            }
        });

        // Set a click listener to Artists List
        artists.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(StoreActivity.this, ArtistListActivity.class);
                startActivity(artistsIntent);
            }
        });

        // Set a click listener to Albums List
        albums.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent albumsIntent = new Intent(StoreActivity.this, AlbumListActivity.class);
                startActivity(albumsIntent);
            }
        });

        // Set a click listener to Musics List
        musics.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent musicIntent = new Intent(StoreActivity.this, MusicListActivity.class);
                startActivity(musicIntent);
            }
        });

        // Set a click listener to Buy Music
        musicPayment.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent musicPayIntent = new Intent(StoreActivity.this, InAppPayment.class);
                startActivity(musicPayIntent);
            }
        });

        // Set a click listener to Buy Album
        albumPayment.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent albumPayIntent = new Intent(StoreActivity.this, InAppPayment.class);
                startActivity(albumPayIntent);
            }
        });

    }
}
