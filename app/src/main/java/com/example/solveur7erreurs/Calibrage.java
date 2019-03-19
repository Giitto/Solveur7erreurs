package com.example.solveur7erreurs;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Calibrage {

    private int width;
    private int height;
    private double pourcent;
    private int rotation;


    @Override
    public String toString() {
        return "Calibrage{" +
                "width=" + width +
                ", height=" + height +
                ", pourcent=" + pourcent +
                ", rotation=" + rotation +
                '}';
    }

    public Calibrage(int width, int height, int poucent, int rotation){
        this.width = width;
        this.height = height;
        this.pourcent = poucent;
        this.rotation = rotation;
    }

   /* public Calibrage MinCal( ArrayList<Calibrage> e){
        cal
            for (int i = 0;i<4;i++)
            {

            }
    }*/

    public static Calibrage recherche(Bitmap zone1, Bitmap im2){
        double p;
        int x1 = (im2.getWidth()/5);
        int y1 = (int) (im2.getHeight()/5);
        System.out.println(x1 + " " +y1);
        System.out.println(zone1.getWidth() + " " + zone1.getHeight());
        int xsize = x1;
        int ysize = y1;
        int x2 = 3*x1;
        int y2 = 3*y1;
        Calibrage cal = new Calibrage(0,0,100,0);
        for(int r=-5 ; r<=-4 ; r++) {
            //zone1=rotateBitmap(zone1,r);
            for (int i = x1; i <= x2+2; i++) {
                for (int j = y1; j <= y2 +2; j++) {
                    p = ConvolutionMatrix.pourcentErreur(zone1, im2, i, j, i + xsize, j + ysize);
                    if (p < cal.getPourcent()) {
                        cal.setPourcent(p);
                        cal.setHeight(j);
                        cal.setWidth(i);
                        //cal.setRotation(r);
                    }
                }
            }
        }

        return cal;
    }

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


    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setPourcent(double pourcent) {
        this.pourcent = pourcent;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }


    private int getWidth() {
        return width;
    }



    private int getHeight() {
        return height;
    }

    private double getPourcent() {
        return pourcent;
    }

    private int getRotation() {
        return rotation;
    }
}

