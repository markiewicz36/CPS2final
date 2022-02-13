package Sygnaly;

public class SygnalCos extends Sygnal {
    double przesuniecie = 0;

    public SygnalCos(double amplituda, double okres, double czas_poczatkowy, double czas_trwania, double przesuniecie) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
        this.przesuniecie = przesuniecie;
    }

    @Override
    public double obliczanie_sygnalu(double x){
        double tmp = x - 1;
        //System.out.println(getAmplituda() * Math.sin((2 * Math.PI / getOkres()) * (x - getCzas_poczatkowy())));
        //return getAmplituda() * Math.sin(((2 * Math.PI / getOkres()) * ((x - getCzas_poczatkowy())-przesuniecie))) ;
        return getAmplituda() * Math.cos(((2 * Math.PI / getOkres()) * ((x - getCzas_poczatkowy())))) ;
    }
}
