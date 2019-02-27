package com.example.solveur7erreurs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import ae.java.awt.image.BufferedImage;

import static android.graphics.Bitmap.createBitmap;


public class Page3 extends AppCompatActivity{


    Bitmap image1 = createBitmap(Page2.getBitmap(1));
    Bitmap image2 = createBitmap(Page2.getBitmap(2));
    TextView textView3;
    ImageView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        result = findViewById(R.id.imageView3);
        textView3.setText(image1.toString());
        result.setImageBitmap((image1));



    }


}
