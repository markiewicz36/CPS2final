package Szumy;

import Operacje.DaneHistogram;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Szum implements Serializable {

    private ArrayList<Point2D.Double> data = new ArrayList<>(); // Punkty
    private double amplituda;
    private double czas_poczatkowy;
    private double czas_trwania;
    //private double okres;
    private boolean czy_Dyskretny = false; // Sprawdzenie rodzaju sygnalu, do wygenerowania wykresu
    private double czestotliwosc = 0;
    private int probkowanie = 0;

    public int getProbkowanie() {
        return probkowanie;
    }

    public void setProbkowanie(int probkowanie) {
        this.probkowanie = probkowanie;
    }

    public Szum(){

    }

    public Szum(double amplituda, double czas_poczatkowy, double czas_trwania){
        this.amplituda = amplituda;
        this.czas_poczatkowy = czas_poczatkowy;
        this.czas_trwania = czas_trwania;
    }

    public void oblicz(double czestotliwosc){
        this.setCzestotliwosc(czestotliwosc);
        ArrayList<Point2D.Double> wartosci = new ArrayList<>();

        for(double i = czas_poczatkowy; i < czas_poczatkowy + czas_trwania; i += czestotliwosc){
            wartosci.add(new Point2D.Double(i, obliczanie_sygnalu(i)));
        }

        data = wartosci;
    }

    public double wartoscSrednia(){
        double tmp = 0;

        for (Point2D.Double t : getAktualneDane()) {
            tmp += t.y;
        }

        return tmp / getAktualneDane().size();
    }

    public double wartoscSredniaBezwzgledna(){
        double tmp = 0;

        for (Point2D.Double t : getAktualneDane()){
            tmp += Math.abs(t.y); // Wartość bezwzględna z y
        }

        return tmp / getAktualneDane().size();
    }

    public double mocSrednia(){
        double tmp = 0;

        for (Point2D.Double t : getAktualneDane()){
            tmp += (t.y * t.y);
        }

        return tmp / getAktualneDane().size();
    }

    public double wariancja(){
        double tmp = 0;
        double srednia = wartoscSrednia();

        for (Point2D.Double t : getAktualneDane()){
            double tmp2 = t.y - srednia;
            tmp += (tmp2 * tmp2);
        }

        return tmp / getAktualneDane().size();
    }

    public ArrayList<DaneHistogram> obliczHistogram(int zakres){
        ArrayList<DaneHistogram> daneHistogram = new ArrayList<>();

        Double min = getAktualneDane().stream().min(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y;
        Double max = getAktualneDane().stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y;
        Double krok = (max - min) / zakres;

        for (int i = 0; i < zakres; i++){
            final Double poczatek = min;
            final Double koniec = min + krok;
            Integer ilosc = Math.toIntExact(getAktualneDane().stream().filter(d -> d.y >= poczatek && d.y <= koniec).count());
            DaneHistogram dane = new DaneHistogram(min, min + krok, ilosc);
            daneHistogram.add(dane);
            min += krok;
        }

        return daneHistogram;
    }

    public double wartoscSkuteczna(){
        return Math.sqrt(mocSrednia());
    }

    public double obliczanie_sygnalu(double x){ return 1; }

    public ArrayList<Point2D.Double> getAktualneDane() { return getData(); }

    public ArrayList<Point2D.Double> getData() {
        return data;
    }

    public void setData(ArrayList<Point2D.Double> data) {
        this.data = data;
    }

    public double getAmplituda() {
        return amplituda;
    }

    public void setAmplituda(double amplituda) {
        this.amplituda = amplituda;
    }

    public double getCzas_poczatkowy() {
        return czas_poczatkowy;
    }

    public void setCzas_poczatkowy(double czas_poczatkowy) {
        this.czas_poczatkowy = czas_poczatkowy;
    }

    public double getCzas_trwania() {
        return czas_trwania;
    }

    public void setCzas_trwania(double czas_trwania) {
        this.czas_trwania = czas_trwania;
    }

    public boolean isCzy_Dyskretny() {
        return czy_Dyskretny;
    }

    public void setCzy_Dyskretny(boolean czy_Dyskretny) {
        this.czy_Dyskretny = czy_Dyskretny;
    }

    public double getCzestotliwosc() {
        return czestotliwosc;
    }

    public void setCzestotliwosc(double czestotliwosc) {
        this.czestotliwosc = czestotliwosc;
    }
}
