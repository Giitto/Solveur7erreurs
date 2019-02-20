package com.example.solveur7erreurs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;


public class Page3 extends AppCompatActivity {


    String chemin1 = Page2.getChemin(1);
    String chemin2 = Page2.getChemin(2);
    Button button3 = findViewById(R.id.button3);

    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        textView3.setText(chemin1 + '\n' + chemin2);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page4 = new Intent(getApplicationContext(), Page4.class);
                startActivity(page4);
                finish();
            }
        });
    }


}
