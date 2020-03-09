import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FenetreA extends JFrame implements ActionListener{

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
    String[] sourcestension = {"source de 5V", "source de 12 V"};  //tableau permettant la selection des elements des menus deroulants
    String[] autrescomposants = {"Résistance", "Bobine", "Condensateur"};  //tableau permettant la selection des elements des menus deroulants
    JComboBox[] tableaumenu = new JComboBox[4]; // tableau de menu déroulants
    boolean[] estvertical = new boolean[4]; // tableau pour savoir si les menus sont sur un segment vertical ou non
    public Panneaudessin Panneausysteme;
    public ImageIcon icone;  // image qui doit s'afficher à la place des menus déroulants
    public JLabel jlabel;   //jlabel contenant l'image qui doit s'afficher

    public FenetreA() {

        this.setSize(largeur,hauteur);			// taille de la fenêtre
        this.setLocation(0,0);		//position de la fenêtre
        this.setVisible(false);			//visibilité de la fenêtre
        this.setTitle("circuit 1");     //titre

        // permet d'afficher la fenêtre en plein écran

        this.pack();
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   // permet de fermer la fenêtre sans fermer tout le programme

        //création du panneau où l'on fait son système

        Panneausysteme = new Panneaudessin(1);
        Panneausysteme.setBounds(0,0,(int) l,hauteur);
        Panneausysteme.setLayout(null);

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

        for (int i=0;i<4;i++){

            if(i==0){
                tableaumenu[i] = new JComboBox(sourcestension);
                tableaumenu[i].setBounds(40,Panneausysteme.getHeight()/2-50,100,50);
                estvertical[i]=true;
            }else{
                tableaumenu[i]= new JComboBox(autrescomposants);
                if(i==1) {
                    tableaumenu[i].setBounds(Panneausysteme.getWidth() / 2 - 50, 45, 100, 50); //composant d'en haut
                    estvertical[i]=false;
                }
                if(i==2){
                    tableaumenu[i].setBounds(Panneausysteme.getWidth()/2+350,Panneausysteme.getHeight()/2-50,100,50); //composant de droite
                    estvertical[i]=true;
                }
                if(i==3){
                    tableaumenu[i].setBounds(Panneausysteme.getWidth()/2-50,Panneausysteme.getHeight()/2+245,100,50); // composant d'en bas
                    estvertical[i]=false;
                }
            }
            Panneausysteme.add(tableaumenu[i]);
        }

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

            boutonvalider.setVisible(true);
            remplacemenu(tableaumenu, estvertical);
            Panneausysteme.repaint();

        }

    }

    //methode qui remplace les menus deroulants par des dessins correspondants aux composants selectionnes

    public void remplacemenu(JComboBox[] tab, boolean[]tab1){


        for (int j=0;j<tab.length;j++) {

            if(tab[j].getSelectedItem().toString()=="Résistance"){

                //ne fonctionne pas

                icone= new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("circuitenparallele.png")));
                jlabel = new JLabel(icone);
                jlabel.setLayout(null);
                jlabel.setBounds(200,200,400,400);
                jlabel.setVisible(true);
                Panneausysteme.add(jlabel);
                Panneausysteme.validate();
                Panneausysteme.updateUI();
                repaint();

            }
            if(tab[j].getSelectedItem().toString()=="Bobine"){

            }
            if(tab[j].getSelectedItem().toString()=="Condensateur"){

            }
            if(tab[j].getSelectedItem().toString()=="source de 5V"){

            }
            if(tab[j].getSelectedItem().toString()=="source de 12 V"){


            }
        }
    }

}
