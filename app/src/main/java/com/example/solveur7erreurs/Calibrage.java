package com.example.solveur7erreurs;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Calibrage {

    private int width;
    private int height;
    private double pourcent;
    private int rotation;
    private int r2;


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

    public Calibrage(int width, int height, int poucent, int rotation, int r2){
        this.width = width;
        this.height = height;
        this.pourcent = poucent;
        this.rotation = rotation;
        this.r2 = r2;
    }



    public static Calibrage MinCal(ArrayList<Calibrage> e){
        Calibrage cal = e.get(0);
            for (int i=1 ; i<4 ; i++)
            {
                if(cal.getPourcent() > e.get(i).getPourcent())
                    cal = e.get(i);
            }
        return cal;
    }

    public static Calibrage recherche(Bitmap im1, Bitmap im2, int r1){
        Bitmap zone2;
        Bitmap zone3=null;
        double p ;
        double p1 ;
        double p2 = 100;
        int x1, y1, x2, y2, z1, z2;
        Calibrage cal = new Calibrage(0,0,100,0, r1);
        int r = 0;
        while(r<=5)
        /*for(int r=-1 ; r<=0 ; r++) */{
            zone2=zone(rotateBitmap(im1,r));
            if(r!=0) {
                zone3 = zone(rotateBitmap(im1, -r));
            }
            x1 = ((im2.getWidth()/9));
            y1 = ((im2.getHeight()/9));
            z1 = zone2.getWidth();
            z2 = zone2.getHeight();
            x2 = (x1*6)-z1;
            y2 = (y1*6)-z2;


            //System.out.println("z1: " +z1 + " z2: " +z2);
            //System.out.println("x1: " +x1 + " y1: " +y1);
            //System.out.println("x2: " +x2 + " y2: " +y1);

            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
//                    System.out.println( "i j" + i + " " +j);
                    p1 = ConvolutionMatrix.pourcentErreur(zone2, im2, i, j, i + z1, j + z2);
                    if(r!=0) {
                        p2 = ConvolutionMatrix.pourcentErreur(zone3, im2, i, j, i + z1, j + z2);
                    }
                        p = Math.min(p1, p2);

                    if (p < cal.getPourcent()) {
                        cal.setPourcent(p);
                        cal.setHeight(j);
                        cal.setWidth(i);
                        if (p == p1) {
                            cal.setRotation(r + r1);
                        }else{
                            cal.setRotation(-r + r1);
                        }
                        if (p==0) return cal;
                    }
                }
            }r++;
        }
        return cal;
    }

    public static Bitmap zone( Bitmap im1){
        int h1 = im1.getHeight();
        int w1 = im1.getWidth();
        int y1 = (int) Math.floor(4*h1/9);
        int y2 = h1 - y1;
        int x1 = (int) Math.floor(4*w1/9);
        int x2 = w1 - x1;
        int[] pixels = new int[(x2 - x1)*(y2 - y1)];
        im1.getPixels(pixels,0,x2-x1,x1,y1,x2-x1,y2-y1);
        Bitmap zone = Bitmap.createBitmap(x2-x1,y2-y1,im1.getConfig());
        zone.setPixels(pixels,0,x2-x1,0,0,x2-x1,y2-y1);

        return zone;

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

    private int getR2(){
        return r2;
    }



    private int getHeight() {
        return height;
    }

    public double getPourcent() {
        return pourcent;
    }

    private int getRotation() {
        return rotation;
    }
}
