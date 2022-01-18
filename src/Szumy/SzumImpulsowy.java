package Szumy;

public class SzumImpulsowy extends Szum{
    private final double skok;

    public SzumImpulsowy(double amplituda, double czas_poczatkowy, double czas_trwania, double skok) {
        super(amplituda, czas_poczatkowy, czas_trwania);
        this.skok = skok;
    }

    @Override
    public double obliczanie_sygnalu(double x) {
        if (x < skok){
            return 0;
        }
        if (x == skok){
            return getAmplituda() / 2;
        }
        return getAmplituda();
    }
}
