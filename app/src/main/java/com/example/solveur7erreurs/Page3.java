package com.example.solveur7erreurs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import com.example.solveur7erreurs.Image;
import static android.graphics.Bitmap.createBitmap;


public class Page3 extends AppCompatActivity{


    //Bitmap image1[][] = createBitmap(Page2.transBitmap(1));
    Bitmap image2 = createBitmap(Page2.transBitmap(2));
    TextView textView3;
    ImageView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        textView3 = findViewById(R.id.textView3);
        result = findViewById(R.id.imageView3);
        textView3.setText(image2.toString());

        Bitmap tuc = RotateBitmap(image2, 90);
        ConvolutionMatrix matrix = new ConvolutionMatrix(3);
        matrix.setAll(1);
        tuc = ConvolutionMatrix.computeConvolution3x3(tuc, matrix);
        result.setImageBitmap((tuc));

    }


    //Bitmap source = BitmapFactory.decodeResource(this.getResources(), R.drawable.image2);

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


}
