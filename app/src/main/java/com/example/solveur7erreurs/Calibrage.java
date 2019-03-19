package com.example.solveur7erreurs;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.CalendarContract;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Calibrage {

    private int width;
    private int height;
    private double pourcent;
    private int rotation;


    @NotNull
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

    public static Calibrage MinCal(ArrayList<Calibrage> e){
        Calibrage cal = e.get(0);
            for (int i = 0;i<4;i++)
            {
                if( cal.pourcent>e.get(i).pourcent )
                    cal=e.get(i);

            }
            return cal;
    }

    public static Calibrage recherche(Bitmap zone1, Bitmap im2){
        double p = 100;
        double p1,p2;
        int r = 0;
        Bitmap z1;
        Bitmap z2;

        //le bug se trouve dans ce bloc
        double xsize = im2.getWidth()/5;
        double ysize = im2.getHeight()/5;
        int x1 = (int) xsize;
        int y1 = (int) ysize;
        int x2 = 4*x1;
        int y2 = 4*y1;
        // fin du bloc
        Calibrage cal = new Calibrage(0,0,100,0);
        while ((p != 0)||(r<2))//for(int r=0 ; r<=1 ; r++)
        {
            z1 = rotateBitmap(zone1,r);
            z2 = rotateBitmap(zone1, -r);
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    p1 = ConvolutionMatrix.pourcentErreur2(z1, im2, x1, y1, x2, y2);
                    p2 = ConvolutionMatrix.pourcentErreur2(z2, im2, x1, y1, x2, y2);
                    p = Math.min(p1,p2);
                    if (p <= cal.getPourcent()) {
                        cal.setPourcent(p);
                        cal.setHeight(i);
                        cal.setWidth(j);
                        if (p == p1) {
                            cal.setRotation(r);
                        }
                        else{
                            cal.setRotation(-r);
                        }
                        if (p == 0) return cal;
                    }
                }
            } r++;
        }
        return cal;
    }

    public static Bitmap zone( Bitmap im1){
        int h1 = im1.getHeight();
        int w1 = im1.getWidth();
        int y1 = (int) Math.floor(2*h1/5);
        int y2 = 3*y1;
        int x1 = (int) Math.floor(2*w1/5);
        int x2 = 3*x1;
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

