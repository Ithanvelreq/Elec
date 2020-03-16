import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import static java.awt.event.KeyEvent.*;

public class FenetreB extends JFrame implements ActionListener{

    //obtient les caractéristiques de l'écran pour que la fenetre occupe tout l'espace

    Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int hauteur = (int)tailleEcran.getHeight();
    int largeur = (int)tailleEcran.getWidth();

    // obtention de 3/4 et de 1/4 de la longueur de l'écran

    double l= (double) 0.75*largeur;
    double l1= (double) 0.25*largeur;

    // declaration des widgets

    public JButton boutonvalider;
    public JButton boutonaffichage;
    public JButton boutonaffichage1;
    String[] sourcestension = {"source de tension"};  //tableau permettant la selection des elements des menus deroulants
    String[] autrescomposants = {"Résistance", "Bobine", "Condensateur"};  //tableau permettant la selection des elements des menus deroulants
    ItemElement[] tableaumenu = new ItemElement[4]; // tableau de menu déroulants
    boolean[] estvertical = new boolean[4]; // tableau pour savoir si les menus sont sur un segment vertical ou non
    public JPanel Panneausysteme; // JPanel dans lequel on insère les JCombobox, les JTextField et l'image du circuit
    public ImageIcon icone;  // image qui doit s'afficher à la place des menus déroulants
    public JLabel jlabel;   //jlabel contenant l'image qui doit s'afficher
    public JLabel zonedessin;
    public ImageIcon imagefond; //image du circuit que l'on met en fond de Panneausysteme
    JTextField[] tableauzonetexte;

    public FenetreB() {

        this.setSize(largeur,hauteur);			// taille de la fenêtre
        this.setLocation(0,0);		//position de la fenêtre
        this.setVisible(false);			//visibilité de la fenêtre
        this.setTitle("circuit 2");     //titre

        // permet d'afficher la fenêtre en plein écran

        this.pack();
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   // permet de fermer la fenêtre sans fermer tout le programme

        //création du panneau où l'on fait son système

        Panneausysteme = new JPanel();
        Panneausysteme.setBounds(0,0,(int) l,hauteur);
        Panneausysteme.setLayout(null);
        Panneausysteme.setBackground(new Color(228,229,230));

        imagefond = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("circuit2-3.png")));
        zonedessin = new JLabel(imagefond);
        zonedessin.setLayout(null);
        zonedessin.setBounds(0,0,(int) l,hauteur);
        zonedessin.setVisible(true);
        Panneausysteme.add(zonedessin);

        //création du panneau avec les boutons de configuration

        JPanel Panneaubouton = new JPanel();
        Panneaubouton.setBounds((int) l,0,(int) l1,hauteur);
        Panneaubouton.setLayout(null);
        Panneaubouton.setBackground(new Color(72, 79, 81));

        boutonvalider = new JButton("Valider");
        boutonvalider.setBounds(Panneaubouton.getWidth()/4,10,200,50);
        boutonvalider.setBackground(Color.gray);
        boutonvalider.setForeground(Color.white);
        boutonvalider.addActionListener(this);
        Panneaubouton.add(boutonvalider);
        boutonvalider.setVisible(false);

        boutonaffichage = new JButton("Afficher les résultats");
        boutonaffichage.setBounds(Panneaubouton.getWidth()/4,100,200,50);
        boutonaffichage.setBackground(Color.gray);
        boutonaffichage.setForeground(Color.white);
        boutonaffichage.addActionListener(this);
        Panneaubouton.add(boutonaffichage);

        boutonaffichage1 = new JButton("Valider les composants");
        boutonaffichage1.setBounds(Panneaubouton.getWidth()/4,200,200,50);
        boutonaffichage1.setBackground(Color.gray);
        boutonaffichage1.setForeground(Color.white);
        boutonaffichage1.addActionListener(this);
        Panneaubouton.add(boutonaffichage1);

        //affichage des menus déroulants

        tableaumenu[0] = new ItemGenerateur();
        tableaumenu[0].setLocation(40,Panneausysteme.getHeight()/2-50);
        estvertical[0]=true;

        tableaumenu[1]= new ItemComposant(autrescomposants);
        tableaumenu[1].setLocation(Panneausysteme.getWidth() / 2 - 250, 81); //composant d'en haut
        estvertical[1]=false;

        tableaumenu[2]= new ItemComposant(autrescomposants);
        tableaumenu[2].setLocation(Panneausysteme.getWidth()/2+334,Panneausysteme.getHeight()/2-50); //composant de droite
        estvertical[2]=true;

        tableaumenu[3]= new ItemComposant(autrescomposants);
        tableaumenu[3].setLocation(Panneausysteme.getWidth()/2-49,Panneausysteme.getHeight()/2-50); // composant du milieu
        estvertical[3]=true;

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
            remplacemenu(tableaumenu, estvertical);
            Panneausysteme.repaint();

        }

    }

    //methode qui remplace les menus deroulants par des dessins correspondants aux composants selectionnes

    public void remplacemenu(ItemElement[] tab, boolean[]tab1){


        for (int j=0;j<tab.length;j++) {
            if(tab[j] instanceof ItemComposant) {
                ItemComposant x = (ItemComposant) tab[j];
                if (x.getComposant() == "Résistance") {

                    icone = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("résistance.png")));
                    jlabel = new JLabel(icone);
                    jlabel.setLayout(null);
                    jlabel.setBounds(tab[j].getX(), tab[j].getY() - 1, 300, 50);
                    jlabel.setVisible(true);
                    tab[j].setVisible(false);
                    zonedessin.add(jlabel);

                    if (tab1[j] == true) {
                        tourneimage(90, jlabel, icone);
                        jlabel.setBounds(tab[j].getX() + 25, tab[j].getY(), 50, 300);
                    }
                }

                if (x.getComposant() == "Bobine") {

                    icone = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("bobines.png")));
                    jlabel = new JLabel(icone);
                    jlabel.setLayout(null);
                    jlabel.setBounds(tab[j].getX(), tab[j].getY() - 6, 300, 50);
                    jlabel.setVisible(true);
                    tab[j].setVisible(false);
                    zonedessin.add(jlabel);

                    if (tab1[j] == true) {
                        tourneimage(90, jlabel, icone);
                        jlabel.setBounds(tab[j].getX() + 31, tab[j].getY(), 50, 300);
                    }
                }

                if (x.getComposant() == "Condensateur") {

                    icone = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("condo.png")));
                    jlabel = new JLabel(icone);
                    jlabel.setLayout(null);
                    jlabel.setBounds(tab[j].getX(), tab[j].getY(), 300, 50);
                    jlabel.setVisible(true);
                    tab[j].setVisible(false);
                    zonedessin.add(jlabel);

                    if (tab1[j] == true) {
                        tourneimage(90, jlabel, icone);
                        jlabel.setBounds(tab[j].getX() + 25, tab[j].getY(), 50, 300);
                    }
                }
            }
            //s'il s'agit du générateur
            if(tab[j] instanceof ItemGenerateur){

                icone= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("sourceU.png")));
                jlabel = new JLabel(icone);
                jlabel.setLayout(null);
                jlabel.setBounds(tab[j].getX(),tab[j].getY(),300,50);
                jlabel.setVisible(true);
                tab[j].setVisible(false);
                zonedessin.add(jlabel);

                if(tab1[j]==true){
                    tourneimage(90,jlabel,icone);
                    jlabel.setBounds(tab[j].getX()+10,tab[j].getY(),50,300);
                }
            }
        }
    }

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

}
