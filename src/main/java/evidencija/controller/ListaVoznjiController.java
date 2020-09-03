package evidencija.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude;
import evidencija.DBconnection;
import evidencija.dao.destinacijaDAO;
import evidencija.dao.korisnikDAO;
import evidencija.dao.voziloDAO;
import evidencija.dao.voznjaDAO;
import evidencija.model.StanjeVozila;
import evidencija.model.SvrhaVoznje;
import evidencija.model.Vozilo;
import evidencija.model.Voznja;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ListaVoznjiController implements Initializable {

    @FXML
    private TableView<Voznja> tbListaVoznji;

    @FXML

    private TableColumn<Voznja,LocalDateTime > clmnPocetakVoznje;

    @FXML
    private TableColumn<Voznja, Integer> clmnPredjenoKM;

    @FXML
    private TableColumn<Voznja, String> clmnSvrhaVoznje;

    @FXML
    private TableColumn<Voznja, String> clmnVozilo;

    @FXML
    private TableColumn<Voznja, LocalDateTime> clmnKrajVoznje;

    @FXML
    private TableColumn<Voznja, String> clmnDestinacija;

    @FXML
    private TableColumn<Voznja, String> clmnKorisnik;
    voziloDAO daoVozilo;
    destinacijaDAO daoDst;
    voznjaDAO daoVoznja;
    korisnikDAO daoKorisnik;
    SvrhaVoznje svrhaVoznje;

    private DBconnection conn = new DBconnection();
    private final LocalDateTime date = LocalDateTime.now();
    private final Preferences pref = Preferences.userRoot();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showListaVoznji();
    }

    @SuppressWarnings("unchecked")
    public void showListaVoznji(){
        daoVoznja = new voznjaDAO();
        String queryVoznja = "SELECT v.id, vz.registarski_broj, v.pocetak_voznje, v.kraj_voznje, dst.grad,v.pocetna_km," +
                " v.predjeno_km, CONCAT(k.ime,' ', k.prezime) AS korisnik, v.svrha FROM voznja v LEFT JOIN vozilo vz " +
                "ON v.vozilo_id = vz.id LEFT JOIN destinacija dst ON v.destinacija_id = dst.id LEFT JOIN korisnik k " +
                "ON v.korisnik_id = k.id  ORDER BY v.pocetna_km";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.  HH:mm:ss");

        tbListaVoznji.getColumns().clear();
        clmnVozilo.setCellValueFactory(new PropertyValueFactory<>("registarski_broj"));
        clmnPocetakVoznje.setCellValueFactory(new PropertyValueFactory<>("pocetak_voznje"));
        clmnKrajVoznje.setCellValueFactory(new PropertyValueFactory<>("kraj_voznje"));
        clmnDestinacija.setCellValueFactory(new PropertyValueFactory<>("grad"));
        clmnPredjenoKM.setCellValueFactory(new PropertyValueFactory<>("predjeno_km"));
        clmnKorisnik.setCellValueFactory(new PropertyValueFactory<>("korisnik"));
        clmnSvrhaVoznje.setCellValueFactory(new PropertyValueFactory<>("svrha_string"));
        tbListaVoznji.getColumns().addAll(
                clmnVozilo,
                clmnPocetakVoznje,
                clmnKrajVoznje,
                clmnDestinacija,
                clmnPredjenoKM,
                clmnKorisnik,
                clmnSvrhaVoznje
        );
        tbListaVoznji.setItems(daoVoznja.getListaVoznji(queryVoznja));
        clmnPocetakVoznje.setCellFactory(column->new TableCell<>(){
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item,empty);
                if (empty){
                    setText("");
                }
                else setText(formatter.format(item));
            }
        });

        clmnKrajVoznje.setCellFactory(column->new TableCell<>(){
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText("");
                else if (item.isBefore(LocalDateTime.parse(("1900-01-01T01:01:01")))){
                    setText("");
                }
                else setText(formatter.format(item));
            }
        });
        LoginController login = new LoginController();
        Preferences pref = Preferences.userRoot();
        System.out.println(pref.get("currentUser","value"));
    }

    public void promeniNaPocetnuStranu(ActionEvent e) throws Exception{
        Parent pocetnaParent = FXMLLoader.load(getClass().getResource("/fxml/pocetna.fxml"));
        Scene pocetnaScene = new Scene(pocetnaParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(pocetnaScene);
        window.show();
    }
}
