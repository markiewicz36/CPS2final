package Operacje;

public class DaneHistogram {

    private double start;
    private double koniec;
    private double ilosc;

    public DaneHistogram(double start, double koniec, double ilosc) {
        this.start = start;
        this.koniec = koniec;
        this.ilosc = ilosc;
    }

    public double getStart() {
        return start;
    }

    public double getKoniec() {
        return koniec;
    }

    public double getIlosc() {
        return ilosc;
    }

    @Override
    public String toString() {
        return "DaneHistogram{" +
                "start=" + start +
                ", koniec=" + koniec +
                ", ilosc=" + ilosc +
                '}';
    }
}
