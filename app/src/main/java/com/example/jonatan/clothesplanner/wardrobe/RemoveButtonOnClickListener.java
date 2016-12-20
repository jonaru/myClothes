package com.example.jonatan.clothesplanner.wardrobe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonatan.clothesplanner.MainActivity;
import com.example.jonatan.clothesplanner.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Jonatan on 2016-12-18.
 */

public class RemoveButtonOnClickListener implements View.OnClickListener {
    private final MainActivity mainActivity;

    public RemoveButtonOnClickListener(MainActivity inputMainActivity){
        super();

        mainActivity = inputMainActivity;
    }

    @Override
    public void onClick(View view) {
        ViewGroup parentView = (ViewGroup) view.getParent();
        TextView textView = (TextView) parentView.getChildAt(0);
        removeWardrobeItemFromFile(textView);

        ViewGroup grandparentView = (ViewGroup) parentView.getParent();
        grandparentView.removeView(parentView);
    }

    //Need to clean up technical debt here
    private void removeWardrobeItemFromFile(TextView textView) {
        try {
            removeLineFromFile(mainActivity.getResources().getString(R.string.wardrobe_view), textView.getText().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean removeLineFromFile(String wardrobeFileString, String lineToRemove) throws IOException {
        FileInputStream fileInputStream = mainActivity.openFileInput(wardrobeFileString);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        final String myTempFileString = "myTempFile.txt";
        FileOutputStream fileOutputStream = mainActivity.openFileOutput(myTempFileString, Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

        File inputFile = new File(mainActivity.getFilesDir()+"/"+wardrobeFileString);
        File tempFile = new File(mainActivity.getFilesDir()+"/"+myTempFileString);
        return tempFile.renameTo(inputFile);
    }
}
