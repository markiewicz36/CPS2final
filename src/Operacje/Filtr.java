package Operacje;

import Szumy.Szum;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Filtr {

    public Szum filtrDolnopasmowy(Szum szum, int M, double fo, boolean czyHanning){
        Szum szum1 = new Szum();
        ArrayList<Point2D.Double> probki = new ArrayList<>();

        double K = Math.abs((1/szum.getCzestotliwosc())/fo);

        for(int i=0;i<M;i++){
            Point2D.Double tmp = new Point2D.Double();
            tmp.x=i;
            tmp.y=h(K,i,M)*(czyHanning? 1.0: windowHamming(i,M));
            probki.add(tmp);
        }

        szum1.setData(probki);
        Operacje o = new Operacje();
        return o.splot(szum,szum1);
    }

    public Szum filtrGornopasmowy(Szum signal,int M, double fo){
        Szum noise = new Szum();
        ArrayList<Point2D.Double> samples = new ArrayList<>();
        double K = Math.abs((1/signal.getCzestotliwosc())/fo);

        for(int i=0;i<M;i++){
            Point2D.Double tmp = new Point2D.Double();
            tmp.x=i;
            tmp.y=h(K,i,M)*Math.pow((-1), i);
            samples.add(tmp);
        }
        noise.setData(samples);
        Operacje o = new Operacje();
        return o.splot(signal,noise);
    }

//    public Szum filtrGornopasmowy(Szum szum, int M, double fo, boolean czyHanning){
//        Szum szum1 = new Szum();
//        ArrayList<Point2D.Double> probki = new ArrayList<>();
//
//        double K = Math.abs((1/szum.getCzestotliwosc())/fo);
//
//        double sign = 1.0;
//
//        for(int i=0;i<M;i++){
//            Point2D.Double tmp = new Point2D.Double();
//            tmp.x=i;
//            double temp = sign * highFilter(K, i, M) *(czyHanning? 1.0: windowHamming(i,M));
//            tmp.y= temp;
//            probki.add(tmp);
//            if(i == (M-1)/2 && temp > 0){
//                sign = -1.0;
//            }
//        }
//
//        szum1.setData(probki);
//        Operacje o = new Operacje();
//        return o.splot(szum,szum1);
//    }

    private double highFilter (double K, int n, int M) {
        if (n % 2==0 || n==0) return h(K,n,M);
        else return -1 * h(K,n,M);
    }

    private double h(double K,int n ,int M){
        int mid = (M-1)/2;
        double diff = (double)n-(double)mid;
        if(n==mid) return 2.0/K;

        return Math.sin((2*Math.PI*diff)/K)/(Math.PI*diff);
    }

    private double windowHamming(double n, int M){
        return 0.5 - 0.5 * Math.cos(2.0 * Math.PI * n / M);
    }

}
