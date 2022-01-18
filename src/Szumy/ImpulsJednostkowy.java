package Szumy;

import java.util.Random;

//TODO Albo szum, sprawdzić działanie
public class ImpulsJednostkowy extends Szum {
    private final double prawdopodobienstwo;
    private final Random random = new Random();

    public ImpulsJednostkowy(double amplituda, double czas_poczatkowy, double czas_trwania, double prawdopodobienstwo) {
        super(amplituda, czas_poczatkowy, czas_trwania);
        this.prawdopodobienstwo = prawdopodobienstwo;
    }

    @Override
    public double obliczanie_sygnalu(double x) {
        if(random.nextDouble() < prawdopodobienstwo){
            return getAmplituda();
        } else {
            return 0.0;
        }
    }
}
