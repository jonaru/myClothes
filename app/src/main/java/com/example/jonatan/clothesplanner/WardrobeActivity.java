package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.RemoveButtonOnClickListener;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WardrobeActivity extends AppCompatActivity {

    private IWardrobe wardrobe;
    private LinearLayout wardrobeItemsLinearLayout;
    private FileOutputStream fileOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        wardrobeItemsLinearLayout = (LinearLayout) findViewById(R.id.wardrobe_layout);
        wardrobe = new Wardrobe();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(getResources().getString(R.string.wardrobe_view));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
                wardrobeItemViewGroup.addView(createNewTextView(trimmedLine));
                wardrobeItemViewGroup.addView(createNewRemoveButton());
                wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) {
        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        wardrobe.addWardrobeItem(editText.toString());

        try {
            fileOutputStream = openFileOutput(getResources().getString(R.string.wardrobe_view), Context.MODE_PRIVATE);
            fileOutputStream.write(editText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
        wardrobeItemViewGroup.addView(createNewTextView(editText.getText().toString()));
        wardrobeItemViewGroup.addView(createNewRemoveButton());
        wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
        editText.setText("");
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

    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        return textView;
    }
}
