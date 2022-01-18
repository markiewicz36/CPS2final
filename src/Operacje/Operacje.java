package Operacje;

import Szumy.Szum;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Operacje {

    public Operacje() {
    }

    public Szum dodawanie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
        ArrayList<Point2D.Double> dane1 = szum1.getData();
        ArrayList<Point2D.Double> dane2 = szum2.getData();
        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
        if(dane1.size() != dane2.size()){
            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
        }
        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
            throw new ZlaCzestotliwosc("Inna czestotliwość!");
        }
        for(int i=0; i<dane1.size(); i++){
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = dane1.get(i).x;
            tmp.y = dane1.get(i).y + dane2.get(i).y;
            dane_koniec.add(tmp);
        }
        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
        finalny_sygnal.setData(dane_koniec);
        return finalny_sygnal;
    }

    public Szum odejmowanie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
        ArrayList<Point2D.Double> dane1 = szum1.getData();
        ArrayList<Point2D.Double> dane2 = szum2.getData();
        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
        if(dane1.size() != dane2.size()){
            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
        }
        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
            throw new ZlaCzestotliwosc("Inna czestotliwość!");
        }
        for(int i=0; i<dane1.size(); i++){
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = dane1.get(i).x;
            tmp.y = dane1.get(i).y - dane2.get(i).y;
            dane_koniec.add(tmp);
        }
        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
        finalny_sygnal.setData(dane_koniec);
        return finalny_sygnal;
    }

    public Szum mnozenie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
        ArrayList<Point2D.Double> dane1 = szum1.getData();
        ArrayList<Point2D.Double> dane2 = szum2.getData();
        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
        if(dane1.size() != dane2.size()){
            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
        }
        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
            throw new ZlaCzestotliwosc("Inna czestotliwość!");
        }
        for(int i=0; i<dane1.size(); i++){
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = 0;
            tmp.y = 1;
            tmp.x = dane1.get(i).x;
            if(dane1.get(i).y == 0){
                tmp.y = 1 * dane2.get(i).y;
            }
            else if(dane2.get(i).y == 0){
                tmp.y = 1 * dane1.get(i).y;
            }
            else {
                tmp.y = dane1.get(i).y * dane2.get(i).y;
            }

            //System.out.println("Syngał: " + dane1.get(i).y + " * " + dane2.get(i).y + " = " + dane1.get(i).y * dane2.get(i).y);
            dane_koniec.add(tmp);
        }
        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
        finalny_sygnal.setData(dane_koniec);
        return finalny_sygnal;
    }

    public Szum dzielenie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
        ArrayList<Point2D.Double> dane1 = szum1.getData();
        ArrayList<Point2D.Double> dane2 = szum2.getData();
        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
        if(dane1.size() != dane2.size()){
            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
        }
        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
            throw new ZlaCzestotliwosc("Inna czestotliwość!");
        }
        for(int i=0; i<dane1.size(); i++){
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = dane1.get(i).x;
            if(dane1.get(i).y == 0){
                tmp.y = 0;
                System.out.println("*Syngał: " + dane1.get(i).y + " * " + dane2.get(i).y + " = " + dane1.get(i).y / dane2.get(i).y);
            }
            else {
                System.out.println("Syngał: " + dane1.get(i).y + " * " + dane2.get(i).y + " = " + dane1.get(i).y / dane2.get(i).y);
                tmp.y = dane1.get(i).y / dane2.get(i).y;
            }
            dane_koniec.add(tmp);
        }
        System.out.println("Szum1: " + dane1.size());
        System.out.println("Szum: " + dane_koniec.size());
        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
        finalny_sygnal.setData(dane_koniec);
        return finalny_sygnal;
    }
}
