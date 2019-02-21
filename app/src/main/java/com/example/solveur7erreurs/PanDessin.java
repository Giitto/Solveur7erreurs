package com.example.solveur7erreurs;

import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import ae.java.awt.Color;
import ae.java.awt.Graphics;
import ae.java.awt.Graphics2D;
import ae.java.awt.geom.AffineTransform;
import ae.java.awt.image.AffineTransformOp;
import ae.java.awt.image.BufferedImage;
import ae.java.awt.image.ColorModel;
import ae.java.awt.image.ConvolveOp;
import ae.java.awt.image.Kernel;
import ae.java.awt.image.Raster;
import ae.java.awt.image.RescaleOp;
import ae.javax.imageio.ImageIO;


public class PanDessin  extends AppCompatActivity{

        BufferedImage monImage = null;
        int numMat;
        int matrice1[][][];
        int matrice2[][][];

        public PanDessin() {
        super();
            numMat = 0;

        }

        public void paintComponent(Graphics g) {
//		super.paintComponent(g);
            if (monImage != null)
                g.drawImage(monImage, 0, 0, null);
        }

        public void reduireImage() {
            BufferedImage imageReduite = new BufferedImage((int) (monImage.getWidth() * 0.5),
                    (int) (monImage.getHeight() * 0.5), monImage.getType());
            AffineTransform reduire = AffineTransform.getScaleInstance(0.5, 0.5);
            int interpolation = AffineTransformOp.TYPE_BICUBIC;
            AffineTransformOp retaillerImage = new AffineTransformOp(reduire, interpolation);
            retaillerImage.filter(monImage, imageReduite);
            monImage = imageReduite;
//		repaint();
        }

        public void agrandirImage() {
            BufferedImage imageZoomer = new BufferedImage((int) (monImage.getWidth() * 1.5),
                    (int) (monImage.getHeight() * 1.5), monImage.getType());
            AffineTransform agrandir = AffineTransform.getScaleInstance(1.5, 1.5);
            int interpolation = AffineTransformOp.TYPE_BICUBIC;
            AffineTransformOp retaillerImage = new AffineTransformOp(agrandir, interpolation);
            retaillerImage.filter(monImage, imageZoomer);
            monImage = imageZoomer;
//		repaint();
        }

        public void imageConvolue()// on va utiliser le masque flou
        {
            BufferedImage imageFlou = new BufferedImage(monImage.getWidth(), monImage.getHeight(), monImage.getType());
            float[] masqueFlou = { 0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f };

            Kernel masque = new Kernel(3, 3, masqueFlou);
            ConvolveOp operation = new ConvolveOp(masque);
            operation.filter(monImage, imageFlou);
            monImage = imageFlou;
            System.out.println("convolution effectuee");
//		repaint();

        }

        public void imageEclaircie() {
            /*
             * RescaleOp brillance = new RescaleOp(A, K, null); 1. A< 1, l image devient
             * plus sombre. 2. A > 1, l image devient plus brillante. 3. K est compris entre
             * 0 et 256 et ajoute un eclairement .
             */
            BufferedImage imgBrillant = new BufferedImage(monImage.getWidth(), monImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            RescaleOp brillance = new RescaleOp(1.2f, 0, null);
            brillance.filter(monImage, imgBrillant);
            monImage = imgBrillant;
//		repaint();

        }

        public void imageSombre() {
            /*
             * RescaleOp assombrir = new RescaleOp(A, K, null);
             *
             * 1. A < 1, l image devient plus sombre. 2. A > 1, l image devient plus
             * brillante. 3. K est compris entre 0 et 256 et ajoute un eclairement .
             *
             */
            BufferedImage imgSombre = new BufferedImage(monImage.getWidth(), monImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            RescaleOp assombrir = new RescaleOp(0.7f, 10, null);
            assombrir.filter(monImage, imgSombre);
            monImage = imgSombre;
            System.out.println("assombrir effectuee");
//		repaint();
        }

        public void imageBinaire() {
            BufferedImage imgBinaire = new BufferedImage(monImage.getWidth(), monImage.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D surfaceImg = imgBinaire.createGraphics();
            surfaceImg.drawImage(monImage, null, null);
            monImage = imgBinaire;
//		repaint();
        }

        public void imageEnNiveauGris() {
            BufferedImage imageGris = new BufferedImage(monImage.getWidth(), monImage.getHeight(),
                    BufferedImage.TYPE_USHORT_GRAY);
            Graphics2D surfaceImg = imageGris.createGraphics();
            surfaceImg.drawImage(monImage, null, null);
            monImage = imageGris;
//		repaint();
        }

        public  void ajouterImage(File fichierImage) { // desiner une image a l'ecran
            try {
                monImage = ImageIO.read(fichierImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ///////////////// ajout & modif du 24 janvier 2019

            // Extraction du raster de monImage (Rappel: monImage de type BufferedImage est
            // compose d'un Raster & d'un ColorModel)
            Raster tramePixel = monImage.getRaster();

            // Extraction du ColorModel
            ColorModel modeleCouleur = monImage.getColorModel();

            // Objet qui sera futur objet de la class color sur lequel on peut appeler les
            // fonctions getRed, getGreen et getBlue qui donne la variation RVB
            Object objCouleur;

            // Ta capter
            int largeur =  monImage.getWidth();
            int hauteur =  monImage.getHeight();

            System.out.println("Largeur: " + largeur + ", Hauteur: " + hauteur);
            if (numMat == 0)
                matrice1 = new int[largeur + 1][hauteur + 1][3];
            else
                matrice2 = new int[largeur + 1][hauteur + 1][3];

            for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    // Le raster contient, sous forme d un Object, les donnees sur les pixels
                    objCouleur = tramePixel.getDataElements(i, j, null);

                    if (numMat == 0) {

                        matrice1[i][j][0] = modeleCouleur.getRed(objCouleur);
                        matrice1[i][j][1] = modeleCouleur.getGreen(objCouleur);
                        matrice1[i][j][2] = modeleCouleur.getBlue(objCouleur);

                    } else {

                        matrice2[i][j][0] = modeleCouleur.getRed(objCouleur);
                        matrice2[i][j][1] = modeleCouleur.getGreen(objCouleur);
                        matrice2[i][j][2] = modeleCouleur.getBlue(objCouleur);

                    }

//				System.out.print("["+ modeleCouleur.getRed(objCouleur) + ","+ modeleCouleur .getGreen(objCouleur) + ","+ +"]");
//				System.out.print(" ");
                }
//			System.out.println("");

            }
            if (numMat == 0) {
                numMat = 1;
                System.out.println("Matrice 1 chargee!");
            } else {
                numMat = 0;
                System.out.println("Matrice 2 chargee!");
                int matTest[][][] = soustrMat(matrice1, matrice2);

                largeur = matTest.length;
                hauteur = matTest[0].length;

                BufferedImage imgCmp = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);

                for (int k = 0; k < largeur; k++) {
                    for (int l = 0; l < hauteur; l++) {
                        Color newColor = new Color(matTest[k][l][0], matTest[k][l][1], matTest[k][l][2]);
                        imgCmp.setRGB(k, l, newColor.getRGB());
                        monImage = imgCmp;
                    }
                }
            }

            /////////////////
//		repaint();

        }

        public BufferedImage getImagePanneau() { // recuperer une image du panneau
            int width = monImage.getWidth();
            int height = monImage.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

//		this.paintAll(g);
            g.dispose();
            return image;
        }

        public void enregistrerImage(File fichierImage) {
            String format = "JPG";
            BufferedImage image = getImagePanneau();
            try {
                ImageIO.write(image, format, fichierImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public int[][][] soustrMat(int[][][] matA, int[][][] matB) {
            int tauxErrMax = 5;

            int largeurA = matA.length;
            int hauteurA = matA[0].length;
            int largeurB = matB.length;
            int hauteurB = matB[0].length;

            int largeurMin = Math.min(largeurA, largeurB);
            int hauteurMin = Math.min(hauteurA, hauteurB);

            int matTmp[][][] = new int[largeurMin][hauteurMin][3];
            int tmpA;
            int tmpB;
            int tmpC;

            int compteurPixel = 0;
            int compteurErreurPixel = 0;

            for (int i = 0; i < largeurMin; i++) {
                for (int j = 0; j < hauteurMin; j++) {

                    tmpA = Math.abs(matA[i][j][0] - matB[i][j][0]);
                    tmpB = Math.abs(matA[i][j][1] - matB[i][j][1]);
                    tmpC = Math.abs(matA[i][j][2] - matB[i][j][2]);

                    matTmp[i][j][0] = tmpA;
                    matTmp[i][j][1] = tmpB;
                    matTmp[i][j][2] = tmpC;

                    compteurPixel++;

                    if (tmpA > tauxErrMax)
                        compteurErreurPixel++;
                    if (tmpB > tauxErrMax)
                        compteurErreurPixel++;
                    if (tmpC > tauxErrMax)
                        compteurErreurPixel++;

                }
            }
            System.out.println(compteurPixel + " pixels, " + compteurErreurPixel / 3 + " erreurs(moyenne)");
            System.out.println(
                    "Taux d'erreur: " + ((((double) compteurErreurPixel / 3) / (double) compteurPixel) * 100) + "%");

            return matTmp;

        }
    }
