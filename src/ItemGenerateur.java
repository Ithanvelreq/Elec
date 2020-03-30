import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.*;

public class ItemGenerateur extends ItemElement {

    //attributs
    JLabel source;
    JLabel freq;
    JLabel ampl;
    JTextField saisieFreq;
    JTextField saisieAmpl;
    Trace_Composant dessin;


    //constructeur
    public ItemGenerateur(){

        this.setBackground(new Color(228,229,230));
        this.setSize(160, 120);
        this.setLayout(null);

        source = new JLabel("Source de tension");
        source.setBounds(8,5,140,30);
        source.setFont(new Font("Arial", Font.BOLD,14));

        freq = new JLabel("Fréquence (Hz)");
        freq.setBounds(10,35,100,30);

        ampl = new JLabel("Amplitude (V)");
        ampl.setBounds(10,75,100,30);

        saisieAmpl = new JTextField();
        saisieAmpl.setBounds(100,75,50,30);

        saisieFreq = new JTextField();
        saisieFreq.setBounds(100,35,50,30);

        //blocage de la saisie de lettre dans les JTextField
        JTextField[] chpSaisie = {saisieFreq,saisieAmpl};
        for (JTextField t : chpSaisie){
            t.addKeyListener(new KeyAdapter(){
                public void keyTyped(KeyEvent e) { //on n'autorise que l'ecriture des chiffres
                    if (e.getKeyChar()==VK_0 || e.getKeyChar()==VK_1 || e.getKeyChar()==VK_2 || e.getKeyChar()==VK_3 || e.getKeyChar()==VK_4 || e.getKeyChar()==VK_5 || e.getKeyChar()==VK_6|| e.getKeyChar()==VK_7|| e.getKeyChar()==VK_8|| e.getKeyChar()==VK_9 ) {
                    }else{
                        e.consume();
                    }
                }
            });
        }

        this.add(source);
        this.add(freq);
        this.add(ampl);
        this.add(saisieFreq);
        this.add(saisieAmpl);
    }

    //GETTER
    public double getFrequence (){
        return  Double.parseDouble(saisieFreq.getText());
    }
    public double getAmpl (){
        return  Double.parseDouble(saisieAmpl.getText());
    }

    //METHODE
    /**
     * méthode qui permet de dessiner la composant choisi
     * @param aDessiner : boolean s'il faut dessiner ou non l'élément
     * @param vertical : boolean sur l'emplacement
     */
    public void dessine(boolean aDessiner, boolean vertical){

        if(aDessiner) {
            dessin = new Trace_Composant("generateur",vertical,this.getHeight(),this.getWidth(),aDessiner);
            this.add(dessin);
            //on cache tous les widgets
            source.setVisible(false);
            freq.setVisible(false);
            ampl.setVisible(false);
            saisieAmpl.setVisible(false);
            saisieFreq.setVisible(false);
            repaint();
        }
        if(!aDessiner){
            dessin.setVisible(false);
            source.setVisible(true);
            freq.setVisible(true);
            ampl.setVisible(true);
            saisieFreq.setVisible(true);
            saisieAmpl.setVisible(true);
            repaint();
        }
    }
}
