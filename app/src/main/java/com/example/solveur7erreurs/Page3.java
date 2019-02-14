package com.example.solveur7erreurs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Page3 extends AppCompatActivity {


    String chemin1 = Page2.getChemin(1);
    String chemin2 = Page2.getChemin(2);

    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        textView3.setText(chemin1 + '\n' + chemin2);

    }


}
