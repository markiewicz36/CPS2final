package Szumy;

public class SkokJednostkowy extends Szum{
    private final double skok;

    public SkokJednostkowy(double amplituda, double czas_poczatkowy, double czas_trwania, double skok) {
        super(amplituda, czas_poczatkowy, czas_trwania);
        this.skok = skok;
    }

    @Override
    public double obliczanie_sygnalu(double x) {
        if (Math.abs(skok - x) <= 0.00001) {
            return getAmplituda();
        }
        return 0;
    }
}
