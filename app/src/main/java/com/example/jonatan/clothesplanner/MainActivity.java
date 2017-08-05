package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IStorageAdapter;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobedb.WardrobeDbHelper;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.WardrobeItemType;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() { super.onResume(); }

    public void enterWardrobe(View view) {
        //Initialize Wardrobe
        IStorageAdapter storageAdapter = new WardrobeDbHelper(getApplicationContext());
        Wardrobe.initInstance(storageAdapter);
        Wardrobe.getInstance().loadWardrobe();

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
