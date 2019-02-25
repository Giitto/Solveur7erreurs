package com.example.solveur7erreurs;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import ae.java.awt.image.BufferedImage;


public class Page3 extends AppCompatActivity{


    File chemin1 = new File(Page2.getChemin(1));
    File chemin2 = new File(Page2.getChemin(2));
    TextView textView3;
    ImageView result;
    BufferedImage monImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        result = findViewById(R.id.imageView3);
        //textView3.setText(chemin1 + '\n' + chemin2);
        textView3.setText(chemin1.toString());
        result.setImageBitmap(BitmapFactory.decodeFile(chemin1.toString()));

        //panneau.ajouterImage(chemin1);
        //panneau.getImagePanneau();

        //panneau.imageEclaircie();


    }


}
