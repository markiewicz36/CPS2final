package Sygnaly;

public class SygnalSinusoidalnyWyprostowanyDwupolowkowo extends Sygnal {
    public SygnalSinusoidalnyWyprostowanyDwupolowkowo(double amplituda, double okres, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, okres, czas_poczatkowy, czas_trwania);
    }

    @Override
    public double obliczanie_sygnalu(double t){
        return getAmplituda() * Math.abs(Math.sin((2 * Math.PI / getOkres()) * (t - getCzas_poczatkowy())));
    }
}
