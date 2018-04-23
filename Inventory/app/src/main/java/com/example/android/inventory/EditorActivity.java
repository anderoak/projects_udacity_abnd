package com.example.android.inventory;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract.InventoryEntry;

/**
 * Created by Anderson on 25/03/2018.
 *
 * Allows user to create a new product or edit an existing one.
 */

public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /** URI of product image */
    private Uri mImageUri;

    /**
     * Current quantity of product
     */
    private int mQuantity;

    /** Identifier for the product data loader */
    private static final int EXISTING_PRODUCT_LOADER = 0;

    /** Content URI for the existing product (null if it's a new product) */
    private Uri mCurrentProductUri;

    /** EditText field to enter the product's name */
    private EditText mNameEditText;

    /** EditText field to enter the product's provider */
    private EditText mProviderEditText;

    /** EditText field to enter the provider's e-mail */
    private EditText mProviderEmailEditText;

    /** EditText field to enter the product's price */
    private EditText mPriceEditText;

    /** EditText field to enter the product's quantity */
    private EditText mQuantityEditText;

    /**
     * ImageView field to enter the product's photo
     */
    private ImageView mPhoto;

    /** Boolean flag that keeps track of whether the product has been edited (true) or not (false) */
    private boolean mProductHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mProductHasChanged boolean to true.
     */
    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    // BOOLEAN status for required fields, TRUE if these fields have been populated
    private boolean hasAllRequiredValues = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new product or editing an existing one.
        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        // Find all relevant views that we will need to read user input from
        mNameEditText = findViewById(R.id.edit_name);
        mProviderEditText = findViewById(R.id.edit_provider);
        mProviderEmailEditText = findViewById(R.id.edit_provider_email);
        mPriceEditText = findViewById(R.id.edit_price);
        /*
      Button ADD PRODUCT to increasing amount of product in the stock
     */
        ImageView mAddProductButton = findViewById(R.id.plus_button);
        mQuantityEditText = findViewById(R.id.edit_quantity);
        /*
      Button REDUCE PRODUCT to decreasing amount of product in the stock
     */
        ImageView mReduceProductButton = findViewById(R.id.minus_button);
        /*
      EditText field to displaying hint on EditorActivity at the bottom of photo
     */
        ImageView mOrderMoreButton = findViewById(R.id.order_more_button);
        mPhoto = findViewById(R.id.product_edit_image);

        // If the intent DOES NOT contain a product content URI, then we know that we are
        // creating a new product.
        if (mCurrentProductUri == null) {
            // This is a new product, so change the app bar to say "Add a Product"
            setTitle(getString(R.string.editor_activity_title_new_product));
            mNameEditText.setEnabled(true);
            mProviderEditText.setEnabled(true);
            mProviderEmailEditText.setEnabled(true);
            mAddProductButton.setVisibility(View.GONE);
            mQuantityEditText.setEnabled(true);
            mReduceProductButton.setVisibility(View.GONE);
            mOrderMoreButton.setVisibility(View.GONE);
            mPhoto.setImageResource(R.drawable.no_image);
            mPhoto.setVisibility(View.VISIBLE);

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a product that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing product, so change app bar to say "Edit Product"
            setTitle(getString(R.string.editor_activity_title_edit_product));

            mProviderEditText.setEnabled(true);
            mProviderEmailEditText.setEnabled(true);
            mAddProductButton.setVisibility(View.VISIBLE);
            mQuantityEditText.setEnabled(true);
            mReduceProductButton.setVisibility(View.VISIBLE);
            mOrderMoreButton.setVisibility(View.VISIBLE);
            mPhoto.setImageResource(R.drawable.no_image);
            mPhoto.setVisibility(View.VISIBLE);

            // Initialize a loader to read the product data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mNameEditText.setOnTouchListener(mTouchListener);
        mProviderEditText.setOnTouchListener(mTouchListener);
        mProviderEmailEditText.setOnTouchListener(mTouchListener);
        mPriceEditText.setOnTouchListener(mTouchListener);
        mQuantityEditText.setOnTouchListener(mTouchListener);
        mAddProductButton.setOnTouchListener(mTouchListener);
        mReduceProductButton.setOnTouchListener(mTouchListener);
        mOrderMoreButton.setOnTouchListener(mTouchListener);
        mPhoto.setOnTouchListener(mTouchListener);

        mAddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemButton();
                mProductHasChanged = true;
            }
        });

        mReduceProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceItemButton();
                mProductHasChanged = true;
            }
        });

        mOrderMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderMore();
            }
        });

        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trySelector();
                mProductHasChanged = true;
            }
        });
    }

    private void trySelector() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
        openSelector();
    }

    private void openSelector() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType(getString(R.string.intent_type));
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openSelector();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mImageUri = data.getData();
                mPhoto.setImageURI(mImageUri);
                mPhoto.invalidate();
            }
        }
    }

    private void orderMore() {
        Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
        intent.setType(getString(R.string.mail_string01));
        intent.setData(Uri.parse(getString(R.string.mail_string02) + mProviderEmailEditText.getText().toString().trim()));
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_string03) +
                mNameEditText.getText().toString().trim());
        String message = getString(R.string.mail_string04)
                        + mNameEditText.getText().toString().trim()
                        + getString(R.string.mail_string05)
                        + getString(R.string.mail_string06);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }

    /**
     * Get user input from editor and save product into database.
     */
    private boolean saveProduct() {

        // Quantity of products
        int quantity;

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String providerString = mProviderEditText.getText().toString().trim();
        String providerEmailString = mProviderEmailEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();

        // Check if this is supposed to be a new product
        // and check if all the fields in the editor are blank
        if (mCurrentProductUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(providerString) &&
                TextUtils.isEmpty(providerEmailString) && TextUtils.isEmpty(priceString) &&
                TextUtils.isEmpty(quantityString) &&
                mImageUri == null) {
            // Since no fields were modified, we can return early without creating a new product.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            hasAllRequiredValues = true;
        }

        // Create a ContentValues object where column names are the keys,
        // and product attributes from the editor are the values.
        ContentValues values = new ContentValues();

        // REQUIRED VALUES
        // Validation section
        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, getString(R.string.validation_msg_product_name), Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;
        } else {
            values.put(InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
        }

        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, getString(R.string.validation_msg_provider_name), Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;
        } else {
            values.put(InventoryEntry.COLUMN_PROVIDER, providerString);
        }

        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, getString(R.string.validation_msg_provider_email), Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;
        } else {
            values.put(InventoryEntry.COLUMN_PROVIDER_MAIL, providerEmailString);
        }

        if (TextUtils.isEmpty(priceString)) {
            Toast.makeText(this, getString(R.string.validation_msg_product_price), Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;
        } else {
            values.put(InventoryEntry.COLUMN_PRICE, priceString);
        }

        if (TextUtils.isEmpty(quantityString)) {
            Toast.makeText(this, getString(R.string.validation_msg_product_quantity), Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;
        } else {
            // If the quantity is not provided by the user, don't try to parse the string into an
            // integer value. Use 0 by default.
            quantity = Integer.parseInt(quantityString);
            values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
        }

        if (mImageUri == null) {
            Toast.makeText(this, getString(R.string.validation_msg_product_image), Toast.LENGTH_SHORT).show();
            return hasAllRequiredValues;
        } else {
            values.put(InventoryEntry.COLUMN_IMAGE_URI, mImageUri.toString());
        }

        // Determine if this is a new or existing pet by checking if mCurrentProductUri is null or not
        if (mCurrentProductUri == null) {
            // This is a NEW product, so insert a new product into the provider,
            // returning the content URI for the new product.
            Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING product, so update the product with content URI: mCurrentProductUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentProductUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentProductUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        hasAllRequiredValues = true;
        return hasAllRequiredValues;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new product, hide the "Delete" menu item.
        if (mCurrentProductUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                saveProduct();
                if (hasAllRequiredValues) {
                    // Exit activity
                    finish();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the product hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the product hasn't changed, continue with handling back button press
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all product attributes, define a projection that contains
        // all columns from the product table
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PROVIDER,
                InventoryEntry.COLUMN_PROVIDER_MAIL,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY,
                InventoryEntry.COLUMN_IMAGE_URI
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentProductUri,            // Query the content URI for the current product
                projection,                    // Columns to include in the resulting Cursor
                null,                  // No selection clause
                null,              // No selection arguments
                null);                // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of product attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int providerColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PROVIDER);
            int providerMailColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PROVIDER_MAIL);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
            int pictureColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE_URI);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String provider = cursor.getString(providerColumnIndex);
            String providerMail = cursor.getString(providerMailColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            String imageUriString = cursor.getString(pictureColumnIndex);
            mQuantity = quantity;
            mImageUri = Uri.parse(imageUriString);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mProviderEditText.setText(provider);
            mProviderEmailEditText.setText(providerMail);
            mPriceEditText.setText(price);
            mQuantityEditText.setText(Integer.toString(quantity));
            mPhoto.setImageURI(mImageUri);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mProviderEditText.setText("");
        mProviderEmailEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
        mPhoto.setImageResource(R.drawable.no_image);
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Prompt the user to confirm that they want to delete this pet.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the product.
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteProduct() {
        // Only perform the delete if this is an existing product.
        if (mCurrentProductUri != null) {
            // Call the ContentResolver to delete the product at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentProductUri
            // content URI already identifies the product that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_product_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_product_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

    private void addItemButton() {
        mQuantity++;
        displayQuantity();
    }

    private void reduceItemButton() {
        if (mQuantity == 0) {
            Toast.makeText(this, getString(R.string.minimum_quantity), Toast.LENGTH_SHORT).show();
        } else {
            mQuantity--;
            displayQuantity();
        }
    }

    private void displayQuantity() {
        mQuantityEditText.setText(String.valueOf(mQuantity));
    }
}
