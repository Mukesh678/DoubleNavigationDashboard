package com.app.doublenavigationdashboard.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.doublenavigationdashboard.R;
import com.app.doublenavigationdashboard.database.DatabaseHelper;
import com.app.doublenavigationdashboard.model.Customer;
import com.app.doublenavigationdashboard.utils.Constants;
import com.app.doublenavigationdashboard.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by d on 07/07/2016.
 */
public class RegistrationActivity extends AppCompatActivity{
    private Customer mCustomer;
    private DatabaseHelper db;

    //Image properties
    private String mCurrentImagePath = null;
    private Uri mCapturedImageURI = null;
    private ImageButton mProfileImageButton;

    private EditText mNameEditText,
            mEmailEditText,
            mPhoneEditText,
            mStreetEditText,
            mCityEditText,
            mZipCodeEditText,
            mStateEditText;
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customer_details);
        db = new DatabaseHelper(this);
        InitializeViews();
    }

    private void InitializeViews() {
        mProfileImageButton = (ImageButton)findViewById(R.id.customer_image_button);
        mNameEditText = (EditText) findViewById(R.id.edit_text_customer_name);
        mEmailEditText = (EditText) findViewById(R.id.edit_text_customer_email);
        mPhoneEditText = (EditText) findViewById(R.id.edit_text_customer_phone);
        mPhoneEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        mPhoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        mStreetEditText = (EditText) findViewById(R.id.edit_text_customer_street_address);
        mCityEditText = (EditText) findViewById(R.id.edit_text_customer_city);
        mStateEditText = (EditText) findViewById(R.id.edit_text_customer_state);
        mZipCodeEditText = (EditText) findViewById(R.id.edit_text_customer_zip_code);
        mButton= (Button) findViewById(R.id.button);
        mProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCustomer();
            }
        });
    }

    private void SaveCustomer(){
        mCustomer = new Customer();
        mCustomer.setName(mNameEditText.getText().toString());
        mCustomer.setEmailAddress(mEmailEditText.getText().toString());
        mCustomer.setPhoneNumber(mPhoneEditText.getText().toString());
        mCustomer.setStreetAddress(mStreetEditText.getText().toString());
        mCustomer.setCity(mCityEditText.getText().toString());
        mCustomer.setState(mStateEditText.getText().toString());
        mCustomer.setPostalCode(mZipCodeEditText.getText().toString());

        //Check to see if there is valid image path temporarily in memory
        //Then save that image path to the database and that becomes the profile
        //Image for this user.
        if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty())
        {
            mCustomer.setImagePath(mCurrentImagePath);
        }

        long result = db.addCustomer(mCustomer);
        if (result == -1 ){
            Toast.makeText(this, "Unable to add customer: " + mCustomer.getName(), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "Customer Added Successfully " + mCustomer.getName(), Toast.LENGTH_LONG).show();

        this.onBackPressed();
    }

    private void chooseImage(){

        //We need the customer's name to to save the image file
        if (mNameEditText.getText() != null && !mNameEditText.getText().toString().isEmpty()) {
            // Determine Uri of camera image to save.
            final File rootDir = new File(Constants.PICTURE_DIRECTORY);

            //noinspection ResultOfMethodCallIgnored
            rootDir.mkdirs();

            // Create the temporary file and get it's URI.

            //Get the customer name
            String customerName = mNameEditText.getText().toString();

            //Remove all white space in the customer name
            customerName.replaceAll("\\s+", "");

            //Use the customer name to create the file name of the image that will be captured
            File file = new File(rootDir, FileUtils.generateImageName(customerName));
            mCapturedImageURI = Uri.fromFile(file);

            // Initialize a list to hold any camera application intents.
            final List<Intent> cameraIntents = new ArrayList<Intent>();

            // Get the default camera capture intent.
            final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Get the package manager.
            final PackageManager packageManager = this.getPackageManager();

            // Ensure the package manager exists.
            if (packageManager != null) {

                // Get all available image capture app activities.
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

                // Create camera intents for all image capture app activities.
                for(ResolveInfo res : listCam) {

                    // Ensure the activity info exists.
                    if (res.activityInfo != null) {

                        // Get the activity's package name.
                        final String packageName = res.activityInfo.packageName;

                        // Create a new camera intent based on android's default capture intent.
                        final Intent intent = new Intent(captureIntent);

                        // Set the intent data for the current image capture app.
                        intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        intent.setPackage(packageName);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                        // Add the intent to available camera intents.
                        cameraIntents.add(intent);
                    }
                }
            }

            // Create an intent to get pictures from the filesystem.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of filesystem options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

            // Start activity to choose or take a picture.
            startActivityForResult(chooserIntent, Constants.ACTION_REQUEST_IMAGE);
        } else {
            mNameEditText.setError("Please enter customer name");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            // Get the resultant image's URI.
            final Uri selectedImageUri = (data == null) ? mCapturedImageURI : data.getData();

            // Ensure the image exists.
            if (selectedImageUri != null) {

                // Add image to gallery if this is an image captured with the camera
                //Otherwise no need to re-add to the gallery if the image already exists
                if (requestCode == Constants.ACTION_REQUEST_IMAGE) {
                    final Intent mediaScanIntent =
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(selectedImageUri);
                    this.sendBroadcast(mediaScanIntent);
                }

                mCurrentImagePath = FileUtils.getPath(this, selectedImageUri);

                // Update client's picture
                if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
                    mProfileImageButton.setImageDrawable(new BitmapDrawable(getResources(),
                            FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
                }
            }
        }

    }
}
