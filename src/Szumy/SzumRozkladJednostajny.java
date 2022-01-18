package Szumy;

import java.util.Random;

public class SzumRozkladJednostajny extends Szum {

    private final Random random = new Random();


    public SzumRozkladJednostajny(double amplituda, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, czas_poczatkowy, czas_trwania);
    }

    @Override
    public double obliczanie_sygnalu(double t) {
        return -getAmplituda() + (2 * getAmplituda()) * random.nextDouble();
    }
}
