package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.R;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    private final List<IWardrobeItem> wardrobeItemList = new ArrayList<>();
    //private FileOutputStream fileOutputStream;

    public Wardrobe ()
    {
    }

    /*
    public Wardrobe(LinearLayout wardrobeItemsLinearLayout, AppCompatActivity activity) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = activity.openFileInput(activity.getResources().getString(R.string.wardrobe_view));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                LinearLayout wardrobeItemViewGroup = new LinearLayout(activity);
                wardrobeItemViewGroup.addView(createNewTextView(trimmedLine, activity));
                wardrobeItemViewGroup.addView(createNewRemoveButton(activity));
                wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    @Override
    public void addWardrobeItem(EditText itemText, LinearLayout wardrobeItemsLinearLayout, AppCompatActivity activity) {
        String wardrobeItemString = itemText.getText().toString();
        IWardrobeItem itemToAdd = new WardrobeItem(wardrobeItemString);
        addWardrobeItem(itemToAdd);

        /*
        try {
            fileOutputStream = activity.openFileOutput(activity.getResources().getString(R.string.wardrobe_view), Context.MODE_PRIVATE);
            fileOutputStream.write(itemText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        LinearLayout wardrobeItemViewGroup = new LinearLayout(activity);
        wardrobeItemViewGroup.addView(createNewTextView(itemText.getText().toString(), activity));
        wardrobeItemViewGroup.addView(createNewRemoveButton(activity));
        wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
        */
    }

    @Override
    public void addWardrobeItem(IWardrobeItem wardrobeItem) {
        wardrobeItemList.add(0, wardrobeItem);
    }

    @Override
    public IWardrobeItem findWardrobeItem(String wardrobeItemString) {
        for (IWardrobeItem item : wardrobeItemList)
        {
            if (item.getWardrobeItemString().compareTo(wardrobeItemString) == 0)
            {
                return item;
            }
        }
        return null;
    }

    /*
    private View createNewRemoveButton(AppCompatActivity activity) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final Button removeButton = new Button(activity);
        removeButton.setLayoutParams(layoutParams);
        removeButton.setText(R.string.remove);
        removeButton.setGravity(View.FOCUS_RIGHT);
        removeButton.setOnClickListener(new RemoveButtonOnClickListener(activity));
        return removeButton;
    }

    private TextView createNewTextView(String text, AppCompatActivity activity) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(activity);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        return textView;
    }
    */

}
