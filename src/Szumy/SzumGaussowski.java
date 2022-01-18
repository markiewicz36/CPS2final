package Szumy;

import java.util.Random;

public class SzumGaussowski extends Szum{

    private Random random = new Random();
    public SzumGaussowski(double amplituda, double czas_poczatkowy, double czas_trwania) {
        super(amplituda, czas_poczatkowy, czas_trwania);
    }

    @Override
    public double obliczanie_sygnalu(double t){
        return random.nextGaussian();
    }
}
