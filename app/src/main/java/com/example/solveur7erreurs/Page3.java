package com.example.solveur7erreurs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class Page3 extends AppCompatActivity{


    String chemin1 = Page2.getChemin(1);
    String chemin2 = Page2.getChemin(2);
    TextView textView3;
    ImageView result;
    private final PanDessin panneau = new PanDessin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        result = findViewById(R.id.imageView3);
        textView3.setText(chemin1 + '\n' + chemin2);


        panneau.ajouterImage(new File(chemin1));
        //panneau.getImagePanneau();

        //panneau.imageEclaircie();


    }



}
