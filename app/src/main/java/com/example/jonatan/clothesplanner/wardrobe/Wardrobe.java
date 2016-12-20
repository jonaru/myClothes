package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2016-12-13.
 */
public class Wardrobe implements IWardrobe {
    private final List<IWardrobeItem> wardrobeItemList = new ArrayList<>();
    private FileOutputStream fileOutputStream;

    public Wardrobe ()
    {
    }

    @Override
    public void addWardrobeItem(EditText itemText, LinearLayout wardrobeItemsLinearLayout, MainActivity mainActivity) {
        String wardrobeItemString = itemText.getText().toString();
        IWardrobeItem itemToAdd = new WardrobeItem(wardrobeItemString);
        addWardrobeItem(itemToAdd);

        try {
            fileOutputStream = mainActivity.openFileOutput(mainActivity.getResources().getString(R.string.wardrobe_view), Context.MODE_PRIVATE);
            fileOutputStream.write(itemText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout wardrobeItemViewGroup = new LinearLayout(mainActivity);
        wardrobeItemViewGroup.addView(createNewTextView(itemText.getText().toString(), mainActivity));
        wardrobeItemViewGroup.addView(createNewRemoveButton(mainActivity));
        wardrobeItemsLinearLayout.addView(wardrobeItemViewGroup);
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

    private View createNewRemoveButton(MainActivity mainActivity) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final Button removeButton = new Button(mainActivity);
        removeButton.setLayoutParams(layoutParams);
        removeButton.setText(R.string.remove);
        removeButton.setGravity(View.FOCUS_RIGHT);
        removeButton.setOnClickListener(new RemoveButtonOnClickListener(mainActivity));
        return removeButton;
    }

    private TextView createNewTextView(String text, MainActivity mainActivity) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(mainActivity);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        return textView;
    }

}
