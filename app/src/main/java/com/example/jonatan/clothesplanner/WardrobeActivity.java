package com.example.jonatan.clothesplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

public class WardrobeActivity extends AppCompatActivity {

    private IWardrobe wardrobe;
    private LinearLayout wardrobeItemsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        wardrobeItemsLinearLayout = (LinearLayout) findViewById(R.id.wardrobe_layout);
        wardrobe = new Wardrobe(wardrobeItemsLinearLayout, this);
    }

    public void addWardrobeItem(@SuppressWarnings("UnusedParameters") View view) {
        EditText editText = (EditText) findViewById(R.id.editText_add_item);
        wardrobe.addWardrobeItem(editText, wardrobeItemsLinearLayout, this);
    }
}
