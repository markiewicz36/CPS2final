package Operacje;

import Sygnaly.Sygnal;
import Sygnaly.SygnalDyskretny;
import Szumy.Szum;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class OperacjeAC {
    private double mse;
    private double snr;
    private double psnr;
    private double md;

    public OperacjeAC() {
    }

    public SygnalDyskretny probkowanie(double czestotliwosc, Szum sygnal){
        SygnalDyskretny sygnalDyskretny = new SygnalDyskretny();
        ArrayList<Point2D.Double> probki = new ArrayList<>();
        sygnalDyskretny.setCzestotliwosc(czestotliwosc);


        int krok = (int) ((int) (1/sygnal.getCzestotliwosc())*czestotliwosc);

        for (int i = 0; i < sygnal.getData().size(); i+= krok){
            probki.add(sygnal.getData().get(i));
        }
        sygnalDyskretny.setData(probki); // stare, przywrocic jak cos
        return sygnalDyskretny;
    }

    public SygnalDyskretny kwantyzacja(Szum szum, int poziomy){
        SygnalDyskretny sygnalDyskretny = new SygnalDyskretny();
        ArrayList<Point2D.Double> punkty = new ArrayList<>();
        ArrayList<Double> poziomyArray = obliczPoziomy(szum, poziomy);

        szum.getData().forEach(v -> {
            double najblizszy = 100000;
            int index = 0;

            for (int i = 0; i < poziomyArray.size(); i++) {
                if (Math.abs(v.y - poziomyArray.get(i)) < najblizszy) {
                    najblizszy = Math.abs(v.y - poziomyArray.get(i));
                    index = i;
                }
            }
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = v.x;
            tmp.y = poziomyArray.get(index);
            punkty.add(tmp);
        });
        sygnalDyskretny.setData(punkty);
        return sygnalDyskretny;
    }

    public ArrayList<Double> obliczPoziomy(Szum szum, int poziomy){
        ArrayList<Double> wartosci = new ArrayList<>();
        double step = (znajdzMax(szum) - znajdzMin(szum)) / (poziomy - 1);
        for (int i = 0; i < poziomy; i++) {
            wartosci.add(step * i + znajdzMin(szum));
        }
        return wartosci;
    }

    private double znajdzMax(Szum szum) {
        double max = Double.MIN_VALUE;
        for (Point2D.Double value : szum.getData()) {
            if (Math.round(value.y) > max) max = value.y;
        }
        return max;
    }

    private double znajdzMin(Szum szum) {
        double min = Double.MAX_VALUE;
        for (Point2D.Double value : szum.getData()) {
            if (Math.round(value.y) < min) min = value.y;
        }
        return min;
    }
}
