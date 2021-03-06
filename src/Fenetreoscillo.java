import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Fenetre qui contient l'oscilloscope
 */
public class Fenetreoscillo extends JFrame implements ActionListener {
    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = -3914578220391097071L;
    /**
     * Tableau de Jcheckbox
     */
    public JCheckBox[] tabcheckbox = new JCheckBox[4]; // t
    /**
     * Tableau rassemblant les inconnues du systeme d'equations du systeme
     */
    public String[] w; // t
    /**
     * Tableau rassemblant les solutions du systeme d'equations
     */
    public Impedance[] z; // tableau rassemblant les solutions du système d'équations du système
    /**
     * Recuperation du tableau contenant les ItemElement, qui nous permettra de recuperer les donnees entrees dans les JtextField
     */
    public ItemElement[] tableaumenu; // récupération du tableau contenant les ItemElement, qui nous permettra de récupérer les données entrées dans les JtextField
    /**
     * Creation d'un tableau de String qui contiendra les noms des composants du circuit
     */
    public String[] nomdescomposants = new String[4]; // création d'un tableau de String qui contiendra les noms des composants du circuit.

    //récupération des dimensions de l'écran et calculs de variables qui nous seront utiles pour le dimensionnement des widgets
    /**
     * Caracteristiques de l'ecran
     */
    Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Hauteur utile de l'ecran
     */
    int hauteur = (int)tailleEcran.getHeight()-40;
    /**
     * Largeur utile de l'ecran
     */
    int largeur = (int)tailleEcran.getWidth();
    /**
     * Variable qui vaut 75% de la hauteur pour dimensionner les panneaux
     */
    double h1= (double) 0.75*hauteur;
    /**
     * Variable qui vaut 25% de la hauteur pour dimensionner les panneaux
     */
    double h=(double) 0.25*hauteur;

    //constructeur
    /**
     * Constructeur qui cree la fenetre de l'oscilloscope et qui place les differents widgets dans la fenetre
     * @param w Inconnues du probleme
     * @param z Solutions du probleme
     * @param tableaumenu Tableau des composants
     */
    public Fenetreoscillo(String[] w, Impedance[] z, ItemElement[] tableaumenu) {

        super( "Oscilloscope" );
        //caractértistiques fenetre
        this.setDefaultCloseOperation( HIDE_ON_CLOSE );
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setSize(largeur,hauteur);			// taille de la fenêtre
        this.setLocation(0,0);		//position de la fenêtre
        this.setVisible(false);			//visibilité de la fenêtre
        this.setLocationRelativeTo( null );
        // récupération des tableaux d'inconnues, de solutions et d'Itemelement contenus dans FenetreA/B/C ou D_bis
        this.w=w;
        this.z=z;
        this.tableaumenu=tableaumenu;

        // creation du panneau principal
        JPanel Panneaumain = (JPanel) this.getContentPane();

        //creation d'un panneau accueillera les Jcheckbox et les curseurs
        JPanel panneaudubas = new JPanel();
        panneaudubas.setLayout(null);
        panneaudubas.setBounds(0, (int)h1, largeur, (int)h);
        panneaudubas.setBackground(new Color(195, 188, 181));

        //initialisation du dessin des courbes
        dessinoscillo dessin = new dessinoscillo(tabcheckbox,z, tableaumenu, panneaudubas); //creation de la courbe

        //ajout JCheckBox au panneau du bas
        nommecomposant();
        for(int i=0;i<tabcheckbox.length;i++) {

            if(tableaumenu[i] instanceof ItemComposant) {
                tabcheckbox[i] = new JCheckBox("Afficher la tension" + nomdescomposants[i]);
                tabcheckbox[i].setBounds((int) (largeur * (1 + (i * 2)) / 8.8 - tabcheckbox[i].getWidth() / 2)-50, 0, 330, (int) h);
                tabcheckbox[i].addActionListener(this);
                tabcheckbox[i].setBackground(new Color(195, 188, 181));
                panneaudubas.add(tabcheckbox[i]);
            }else {
                ItemGenerateur y = (ItemGenerateur) tableaumenu[i];
                tabcheckbox[i] = new JCheckBox("Afficher la tension du générateur");
                tabcheckbox[i].setBounds((int) (largeur * (1 + (i * 2)) / 9 - tabcheckbox[i].getWidth() / 2), 0, 230, (int) h);
                tabcheckbox[i].addActionListener(this);
                tabcheckbox[i].setBackground(new Color(195, 188, 181));
                panneaudubas.add(tabcheckbox[i]);
            }

        }

        //on ajoute la courbe et le panneau du bas
        Panneaumain.add( panneaudubas);
        Panneaumain.add(dessin);

    }

    //méthode évènement
    @Override
    public void actionPerformed (ActionEvent e) {

    }

    /**
     * Methode qui differencie les composants en les nommant dans les cas ou des circuits ont 2 composants identiques
     */
    public void nommecomposant(){
        NumberFormat format = new DecimalFormat("0.##E0");
        double s;

        for(int i=0; i<tableaumenu.length;i++){
            if(tableaumenu[i] instanceof ItemComposant){
                ItemComposant y = (ItemComposant) tableaumenu[i];
                s = Double.parseDouble(y.saisie.getText());
                if(y.getComposant()=="Resistance"){
                    nomdescomposants[i] =" de la Résistance de "+ format.format(s) + " ohms";
                }
                if(y.getComposant()=="Condensateur"){
                    nomdescomposants[i] =" du Condensateur de "+ format.format(s) + " F";
                }
                if(y.getComposant()=="Bobine"){
                    nomdescomposants[i] =" de la Bobine de "+ format.format(s) + " H";
                }
            }
        }

    }

}