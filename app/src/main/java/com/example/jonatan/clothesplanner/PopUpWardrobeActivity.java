package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PopUpWardrobeActivity extends Activity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 1;
    private Drawable selectedDrawable;
    private WardrobeItemType selectedItemType;
    private View highlightedView;
    private ImageButton galleryImageButton;
    private Bitmap imageBitmap;

    // save the Activity's instance state as it gets destroyed
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("imageBitmap", imageBitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_wardrobe);
        selectedDrawable = null;
        selectedItemType = null;
        highlightedView = null;

        galleryImageButton = (ImageButton) findViewById(R.id.galleryImageButton);

        //Need to restore the state of the camera's captured image bitmap since it seems the activity often gets recreated on some devices (Samsung) and the image then lost and not displayed
        if (savedInstanceState != null) {
            imageBitmap = savedInstanceState.getParcelable("imageBitmap");
        }

        if(imageBitmap != null) {
            galleryImageButton.setImageBitmap(imageBitmap);
        }
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        IWardrobe wardrobe = Wardrobe.getInstance();

        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        String itemText = editText.getText().toString();


        if (selectedDrawable != null)
        {
            wardrobe.addWardrobeItem(itemText, selectedItemType, selectedDrawable);
        }
        else
        {
            Spinner wardrobeSpinner = (Spinner) findViewById(R.id.wardrobe_spinner);
            String itemTypeString = (String) wardrobeSpinner.getSelectedItem();
            wardrobe.addWardrobeItem(itemText, itemTypeString);
        }

        finish();
    }

    public void selectShirt(@SuppressWarnings("UnusedParameters") View view) {
        highlightSelection(view);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.UPPER;
    }

    public void selectTrousers(@SuppressWarnings("UnusedParameters") View view) {
        highlightSelection(view);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.LOWER;
    }

    public void selectFromGallery(@SuppressWarnings("UnusedParameters") View view) {
        highlightSelection(view);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();

        Spinner wardrobeSpinner = (Spinner) findViewById(R.id.wardrobe_spinner);
        String itemTypeString = (String) wardrobeSpinner.getSelectedItem();
        selectedItemType = Wardrobe.getInstance().getItemTypeFromString(itemTypeString);
    }

    public void getFromGallery(@SuppressWarnings("UnusedParameters") View view) {
        //Set up gallery intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri pictureDirectoryUri = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(pictureDirectoryUri, "image/*");

        //launch gallery
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    public void takePhoto(@SuppressWarnings("UnusedParameters") View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (imageBitmap != null){
                imageBitmap.recycle();
            }

            if (requestCode == IMAGE_GALLERY_REQUEST){
                displayPickedGalleryImage(data);
            }
            else if (requestCode == CAMERA_REQUEST){
                displayCapturedCameraImage(data);
            }
        }
    }

    private void displayPickedGalleryImage(Intent data) {
        Uri imageUri = data.getData();
        InputStream inputStream;

        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            imageBitmap = BitmapFactory.decodeStream(inputStream);

            //Scale the image to make it thumb sized
            float imageOriginalWidthHeightRatio = (float) imageBitmap.getWidth() / (float) imageBitmap.getHeight();
            int imageToShowWidth = galleryImageButton.getWidth() - 15; //make room for the highlight to show
            int imageToShowHeight = (int) (imageToShowWidth / imageOriginalWidthHeightRatio);
            Bitmap imageToShow = Bitmap.createScaledBitmap(imageBitmap, imageToShowWidth, imageToShowHeight, true);

            //display the image
            galleryImageButton.setImageBitmap(imageToShow);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
        }
    }

    private void displayCapturedCameraImage(Intent data) {
        Bundle extras = data.getExtras();
        imageBitmap = (Bitmap) extras.get("data");
        galleryImageButton.setImageBitmap(imageBitmap);
    }

    private void highlightSelection(View view) {
        if (highlightedView != null){
            highlightedView.setBackground(null);
        }

        Drawable highlight = ResourcesCompat.getDrawable(getResources(), R.drawable.highlight, null);
        view.setBackground(highlight);
        highlightedView = view;
    }
}
