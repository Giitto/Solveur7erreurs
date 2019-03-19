package com.example.solveur7erreurs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Detection {


    Paint paint;
    Canvas canvas;
    Bitmap bmp;

    public Detection(Bitmap bitmap) {

        this.paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);

        this.bmp = bitmap.copy(bitmap.getConfig(), true);
        this.canvas = new Canvas(bmp);
    }


    public void toCircle(int x, int y, int rayon){
        canvas.drawCircle(x, y, rayon, this.paint);
    }

    public Bitmap getBmp() {
        return bmp;
    }
}
