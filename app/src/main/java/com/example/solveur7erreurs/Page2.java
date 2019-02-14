package com.example.solveur7erreurs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Page2 extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 0;
    private static String chemin;
    private static String chemin2;
    private int RESULT_LOAD_IMAGE = 0;


    ImageView imageView;
    ImageView imageView2;
    Button button;
    Button button2;
    Button comparer;


    public static String getChemin(int i) {
        if(i==1)
            return chemin;
        else
            return chemin2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);

        }

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                RESULT_LOAD_IMAGE = 1;
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                RESULT_LOAD_IMAGE = 2;
                startActivityForResult(j, RESULT_LOAD_IMAGE);

            }
        });

        this.comparer = findViewById(R.id.angry_btn2);
        comparer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page3 = new Intent(getApplicationContext(), Page3.class);
                startActivity(page3);
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case  1 :
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn1 ={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn1, null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn1[0]);
                    String picturePath = cursor.getString(columnIndex);
                    chemin = picturePath;
                    cursor.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                }
                break;
            case  2 :
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn ={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    chemin2 = picturePath;
                    cursor.close();
                    imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
                break;
        }
    }

}
