package com.example.android.riotourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // Identify the fragment by position
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HotelFragment();
        } else if (position == 1){
            return new RestaurantFragment();
        } else if (position == 2){
            return new ShoppingFragment();
        } else {
            return new AttractionsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    // Get tab name.
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return mContext.getString(R.string.category_hotels);
        } else if (position == 1) {
            return mContext.getString(R.string.category_restaurants);
        } else if (position == 2) {
            return mContext.getString(R.string.category_shoppings);
        } else {
            return mContext.getString(R.string.category_attractions);
        }
    }
}
