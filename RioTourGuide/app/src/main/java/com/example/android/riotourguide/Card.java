package com.example.android.riotourguide;

/**
 * Created by Anderson on 28/02/2018.
 *
 * A Informative touristic card.
 */

public class Card {

    private int mCardImageSource;
    private String mCardTitle;
    private String mCardAddress;
    private String mCardSite;
    private String mCardPhone;

    /**
     *
     * @param cardImageSource   Reference to place photo file.
     * @param cardTitle         Place name.
     * @param cardAddress       Place address.
     * @param cardSite          Place site address, without "http://".
     * @param cardPhone         Place phone number.
     */
    public Card(int cardImageSource, String cardTitle, String cardAddress, String cardSite, String cardPhone){
        mCardImageSource = cardImageSource;
        mCardTitle = cardTitle;
        mCardAddress = cardAddress;
        mCardSite = cardSite;
        mCardPhone = cardPhone;
    }

    /**
     *
     * @return Reference to place photo file.
     */
    public int getCardImageSource() {
        return mCardImageSource;
    }

    /**
     *
     * @return Place name.
     */
    public String getmCardTitle() {
        return mCardTitle;
    }

    /**
     *
     * @return  Place address.
     */
    public String getmCardAddress() {
        return mCardAddress;
    }

    /**
     *
     * @return Place site address, without "http://".
     */
    public  String getmCardSite() {
        return mCardSite;
    }

    /**
     *
     * @return Place phone number.
     */
    public String getmCardPhone() {
        return mCardPhone;
    }
}
