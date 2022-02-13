package Operacje;

import Szumy.Szum;

import java.awt.geom.Point2D;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Operacje {

    public Operacje() {
    }

    public Szum operacje(Szum szum1, Szum szum2, int wybor) {
        ArrayList<Point2D.Double> probkiPierwsze = szum1.getData();
        ArrayList<Point2D.Double> probkiDrugie = szum2.getData();
        ArrayList<Point2D.Double> finalData = new ArrayList<>();

        switch (wybor){
            case 0:
                for (int i = 0; i < probkiPierwsze.size(); i++) {
                    Point2D.Double tmp = new Point2D.Double();
                    tmp.x = probkiPierwsze.get(i).x;
                    tmp.y = probkiPierwsze.get(i).y + probkiDrugie.get(i).y;
                    finalData.add(tmp);
                }
                break;
            case 1:
                for (int i = 0; i < probkiPierwsze.size(); i++) {
                    Point2D.Double tmp = new Point2D.Double();
                    tmp.x = probkiPierwsze.get(i).x;
                    tmp.y = probkiPierwsze.get(i).y - probkiDrugie.get(i).y;
                    finalData.add(tmp);
                }
                break;
            case 2:
                for (int i = 0; i < probkiPierwsze.size(); i++) {
                    Point2D.Double tmp = new Point2D.Double();
                    tmp.x = probkiPierwsze.get(i).x;
                    tmp.y = probkiPierwsze.get(i).y * probkiDrugie.get(i).y;
                    finalData.add(tmp);
                }
                break;
            case 3:
                for (int i = 0; i < probkiPierwsze.size(); i++) {
                    Point2D.Double tmp = new Point2D.Double();
                    tmp.x = probkiPierwsze.get(i).x;
                    if (probkiPierwsze.get(i).y == 0) {
                        tmp.y = 0;
                    } else {
                        tmp.y = probkiPierwsze.get(i).y / probkiDrugie.get(i).y;
                    }
                    finalData.add(tmp);
                }
                break;
            case 4:
                finalData=splot(szum1,szum2).getData();
                break;
            case 5:
                finalData=korelacja(szum1,szum2).getData();
                break;
            case 6:
                finalData=korelacjaZeSplotem(szum1,szum2).getData();
        }
        Double ampl = (finalData.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
        Szum finalnySygnal = new Szum(ampl, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());

        finalnySygnal.setData(finalData);
        finalnySygnal.setCzestotliwosc(szum1.getCzestotliwosc());
        return finalnySygnal;
    }


    public Szum korelacjaZeSplotem(Szum szum1, Szum szum2){
        if (szum1.getData().size() >= szum2.getData().size()) {
            ArrayList<Point2D.Double> tmp = szum2.getData();
            Collections.reverse(tmp);
            szum2.setData(tmp);
            return splot(szum1, szum2);
        }
        ArrayList<Point2D.Double> tmp = szum1.getData();
        Collections.reverse(tmp);
        szum1.setData(tmp);
        return splot(szum1, szum2);
    }



    public Szum korelacja(Szum szum1, Szum szum2) {
        Szum szum = new Szum();
        Szum pierwszy;
        Szum drugi;
        ArrayList<Point2D.Double> probki = new ArrayList<>();
        if (szum1.getData().size() >= szum2.getData().size()) {
            pierwszy = szum1;
            drugi = szum2;
        } else {
            pierwszy = szum2;
            drugi = szum1;
        }
        double step = (pierwszy.getData().get(pierwszy.getData().size() - 1).getX() - pierwszy.getData().get(0).getX()
                + drugi.getData().get(drugi.getData().size() - 1).getX() - drugi.getData().get(0).getX())
                / (pierwszy.getData().size() + drugi.getData().size());


        int iteracje = 0;

        for (int i = -(drugi.getData().size() - 1); i < pierwszy.getData().size(); i++) {
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = iteracje * step;
            iteracje++;
            tmp.y = obliczKorelacje(pierwszy, drugi, i);
            probki.add(tmp);
        }

        szum.setData(probki);
        //szum.setCzestotliwosc(100);

        return szum;
    }

    private double obliczKorelacje(Szum sygnal1, Szum sygnal2, int index){
        double wynik = 0.0;

        for (int i = 0; i < sygnal1.getData().size(); i++) {
            double secondY = 0.0;
            if (i - index < sygnal2.getData().size() && i - index >= 0){
                secondY = sygnal2.getData().get(i - index).getY();
            }
            wynik += sygnal1.getData().get(i).getY() * secondY;
        }

        return wynik;
    }


    public Szum splot(Szum szum1, Szum szum2) {
        Szum signal = new Szum();
        ArrayList<Point2D.Double> samples = new ArrayList<>();
        double step = (szum1.getData().get(szum1.getData().size() - 1).getX() - szum1.getData().get(0).getX()
                + szum2.getData().get(szum2.getData().size() - 1).getX() - szum2.getData().get(0).getX())
                / (szum1.getData().size() + szum2.getData().size());

        for (int i = 0; i < szum1.getData().size() + szum2.getData().size() - 1; i++) {
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = i * step;
            tmp.y = obliczSplot(szum1.getData(), szum2.getData(), i);
            samples.add(tmp);
        }
        signal.setData(samples);

        return signal;
    }



    private double obliczSplot(ArrayList<Point2D.Double> szum1,ArrayList<Point2D.Double> szum2, int index){
        Double result = 0.0;

        if (szum1.size() >= szum2.size()) {
            for (int i = 0; i < szum1.size(); i++) {
                double secondY = 0.0;
                if (index - i >= 0 && index - i < szum2.size())
                    secondY = szum2.get(index - i).getY();
                result += szum1.get(i).getY() * secondY;
            }
        } else {
            for (int i = 0; i < szum2.size(); i++) {
                double firstY = 0.0;
                if ((index - i) >= 0 && index - i < szum1.size())
                    firstY = szum1.get(index - i).getY();
                result += szum2.get(i).getY() * firstY;
            }
        }
        return result;
    }

    public Szum przesuniecieSygnalu(Szum szum1, double przesuniecie) {
        Szum signal = new Szum();
        ArrayList<Point2D.Double> samples = new ArrayList<>();

        for (int i = 0; i < szum1.getData().size(); i++) {
            Point2D.Double tmp = new Point2D.Double();
            tmp.x = szum1.getData().get(i).x - przesuniecie;
            tmp.y = szum1.getData().get(i).getY();
            samples.add(tmp);
        }
        signal.setData(samples);

        return signal;
    }
//
//    public Szum dodawanie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
//        ArrayList<Point2D.Double> dane1 = szum1.getData();
//        ArrayList<Point2D.Double> dane2 = szum2.getData();
//        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
//        if(dane1.size() != dane2.size()){
//            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
//        }
//        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
//            throw new ZlaCzestotliwosc("Inna czestotliwość!");
//        }
//        for(int i=0; i<dane1.size(); i++){
//            Point2D.Double tmp = new Point2D.Double();
//            tmp.x = dane1.get(i).x;
//            tmp.y = dane1.get(i).y + dane2.get(i).y;
//            dane_koniec.add(tmp);
//        }
//        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
//        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
//        finalny_sygnal.setData(dane_koniec);
//        return finalny_sygnal;
//    }
//
//    public Szum odejmowanie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
//        ArrayList<Point2D.Double> dane1 = szum1.getData();
//        ArrayList<Point2D.Double> dane2 = szum2.getData();
//        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
//        if(dane1.size() != dane2.size()){
//            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
//        }
//        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
//            throw new ZlaCzestotliwosc("Inna czestotliwość!");
//        }
//        for(int i=0; i<dane1.size(); i++){
//            Point2D.Double tmp = new Point2D.Double();
//            tmp.x = dane1.get(i).x;
//            tmp.y = dane1.get(i).y - dane2.get(i).y;
//            dane_koniec.add(tmp);
//        }
//        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
//        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
//        finalny_sygnal.setData(dane_koniec);
//        return finalny_sygnal;
//    }
//
//    public Szum mnozenie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
//        ArrayList<Point2D.Double> dane1 = szum1.getData();
//        ArrayList<Point2D.Double> dane2 = szum2.getData();
//        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
//        if(dane1.size() != dane2.size()){
//            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
//        }
//        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
//            throw new ZlaCzestotliwosc("Inna czestotliwość!");
//        }
//        for(int i=0; i<dane1.size(); i++){
//            Point2D.Double tmp = new Point2D.Double();
//            tmp.x = 0;
//            tmp.y = 1;
//            tmp.x = dane1.get(i).x;
//            if(dane1.get(i).y == 0){
//                tmp.y = 1 * dane2.get(i).y;
//            }
//            else if(dane2.get(i).y == 0){
//                tmp.y = 1 * dane1.get(i).y;
//            }
//            else {
//                tmp.y = dane1.get(i).y * dane2.get(i).y;
//            }
//
//            //System.out.println("Syngał: " + dane1.get(i).y + " * " + dane2.get(i).y + " = " + dane1.get(i).y * dane2.get(i).y);
//            dane_koniec.add(tmp);
//        }
//        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
//        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
//        finalny_sygnal.setData(dane_koniec);
//        return finalny_sygnal;
//    }
//
//    public Szum dzielenie(Szum szum1, Szum szum2) throws ZlyRozmiar, ZlaCzestotliwosc {
//        ArrayList<Point2D.Double> dane1 = szum1.getData();
//        ArrayList<Point2D.Double> dane2 = szum2.getData();
//        ArrayList<Point2D.Double> dane_koniec = new ArrayList<>();
//        if(dane1.size() != dane2.size()){
//            throw new ZlyRozmiar("Rozmar sygnałów jest różny!");
//        }
//        if(dane1.get(1).x - dane1.get(0).x != dane2.get(1).x - dane2.get(0).x){
//            throw new ZlaCzestotliwosc("Inna czestotliwość!");
//        }
//        for(int i=0; i<dane1.size(); i++){
//            Point2D.Double tmp = new Point2D.Double();
//            tmp.x = dane1.get(i).x;
//            if(dane1.get(i).y == 0){
//                tmp.y = 0;
//                System.out.println("*Syngał: " + dane1.get(i).y + " * " + dane2.get(i).y + " = " + dane1.get(i).y / dane2.get(i).y);
//            }
//            else {
//                System.out.println("Syngał: " + dane1.get(i).y + " * " + dane2.get(i).y + " = " + dane1.get(i).y / dane2.get(i).y);
//                tmp.y = dane1.get(i).y / dane2.get(i).y;
//            }
//            dane_koniec.add(tmp);
//        }
//        System.out.println("Szum1: " + dane1.size());
//        System.out.println("Szum: " + dane_koniec.size());
//        Double amplituda = (dane_koniec.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
//        Szum finalny_sygnal = new Szum(amplituda, szum1.getCzas_poczatkowy(), szum1.getCzas_trwania());
//        finalny_sygnal.setData(dane_koniec);
//        return finalny_sygnal;
//    }
//
//    public Szum oblicz(Szum szum1, Szum szum2, int wybor){
//        ArrayList<Point2D.Double> finalData = new ArrayList<>();
//
//        switch (wybor){
//            case 0:
//                finalData = splot(szum1, szum2).getData();
//                break;
//            case 1:
//                finalData = korelacja(szum1, szum2).getData();
//                break;
//            case 2:
//                finalData = korelacjaZeSplotem(szum1, szum2).getData();
//                break;
//        }
//
//        Double ampl = (finalData.stream().max(Comparator.comparing(Point2D.Double::getY)).orElseThrow(NoSuchElementException::new).y);
//
//        Szum finalNoise = new Szum(ampl, szum1.getCzas_trwania(), szum2.getCzas_trwania());
//        finalNoise.setData(finalData);
//        finalNoise.setCzestotliwosc(szum1.getCzestotliwosc());
//
//        return finalNoise;
//    }

//    public Szum korelacja1(Szum szum1, Szum szum2){
//        ArrayList<Point2D.Double> probki = new ArrayList<>();
//        Szum szum = new Szum();
//        int maxPierwszy = szum1.getData().size()-1;
//        int maxDrugi = szum2.getData().size()-1;
//
//        double sampling = Math.abs(szum1.getData().get(1).getX() - szum1.getData().get(0).getX());
//
//        int maxKorelacja = maxPierwszy + maxDrugi;
//
//
//        for (int i = 0; i <= maxKorelacja; i++){
//            double h = 0.0;
//            double t;
//            if(i <= maxPierwszy){
//                t = szum1.getData().get(i).getX();
//            }
//            else {
//                t = szum1.getData().get(maxPierwszy).getX() + ((i - maxPierwszy) * sampling);
//            }
//            int drugiIndex = maxDrugi;
//            while (drugiIndex >= 0){
//                int pierwszyIndex = i - maxDrugi + drugiIndex;
//                if(pierwszyIndex <= maxPierwszy && pierwszyIndex >=0){
//                    h += getYbyX(szum1, szum1.getData().get(pierwszyIndex).getX()) * getYbyX(szum2, szum2.getData().get(drugiIndex).getX());
//                }
//                drugiIndex--;
//            }
//            Point2D.Double tmp = new Point2D.Double();
//            tmp.x = t;
//            tmp.y = h;
//            probki.add(tmp);
//        }
//        szum.setData(probki);
//        double najwyzszy = 0;
//        double najwyzszyX = 0;
//
//        for (int i = 0; i < szum.getData().size(); i++) {
//            if(szum.getData().get(i).y > najwyzszy){
//                najwyzszy = szum.getData().get(i).y;
//                najwyzszyX = szum.getData().get(i).x;
//            }
//        }
//        double tmp = (szum.getCzas_trwania()/2) - najwyzszyX;
//        System.out.println(tmp);
//
//
//        return szum;
//    }
//
//    double getYbyX (Szum szum1 ,double x){
//        for(int i = 0; i < szum1.getData().size(); i++){
//            if(Math.abs(szum1.getData().get(i).getX() - x) < 0.00000001d){
//                return szum1.getData().get(i).getY();
//            }
//        }
//        return 0;
//    }

}
