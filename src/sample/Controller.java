package sample;

import Operacje.DaneHistogram;
import Operacje.Operacje;
import Sygnaly.*;
import Szumy.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import Operacje.*;
import javafx.stage.FileChooser;

import javax.swing.plaf.FileChooserUI;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Controller {

    @FXML
    private BarChart<String, Number> barChart1;

    @FXML
    private Button buttonGeneruj;

    @FXML
    private BorderPane chartsPane;

    @FXML
    private Pane exampleChartPane;

    @FXML
    private LineChart<Number, Number> wykres;

    @FXML
    private TextField wpiszAmplituda;


    @FXML
    private CheckBox czyZaznaczonyHanning;

    @FXML
    private TextField wpiszCzasKoncowy;

    @FXML
    private TextField wpiszCzasPoczatkowy;

    @FXML
    private TextField wpiszInterpolacje;

    @FXML
    private TextField wpiszCzasSkoku;

    @FXML
    private TextField wpiszOkres;

    @FXML
    private TextField wpiszWartoscM;

    @FXML
    private TextField wpiszWartoscFO;

    @FXML
    private TextField wpiszPrawdopodobienstwo;

    @FXML
    private TextField wpiszWspolczynnik;

    @FXML
    private TextField wpiszWartoscSinc;

    @FXML
    private TextField wpiszCzestotliwosc;

    @FXML
    private TextField wpiszPrzesuniecie;

    @FXML
    private ComboBox wybierzSygnal;

    @FXML
    private TextField wypiszMocSrednia;

    @FXML
    private TextField wypiszWariancja;

    @FXML
    private TextField wypiszWartoscSkuteczna;

    @FXML
    private TextField wypiszWartoscSrednia;

    @FXML
    private TextField wypiszWartoscSredniaBezwzgledna;

    @FXML
    private TextField wpiszCzestotliwoscProbkowania;

    @FXML
    private TextField wpiszPoziomKwantyzacji;

    @FXML
    private TextField wypuszPSNR;

    @FXML
    private TextField wypiszSNR;

    @FXML
    private TextField wypiszENOB;

    @FXML
    private TextField wypiszMD;

    @FXML
    private TextField wypiszMSE;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ComboBox wybierzDrugiSygnal;

    @FXML
    private ComboBox wybierzPierwszySygnal;

    @FXML
    private ComboBox wybierzWyswietlanySygnal;

    @FXML
    private Button buttonDodaj;

    @FXML
    private Button buttonInterpolacja;

    @FXML
    private Button buttonDzielenie;

    @FXML
    private Button buttonMnozenie;

    @FXML
    private Button buttonOdejmij;

    @FXML
    private Button buttonWczytajSygnal;

    @FXML
    private Button buttonZapiszSygnal;

    private Integer wybranySygnal;
    private ArrayList<Szum> wygenerowaneSzumy = new ArrayList<>();

    private OperacjePliki operacjePliki = new OperacjePliki();

    private Szum aktualnySzum;
    private Szum ostatniSzumPrzedProbkowaniem;


    public void initialize() {
        wybierzSygnal.getItems().addAll(
                "(S01) Szum o rozkladzie jednostajnym",
                "(S02) Szum gaussowski",
                "(S03) Sygnal sinusoidalny",
                "(S04) Sygnal sinusoidalny wyprostowany jednopolowkowo",
                "(S05) Sygnal sinusoidalny wyprostowany dwupolowkowo",
                "(S06) Sygnal prostokatny",
                "(S07) Sygnal prostokatny symetryczny",
                "(S08) Sygnal trojkatny",
                "(S09) Skok jednostkowy",
                "(S10) Impuls jednostkowy",
                "(S11) Szum impulsowy"
        );

        wpiszAmplituda.setDisable(true);
        wpiszCzasPoczatkowy.setDisable(true);
        wpiszCzasKoncowy.setDisable(true);
        wpiszOkres.setDisable(true);
        wpiszCzasSkoku.setDisable(true);
        wpiszPrawdopodobienstwo.setDisable(true);
        wpiszWspolczynnik.setDisable(true);
        wpiszCzestotliwosc.setDisable(true);//true
    }

    @FXML
    void generujSygnal(ActionEvent event) {

        wykres.getStylesheets().clear();

        if (wybierzSygnal.getSelectionModel().getSelectedItem() != null) {
            Szum szum = new Szum(0, 0 ,0);

            switch (wybranySygnal){
                case 1 -> szum = new SzumRozkladJednostajny(Double.parseDouble(wpiszAmplituda.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()));
                case 2 -> szum = new SzumGaussowski(Double.parseDouble(wpiszAmplituda.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()));
                case 3 -> szum = new SygnalSinusoidalny(Double.parseDouble(wpiszAmplituda.getText()),
                        1 / Double.parseDouble(wpiszOkres.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()));
                case 4 -> szum = new SygnalCos(Double.parseDouble(wpiszAmplituda.getText()),
                        1 / Double.parseDouble(wpiszOkres.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()),
                        Double.parseDouble(wpiszPrzesuniecie.getText()));
                case 5 -> szum = new SygnalSinusoidalnyWyprostowanyDwupolowkowo(Double.parseDouble(wpiszAmplituda.getText()),
                        1 / Double.parseDouble(wpiszOkres.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()));
                case 6 -> szum = new SygnalProstokatny(Double.parseDouble(wpiszAmplituda.getText()),
                        1 / Double.parseDouble(wpiszOkres.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()),
                        Double.parseDouble(wpiszWspolczynnik.getText()));
                case 7 -> {
                    szum = new SygnalProstokatnySymetryczny(Double.parseDouble(wpiszAmplituda.getText()),
                            1 / Double.parseDouble(wpiszOkres.getText()),
                            Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                            Double.parseDouble(wpiszCzasKoncowy.getText()),
                            Double.parseDouble(wpiszWspolczynnik.getText()));
                    ;
                }
                case 8 -> {
                    szum = new SygnalTrojkatny(Double.parseDouble(wpiszAmplituda.getText()),
                            1 / Double.parseDouble(wpiszOkres.getText()),
                            Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                            Double.parseDouble(wpiszCzasKoncowy.getText()),
                            Double.parseDouble(wpiszWspolczynnik.getText()));; }
                case 9 -> szum = new SkokJednostkowy(Double.parseDouble(wpiszAmplituda.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()),
                        Double.parseDouble(wpiszCzasSkoku.getText()));
                case 10 -> szum = new ImpulsJednostkowy(Double.parseDouble(wpiszAmplituda.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()),
                        Double.parseDouble(wpiszCzasSkoku.getText()));
                case 11 -> szum = new SzumImpulsowy(Double.parseDouble(wpiszAmplituda.getText()),
                        Double.parseDouble(wpiszCzasPoczatkowy.getText()),
                        Double.parseDouble(wpiszCzasKoncowy.getText()),
                        Double.parseDouble(wpiszPrawdopodobienstwo.getText()));
            }

            double czestotliwosc = 0;
            if(wpiszCzestotliwosc.getText().isEmpty()){
                czestotliwosc = 100;
            } else {
                czestotliwosc = Double.parseDouble(wpiszCzestotliwosc.getText());
            }
            szum.oblicz(1 / czestotliwosc);
            aktualnySzum = szum;
            //Dodać do listy szumów
            dodajSygnalDoListy(szum, szum.getClass().getSimpleName());
            wykres.getData().clear();
            wykres.setCreateSymbols(false);
            wykres.getStylesheets().add("GlobalChart.css");
            generujWykres();
            generujHistogram();

            wypiszMSE.setText("");
            wypiszSNR.setText("");
            wypuszPSNR.setText("");
            wypiszMD.setText("");
            wypiszENOB.setText("");
        }
    }

    public void generujWykres(){
        XYChart.Series<Number, Number> seria = new XYChart.Series<>();
        aktualnySzum.getData().forEach(v -> seria.getData().add(new XYChart.Data<>(v.x, v.y)));
        wykres.setAnimated(false);

        //wykres.getData().clear();
        wykres.layout();
        wykres.getData().add(seria);
    }

    public void generujHistogram(){
        XYChart.Series<String, Number> seria = new XYChart.Series();
        ArrayList<DaneHistogram> histogram = aktualnySzum.obliczHistogram(20);
        wypiszWartoscSrednia.setText(aktualnySzum.wartoscSrednia() + "");
        wypiszMocSrednia.setText(aktualnySzum.mocSrednia() + "");
        wypiszWariancja.setText(aktualnySzum.wariancja() + "");
        wypiszWartoscSkuteczna.setText(aktualnySzum.wartoscSkuteczna() + "");
        wypiszWartoscSredniaBezwzgledna.setText(aktualnySzum.wartoscSredniaBezwzgledna() + "");
        histogram.forEach(v -> seria.getData().add(new XYChart.Data((Math.round(v.getStart() * 100) / 100.0) + " to " + (Math.round(v.getKoniec() * 100) / 100.0), v.getIlosc())));
        barChart1.getXAxis().setAnimated(false);
        barChart1.getData().clear();
        barChart1.layout();
        barChart1.getData().add(seria);
    }

    @FXML
    void wybierzSygnalListener(ActionEvent event) {

        wybranySygnal = wybierzSygnal.getSelectionModel().getSelectedIndex() + 1;

        switch (wybranySygnal){
            case 1:
            case 2: {
                wpiszAmplituda.setDisable(false);
                wpiszOkres.setDisable(true); //true
                wpiszCzasPoczatkowy.setDisable(false);
                wpiszCzasKoncowy.setDisable(false);
                wpiszWspolczynnik.setDisable(true);     //true
                wpiszPrawdopodobienstwo.setDisable(true); //true
                wpiszCzasSkoku.setDisable(true);//true
                wpiszCzestotliwosc.setDisable(false);//true
                break;
            }
            case 3:
            case 4:
            case 5: {
                wpiszAmplituda.setDisable(false);
                wpiszOkres.setDisable(false);
                wpiszCzasPoczatkowy.setDisable(false);
                wpiszCzasKoncowy.setDisable(false);
                wpiszWspolczynnik.setDisable(true);
                wpiszCzasSkoku.setDisable(true);
                wpiszPrawdopodobienstwo.setDisable(true);
                wpiszCzestotliwosc.setDisable(false);//true
                break;
            }
            case 6:
            case 7:
            case 8: {
                wpiszAmplituda.setDisable(false);
                wpiszOkres.setDisable(false);
                wpiszCzasPoczatkowy.setDisable(false);
                wpiszCzasKoncowy.setDisable(false);
                wpiszWspolczynnik.setDisable(false);
                wpiszCzasSkoku.setDisable(true);
                wpiszPrawdopodobienstwo.setDisable(true);
                wpiszCzestotliwosc.setDisable(false);//true
                break;
            }
            case 9:
            case 10: {
                wpiszAmplituda.setDisable(false);
                wpiszOkres.setDisable(true);
                wpiszCzasPoczatkowy.setDisable(false);
                wpiszCzasKoncowy.setDisable(false);
                wpiszWspolczynnik.setDisable(true);
                wpiszCzasSkoku.setDisable(false);
                wpiszPrawdopodobienstwo.setDisable(true);
                wpiszCzestotliwosc.setDisable(false);//true
                break;
            }
            case 11: {
                wpiszAmplituda.setDisable(false);
                wpiszOkres.setDisable(true);
                wpiszCzasPoczatkowy.setDisable(false);
                wpiszCzasKoncowy.setDisable(false);
                wpiszWspolczynnik.setDisable(true);
                wpiszCzasSkoku.setDisable(true);
                wpiszPrawdopodobienstwo.setDisable(false);
                wpiszCzestotliwosc.setDisable(false);//true
                break;
            }
        }
    }

    public void dodajSygnalDoListy(Szum szum, String nazwa){
        wygenerowaneSzumy.add(szum);
        String info = nazwa + " ";
        wybierzPierwszySygnal.getItems().add(nazwa);
        wybierzDrugiSygnal.getItems().add(nazwa);
        wybierzWyswietlanySygnal.getItems().add(nazwa);
    }

    @FXML
    void dodajSygnaly(ActionEvent event) throws ZlyRozmiar, ZlaCzestotliwosc {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 0);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        wykres.setCreateSymbols(false);
        generujWykres();
        generujHistogram();
    }


    @FXML
    void mnozenieSygnaly(ActionEvent event) throws ZlyRozmiar, ZlaCzestotliwosc {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 2);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        wykres.setCreateSymbols(false);
        generujWykres();
        generujHistogram();
    }

    @FXML
    void odemijSygnaly(ActionEvent event) throws ZlyRozmiar, ZlaCzestotliwosc {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 1);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        wykres.setCreateSymbols(false);
        generujWykres();
        generujHistogram();
    }

    @FXML
    void dzielenieSygnaly(ActionEvent event) throws ZlyRozmiar, ZlaCzestotliwosc {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 3);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        generujWykres();
        generujHistogram();
    }


    @FXML
    void zapiszSygnal(ActionEvent event) {
        try {
            if(aktualnySzum != null){
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
                File file = fileChooser.showSaveDialog(exampleChartPane.getScene().getWindow());

                operacjePliki.zapiszDoPliku(aktualnySzum, file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void wczytajSygnal(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));

            File file = fileChooser.showOpenDialog(exampleChartPane.getScene().getWindow());

            Szum szum = (Szum) operacjePliki.wczytajZPliku(file.getAbsolutePath());
            aktualnySzum = szum;
            dodajSygnalDoListy(szum, file.getName());
            wykres.getData().clear();
            wykres.setCreateSymbols(false);
            generujWykres();
            generujHistogram();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ktorySygnalWyswietlany(ActionEvent event) {
        int wybor = wybierzWyswietlanySygnal.getSelectionModel().getSelectedIndex();
        aktualnySzum = wygenerowaneSzumy.get(wybor);
        //wykres.getData().clear();
        generujWykres();
        generujHistogram();
    }


    @FXML
    void probkojSygnal(ActionEvent event) {
        OperacjeAC operacjeAC = new OperacjeAC();
        ostatniSzumPrzedProbkowaniem = aktualnySzum;
        Szum szum = operacjeAC.probkowanie(1/Double.parseDouble(wpiszCzestotliwoscProbkowania.getText()), aktualnySzum);
        szum.setCzy_Dyskretny(true);
        aktualnySzum = szum;
        dodajSygnalDoListy(szum, "Probkowany sygnał");

        wykres.getStylesheets().add("Chart.css");
        wykres.setCreateSymbols(true);
        /////////////////////////
        wykres.getData().clear();
        generujWykres();
        generujHistogram();
    }

    public void kwantyzacjaSygnalu(ActionEvent actionEvent) throws ZlyRozmiar {
        wykres.getStylesheets().clear();
        OperacjeAC operacjeAC = new OperacjeAC();
        Szum szum = operacjeAC.kwantyzacja(aktualnySzum, Integer.parseInt(wpiszPoziomKwantyzacji.getText()));
        ostatniSzumPrzedProbkowaniem = aktualnySzum;
        aktualnySzum = szum;
        wykres.getStylesheets().add("GlobalChart.css");
        dodajSygnalDoListy(szum, "Kwantyzacja sygnału");
        wykres.setCreateSymbols(false);
        /////////////////////////
        wykres.getData().clear();
        generujWykres();
        generujHistogram();

        ObliczBledy obliczBledy = new ObliczBledy();

        double MSE = obliczBledy.obliczMSE(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double SNR = obliczBledy.obliczSNR(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double PSNR = obliczBledy.obliczPSNR(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double MD = obliczBledy.obliczMD(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double enob = obliczBledy.obliczENOB(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());

        wypiszMSE.setText(MSE + "");
        wypiszSNR.setText(SNR + "");
        wypuszPSNR.setText(PSNR + "");
        wypiszMD.setText(MD + "");
        wypiszENOB.setText(enob + "");
    }

    public void wyborPierwszegoSygnalu(ActionEvent actionEvent) {
    }

    public void wyborDrugiegoSygnalu(ActionEvent actionEvent) {
    }

    public void interpolacjaSinc(ActionEvent actionEvent) throws ZlyRozmiar {
        OperacjeCA operacjeCA = new OperacjeCA();
        int wartosc = (int) Double.parseDouble(wpiszWartoscSinc.getText());

        wykres.getStylesheets().clear();
        Szum szum = new Szum();
        szum = operacjeCA.interpolacjaSinc(aktualnySzum, wartosc, ostatniSzumPrzedProbkowaniem);
        //szum = operacjeCA.interpolacjaSinc(aktualnySzum, wartosc, xQuantized);
        aktualnySzum = szum;
        wykres.getStylesheets().add("GlobalChart.css");
        dodajSygnalDoListy(szum, "Rekonstrukcja");
        wykres.setCreateSymbols(false);
        /////////////////////////
        wykres.getData().clear();
        generujWykres();
        generujHistogram();

        ObliczBledy obliczBledy = new ObliczBledy();

        double MSE = obliczBledy.obliczMSE(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double SNR = obliczBledy.obliczSNR(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double PSNR = obliczBledy.obliczPSNR(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double MD = obliczBledy.obliczMD(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double enob = obliczBledy.obliczENOB(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());

        wypiszMSE.setText(MSE + "");
        wypiszSNR.setText(SNR + "");
        wypuszPSNR.setText(PSNR + "");
        wypiszMD.setText(MD + "");
        wypiszENOB.setText(enob + "");

    }

    public void ekstrapolacja(ActionEvent actionEvent) throws ZlyRozmiar {
        OperacjeCA operacjeCA = new OperacjeCA();

        wykres.getStylesheets().clear();
        Szum szum = new Szum();
        aktualnySzum.setProbkowanie(Integer.parseInt(wpiszCzestotliwoscProbkowania.getText()));
        szum = operacjeCA.ekstrapolacja(aktualnySzum, ostatniSzumPrzedProbkowaniem);
        aktualnySzum = szum;
        wykres.getStylesheets().add("GlobalChart.css");
        dodajSygnalDoListy(szum, "Rekonstrukcja");
        wykres.setCreateSymbols(false);
        /////////////////////////
        wykres.getData().clear();
        generujWykres();
        generujHistogram();

        ObliczBledy obliczBledy = new ObliczBledy();

        double MSE = obliczBledy.obliczMSE(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double SNR = obliczBledy.obliczSNR(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double PSNR = obliczBledy.obliczPSNR(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double MD = obliczBledy.obliczMD(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());
        double enob = obliczBledy.obliczENOB(ostatniSzumPrzedProbkowaniem.getData(), aktualnySzum.getData());

        wypiszMSE.setText(MSE + "");
        wypiszSNR.setText(SNR + "");
        wypuszPSNR.setText(PSNR + "");
        wypiszMD.setText(MD + "");
        wypiszENOB.setText(enob + "");
    }

    public void wyczyscWykres(ActionEvent actionEvent) {
        wykres.getStylesheets().clear();
        wykres.getData().clear();
    }

    public void wykonajSplot(ActionEvent actionEvent) {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 4);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        generujWykres();
        generujHistogram();
    }

    public void wykonajKorelacje(ActionEvent actionEvent) {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 5);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        generujWykres();
        generujHistogram();
    }

    public void wykonajKorelacjeSplot(ActionEvent actionEvent) {
        Integer wybor1 = wybierzPierwszySygnal.getSelectionModel().getSelectedIndex();
        Szum szum1 = wygenerowaneSzumy.get(wybor1);

        Integer wybor2 = wybierzDrugiSygnal.getSelectionModel().getSelectedIndex();
        Szum szum2 = wygenerowaneSzumy.get(wybor2);

        Operacje operacje = new Operacje();
        Szum szum3 = operacje.operacje(szum1, szum2, 5);

        aktualnySzum = szum3;
        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " po dodawaniu");
        wykres.getData().clear();
        generujWykres();
        generujHistogram();
    }

    public void wykonajFiltrowanieDolnopasmowe(ActionEvent actionEvent){
        Filtr  filtr = new Filtr();
        Szum filtered = new Szum();

        boolean czyHanning = czyZaznaczonyHanning.isSelected();

        int M = Integer.parseInt(wpiszWartoscM.getText());
        double fo = Double.parseDouble(wpiszWartoscFO.getText());

        filtered = filtr.filtrDolnopasmowy(aktualnySzum, M, fo, czyHanning);
        aktualnySzum = filtered;
        dodajSygnalDoListy(filtered,"Filtered signal");
        wykres.getData().clear();
        wykres.getStylesheets().clear();
        wykres.getStylesheets().add("Chart.css");
        generujWykres();
        generujHistogram();
    }

    public void wykonajFiltrowanieGornopasmowe(ActionEvent actionEvent){
        Filtr  filtr = new Filtr();
        Szum filtered = new Szum();

        boolean czyHanning = czyZaznaczonyHanning.isSelected();

        int M = Integer.parseInt(wpiszWartoscM.getText());
        double fo = Double.parseDouble(wpiszWartoscFO.getText());

        filtered = filtr.filtrGornopasmowy(aktualnySzum, M, fo, czyHanning);
        aktualnySzum = filtered;
        dodajSygnalDoListy(filtered,"Filtered signal");
        wykres.getData().clear();
        wykres.getStylesheets().clear();
        wykres.getStylesheets().add("Chart.css");
        generujWykres();
        generujHistogram();
    }

//    public void przesunSygnal(ActionEvent actionEvent) {
//        double przesuniecie = Double.parseDouble(wpiszPrzesuniecie.getText());
//        Operacje operacje = new Operacje();
//        Szum szum3 = operacje.przesuniecieSygnalu(aktualnySzum, przesuniecie);
//
//        aktualnySzum = szum3;
//        dodajSygnalDoListy(szum3, szum3.getClass().getSimpleName() + " przesuniety");
//        wykres.getData().clear();
//        generujWykres();
//        generujHistogram();
//    }
}
