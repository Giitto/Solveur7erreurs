package com.example.solveur7erreurs;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class ConvolutionMatrix {
    private static final int SIZE = 3;
    private static final int threashold = 30;

    private double[][] Matrix;
    private double Factor = 1;
    private double Offset = 1;

    public ConvolutionMatrix(int size) {
        Matrix = new double[size][size];
    }

    public void setAll(double value) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = value;
            }
        }
    }

    public void applyConfig(double[][] config) {
        for(int x = 0; x < SIZE; ++x) {
            for(int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = config[x][y];
            }
        }
    }

    public static Bitmap computeConvolution3x3(Bitmap src, ConvolutionMatrix matrix) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixel = new int[width * height];
        src.getPixels(pixel,0,width,0,0,width,height);
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZE][SIZE];

        for(int y = 0; y < height - 2; ++y) {
            for(int x = 0; x < width - 2; ++x) {

                // get pixel matrix
                for(int i = 0; i < SIZE; ++i) {
                    for(int j = 0; j < SIZE; ++j) {
                        pixels[i][j] = pixel[(i+y)*width + j + x];
                    }
                }

                // get alpha of center pixel
                A = Color.alpha(pixels[1][1]);

                // init color sum
                sumR = sumG = sumB = 0;

                // get sum of RGB on matrix
                for(int i = 0; i < SIZE; ++i) {
                    for(int j = 0; j < SIZE; ++j) {
                        sumR += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumG += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
                    }
                }

                // get final Red
                R = (int)(sumR / matrix.Factor + matrix.Offset);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                // get final Green
                G = (int)(sumG / matrix.Factor + matrix.Offset);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                // get final Blue
                B = (int)(sumB / matrix.Factor + matrix.Offset);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // apply new pixel
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }

        // final image
        return result;
    }

    public static Bitmap computeConvolution5x5(Bitmap src, ConvolutionMatrix matrix) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixel = new int[width * height];
        src.getPixels(pixel,0,width,0,0,width,height);

        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZE][SIZE];

        for(int y = 0; y < height - 4; ++y) {
            for(int x = 0; x < width - 4; ++x) {

                // get pixel matrix
                for(int i = 0; i < SIZE; ++i) {
                    for(int j = 0; j < SIZE; ++j) {
                       // Log.d(TAG, "computeConvolution5x5: "+ j + x + " " + y + i+" ");
                        pixels[i][j] = pixel[(i+y)*width + j + x];
                    }
                }

                // get alpha of center pixel
                A = Color.alpha(pixels[1][1]);

                // init color sum
                sumR = sumG = sumB = 0;

                // get sum of RGB on matrix
                for(int i = 0; i < SIZE; ++i) {
                    for(int j = 0; j < SIZE; ++j) {
                        sumR += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumG += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
                    }
                }

                // get final Red
                R = (int)(sumR / matrix.Factor + matrix.Offset);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                // get final Green
                G = (int)(sumG / matrix.Factor + matrix.Offset);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                // get final Blue
                B = (int)(sumB / matrix.Factor + matrix.Offset);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // apply new pixel
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }

        // final image
        return result;
    }

    public static Bitmap gaussianBlur(Bitmap src) {
        double [] [] EmbossConfig = new double [] [] {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix (3);
        convMatrix.applyConfig (EmbossConfig);
        convMatrix.Factor = 16;
        convMatrix.Offset = 0;
        return ConvolutionMatrix.computeConvolution3x3 (src, convMatrix);
    }

    public static Bitmap gaussianBlur5x5(Bitmap src) {
        double [] [] EmbossConfig = new double [] [] {
                {1, 4, 6, 4, 1},
                {4, 16, 24, 16, 4},
                {6, 24, 36, 24, 6},
                {4, 16, 24, 16, 4},
                {1, 4, 6, 4, 1}

        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix (5);
        convMatrix.applyConfig (EmbossConfig);
        convMatrix.Factor = 256;
        convMatrix.Offset = 0;
        return ConvolutionMatrix.computeConvolution5x5 (src, convMatrix);
    }

    public static Bitmap nettete(Bitmap src) {
        double [] [] EmbossConfig = new double [] [] {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix (3);
        convMatrix.applyConfig (EmbossConfig);
        convMatrix.Factor = 1;
        convMatrix.Offset = 0;
        return ConvolutionMatrix.computeConvolution3x3 (src, convMatrix);
    }

    public static Bitmap boxBlur(Bitmap src) {
        double [] [] EmbossConfig = new double [] [] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix (3);
        convMatrix.applyConfig (EmbossConfig);
        convMatrix.Factor = 9;
        convMatrix.Offset = 0;
        return ConvolutionMatrix.computeConvolution3x3 (src, convMatrix);
    }

    public static Bitmap boxBlur1(Bitmap src) {

        ConvolutionMatrix convMatrix = new ConvolutionMatrix (9);
        convMatrix.setAll(1);
        convMatrix.Factor = 27;
        convMatrix.Offset = 0;
        return ConvolutionMatrix.computeConvolution3x3 (src, convMatrix);
    }

    public static Bitmap findDifference(@NotNull Bitmap firstImage, @NotNull Bitmap secondImage) {
        Bitmap bmp = secondImage.copy(secondImage.getConfig(), true);

        int wthmin = Math.min(firstImage.getWidth(), secondImage.getWidth());
        int hgtmin = Math.min(firstImage.getHeight(), secondImage.getHeight());
/*        if (firstImage.getWidth() != secondImage.getWidth()
                || firstImage.getHeight() != secondImage.getHeight()) {
            return bmp;
       }*/

        for (int i = 0; i < wthmin; i++) {
            for (int j = 0; j < hgtmin; j++) {
                int pixel = firstImage.getPixel(i,j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                int pixel2 = secondImage.getPixel(i,j);
                int redValue2 = Color.red(pixel2);
                int blueValue2 = Color.blue(pixel2);
                int greenValue2 = Color.green(pixel2);
                if (Math.abs(redValue2 - redValue) + Math.abs(blueValue2 - blueValue) + Math.abs(greenValue2 - greenValue) >= threashold) {
                    bmp.setPixel(i, j, Color.YELLOW);
                }
            }
        }

        return bmp;
    }

    public static Bitmap findDifference1(@NotNull Bitmap firstImage, @NotNull Bitmap secondImage) {
        Bitmap bmp = secondImage.copy(secondImage.getConfig(), true);

        if (firstImage.getWidth() != secondImage.getWidth()
                || firstImage.getHeight() != secondImage.getHeight()) {
            return bmp;
        }

        for (int i = 0; i < firstImage.getWidth(); i++) {
            for (int j = 0; j < firstImage.getHeight(); j++) {
                int pixel = firstImage.getPixel(i,j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                int pixel2 = secondImage.getPixel(i,j);
                int redValue2 = Color.red(pixel2);
                int blueValue2 = Color.blue(pixel2);
                int greenValue2 = Color.green(pixel2);
                if (Math.abs(redValue2 - redValue) + Math.abs(blueValue2 - blueValue) + Math.abs(greenValue2 - greenValue) >= threashold) {
                    bmp.setPixel(i, j, Color.YELLOW);
                }
            }
        }

        return bmp;
    }

}