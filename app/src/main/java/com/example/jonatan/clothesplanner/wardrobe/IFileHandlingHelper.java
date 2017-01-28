package com.example.jonatan.clothesplanner.wardrobe;

import java.io.IOException;

/**
 * Created by Jonatan on 2017-01-28.
 */

public interface IFileHandlingHelper {
    public void loadWardrobe(IWardrobe wardrobe);

    public void writeToWardrobeFile(String itemText, String itemTypeString) throws IOException;
}
