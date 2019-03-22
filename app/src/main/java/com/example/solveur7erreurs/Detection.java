package com.example.solveur7erreurs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jetbrains.annotations.NotNull;

public class Detection {

    private static final int threashold = 30;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private int tab[][];
    private int tabfus[][];
    private int wthmin;
    private int hgtmin;

    public Detection(Bitmap image1, Bitmap image2)
    {
        this.paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        this.bmp1 = image1.copy(image1.getConfig(), true);
        this.bmp2 = image2.copy(image2.getConfig(), true);
        this.canvas = new Canvas(bmp2);
        this.wthmin = Math.min(bmp1.getWidth(), bmp2.getWidth());
        this.wthmin -= wthmin%4;
        this.hgtmin = Math.min(bmp1.getHeight(), bmp2.getHeight());
        this.hgtmin -= hgtmin%4;
        this.tab = new int[wthmin][hgtmin];
        this.tabfus = new int[wthmin/4][hgtmin/4];

    }


    public void toCircle(int x, int y, int rayon){
        canvas.drawCircle(x, y, rayon, this.paint);
    }

    public Bitmap getBmp() {
        return bmp1;
    }


    public void binaryDiff()
    {

        for (int i = 0; i < wthmin; i++)
        {
            for (int j = 0; j < hgtmin; j++)
            {
                int pixel = bmp1.getPixel(i, j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                int pixel2 = bmp2.getPixel(i, j);
                int redValue2 = Color.red(pixel2);
                int blueValue2 = Color.blue(pixel2);
                int greenValue2 = Color.green(pixel2);

                if (Math.abs(redValue2 - redValue) + Math.abs(blueValue2 - blueValue) + Math.abs(greenValue2 - greenValue) >= threashold)
                {
                    tab[i][j] = 1;
                }
                else
                {
                    tab[i][j] = 0;
                }
            }
        }
    }

    public void fusion()
    {
        int sum;
        for (int i = 0; i < (wthmin/4); i++)
        {
            for (int j = 0; j < (hgtmin/4); j++)
            {
                sum = 0;
                for(int k =0; k< 4; k++){
                    for(int l = 0; l<4; l++){
                        sum+= tab[4*i + k][4*j + l];
                    }
                }
                if (sum <9)
                    tabfus[i][j]=0;
                else
                    tabfus[i][j]=1;
            }
        }

    }

    public void affiche1(){
        for (int i = 0; i <this.wthmin/4; i++)
        {
            for (int j= 0; j <this.hgtmin/4; j++)
            {
                System.out.print(tabfus[i][j] + " ");
            }
            System.out.println("fin "+ wthmin/4 + " "+ hgtmin/4 );
        }
    }

    public void affiche(){
        for (int i = 0; i <this.wthmin; i++)
        {
            for (int j= 0; j <this.hgtmin; j++)
            {
                System.out.print(tab[i][j] + " ");
            }
        }
    }
}
