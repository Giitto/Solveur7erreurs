package com.example.solveur7erreurs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import ae.java.awt.Image;

import static ae.javax.imageio.ImageIO.read;


public class Page3 extends AppCompatActivity {


    String chemin1 = Page2.getChemin(1);
    String chemin2 = Page2.getChemin(2);
    Image image = read(new File(chemin1));;
    TextView textView3;
    ImageView result;

    public Page3() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        textView3.setText(chemin1 + '\n' + chemin2);
        result = findViewById(R.id.imageView3);
        PanDessin.ajouterImage(image);

    }



}
