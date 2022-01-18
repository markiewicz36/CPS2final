package Sygnaly;

public class SygnalTrojkatny extends Sygnal {
    private final double wypelnienie;

    public SygnalTrojkatny(double amplituda, double okres, double czas_poczatkowy, double czas_trwania, double wypelnienie) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
        this.wypelnienie = wypelnienie;
    }

    @Override
    public double obliczanie_sygnalu(double t){
        double tmp = ((t - getCzas_poczatkowy()) / getOkres()) - Math.floor((t - getCzas_poczatkowy()) / getOkres());
        if (tmp < wypelnienie){
            return tmp / wypelnienie * getAmplituda();
        } else {
            return (1 - (tmp - wypelnienie) / (1 - wypelnienie)) * getAmplituda();
        }
    }
}
