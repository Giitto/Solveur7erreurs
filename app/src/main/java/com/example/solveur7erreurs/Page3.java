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
    Calibrage c1,c2,c3,c4;
    String cal;
    Boolean stop = false;
    TextView textView3;
    ImageView result1;
    ImageView result2;
    ImageView result3;
    ArrayList<Bitmap> zonerot = new ArrayList<Bitmap>();
    ArrayList<Calibrage> cal1 = new ArrayList<Calibrage>();
    Bitmap tuc;
    int r1,r2,r3,r4;
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


        r1 = Math.abs(image1.getWidth()-image2.getWidth());
        r2 = Math.abs(image1.getHeight()-image2.getHeight());
        if((r1 == r2) || (r1<20 || r2<20))
        {
            if(image1.getWidth() >= 720 || image1.getHeight() >= 720)
            {
                image1 = Bitmap.createScaledBitmap(image1, (int) (image1.getWidth()*0.5), (int) (image1.getHeight()*0.5) , true);
                image2 = Bitmap.createScaledBitmap(image1, (int) (image2.getWidth()*0.5), (int) (image2.getHeight()*0.5) , true);
            }

            t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,0));
                    c1 = Calibrage.recherche(zonerot.get(0),image2,0);
                    cal1.add(c1);
                    if(c1.getPourcent() <= 10)
                        stop=true;

                }
            });
            t2 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,90));
                    c2 = Calibrage.recherche(zonerot.get(1),image2,90);
                    cal1.add(c2);
                    if(c2.getPourcent() <= 10)
                        stop=true;
                }
            });
            t3 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,180));
                    c3 = Calibrage.recherche(zonerot.get(2),image2,180);
                    cal1.add(c3);
                    if(c3.getPourcent() <= 10)
                        stop=true;
                }
            });
            t4 = new Thread(new Runnable() {
                @Override
                public void run() {

                    zonerot.add(rotateBitmap(image1,270));
                    c4 = Calibrage.recherche(zonerot.get(3),image2,270);
                    cal1.add(c4);
                    if(c4.getPourcent() <= 10)
                        stop=true;
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
            for (Thread t : new Thread[]{t1, t2, t3, t4}) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            cal = Calibrage.MinCal(cal1).toString();
        }
        else
        {
            cal = "Les photos ne sont pas comparable";
        }

        //textView3.setText(cal.toString());

        //Test 19/03/2019

        //Detection detect = new Detection(image1);
        //detect.toCircle(50,50,20);
        //image1 = detect.getBmp();


        //
        result1.setImageBitmap(image1);
        result2.setImageBitmap(image2);
        result3.setImageBitmap(image1);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Title");
        builder.setMessage(cal);
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
