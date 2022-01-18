package Operacje;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ObliczBledy {

    public ObliczBledy() {
    }

    public double obliczMSE(ArrayList<Point2D.Double> orginalnySygnal, ArrayList<Point2D.Double> rekonstrukcja) throws ZlyRozmiar {
        if (orginalnySygnal.size() == rekonstrukcja.size()){
            double sum = 0;

            for (int i = 0; i < orginalnySygnal.size(); i++){
                sum += (orginalnySygnal.get(i).y - rekonstrukcja.get(i).y) * (orginalnySygnal.get(i).y - rekonstrukcja.get(i).y);
            }
            return sum/orginalnySygnal.size();
        }
        throw new ZlyRozmiar("Zly rozmar");
    }

    public double obliczSNR(ArrayList<Point2D.Double> orginalnySygnal, ArrayList<Point2D.Double> rekonstrukcja) throws ZlyRozmiar {
        double MSE = obliczMSE(orginalnySygnal,rekonstrukcja)*orginalnySygnal.size();
        double sum = orginalnySygnal.stream().mapToDouble(v -> v.y * v.y).sum();

        return 10*Math.log10(sum/MSE);
    }

    public double obliczPSNR(ArrayList<Point2D.Double> orginalnySygnal, ArrayList<Point2D.Double> rekonstrukcja) throws ZlyRozmiar {
        double MSE = obliczMSE(orginalnySygnal,rekonstrukcja);
        double max = orginalnySygnal.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y;

        return 10*Math.log10(max/MSE);
    }

    public double obliczMD(ArrayList<Point2D.Double> orginalnySygnal, ArrayList<Point2D.Double> rekonstrukcja) throws ZlyRozmiar {
        ArrayList<Double> roznica = new ArrayList<Double>();
        if (orginalnySygnal.size() == rekonstrukcja.size()) {
            for (int i = 0; i < orginalnySygnal.size(); i++) {
                roznica.add(Math.abs(orginalnySygnal.get(i).y - rekonstrukcja.get(i).y));
            }
            return roznica.stream().max(Double::compare).orElseThrow(NoSuchElementException::new);

        }
        throw new ZlyRozmiar("original and restored signals are not same size");
    }

    public double obliczENOB(ArrayList<Point2D.Double> original , ArrayList<Point2D.Double> restored) throws ZlyRozmiar {
        return (obliczSNR(original,restored)-1.76)/6.02;
    }
}
