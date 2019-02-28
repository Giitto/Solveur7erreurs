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


    Bitmap image1[][] = createBitmap(Page2.transBitmap(1));
    Bitmap image2 = createBitmap(Page2.transBitmap(2));
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

    public Bitmap getBitmap(){
        int alpha = angle;
        rotateBitmaps(alpha);

        int nWidth = this.getWidth();
        int nHeight = this.getHeight();

        Log.i("DBG", "Save image : "+nWidth+"x"+nHeight);

        Bitmap newBitmap = Bitmap.createBitmap(nWidth, nHeight, image1[0][0].getConfig());
        Canvas canvas = new Canvas(newBitmap);

        drawOrientedBitmap(canvas, alpha);
        rotateBitmaps(-1 * alpha);
        //draw(canvas, new Paint(), nWidth/2, nHeight/2, 1);

        return newBitmap;
    }

    private void rotateBitmaps(int angle){

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                Bitmap tmp = this.getBitmap(x, y);
                bitmap[x][y] = Bitmap.createBitmap(tmp, 0, 0, tmp.getWidth(), tmp.getHeight(), matrix, true);
            }
        }

    }

}
