package com.example.solveur7erreurs;

import android.support.v7.app.AppCompatActivity;

import java.io.File;

import javax.swing.JFrame;

import ae.java.awt.event.ActionEvent;
import ae.java.awt.event.ActionListener;

public class Page4 extends JFrame {

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fichierMenu = new JMenu();
    private final JMenuItem ouvrirMenu = new JMenuItem();
    private final JMenu filtreMenu = new JMenu();
    private final PanDessin panneau = new PanDessin();
    private final JMenuItem enregistrerMenu = new JMenuItem();
    private final JMenuItem niveauGrisMenu = new JMenuItem();
    private final JMenuItem assombrirMenu = new JMenuItem();
    private final JMenuItem brillanceMenu = new JMenuItem();
    private final JMenuItem binarisationMenu = new JMenuItem();
    private final JMenuItem convolutionMenu = new JMenuItem();
    private final JMenu retaillerMenu = new JMenu();
    private final JMenuItem agrandirMenu = new JMenuItem();
    private final JMenuItem reduireMenu = new JMenuItem();


    public Page4() {
        super();
        setBounds(100, 100, 500, 375);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        try {
            creerMenu();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //
    }
    private void creerMenu() throws Exception {

        // construction du menu
        setJMenuBar(menuBar);
        menuBar.add(fichierMenu);
        fichierMenu.setText("Fichier");
        fichierMenu.add(ouvrirMenu);
        ouvrirMenu.addActionListener((ActionListener)this);
        ouvrirMenu.setText("ouvrir");

        fichierMenu.add(enregistrerMenu);
        enregistrerMenu.addActionListener((ActionListener)this);
        enregistrerMenu.setText("enregistrer");
        menuBar.add(filtreMenu);
        filtreMenu.setText("Filtre");

        filtreMenu.add(niveauGrisMenu);
        niveauGrisMenu.addActionListener((ActionListener)this);
        niveauGrisMenu.setText("niveau de gris");

        filtreMenu.add(binarisationMenu);
        binarisationMenu.addActionListener((ActionListener)this);
        binarisationMenu.setText("binarisation");

        filtreMenu.add(assombrirMenu);
        assombrirMenu.addActionListener((ActionListener)this);
        assombrirMenu.setText("assombrir");

        filtreMenu.add(brillanceMenu);
        brillanceMenu.addActionListener((ActionListener)this);
        brillanceMenu.setText("brillance");

        filtreMenu.add(convolutionMenu);
        convolutionMenu.addActionListener((ActionListener)this);
        convolutionMenu.setText("convolution");

        menuBar.add(retaillerMenu);
        retaillerMenu.setText("retailler");

        retaillerMenu.add(agrandirMenu);
        agrandirMenu.addActionListener((ActionListener)this);
        agrandirMenu.setText("agrandir");

        retaillerMenu.add(reduireMenu);
        reduireMenu.addActionListener((ActionListener)this);
        reduireMenu.setText("reduire");

        // ajouter le panneau de dessin
        getContentPane().add(panneau);
    }
    public void actionPerformed(ActionEvent cliqueMenu) {
        if (cliqueMenu.getSource().equals(ouvrirMenu))
        {
            JFileChooser fileOuvrirImage = new JFileChooser();
            if (fileOuvrirImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                panneau.ajouterImage(new File(fileOuvrirImage.getSelectedFile()
                        .getAbsolutePath()));
            }
        } else if (cliqueMenu.getSource().equals(enregistrerMenu)) {
            JFileChooser fileEnregistrerImage = new JFileChooser();
            if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fichierEnregistrement = new File(fileEnregistrerImage.getSelectedFile().getAbsolutePath()+ ".JPG");
                panneau.enregistrerImage(fichierEnregistrement);
            }
        } else
        if (cliqueMenu.getSource().equals(niveauGrisMenu)) {
            panneau.imageEnNiveauGris();
        } else if (cliqueMenu.getSource().equals(brillanceMenu)) {
            panneau.imageEclaircie();
        } else if (cliqueMenu.getSource().equals(binarisationMenu)) {
            panneau.imageBinaire();
        } else if (cliqueMenu.getSource().equals(convolutionMenu)) {
            panneau.imageConvolue();
            System.out.println("appel convolution");
        } else if (cliqueMenu.getSource().equals(agrandirMenu)) {
            panneau.agrandirImage();
        } else if (cliqueMenu.getSource().equals(reduireMenu)) {
            panneau.reduireImage();
        }else if(cliqueMenu.getSource().equals(assombrirMenu)){
            panneau.imageSombre();
        }
    }

    public static void main(String args[])
    {
        try {
            Page4 frame = new Page4();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
