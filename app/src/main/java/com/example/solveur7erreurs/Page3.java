package com.example.solveur7erreurs;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.graphics.Bitmap.createBitmap;
import static com.example.solveur7erreurs.Calibrage.rotateBitmap;
import static java.lang.Thread.sleep;


public class Page3 extends AppCompatActivity{


    Bitmap image1 = createBitmap(Page2.transBitmap(1));
    Bitmap image2 = createBitmap(Page2.transBitmap(2));
    Calibrage c1,c2,c3,c4;
    String cal;
    Boolean stop = false;
    ImageView result1;
    ImageView result2;
    ImageView result3;
    ArrayList<Bitmap> zonerot = new ArrayList<>();
    ArrayList<Calibrage> cal1 = new ArrayList<>();
    int x,y,r1,r2,r3,r4,ro=0;
    Thread t1,t2,t3,t4;
    Button button;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        result1 = findViewById(R.id.imageView3);
        result2 = findViewById(R.id.imageView4);
        result3 = findViewById(R.id.imageView5);

        button = findViewById(R.id.angry_btn3);

        r1 = image1.getWidth();
        r2 = image2.getWidth();
        r3 = image1.getHeight();
        r4 = image2.getHeight();
        if((r1 == r2 && r3==r4) || (r1 == r4 && r3==r2) || (Math.abs(r1-r4)<20 && Math.abs(r2-r3)<20) || (Math.abs(r1-r2)<20 && Math.abs(r3-r4)<20))
        {
            while(image1.getWidth() >= 720 || image1.getHeight() >= 720)
            {
                image1 = Bitmap.createScaledBitmap(image1, (int) (image1.getWidth()*0.5), (int) (image1.getHeight()*0.5) , true);
                image2 = Bitmap.createScaledBitmap(image2, (int) (image2.getWidth()*0.5), (int) (image2.getHeight()*0.5) , true);
                image1 = Calibrage.Compress(image1,50);
                image2 = Calibrage.Compress(image2,50);

            }

            System.out.println("conv");
            image1 = ConvolutionMatrix.gaussianBlur5x5(image1);
            image2 = ConvolutionMatrix.gaussianBlur5x5(image2);
            System.out.println("thread");

            t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,0));
                    c1 = Calibrage.recherche(zonerot.get(0),image2,0);
                    cal1.add(c1);
                    if(c1.getPourcent() <= 10) {
                        stop = true;
                    }
                }
            });
            t2 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,90));
                    c2 = Calibrage.recherche(zonerot.get(1),image2,90);
                    cal1.add(c2);
                    if(c2.getPourcent() <= 10){
                        stop=true;
                    }
                }
            });
            t3 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,180));
                    c3 = Calibrage.recherche(zonerot.get(2),image2,180);
                    cal1.add(c3);
                    if(c3.getPourcent() <= 10) {
                        stop = true;
                    }
                }
            });
            t4 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,270));
                    c4 = Calibrage.recherche(zonerot.get(3),image2,270);
                    cal1.add(c4);
                    if(c4.getPourcent() <= 10) {
                        stop = true;
                    }

                }
            });

            for (Thread t : new Thread[]{t1, t2, t3, t4}) {
                try {
                    sleep(100);
                    t.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Thread t : new Thread[]{t1, t2, t3, t4}) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            cal = Calibrage.MinCal(cal1).toString();
            Calibrage cal2 = Calibrage.MinCal(cal1);
            ro = cal2.getRotation();
            x = cal2.getWidth();
            y = cal2.getHeight();
        }
        else
        {
            cal = "Les photos ne sont pas comparable";
        }




        Bitmap test = Calibrage.superposer(image1, image2, ro, x, y);
        Detection d = new Detection(image1, test);
        Bitmap tmp = d.laTotaleChef();
        //test = ConvolutionMatrix.findDifference(image1,test);
        //result3.setImageBitmap(test);
        result1.setImageBitmap(image1);
        result2.setImageBitmap(image2);
        result3.setImageBitmap(tmp);

/////////////////////////////////////////////////////////////////////////////////////////////

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Title");
        builder.setMessage(cal1.toString());
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page2 = new Intent(getApplicationContext(), Page2.class);
                startActivity(page2);
                finish();
            }
        });
    }
}
