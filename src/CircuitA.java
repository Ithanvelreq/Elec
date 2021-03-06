/**
 * Classe permettant de realiser les calculs du circuit A, en haut a gauche
 */
public class CircuitA extends Circuit {
    /**
     * Constructeur
     * @param compA Tableau des differents ItemElement choisi par l'utilisateur qui sont stockes dans FenetreA_bis
     */
    public CircuitA(ItemElement[] compA) {
        //création du circuit
        super(compA);
        mailles.add(new Maille(compA));

        //récupération de la fréquence et de la tension du générateur
        ItemGenerateur x =(ItemGenerateur) compA[0];
        frequence = x.getFrequence();
        amplitude = x.getAmpl();

        //remplissage de la matrice m1 qu'avec des 0
        for(int i1=0;i1<m1.length;i1++){
            for(int j1=0;j1<m1[0].length;j1++){
                m1[i1][j1] = new Impedance(0,0);
            }

        }

        //remplissage de la matrice m2 qu'avec des 0
        for(int i2=0;i2<m2.length;i2++){
            for(int j2=0;j2<m2[0].length;j2++){
                m2[i2][j2] = new Impedance(0,0);
            }

        }


        //remplissage de chaque ligne de m1 avec les coefficients gauches de chaque équation
        Dipole a;
        m1[0][0]= new Impedance(1,0);
        m1[1][0]= new Impedance(1,0);
        m1[2][1]=new Impedance(1,0);
        m1[2][2]=new Impedance(-1,0);
        m1[3][2]=new Impedance(1,0);
        m1[3][3]=new Impedance(-1,0);
        for(int l=1;l<mailles.get(0).Icomposants.size();l++){
            ItemComposant y = (ItemComposant) mailles.get(0).Icomposants.get(l);
            a=y.RenvoiComposant(y.getComposant(),frequence);
            m1[1][l] = a.z.multiplicationV2(new Impedance(-1,0));
            m1[l+3][l] = a.z.multiplicationV2(new Impedance(-1,0));
        }
        for(int j=0;j<3;j++){
            m1[j+4][j+4]=new Impedance(1,0);
        }

       //remplissage de m2 avec les coefficients droits de chaque équation
       m2[0][0]= new Impedance(amplitude,0);
    }

}
