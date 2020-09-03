package evidencija.controller;

import evidencija.dao.destinacijaDAO;
import evidencija.dao.korisnikDAO;
import evidencija.dao.voziloDAO;
import evidencija.dao.voznjaDAO;
import evidencija.model.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import evidencija.DBconnection;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.prefs.Preferences;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

public class OtvoriVoznjuController implements Initializable {

    @FXML
    private ComboBox<Vozilo> cbIzaberiVozilo;

    @FXML
    private TextArea taNapomena;

    @FXML
    public ComboBox<SvrhaVoznje> cbSvrhaVoznje;

    @FXML
    private Button btnKreirajVoznju;

    @FXML
    private TextField tfDatumPocetka;

    @FXML
    private TextField tfPocetnoStanjeKM;

    @FXML
    private ComboBox<Destinacija> cbDestinacija;

    @FXML
    private Button btnPocetna;

    @FXML
    private Button bntOsvezi;

    @FXML
    private ComboBox<StanjeVozila> cbStanjeVozila;
/*
    @FXML
    private ComboBox<Korisnik> cbZaposleni;
*/
    @FXML
    private TextField tfKorisnik;

    voziloDAO daoVozilo;
    destinacijaDAO daoDst;
    voznjaDAO daoVoznja;
    korisnikDAO daoKorisnik;

    private final LocalDateTime date = LocalDateTime.now();
    private final Preferences pref = Preferences.userRoot();
    private final String korisnik = pref.get("currentUser","user");
    private final String queryKorisnik = "SELECT id,ime,prezime,korisnicko_ime FROM korisnik WHERE korisnicko_ime ='" + korisnik +"'";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Vozilo START
        daoVozilo = new voziloDAO();
/*
        String query = "SELECT car1.id, car1.registarski_broj, v1.zavrsna_km, car1.naziv, v1.predjeno_km " +
                "FROM voznja AS v1 JOIN (SELECT MAX(zavrsna_km) AS zavrsna_km, vozilo_id FROM voznja JOIN vozilo ON voznja.vozilo_id = vozilo.id WHERE vozilo.zauzeto !=1 GROUP BY vozilo_id) AS r1\n" +
                "ON v1.vozilo_id = r1.vozilo_id AND r1.zavrsna_km = v1.zavrsna_km LEFT JOIN vozilo AS car1 ON v1.vozilo_id = car1.id";
*/
        String query = "SELECT car1.id, car1.registarski_broj, v1.zavrsna_km, car1.naziv, v1.predjeno_km FROM voznja AS v1 JOIN (SELECT MAX(zavrsna_km) AS zavrsna_km, vozilo_id FROM voznja GROUP BY vozilo_id) AS r1 ON v1.vozilo_id = r1.vozilo_id AND r1.zavrsna_km = v1.zavrsna_km LEFT JOIN vozilo AS car1 ON v1.vozilo_id = car1.id WHERE car1.id NOT IN(SELECT vozilo.id FROM vozilo JOIN voznja ON vozilo.id = voznja.vozilo_id WHERE voznja.zavrsna_km IS NULL)";

        cbIzaberiVozilo.setItems(daoVozilo.getAllVozilo(query));
        System.out.println(daoVozilo.getAllVozilo(query));
        class ItemCell extends ListCell<Vozilo>{
            @Override
            protected void updateItem(Vozilo vozilo, boolean empty){
                super.updateItem(vozilo, empty);
                setText(vozilo == null ? "": vozilo.getRegistarski_broj());
            }
        }
        cbIzaberiVozilo.setCellFactory(bb -> new ItemCell());
        cbIzaberiVozilo.setButtonCell(new ItemCell());
        //Vozilo END

        cbStanjeVozila.getItems().setAll(StanjeVozila.values());
        cbStanjeVozila.getSelectionModel().selectFirst();
        cbSvrhaVoznje.getItems().setAll(SvrhaVoznje.values());
        cbSvrhaVoznje.getSelectionModel().selectFirst();

            cbIzaberiVozilo.setOnAction(bb-> {
                try {
                    pocetnoStanje();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        //Destinacija START
        daoDst = new destinacijaDAO();
        String queryDst = "SELECT * FROM destinacija";
        cbDestinacija.setItems(daoDst.getDestinacija(queryDst));
        cbDestinacija.getSelectionModel().selectFirst();
        class DstCell extends ListCell<Destinacija>{
            @Override
            protected void updateItem(Destinacija destinacija, boolean empty){
                super.updateItem(destinacija, empty);
                setText(destinacija == null ? "": destinacija.getGrad());
            }
        }
        cbDestinacija.setCellFactory(bb -> new DstCell());
        cbDestinacija.setButtonCell(new DstCell());
        //Destinacija END
        tfDatumPocetka.setText(getDate(date));
        tfKorisnik.setText(getKorisnikImePrezime());
    }

    private void pocetnoStanje() throws Exception {
        daoVoznja = new voznjaDAO();
        String query = "SELECT zavrsna_km FROM voznja where vozilo_id = ? ORDER BY zavrsna_km DESC LIMIT 1";
        //Vozilo vozilo = cbIzaberiVozilo.getSelectionModel().getSelectedItem();
        int id = getVoziloId();
        tfPocetnoStanjeKM.setText(String.valueOf(daoVoznja.getPocetnoStanje(query,id)));
    }

    public String getDate(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.  HH:mm:ss"));
    }

    public void saveVoznja(ActionEvent e) throws Exception {

        String query = "INSERT INTO voznja (pocetak_voznje,pocetna_km,stanje_vozila,svrha,napomena,destinacija_id,korisnik_id,vozilo_id) VALUES(?,?,?,?,?,?,?,?);";
        //String queryVoziloZauzeto = "UPDATE vozilo SET zauzeto = 1 WHERE id = ?";
        String dp = tfDatumPocetka.getText();
        int pskm = Integer.parseInt(tfPocetnoStanjeKM.getText());
        int stanje = cbStanjeVozila.getSelectionModel().getSelectedIndex();
        int svrha = cbSvrhaVoznje.getSelectionModel().getSelectedIndex();
        String napomena = taNapomena.getText();
        int dstID = getDestinacijaID();
        int korisnikID = getKorisnikID();
        int voziloID = getVoziloId();
        daoVoznja.insertVoznja(query, dp, pskm, stanje, svrha, napomena, dstID, korisnikID, voziloID);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText("Vožnja je uspešno otvorena!");
        alert.showAndWait();
        promeniNaPocetnuStranu(e);
    }

    public int getVoziloId() {
        Vozilo vozilo = cbIzaberiVozilo.getSelectionModel().getSelectedItem();
        return vozilo.getId();
    }

    public int getDestinacijaID(){
        Destinacija destinacija = cbDestinacija.getSelectionModel().getSelectedItem();
        return destinacija.getId();
    }

    public int getKorisnikID(){
        daoKorisnik = new korisnikDAO();
        return daoKorisnik.getKorisnici(queryKorisnik).get(0).getId();
    }

    public String getKorisnikImePrezime(){
        daoKorisnik = new korisnikDAO();
        ObservableList<Korisnik> oneKorisnik = daoKorisnik.getKorisnici(queryKorisnik);
        Korisnik k = oneKorisnik.get(0);
        return  k.getIme() + " " + k.getPrezime();
    }
    public void promeniNaPocetnuStranu(ActionEvent e) throws Exception{
        Parent pocetnaParent = FXMLLoader.load(getClass().getResource("/fxml/pocetna.fxml"));
        Scene pocetnaScene = new Scene(pocetnaParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(pocetnaScene);
        window.show();
    }
}
