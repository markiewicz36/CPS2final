package Szumy;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.complex.Complex;

public class DFTSygnal extends Szum{
    private List<Double> punktyX;
    private List<Complex> pynktyY;

    public DFTSygnal(Szum szum){
        super(szum.getAmplituda(), szum.getCzas_poczatkowy(), szum.getCzas_trwania());
        this.setData(szum.getData());
        this.setCzestotliwosc(szum.getCzestotliwosc());
        this.zmienNaLiczbyZespolone();
    }

    private void zmienNaLiczbyZespolone() {
        punktyX = this.getData().stream().map(punkt-> punkt.x).collect(Collectors.toList());
        pynktyY = this.getData().stream().map(punkt-> new Complex(punkt.y, 0)).collect(Collectors.toList());
        //System.out.println("x: " + punktyX);
        //System.out.println("y: " + pynktyY);
    }

    public void setPynktyY(List <Complex> punktyY){
        this.pynktyY = punktyY;
    }

    public List<Complex> getPynktyY() {
        return pynktyY;
    }
}
