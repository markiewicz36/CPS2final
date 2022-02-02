package Sygnaly;

public class SygnalCos extends Sygnal {

    public SygnalCos(double amplituda, double okres, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
    }

    @Override
    public double obliczanie_sygnalu(double x){
        double tmp = x - 1;
        //System.out.println(getAmplituda() * Math.sin((2 * Math.PI / getOkres()) * (x - getCzas_poczatkowy())));
        return getAmplituda() * Math.sin((2 * Math.PI / getOkres()) * (tmp - getCzas_poczatkowy()));
    }
}
