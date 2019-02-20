package com.example.solveur7erreurs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

//Classe de la page d'accueil

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutP1;          //Initialisation du layout
    private Button play;                    //Initialisation du bouton de lancement de l'application

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.layoutP1 = findViewById(R.id.LayoutPage1);

        play = findViewById(R.id.angry_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override

            /*Fonction de changement de page au clic sur le bouton play*/
            public void onClick(View v) {
                Intent page2 = new Intent(getApplicationContext(), Page2.class);
                startActivity(page2);
                finish();
            }
        });
    }
}
