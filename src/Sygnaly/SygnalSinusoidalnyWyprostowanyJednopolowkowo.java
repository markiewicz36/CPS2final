package Sygnaly;

public class SygnalSinusoidalnyWyprostowanyJednopolowkowo extends Sygnal {
    public SygnalSinusoidalnyWyprostowanyJednopolowkowo(double amplituda, double okres, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
    }

    @Override
    public double obliczanie_sygnalu(double t){
        return 0.5 * getAmplituda() * (
                           Math.sin((2 * Math.PI / getOkres()) * (t - getCzas_poczatkowy()))
                + Math.abs(Math.sin((2 * Math.PI / getOkres()) * (t - getCzas_poczatkowy())))
                );
    }
}
