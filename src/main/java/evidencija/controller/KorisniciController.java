package evidencija.controller;

import evidencija.DBconnection;
import evidencija.dao.destinacijaDAO;
import evidencija.dao.korisnikDAO;
import evidencija.dao.voziloDAO;
import evidencija.dao.voznjaDAO;
import evidencija.model.*;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class KorisniciController implements Initializable {

    private DBconnection conn = new DBconnection();
    private ResultSet result;
    private PreparedStatement pst;
    private Connection connect;
    private Statement st;
    LocalDateTime date = LocalDateTime.now();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfKorisnickoIme;

    @FXML
    private TextField tfPrezime;

    @FXML
    private TextField tfLozinka;

    @FXML
    private PasswordField tfLozinkaProvera;

    @FXML
    private TextField tfIme;

    @FXML
    private TableView<Korisnik> tbKorisnici;

    @FXML
    private TableColumn<Korisnik, String> clmnIme;

    @FXML
    private TableColumn<Korisnik, String> clmnPrezime;

    @FXML
    private TableColumn<Korisnik, String> clmnKorisnickoIme;

    @FXML
    private Button btnKreirajVoznju;

    @FXML
    private Button btnPocetna;

    @FXML
    private Button bntOsvezi;

    korisnikDAO daoKorisnik;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showKorisnici();
    }
    @SuppressWarnings("unchecked")
    public void showKorisnici(){
        daoKorisnik = new korisnikDAO();
        String query = "SELECT * FROM korisnik";
        tbKorisnici.getColumns().clear();
        clmnIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        clmnPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        clmnKorisnickoIme.setCellValueFactory(new PropertyValueFactory<>("korisnicko_ime"));
        tbKorisnici.getColumns().addAll(clmnIme,clmnPrezime,clmnKorisnickoIme);
        tbKorisnici.setItems(daoKorisnik.getKorisnici(query));
        LoginController login = new LoginController();
        Preferences pref = Preferences.userRoot();
        System.out.println(pref.get("currentUser","value"));
    }

    public void kreirajKorisnika(ActionEvent e) throws Exception {
        daoKorisnik = new korisnikDAO();
        String query = "INSERT INTO korisnik (ime,prezime,korisnicko_ime,lozinka) VALUES(?,?,?,?)";
        if (tfLozinka.equals(tfLozinkaProvera)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!!!");
            alert.setHeaderText(null);
            alert.setContentText("Lozinke se ne poklapaju!");
            alert.showAndWait();
        } else {
            daoKorisnik.insertKorisnik(query, tfIme.getText(), tfPrezime.getText(), tfKorisnickoIme.getText(), tfLozinka.getText());
            System.out.println(tfPrezime.getText());
            PocetnaController korisnici = new PocetnaController();
            korisnici.Korisnici(e);
        }
    }
    public void izmeniKorisnika(ActionEvent e) throws Exception {
        daoKorisnik = new korisnikDAO();
        Korisnik korisnik = tbKorisnici.getSelectionModel().getSelectedItem();
        System.out.println(korisnik.getIme());
        String query = "UPDATE korisnik SET ime=?,prezime=?,korisnicko_ime=? where id = ?";
        daoKorisnik.updateKorisnik(query,tfIme.getText(),tfPrezime.getText(),tfKorisnickoIme.getText(),korisnik.getId());
        System.out.println(tfPrezime.getText());
        showKorisnici();
    }

    public void obrisiKorisnika(ActionEvent e) throws Exception {
        daoKorisnik = new korisnikDAO();
        Korisnik korisnik = tbKorisnici.getSelectionModel().getSelectedItem();
        System.out.println(korisnik.getId());
        String query = "DELETE FROM korisnik where id = ?";
        daoKorisnik.obrisiKorisnik(query, korisnik.getId());
        showKorisnici();
        }

    @FXML
    private void selectOnMouseClick(MouseEvent e){
        Korisnik korisnik = tbKorisnici.getSelectionModel().getSelectedItem();
        //System.out.println("id " + korisnik.getId());
        tfIme.setText(korisnik.getIme());
        tfPrezime.setText(korisnik.getPrezime());
        tfKorisnickoIme.setText(korisnik.getKorisnicko_ime());
        tfLozinka.setText(korisnik.getLozinka());
    }

    public void promeniNaPocetnuStranu (ActionEvent e) throws Exception {
            Parent pocetnaParent = FXMLLoader.load(getClass().getResource("/fxml/pocetna.fxml"));
            Scene pocetnaScene = new Scene(pocetnaParent);
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(pocetnaScene);
            window.show();
        }
}
