package com.example.android.news;/*
  Created by Anderson on 16/03/2018.
  This class represent a simple news object.
 */

/**
 * An {@link News} object contains information related to a single news.
 */
public class News {

    // URL of the news thumbnail image
    private String mThumbnail;

    // Title of the news
    private String mTitle;

    // Section where the news was publicated
    private String mSection;

    // Date of the news was publicated
    private String mDatePubl;

    // Info Web Page URL of the news
    private String mWebUrl;

    /**
     * Constructs a new {@link News} object.
     * @param thumbnail is the URL of the news thumbnail image
     * @param title is the title of the news
     * @param section is the section where the news was publicated
     * @param datePubl is the date of the news was publicated
     * @param webUrl is the website URL to find more details about of the news
     */
    public News(String thumbnail, String title, String section, String datePubl, String webUrl) {
        mThumbnail = thumbnail;
        mTitle = title;
        mSection = section;
        mDatePubl = datePubl;
        mWebUrl = webUrl;
    }

    // Returns the URL of the news thumbnail cover image.
    public String getThumbnail() {
        return mThumbnail;
    }

    // Returns the title of the news.
    public String getTitle() {
        return mTitle;
    }

    // Returns the section where the news was publicated
    public String getSection() {
        return mSection;
    }

    // Returns the date the news was publicated
    public String getDatePubl() {
        return mDatePubl;
    }

    // Returns the website URL to find more information about the news.
    public String getWebUrl() {
        return mWebUrl;
    }
}