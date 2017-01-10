package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.RemoveButtonOnClickListener;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WardrobeActivity extends AppCompatActivity {

    private IWardrobe wardrobe;
    private LinearLayout wardrobeItemsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        wardrobeItemsLinearLayout = (LinearLayout) findViewById(R.id.wardrobe_layout);
        wardrobe = Wardrobe.getInstance();

        try {
            readWardrobeFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) throws WardrobeException {
        if (wardrobe == null) {
            throw new WardrobeException();
        }

        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        Spinner wardrobeSpinner = (Spinner) findViewById(R.id.wardrobe_spinner);
        String itemTypeString = (String) wardrobeSpinner.getSelectedItem();
        String itemText = editText.getText().toString();

        IWardrobeItem addedWardrobeItem = wardrobe.addWardrobeItem(itemText, itemTypeString);
        addWardrobeItemToView(addedWardrobeItem);
        editText.setText("");

        try {
            writeToWardrobeFile(itemText, itemTypeString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToWardrobeFile(String itemText, String itemTypeString) throws IOException {
        FileOutputStream fileOutputStream;
        fileOutputStream = openFileOutput(getResources().getString(R.string.wardrobe_view), Context.MODE_PRIVATE);
        fileOutputStream.write(itemText.getBytes());
        fileOutputStream.close();
    }

    private void readWardrobeFromFile() throws IOException {
        FileInputStream fileInputStream = null;

        fileInputStream = openFileInput(getResources().getString(R.string.wardrobe_view));
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            IWardrobeItem addedWardrobeItem = wardrobe.addWardrobeItem(trimmedLine, getResources().getString(R.string.shirt));
            addWardrobeItemToView(addedWardrobeItem);
        }

        reader.close();
    }

    private void addWardrobeItemToView(IWardrobeItem addedWardrobeItem) {
        LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
        wardrobeItemViewGroup.addView(addedWardrobeItem.getView(this));
        wardrobeItemViewGroup.addView(createNewRemoveButton());
        wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
    }

    private View createNewRemoveButton() {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final Button removeButton = new Button(this);
        removeButton.setLayoutParams(layoutParams);
        removeButton.setText(R.string.remove);
        removeButton.setGravity(View.FOCUS_RIGHT);
        removeButton.setOnClickListener(new RemoveButtonOnClickListener(this));
        return removeButton;
    }
}
