package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final IWardrobe wardrobe = new Wardrobe();
    private LinearLayout wardrobeItemsLinearLayout;
    private FileOutputStream fileOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wardrobeItemsLinearLayout = (LinearLayout) findViewById(R.id.wardrobe_layout);

        try {
            fileOutputStream = openFileOutput("wardrobeView", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addWardrobeItem(View view) {
        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        wardrobe.addWardrobeItem(editText, wardrobeItemsLinearLayout, this);

        /*
        LinearLayout wardrobeItemViewGroup = new LinearLayout(this);
        wardrobeItemViewGroup.addView(createNewTextView(editText.getText().toString()));
        wardrobeItemViewGroup.addView(createNewRemoveButton());
        wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
        */

        try {
            fileOutputStream.write(editText.getText().toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    private View createNewRemoveButton() {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final Button removeButton = new Button(this);
        removeButton.setLayoutParams(layoutParams);
        removeButton.setText("remove");
        removeButton.setGravity(View.FOCUS_RIGHT);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup parentView = (ViewGroup) view.getParent();
                ViewGroup grandparentView = (ViewGroup) parentView.getParent();
                grandparentView.removeView(parentView);
            }
        });
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
    */
}
