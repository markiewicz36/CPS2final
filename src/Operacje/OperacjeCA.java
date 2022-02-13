package Operacje;

import Sygnaly.SygnalDyskretny;
import Szumy.Szum;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class OperacjeCA {

    public OperacjeCA() {
    }


    public Szum ekstrapolacja(Szum szum, Szum przed){
        Szum szum1 = new Szum();
        ArrayList<Point2D.Double> probki = new ArrayList<>();

        int tmpx = 0;
        double currrent = 0;
        double y = 0;

        for(int i = 0; i < przed.getData().size(); i++){
            if(tmpx < szum.getData().size()) {
                double orginalX = przed.getData().get(i).x;
                double qaX = szum.getData().get(tmpx).x;



                if (Math.abs(orginalX - qaX) < 0.000001d) {

                    System.out.println("test" + i);
                    currrent = szum.getData().get(tmpx).y;
                    y = currrent;
                    tmpx++;
                }
            }
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = przed.getData().get(i).x;
            tmp.y = y;
            probki.add(tmp);
        }
        System.out.println(probki);

        szum1.setData(probki);
        return szum1;
    }


    public Szum interpolacjaSinc(Szum szum, int iloscSasiadow, Szum ostatniSzumPrzedProbkowaniem) {
        Szum szum1 = new Szum();

        int iloscProbek = ostatniSzumPrzedProbkowaniem.getData().size();
        ArrayList<Point2D.Double> probki = new ArrayList<>(iloscProbek);



        double czasTrwania = szum.getData().get(szum.getData().size()-1).getX() - szum.getData().get(0).getX();
        double step = czasTrwania / iloscProbek ;


        // wypelnienie wstepne z punktami na osi X
        for (int i = 0; i < iloscProbek; i++) {
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = step * i;
            tmp.y = value(tmp.x, iloscSasiadow, szum);
            probki.add(tmp);
        }



        szum1.setData(probki);
        return szum1;
    }


    public double value(double tmpX, int iloscSasiadow, Szum szum) {

        double start = szum.getData().get(0).getX();
        double koniec = szum.getData().get(szum.getData().size()-1).getX();
        int index = (int) Math.floor((tmpX - start) / koniec * szum.getData().size());

        int pierwszaProbka = index - iloscSasiadow;
        int ostatniaProbka = index + iloscSasiadow +1;

        if (pierwszaProbka < 0) {
            ostatniaProbka = ostatniaProbka - pierwszaProbka;
            pierwszaProbka = 0;
            if (ostatniaProbka > szum.getData().size()) {
                ostatniaProbka = szum.getData().size();
            }
        } else if (ostatniaProbka > szum.getData().size()) {
            pierwszaProbka = pierwszaProbka - (ostatniaProbka - szum.getData().size());
            ostatniaProbka = szum.getData().size();
            if (pierwszaProbka < 0) {
                pierwszaProbka = 0;
            }
        }

        double step = koniec / szum.getData().size();

        //sumując wartości wszystkich funkcji sinc dla każdego indeksu reprezentowanego przez próbkę, otrzymamy dokładnie wartości sygnału
        double sum = 0.0;
        for (int i = pierwszaProbka; i < ostatniaProbka; i++) {
            sum += szum.getData().get(i).getY() * sinc(tmpX / step - i);
        }
        return sum;
    }

    private double sinc(double x){
        if(Math.abs(x) < 0.00000000000001){
            return 1.0;
        }
        return Math.sin(Math.PI * x)/(Math.PI *x);
    }

}
