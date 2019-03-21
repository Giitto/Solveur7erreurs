package com.example.solveur7erreurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.graphics.Bitmap.createBitmap;
import static com.example.solveur7erreurs.Calibrage.Compress;
import static com.example.solveur7erreurs.Calibrage.rotateBitmap;
import static java.lang.Thread.sleep;


public class Page3 extends AppCompatActivity{


    Bitmap image1 = createBitmap(Page2.transBitmap(1));
    Bitmap image2 = createBitmap(Page2.transBitmap(2));
    Bitmap zone;
    Calibrage cal;
    TextView textView3;
    ImageView result1;
    ImageView result2;
    ImageView result3;
    ArrayList<Bitmap> zonerot = new ArrayList<Bitmap>();
    ArrayList<Calibrage> cal1 = new ArrayList<Calibrage>();
    Bitmap tuc;
    Thread t1,t2,t3,t4;
    Button button;
    double pourcentage;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        result1 = findViewById(R.id.imageView3);
        result2 = findViewById(R.id.imageView4);
        result3 = findViewById(R.id.imageView5);

        button = findViewById(R.id.angry_btn3);
        pourcentage = ConvolutionMatrix.pourcentErreur(image1,image2);
        if(pourcentage==0)
            textView3.setText(image1.toString() + "\n" + image2.toString());

        /*t1 = new Thread(new Runnable() {
            public void run(){
                image1 = Bitmap.createScaledBitmap(image1, (int) (image1.getWidth()*0.5), (int) (image1.getHeight()*0.5) , true);
                //image1 = Compress(image1, 25);
                //image1 = ConvolutionMatrix.gaussianBlur5x5(image1);
            }
        });

        t2 = new Thread(new Runnable() {
            public void run(){
                image2 = Bitmap.createScaledBitmap(image2, (int) (image2.getWidth()*0.5), (int) (image2.getHeight()*0.5) , true);
                //image2 = Compress(image2, 25);
                //image2 = ConvolutionMatrix.gaussianBlur5x5(image2);
            }
        });

        //t1.start();
        //t2.start();
        */
        image1 = Calibrage.rotateBitmap(image1,90);


        t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                zonerot.add(rotateBitmap(image1,0));
               cal1.add(Calibrage.recherche(zonerot.get(0),image2,0));
            }
        });
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                zonerot.add(rotateBitmap(image1,90));
                cal1.add(Calibrage.recherche(zonerot.get(1),image2,90));
            }
        });
        t3 = new Thread(new Runnable() {
            @Override
            public void run() {

                zonerot.add(rotateBitmap(image1,180));
                cal1.add(Calibrage.recherche(zonerot.get(2),image2,180));
            }
        });
        t4 = new Thread(new Runnable() {
            @Override
            public void run() {

                zonerot.add(rotateBitmap(image1,270));
                cal1.add(Calibrage.recherche(zonerot.get(3),image2,270));
            }
        });
        t1.start();
        try {
            t2.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        try {
            t3.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t3.start();
        try {
            t4.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t4.start();

        for (Thread t : new Thread[] { t1,t2,t3,t4}) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //image2 = rotateBitmap(image2,180);

        //cal = Calibrage.recherche(zone,image2,0);//ConvolutionMatrix.findDifference(image1,image2);
        //textView3.setText(cal.toString());

        //Test 19/03/2019

        //Detection detect = new Detection(image1);
        //detect.toCircle(50,50,20);
        //image1 = detect.getBmp();


        //
        result1.setImageBitmap(image1);
        result2.setImageBitmap(image2);
        result3.setImageBitmap(zone);
        cal = Calibrage.MinCal(cal1);


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


    //Bitmap source = BitmapFactory.decodeResource(this.getResources(), R.drawable.image2);



}
