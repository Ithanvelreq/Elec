public class CircuitC extends Circuit {

    public CircuitC(ItemElement[] compC) {
        //création du circuit
        super(compC);
        ItemElement[] maille1 = {compC[0], compC[3]};
        mailles.add(new Maille(maille1));
        ItemElement[] maille2 = {compC[3], compC[1], compC[2]};
        mailles.add(new Maille(maille2));


        //récupération de la fréquence et de la tension du générareur
        ItemGenerateur x = (ItemGenerateur) compC[0];
        frequence = x.getFrequence();
        amplitude = x.getAmpl();

        //remplissage de la matrice m1 qu'avec des 0
        for (int i1 = 0; i1 < m1.length; i1++) {
            for (int j1 = 0; j1 < m1[0].length; j1++) {
                m1[i1][j1] = new Impedance(0, 0);
            }

        }

        //remplissage de la matrice m2 qu'avec des 0
        for (int i2 = 0; i2 < m2.length; i2++) {
            for (int j2 = 0; j2 < m2[0].length; j2++) {
                m1[i2][j2] = new Impedance(0, 0);
            }

        }


        //remplissage de chaque ligne de m1 avec les coefficients gauches de chaque équation
        Dipole a = new Dipole();
        m1[0][0]= new Impedance(1,0);
        m1[1][0]= new Impedance(1,0);
        m1[3][1]= new Impedance(-1,0);
        m1[3][2]= new Impedance(-1,0);
        for(int l=1;l<mailles.get(1).Icomposants.size();l++){
            ItemComposant y = (ItemComposant) mailles.get(1).Icomposants.get(l);
            a=y.RenvoiComposant(y.getComposant(),frequence);
            m1[l][l]= a.z.multiplicationV2(new Impedance(-1,0));
        }

        for(int j=0;j<mailles.get(0).Icomposants.size();j++){
            ItemComposant y = (ItemComposant) mailles.get(0).Icomposants.get(j);
            a=y.RenvoiComposant(y.getComposant(),frequence);
            m1[2][j] = a.z.multiplicationV2(new Impedance(-1,0));
            m1[j+4][j+1] = a.z.multiplicationV2(new Impedance(-1,0));
        }
        m1[2][1]=m1[2][1].multiplicationV2(new Impedance(-1,0));
        for(int k=0;k<3;k++){
            m1[k+4][k+4]=new Impedance(1,0);
        }

        //remplissage de m2 avec les coefficients droits de chaque équation
        m2[0][0]= new Impedance(amplitude,0);
    }
}