package com.example.jonatan.clothesplanner.wardrobe;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jonatan on 2017-01-28.
 */

public interface IFileHandlingHelper {
    void loadWardrobe(IWardrobe wardrobe);

    void storeWardrobe(IWardrobe wardrobe);
}
