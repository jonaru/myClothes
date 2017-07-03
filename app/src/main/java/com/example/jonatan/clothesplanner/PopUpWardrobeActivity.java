package com.example.jonatan.clothesplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private Drawable selectedDrawable;
    private WardrobeItemType selectedItemType;
    private View highlightedView;
    private ImageButton galleryImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_wardrobe);
        selectedDrawable = null;
        selectedItemType = null;
        highlightedView = null;

        galleryImageButton = (ImageButton) findViewById(R.id.galleryImageButton);
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        IWardrobe wardrobe = Wardrobe.getInstance();

        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        Spinner wardrobeSpinner = (Spinner) findViewById(R.id.wardrobe_spinner);
        String itemTypeString = (String) wardrobeSpinner.getSelectedItem();
        String itemText = editText.getText().toString();


        if (selectedDrawable != null)
        {
            wardrobe.addWardrobeItem(itemText, selectedItemType, selectedDrawable);
        }
        else
        {
            wardrobe.addWardrobeItem(itemText, itemTypeString);
        }

        finish();
    }

    public void selectShirt(@SuppressWarnings("UnusedParameters") View view) {
        highlightSelection(view);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.SHIRT;
    }

    public void selectTrousers(@SuppressWarnings("UnusedParameters") View view) {
        highlightSelection(view);
        ImageButton imageButton = (ImageButton)view;
        selectedDrawable = imageButton.getDrawable();
        selectedItemType = WardrobeItemType.TROUSERS;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_GALLERY_REQUEST){
                //Got back image
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    //Scale the image
                    float imageOriginalWidthHeightRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
                    int imageToShowWidth = galleryImageButton.getWidth();
                    int imageToShowHeight = (int) (imageToShowWidth / imageOriginalWidthHeightRatio);
                    Bitmap imageToShow = Bitmap.createScaledBitmap(bitmap, imageToShowWidth, imageToShowHeight, true);

                    //display the image
                    galleryImageButton.setImageBitmap(imageToShow);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void takePhoto(@SuppressWarnings("UnusedParameters") View view) {
        //Invoke take photo
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
