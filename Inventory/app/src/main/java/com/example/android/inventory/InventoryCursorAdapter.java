package com.example.android.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

import static android.content.ContentValues.TAG;

/**
 * Created by Anderson on 25/03/2018.
 *
 * @link Inventory CursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of products data in the {@link Cursor}.
 */

public class InventoryCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.product_name);
        TextView priceTextView = view.findViewById(R.id.product_price);
        TextView quantityTextView = view.findViewById(R.id.product_quantity);
        ImageView photoImageView = view.findViewById(R.id.product_image);
        ImageView buyImageButton = view.findViewById(R.id.sell_product);

        // Find the columns of product attributes that we're interested in
        final int productIdColumnIndex = cursor.getInt(cursor.getColumnIndex(InventoryEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
        int photoColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE_URI);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        String imageUriString = cursor.getString(photoColumnIndex);
        Uri productImageUri = Uri.parse(imageUriString);

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        quantityTextView.setText(String.valueOf(productQuantity));
        photoImageView.setImageURI(productImageUri);

        buyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri productUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, productIdColumnIndex);
                adjustProductQuantity(context, productUri, productQuantity);
            }
        });
    }

    /**
     * This method reduced product stock by 1
     *
     * @param context                - Activity context
     * @param productUri             - Uri used to update the stock of a specific product in the ListView
     * @param currentQuantityInStock - current stock of that specific product
     */
    private void adjustProductQuantity(Context context, Uri productUri, int currentQuantityInStock) {

        // Subtract 1 from current value if quantity of product >= 1
        int newQuantityValue = (currentQuantityInStock >= 1) ? currentQuantityInStock - 1 : 0;

        if (currentQuantityInStock == 0) {
            Toast.makeText(context.getApplicationContext(), R.string.toast_out_of_stock_msg, Toast.LENGTH_SHORT).show();
        }

        // Update table by using new value of quantity
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryEntry.COLUMN_QUANTITY, newQuantityValue);
        int numRowsUpdated = context.getContentResolver().update(productUri, contentValues, null, null);
        if (numRowsUpdated > 0) {
            // Show error message in Logs with info about pass update.
            Log.i(TAG, context.getString(R.string.buy_msg_confirm));
        } else {
            Toast.makeText(context.getApplicationContext(), R.string.no_product_in_stock, Toast.LENGTH_SHORT).show();
            // Show error message in Logs with info about fail update.
            Log.e(TAG, context.getString(R.string.error_msg_stock_update));
        }
    }
}
