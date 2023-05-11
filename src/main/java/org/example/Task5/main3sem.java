package org.example.Task5;

import java.io.File;
import java.io.IOException;

public class main3sem {
    public static void main(String[] args) throws IOException {
        File file = new File("256colour.png");

        Image img = new Image(file);

        Float val = 1f;
        Image newImg = img.blur(val, 10);
//        Image newImg = img.blur(val, val, val, val, val);
        newImg.save("NEW.png");
    }
}
