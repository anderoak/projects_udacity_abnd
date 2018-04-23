package com.example.android.booklisting;

/*
  Created by Anderson on 12/03/2018.
  This class represent a simple book object.
 */

/**
 * An {@link Book} object contains information related to a single book.
 */
public class Book {

    // URL of the book thumbnail cover image
    private String mThumbnail;

    // Name of the book
    private String mTitle;

    // Authors of the book
    private String mAuthors;

    // Publisher of the book
    private String mPublisher;

    // Info Webpage URL of the book
    private String mInfoLink;

    /**
     * Constructs a new {@link Book} object.
     * @param thumbnail is the URL of the book thumbnail cover image
     * @param title is the name of the book
     * @param authors is the author of the book
     * @param publisher is the publisher of the book
     * @param infolink is the website URL to find more details about of the book
     */
    public Book(String thumbnail, String title, String authors, String publisher, String infolink) {
        mThumbnail = thumbnail;
        mTitle = title;
        mAuthors = authors;
        mPublisher = publisher;
        mInfoLink = infolink;
    }

    // Returns the URL of the book thumbnail cover image.
    public String getThumbnail() {
        return mThumbnail;
    }

    // Returns the title of the book.
    public String getTitle() {
        return mTitle;
    }

    // Returns the authors of the book.
    public String getAuthors() {
        return mAuthors;
    }

    // Returns the publisher of the book.
    public String getPublisher() {
        return mPublisher;
    }

    // Returns the website URL to find more information about the book.
    public String getInfoLink() {
        return mInfoLink;
    }
}