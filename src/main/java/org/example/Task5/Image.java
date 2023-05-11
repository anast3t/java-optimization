package org.example.Task5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Image {
    private static class RGB {
        public int red;
        public int green;
        public int blue;

        RGB(int r, int g, int b) {
            red = r;
            green = g;
            blue = b;
        }

        public RGB mult(Float coef){
            return new RGB((int) (red*coef), (int) (green*coef), (int) (blue*coef));
        }

        static RGB sum(RGB[] values){
            RGB res = new RGB(0, 0, 0);
            for(RGB value: values){
               res.red += value.red;
               res.green += value.green;
               res.blue += value.blue;
            }
            res.red = (int) Math.floor((float) (res.red) / values.length);
            res.green = (int) Math.floor((float) (res.green) / values.length);
            res.blue = (int) Math.floor((float) (res.blue) / values.length);

            return res;
        }
    }

    private final int sizeX;
    private final int sizeY;

    private RGB[][] imageData;

    public void setPixel(int x, int y, RGB pixel) {
        this.imageData[x][y] = pixel;
    }

    Image(File imgFile) throws IOException {
        BufferedImage img = ImageIO.read(imgFile);


        sizeX = img.getWidth();
        sizeY = img.getHeight();
        this.imageData = new RGB[sizeX][sizeY];

        System.out.println(Arrays.toString(intToRaw(img.getRGB(63, 63))));
         
        System.out.println( img.getType());
//        int x = 0;
//        int y = 0;
//        System.out.println("img.getRGB(30, 30) = " + img.getRGB(x, y));
//        System.out.println("converted int-raw-int = " + rawToInt(intToRaw(img.getRGB(x, y))));
//        System.out.println("converted int-rgb-int = " + RGBToInt(intToRGB(img.getRGB(x, y))));

        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                setPixel(x, y, intToRGB(img.getRGB(x, y)));
            }
        }

    }

    Image(int X, int Y){
        sizeX = X;
        sizeY = Y;
        imageData = new RGB[X][Y];
    }

    public void save(String name) throws IOException {
        int[] data = new int[sizeX * sizeY];
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                data[y*sizeX + x] = RGBToInt(imageData[x][y]);
            }
        }
        BufferedImage newImage = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_3BYTE_BGR);
        newImage.setRGB(0, 0, sizeX, sizeY, data, 0, sizeX);

        File newFile = new File(name);
        ImageIO.write(newImage, "PNG", newFile);
    }

    public Image blur(Float a, Float b, Float c, Float d, Float e){
        Image imgNew = new Image(sizeX, sizeY);


        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++){
                if(x == 0 || x == (sizeX - 1) || y == 0 || y == (sizeY - 1)){
                    imgNew.setPixel(x, y, imageData[x][y]);
                } else {
                    imgNew.setPixel(x, y, RGB.sum(
                            new RGB[]{
                                    imageData[x][y].mult(a),
                                    imageData[x-1][y].mult(b),
                                    imageData[x+1][y].mult(c),
                                    imageData[x][y-1].mult(d),
                                    imageData[x][y+1].mult(e)
                            }
                    ));
                }
            }
        }
        return imgNew;
    }

    public Image blur(Float coef, int radius){
        Image imgNew = new Image(sizeX, sizeY);

        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++){

                int startX = x - radius;
                int endX = x + radius;
                int startY = y - radius;
                int endY = y + radius;

                if(startX < 0)
                    startX = 0;
                if(startY < 0)
                    startY = 0;
                if(endX >= sizeX-1)
                    endX = sizeX-1;
                if(endY >= sizeY-1)
                    endY = sizeY-1;

                RGB[] rgbarr = new RGB[(endX - startX + 1) * (endY - startY + 1)];

                for(int i = startX; i <= endX; i++){
                    for(int j = startY; j <= endY; j++){

                        int slot = (i-startX)*(endY - startY + 1) + (j-startY);
                        rgbarr[slot] = imageData[i][j].mult(coef);

                    }
                }

                imgNew.setPixel(x, y, RGB.sum(rgbarr));
            }
        }
        return imgNew;
    }

    private static int[] intToRaw(int value) {
        return new int[]{
                (int) (char) (value >>> 24) & 0xFF, //A
                (int) (char) (value >>> 16) & 0xFF, //R
                (int) (char) (value >>> 8) & 0xFF, //G
                (int) (char) value & 0xFF}; //B
    }

    private RGB intToRGB(int value) {
        return new RGB(
                (int) (char) (value >>> 16) & 0xFF,
                (int) (char) (value >>> 8) & 0xFF,
                (int) (char) value & 0xFF);
    }

    private int RGBToInt(RGB rgb) {
        int value = 0;
        int[] raw = {255, rgb.red, rgb.green, rgb.blue};
        for (int color : raw) {
            value = (value << 8) + (color & 0xFF);
        }
        return value;
    }

    private int rawToInt(int[] raw) {
        int value = 0;
        for (int color : raw) {
            value = (value << 8) + (color & 0xFF);
        }
        return value;
    }
}
