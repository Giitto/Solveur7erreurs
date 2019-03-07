package com.example.solveur7erreurs;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

public class Calibrage {

    public static Bitmap zone( Bitmap im1){
        int h1 = im1.getHeight();
        int w1 = im1.getWidth();
        int y1 = (int) Math.floor(2*h1/5);
        int y2 = h1 - y1;
        int x1 = (int) Math.floor(2*w1/5);
        int x2 = w1 - x1;
        int[] pixels = new int[(x2 - x1)*(y2 - y1)];
        im1.getPixels(pixels,0,x2-x1,x1,y1,x2-x1,y2-y1);
        Bitmap zone = Bitmap.createBitmap(x2-x1,y2-y1,im1.getConfig());
        zone.setPixels(pixels,0,x2-x1,0,0,x2-x1,y2-y1);

        return zone;

    }

    public static Bitmap calibrage1(Bitmap im1, Bitmap im2)
    {
        while(ConvolutionMatrix.pourcentErreur(im1,im2) !=0)
        {
            im2 = rotateBitmap(im2,90);
        }
        return ConvolutionMatrix.findDifference(im1,im2);
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap Compress(Bitmap source, int qualite)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.JPEG, qualite, stream);
        byte[] byteArray = stream.toByteArray();
        source = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        return source;
    }
}
