import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import static java.awt.event.KeyEvent.*;

public class FenetreA extends JFrame implements ActionListener{

    //obtient les caractéristiques de l'écran pour que la fenetre occupe tout l'espace

    Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int hauteur = (int)tailleEcran.getHeight();
    int largeur = (int)tailleEcran.getWidth();

    // obtention de 3/4 et de 1/4 de la longueur de l'écran

    double l= (double) 0.75*largeur;
    double l1= (double) 0.25*largeur;
    double h1= (double) 0.75*hauteur;
    double h=(double) 0.25*hauteur;

    // declaration des widgets

    JButton boutonvalider;
    JButton boutonaffichage1;
    JButton boutonreinit;
    String[] autrescomposants = {"Résistance", "Bobine", "Condensateur"};  //tableau permettant la selection des elements des menus deroulants
    ItemElement[] tableaumenu = new ItemElement[4]; // tableau de menu déroulants
    JLabel[] tabjlab = new JLabel[4]; // tableau de JLabel
    boolean[] estvertical = new boolean[4]; // tableau pour savoir si les menus sont sur un segment vertical ou non
    JPanel Panneausysteme; // JPanel dans lequel on insère les JCombobox, les JTextField et l'image du circuit
    JPanel Panneaubouton;
    ImageIcon icone;  // image qui doit s'afficher à la place des menus déroulants
    JLabel zonedessin;
    ImageIcon imagefond; //image du circuit que l'on met en fond de Panneausysteme
    JTextField[] tableauzonetexte = new JTextField[4];
    boolean composantvalide=false;
    Fenetreoscillo oscillo;

    public FenetreA() {

        this.setSize(largeur,hauteur);			// taille de la fenêtre
        this.setLocation(0,0);		//position de la fenêtre
        this.setVisible(false);			//visibilité de la fenêtre
        this.setTitle("circuit 1");     //titre
        this.setBackground(new Color(228,229,230));


        /*// permet d'afficher la fenêtre en plein écran

        this.pack();
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);

         */

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   // permet de fermer la fenêtre sans fermer tout le programme

        //création du panneau où l'on fait son système

        Panneausysteme = new JPanel();
        Panneausysteme.setBounds(0,0,(int) l,hauteur-50);
        Panneausysteme.setLayout(null);
        Panneausysteme.setBackground(new Color(228,229,230));

        imagefond = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("circuitnumero1.png")));

        //code ajoute ci-dessous

        Image image = imagefond.getImage().getScaledInstance((int) l, hauteur, Image.SCALE_SMOOTH);
        imagefond = new ImageIcon(image, imagefond.getDescription());

        // il reste a modifier les emplacements dapparition des composants en bas pour les fenetres B C et D

        zonedessin = new JLabel(imagefond);
        zonedessin.setLayout(null);
        zonedessin.setBounds(0,0,(int) l,hauteur);
        zonedessin.setVisible(true);
        Panneausysteme.add(zonedessin);

        //création du panneau avec les boutons de configuration

        Panneaubouton = new JPanel();
        Panneaubouton.setBounds((int) l,0,(int) l1,hauteur);
        Panneaubouton.setLayout(null);
        Panneaubouton.setBackground(new Color(72, 79, 81));

        boutonvalider = new JButton("Afficher les résultats");
        boutonvalider.setBounds(70,(int)(h1)-150,200,50);
        boutonvalider.setBackground(Color.gray);
        boutonvalider.setForeground(Color.white);
        boutonvalider.addActionListener(this);
        Panneaubouton.add(boutonvalider);
        boutonvalider.setVisible(false);

        boutonaffichage1 = new JButton("Valider les composants");
        boutonaffichage1.setBounds(70,(int)(h1)+50,200,50);
        boutonaffichage1.setBackground(Color.gray);
        boutonaffichage1.setForeground(Color.white);
        boutonaffichage1.addActionListener(this);
        Panneaubouton.add(boutonaffichage1);

        boutonreinit = new JButton("Réinitialiser");
        boutonreinit.setBounds(70,(int)(h1)-50,200,50);
        boutonreinit.setBackground(Color.gray);
        boutonreinit.setForeground(Color.white);
        boutonreinit.addActionListener(this);
        Panneaubouton.add(boutonreinit);

        affichemenu(); //création et affichage des menus déroulants via la méthode affichemenu

        for (ItemElement i : tableaumenu){
            zonedessin.add(i); //on ajoute l'ItemComposant à la zone de dessin
        }

        //on regroupe tous les JTextFields dans un tableau pour faciliter la manipulation
        tableauzonetexte = this.regrouperJTextField(tableaumenu.length+1);


        //création panneau principal

        JPanel Panneaumain = new JPanel();
        Panneaumain.setBounds(0,0,largeur,hauteur);
        Panneaumain.setLayout(null);
        Panneaumain.setBackground(Color.gray);

        Panneaumain.add(Panneaubouton);
        Panneaumain.add(Panneausysteme);

        //ajout du panneau à la fenêtre

        add(Panneaumain);

    }

    //methode actionperformed

    public void actionPerformed (ActionEvent e) {

        if (e.getSource()==boutonaffichage1){

            for(int i=0; i<4;i++) {
                while (tableauzonetexte[i].getText().equals("") ||Integer.parseInt(tableauzonetexte[i].getText()) > 10000 || Integer.parseInt(tableauzonetexte[i].getText()) < 1) {
                    JOptionPane.showMessageDialog(this, "Veuillez rentrer une valeur de R, L ou C correcte (entre 1 et 10000 USI) !");
                    tableauzonetexte[i].setText("Changer"); // le fait de faire apparaitre changer fait apparaitre des messages d'erreur dans la console mais ce n'est pas grave, c'est parce que le TextField n'est pas censé pouvoir contenir du texte
                }
            }

            boutonvalider.setVisible(true);
            composantvalide=true;
            remplacemenu(tableaumenu, estvertical);
            Panneausysteme.repaint();

        }

        if (e.getSource()==boutonvalider) {

            oscillo = new Fenetreoscillo();
            oscillo.setVisible(true);
            //CircuitA circuitCalcul = new CircuitA(tableaumenu);

        }

        if (e.getSource()==boutonreinit) {

            if (composantvalide==true){
                boutonvalider.setVisible(false);
                for (int k = 0; k < 4; k++) {

                    tabjlab[k].setVisible(false);
                    tableaumenu[k].setVisible(true);

                }
            }
            composantvalide=false;
        }

    }

    //methode qui remplace les menus deroulants par des dessins correspondants aux composants selectionnes

    public void remplacemenu(ItemElement[] tab, boolean[]tab1){

        for (int j=0;j<tab.length;j++) {
            //s'il s'agit d'un composant
            if(tab[j] instanceof ItemComposant){
                ItemComposant x = (ItemComposant) tab[j];
                if(x.getComposant()=="Résistance"){

                    icone= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("résistance.png")));
                    tabjlab[j] = new JLabel(icone);
                    tabjlab[j].setLayout(null);
                    tabjlab[j].setBounds(tab[j].getX(),tab[j].getY(),300,50);
                    tabjlab[j].setVisible(true);
                    tab[j].setVisible(false);
                    zonedessin.add(tabjlab[j]);

                    if(tab1[j]==true){
                        tourneimage(90,tabjlab[j],icone);
                        tabjlab[j].setBounds(tab[j].getX(),tab[j].getY(),50,300);
                    }

                }

                if(x.getComposant()=="Bobine"){

                    icone= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("bobines.png")));
                    tabjlab[j] = new JLabel(icone);
                    tabjlab[j].setLayout(null);
                    tabjlab[j].setBounds(tab[j].getX(),tab[j].getY()-5,300,50);
                    tabjlab[j].setVisible(true);
                    tab[j].setVisible(false);
                    zonedessin.add(tabjlab[j]);

                    if(tab1[j]==true){
                        tourneimage(90,tabjlab[j],icone);
                        tabjlab[j].setBounds(tab[j].getX()+6,tab[j].getY(),50,300);
                    }
                }

                if(x.getComposant()=="Condensateur"){

                    icone= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("condo.png")));
                    tabjlab[j] = new JLabel(icone);
                    tabjlab[j].setLayout(null);
                    tabjlab[j].setBounds(tab[j].getX(),tab[j].getY()+1,300,50);
                    tabjlab[j].setVisible(true);
                    tab[j].setVisible(false);
                    zonedessin.add(tabjlab[j]);

                    if(tab1[j]==true){
                        tourneimage(90,tabjlab[j],icone);
                        tabjlab[j].setBounds(tab[j].getX(),tab[j].getY(),50,300);
                    }
                }
            }
            //s'il s'agit du générateur
            if(tab[j] instanceof ItemGenerateur) {
                icone = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("sourceU.png")));
                tabjlab[j] = new JLabel(icone);
                tabjlab[j].setLayout(null);
                tabjlab[j].setBounds(tab[j].getX() + 1, tab[j].getY(), 300, 50);
                tabjlab[j].setVisible(true);
                tab[j].setVisible(false);
                zonedessin.add(tabjlab[j]);

                if (tab1[j] == true) {
                    tourneimage(90, tabjlab[j], icone);
                    tabjlab[j].setBounds(tab[j].getX(), tab[j].getY(), 50, 300);
                }
            }
        }
    }

    //methode qui permet de tourner une image de x degres
    public void tourneimage(int angle, JLabel lelabel, ImageIcon imageselectionnee) {
        int w = lelabel.getIcon().getIconWidth();
        int h = lelabel.getIcon().getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api

        BufferedImage DaImage = new BufferedImage(h, w, type);
        Graphics2D g2 = DaImage.createGraphics();

        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);

        at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        g2.drawImage(imageselectionnee.getImage(), at, lelabel);
        g2.dispose();

        lelabel.setIcon(new ImageIcon(DaImage));
    }

    public JTextField[] regrouperJTextField(int taille){
        JTextField[] r = new JTextField[taille];

        for (int i=0;i<tableaumenu.length;i++){
            if(tableaumenu[i] instanceof ItemGenerateur){
                ItemGenerateur x = (ItemGenerateur) tableaumenu[i];
                r[i]=x.saisieAmpl;
                r[i+1]=x.saisieFreq;
            }
            if(tableaumenu[i] instanceof ItemComposant){
                ItemComposant x = (ItemComposant)tableaumenu[i];
                r[i+1]=x.saisie;
            }
        }
        return r;
    }

    public void affichemenu(){  // permet d'afficher les combobox au bon endroit suivant notre résolution d'écran

        if (largeur>1300 && largeur<=1600 && hauteur>600 && hauteur <=900) {  //ordi 15 pouces
            tableaumenu[0] = new ItemGenerateur();
            tableaumenu[0].setLocation(Panneausysteme.getWidth() / 20, (int) (Panneausysteme.getHeight() * 0.435));
            estvertical[0] = true;

            tableaumenu[1] = new ItemComposant(autrescomposants);
            tableaumenu[1].setLocation((int) (Panneausysteme.getWidth() * 0.452), (int) (Panneausysteme.getHeight() / 13.5)); //composant d'en haut
            estvertical[1] = false;

            tableaumenu[2] = new ItemComposant(autrescomposants);
            tableaumenu[2].setLocation((int) (Panneausysteme.getWidth() * 0.85), (int) (Panneausysteme.getHeight() * 0.435)); //composant de droite
            estvertical[2] = true;

            tableaumenu[3] = new ItemComposant(autrescomposants);
            tableaumenu[3].setLocation((int) (Panneausysteme.getWidth() * 0.452), (int) (Panneausysteme.getHeight() * 0.891)); // composant d'en bas
            estvertical[3] = false;
        }

        if (largeur>1600 && largeur<2100 && hauteur>900 && hauteur < 1300) {  //ordi veloso et ithan (avec resolution 1920*1080)

            tableaumenu[0] = new ItemGenerateur();
            tableaumenu[0].setLocation(Panneausysteme.getWidth() / 20, (int) (Panneausysteme.getHeight() * 0.435));
            estvertical[0] = true;

            tableaumenu[1] = new ItemComposant(autrescomposants);
            tableaumenu[1].setLocation((int) (Panneausysteme.getWidth() * 0.452), (int) (Panneausysteme.getHeight() / 13.5)); //composant d'en haut
            estvertical[1] = false;

            tableaumenu[2] = new ItemComposant(autrescomposants);
            tableaumenu[2].setLocation((int) (Panneausysteme.getWidth() * 0.85), (int) (Panneausysteme.getHeight() * 0.435)); //composant de droite
            estvertical[2] = true;

            tableaumenu[3] = new ItemComposant(autrescomposants);
            tableaumenu[3].setLocation((int) (Panneausysteme.getWidth() * 0.452), (int) (Panneausysteme.getHeight() * 0.891)); // composant d'en bas
            estvertical[3] = false;
        }

        //rajouter des resolutions suivant les écrans que l'on a
    }

}


