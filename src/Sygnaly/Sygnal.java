package Sygnaly;

import Szumy.Szum;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Sygnal extends Szum {
    private double okres;

    public Sygnal(double amplituda, double okres, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, czas_poczatkowy, czas_trwania);
        this.okres = okres;
    }

    @Override
    public double obliczanie_sygnalu(double x){ return 1; }

    @Override
    public ArrayList<Point2D.Double> getAktualneDane() {
        return  wytnijPelenOkres();
    }

    public ArrayList<Point2D.Double> wytnijPelenOkres() {
        double iloscOkresow = (getCzas_trwania() / okres);
        double wszystkieOkresy = iloscOkresow * okres;

        // Przez filtr przechodza dalej elementy które spełniają warunek (zostaw x tylko są mniejsze niż wszystkieOkresy)
        //TODO Jak to czytać poprawnie
        return getData().stream().filter(t -> t.y <= wszystkieOkresy).collect(Collectors.toCollection(ArrayList::new));
    }

    public double getOkres() {
        return okres;
    }
}
