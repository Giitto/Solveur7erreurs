package com.example.solveur7erreurs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jetbrains.annotations.NotNull;

import static android.graphics.Bitmap.createBitmap;

public class Detection {

    private static final int threashold = 30;

    private Paint paint;
    private Canvas canvas;
    private Bitmap bmp1;
    private Bitmap bmp2;

    private int tab[][];
    private int wthmin;
    private int hgtmin;
    private int valeurDeSubdivision = 20;
    private int isAnError =5;
    private int tabSommeParGroupe [][];
    private int dejaVisitee[][];
    private int nombreErreurs = 14;
    private int groupeDesSeptsErreurs [][] = new int[nombreErreurs][4];  //7erreurs max, on retiendra les 4 positions extremes N,S,E,O
    int tabRayon[][] = new int[nombreErreurs][3];



    public Detection(Bitmap image1, Bitmap image2)
    {
        this.paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        this.bmp1 = image1.copy(image1.getConfig(), true);
        this.bmp2 = image2.copy(image2.getConfig(), true);

        this.canvas = new Canvas(bmp2);
        this.wthmin = Math.min(bmp1.getWidth(), bmp2.getWidth());
        this.hgtmin = Math.min(bmp1.getHeight(), bmp2.getHeight());
        this.tab = new int[wthmin][hgtmin];
        this.dejaVisitee = new int[(int) wthmin/valeurDeSubdivision][(int) hgtmin/valeurDeSubdivision];
        this.tabSommeParGroupe = new int[(int) wthmin/valeurDeSubdivision][(int) hgtmin/valeurDeSubdivision];

    }


    public void toCircle(int x, int y, int rayon)
    {
        canvas.drawCircle(x, y, rayon, this.paint);
    }


    public Bitmap laTotaleChef(){
        binaryDiff();
        groupage();
        localisationDesErreurs();
        for(int rayon[] :tabRayon){
            toCircle(rayon[0],rayon[1],rayon[2]);

        }

        return bmp2;
    }

    public void binaryDiff()
    {

        for (int i = 0; i < wthmin; i++)
        {
            for (int j = 0; j < hgtmin; j++)
            {
                int pixel = bmp1.getPixel(i, j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);

                int pixel2 = bmp2.getPixel(i, j);
                int redValue2 = Color.red(pixel2);
                int blueValue2 = Color.blue(pixel2);
                int greenValue2 = Color.green(pixel2);

                if (Math.abs(redValue2 - redValue) + Math.abs(blueValue2 - blueValue) + Math.abs(greenValue2 - greenValue) >= threashold)
                {
                    tab[i][j] = 1;
                    bmp1.setPixel(i, j, Color.YELLOW);//test

                }
                else
                {
                    tab[i][j] = 0;
                    bmp1.setPixel(i, j, Color.GREEN);//test

                }
                if(j%valeurDeSubdivision==0)
                    bmp1.setPixel(i,j,Color.BLACK);//test
                if(i%valeurDeSubdivision==0)
                    bmp1.setPixel(i,j,Color.BLACK);//test
            }
        }
    }


    public void groupage()
    {
        int wthReduit = (int) wthmin/valeurDeSubdivision;
        int hgtReduit = (int) hgtmin/valeurDeSubdivision;

        int sommeParGroupeDePixel =0;

        for (int i = 0; i < wthReduit; i++)
        {
            for (int j = 0; j < hgtReduit; j++)
            {

                for(int k =0; k<valeurDeSubdivision; k++){
                    for(int l=0; l<valeurDeSubdivision; l++){
                        sommeParGroupeDePixel += tab[(i*valeurDeSubdivision)+k] [(j*valeurDeSubdivision)+l];
                    }
                }
                sommeParGroupeDePixel = (int) (sommeParGroupeDePixel/(valeurDeSubdivision));

                if(sommeParGroupeDePixel<isAnError)
                    tabSommeParGroupe[i][j] = 0;
                else
                    tabSommeParGroupe[i][j] = 1;

                dejaVisitee[i][j]=0;//initialisation

            }
        }
    }

    public void localisationDesErreurs(){

        int wthReduit = (int) wthmin/valeurDeSubdivision;
        int hgtReduit = (int) hgtmin/valeurDeSubdivision;
        int groupeNumero=0;
        for(int i = 0; i< wthReduit; i++){
            for(int j = 0; j<hgtReduit; j++){
                if(tabSommeParGroupe[i][j]==1 && dejaVisitee[i][j]!=1){     //Donc si c'est une nouvelle erreur
                    groupeDesSeptsErreurs[groupeNumero][0]=j;//N
                    groupeDesSeptsErreurs[groupeNumero][1]=i;//O
                    groupeDesSeptsErreurs[groupeNumero][2]=j;//S
                    groupeDesSeptsErreurs[groupeNumero][3]=i;//E
                    checkAlentour(groupeNumero,i,j,wthReduit-1,hgtReduit-1);    //On parcours toute les erreurs qui lui sont collées
                    if(groupeNumero<nombreErreurs-1)
                        groupeNumero++;
                    ;
                }
            }
        }

        int centreX =0;
        int centreY =0;
        int rayon =0;

        int tabGroupTmp[];

        for(groupeNumero =0; groupeNumero<nombreErreurs; groupeNumero ++){

            tabGroupTmp = groupeDesSeptsErreurs[groupeNumero];

            centreX = ((int)((tabGroupTmp[1]+tabGroupTmp[3]))) * ((int)(valeurDeSubdivision/2));
            centreY = ((int)((tabGroupTmp[0]+tabGroupTmp[2]))) * ((int)(valeurDeSubdivision/2));

            if(centreX==0 && centreY==0)
                rayon=0;
            else{
                rayon = Math.max((int)((tabGroupTmp[1]-tabGroupTmp[3])) , (int)((tabGroupTmp[2]-tabGroupTmp[0]))) ;
                rayon += (int)Math.sqrt(valeurDeSubdivision);
                rayon = rayon * ((int)(valeurDeSubdivision/2));

            }
            tabRayon[groupeNumero][0]=centreX;
            tabRayon[groupeNumero][1]=centreY;
            tabRayon[groupeNumero][2]=rayon;

            System.out.println("info tab groupe: " + tabGroupTmp[0]+ " "+ tabGroupTmp[1]+ " "+ tabGroupTmp[2] +" "+ tabGroupTmp[3]);
            System.out.println(centreX + " " + centreY + " "+ rayon);

        }
        compareRayon();///a terminer
    }


    public void checkAlentour(int groupeNumero, int i, int j, int iMax, int jMax){

        if(tabSommeParGroupe[i][j]==1 && dejaVisitee[i][j]!=1)
        {
            dejaVisitee[i][j]=1;
            checkCardinalite(groupeNumero,i,j);
            if(i<iMax)
            if(j<jMax){
                checkAlentour(groupeNumero,i,j+1,iMax,jMax);
            }
            if(i>1){
                checkAlentour(groupeNumero,i-1,j,iMax,jMax);
            }
            if(j>1){
                checkAlentour(groupeNumero,i,j-1,iMax,jMax);
            }
        }

    }

    public void checkCardinalite(int numGroupe, int i, int j){
        /**
         *
         * On cherche ici à determiner les 4 coins d'un carré qui contiendrait parfaitement notre erreur
         */

        int tabTMP[] = groupeDesSeptsErreurs[numGroupe]; //On prends dans une variable tampon le sous-tableau de int correspondant a ce groupe d'erreur
        if(tabTMP!=null){

            if(j<tabTMP[0]) //deplacement en haut: si le nouveau j est plus petit que notre min en hauteur, on le remplace
                tabTMP[0]=j;

            if(i>tabTMP[1]) //deplacement a droite: si le nouveau i est plus grand que notre max en largeur, on le remplace
                tabTMP[1]=i;

            if(j>tabTMP[2]) //deplacement en bas: si le nouveau j est plus grand que notre max en hauteur, on le remplace
                tabTMP[2]=j;

            if(i<tabTMP[3]) //deplacement a gauche: si le nouveau i est plus petit que notre min en largeur, on le remplace
                tabTMP[3]=i;

        }

    }

    public void compareRayon(){

    }




    /*public void quadrillage()
    {
        int cmp1 = 0, cmp2 = 0, cmp3 = 0, cmp4 = 0;


        for (int i = 0; i < wthmin/2; i++)
        {

        for (int i = 0; i < wthmin/2; i++)
        {
            for (int j = 0; j < hgtmin/2; j++)
            {
                if(tab[i][j] == 1)
                {
                    cmp1++;
                }
            }
        }
        cmp1 = cmp1 / ((wthmin/2)*(hgtmin/2));

        for (int i = 0; wthmin/2 < wthmin; i++)
        {
            for (int j = 0; j < hgtmin / 2; j++)
            {
                if(tab[i][j] == 1)
                {
                    cmp2++;
                }
            }
        }
        cmp2 = cmp2 / ((wthmin/2)*(hgtmin/2));

        for (int i = 0; i < wthmin/2; i++)
        {
            for (int j = 0; hgtmin/2 < hgtmin; j++)
            {
                if(tab[i][j] == 1)
                {
                    cmp3++;
                }
            }
        }
        cmp3 = cmp3 / ((wthmin/2)*(hgtmin/2));

        for (int i = 0; wthmin/2 < wthmin; i++)
        {
            for (int j = 0; hgtmin/2 < hgtmin; j++)
            {
                if(tab[i][j] == 1)
                {
                    cmp4++;
                }
            }
        }
        cmp4 = cmp4 / ((wthmin/2)*(hgtmin/2));

    }*/

/////POUR LES TEST/////
    public Bitmap getBmp1(){
        return bmp1;
    }


    public void afficher()
    {
        for (int i = 0; i <this.wthmin/valeurDeSubdivision; i++)
        {
            for (int j = 0; j <this.hgtmin/valeurDeSubdivision; j++)
            {
                System.out.print(tabSommeParGroupe[i][j] + " ");
            }
            System.out.println();
        }

    }




}
