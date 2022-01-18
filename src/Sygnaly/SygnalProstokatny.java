package Sygnaly;

public class SygnalProstokatny extends Sygnal{
    private double wypelnienie;

    public SygnalProstokatny(double amplituda, double okres, double czas_poczatkowy, double czas_trwania, double wypelnienie) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
        this.wypelnienie = wypelnienie;
    }

    @Override
    public double obliczanie_sygnalu(double t){
        if (((t - getCzas_poczatkowy()) / getOkres()) - Math.floor((t - getCzas_poczatkowy()) / getOkres()) < wypelnienie){
            return getAmplituda();
        }
        else {
            return 0.0;
        }
    }
}
