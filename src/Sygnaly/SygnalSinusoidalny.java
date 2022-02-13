package Sygnaly;

public class SygnalSinusoidalny extends Sygnal {

    public SygnalSinusoidalny(double amplituda, double okres, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
    }

    @Override
    public double obliczanie_sygnalu(double x){
        //System.out.println(getAmplituda() * Math.sin((2 * Math.PI / getOkres()) * (x - getCzas_poczatkowy())));
        //return getAmplituda() * Math.sin(((2 * Math.PI / getOkres()) * ((x - getCzas_poczatkowy())-przesuniecie))) ;
        return getAmplituda() * Math.sin(((2 * Math.PI / getOkres()) * (x - getCzas_poczatkowy()))) ;
    }
}
