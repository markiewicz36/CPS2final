package Operacje;

import Szumy.DFTSygnal;
import Szumy.Szum;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

public class DFT {
    public static DFTSygnal oblicz(Szum szum){
        DFTSygnal dftSygnal = new DFTSygnal(szum);
        List<Complex> complexList= new ArrayList<>();

        //czas trwania jedna setna 0.1

        //Rozmiar sygnału
        int N = dftSygnal.getPynktyY().size();

        for(int m = 0; m < N; m++){
            //Punkt z liczbą zespoloną a + ib
            Complex punkt = new Complex(0, 0);
            for(int j = 0; j < N; j++){

                //Obliczenie jądra transformacji
                int tmp = -2 * m * j;
                Complex w = new Complex(0.0, ((Math.PI * tmp) / (double) N));

                //Pomnożenie sygnału 'orginalnego' z oblczoną próbką
                punkt = punkt.add(dftSygnal.getPynktyY().get(j).multiply(w.exp()));

            }
            complexList.add(punkt);
            //System.out.println(punkt);
        }
        dftSygnal.setPynktyY(complexList);
        return dftSygnal;
    }
}
