package evidencija.controller;

import evidencija.DBconnection;
import evidencija.dao.voziloDAO;
import evidencija.dao.voziloDAO;
import evidencija.dao.voznjaDAO;
import evidencija.model.Korisnik;
import evidencija.model.Vozilo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class VoziloController implements Initializable {

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
    private TextField tfRegistarskiBroj;

    @FXML
    private TextField tfNaziv;

    @FXML
    private TextField tfPredjenoKM;

    @FXML
    private TableView<Vozilo> tbVozilo;

    @FXML
    private TableColumn<Vozilo, String> clmnRegistarskiBroj;

    @FXML
    private TableColumn<Vozilo, String> clmnNaziv;

    @FXML
    private TableColumn<Vozilo, String> clmnPredjenoKM;

    @FXML
    private Button btnKreirajVoznju;

    @FXML
    private Button btnPocetna;

    @FXML
    private Button bntOsvezi;

    voziloDAO daoVozilo;
    voznjaDAO daoVoznja;
    public Preferences pref = Preferences.userRoot();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showVozilo();
    }

    @SuppressWarnings("unchecked")
    public void showVozilo(){
        daoVozilo = new voziloDAO();
        String query = "SELECT * FROM vozilo";
        tbVozilo.getColumns().clear();
        clmnRegistarskiBroj.setCellValueFactory(new PropertyValueFactory<>("registarski_broj"));
        clmnNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        clmnPredjenoKM.setCellValueFactory(new PropertyValueFactory<>("predjeno_km"));
        tbVozilo.getColumns().addAll(clmnRegistarskiBroj,clmnNaziv,clmnPredjenoKM);
        tbVozilo.setItems(daoVozilo.getAllVozilo(query));
        LoginController login = new LoginController();
        Preferences pref = Preferences.userRoot();

        System.out.println(pref.get("currentUser","value"));
    }

    public void kreirajVozilo(ActionEvent e) throws Exception {
        daoVozilo = new voziloDAO();
        OtvoriVoznjuController ov = new OtvoriVoznjuController();
        int korisnikID = ov.getKorisnikID();
        int predjenoKM = Integer.parseInt(tfPredjenoKM.getText());
        String query = "INSERT INTO vozilo (registarski_broj,naziv,predjeno_km) VALUES(?,?,?)";
        int voziloID = daoVozilo.insertVozilo(query,tfRegistarskiBroj.getText(),tfNaziv.getText(),predjenoKM);
        showVozilo();
        daoVoznja = new voznjaDAO();
        String queryDefaultVoznja = "INSERT INTO voznja (pocetak_voznje,kraj_voznje,pocetna_km,zavrsna_km,napomena,vozilo_id) VALUES (?,?,?,?,?,?)";
        daoVoznja.insertDefaultVoznja(queryDefaultVoznja,predjenoKM, voziloID);
    }

    public void izmeniVozilo(ActionEvent e) throws Exception {
        daoVozilo = new voziloDAO();
        Vozilo vozilo = tbVozilo.getSelectionModel().getSelectedItem();
        System.out.println(vozilo.getRegistarski_broj());
        String query = "UPDATE vozilo SET registarski_broj=?,naziv=?,predjeno_km=? where id = ?";
        daoVozilo.updateVozilo(query,tfRegistarskiBroj.getText(),tfNaziv.getText(),Integer.parseInt(tfPredjenoKM.getText()),vozilo.getId());
        System.out.println(tfPredjenoKM.getText());
        showVozilo();
    }

    public void obrisiVozilo(ActionEvent e) throws Exception {
        daoVozilo = new voziloDAO();
        Vozilo vozilo = tbVozilo.getSelectionModel().getSelectedItem();
        System.out.println(vozilo.getId());
        String query = "DELETE FROM vozilo where id = ?";
        daoVozilo.obrisiVozilo(query, vozilo.getId());
        showVozilo();
        }

    @FXML
    private void selectOnMouseClick(MouseEvent e){
        Vozilo vozilo = tbVozilo.getSelectionModel().getSelectedItem();
        //System.out.println("id " + korisnik.getId());
        tfRegistarskiBroj.setText(vozilo.getRegistarski_broj());
        tfNaziv.setText(vozilo.getNaziv());
        tfPredjenoKM.setText(String.valueOf(vozilo.getPredjeno_km()));
    }

    public void promeniNaPocetnuStranu (ActionEvent e) throws Exception {
            Parent pocetnaParent = FXMLLoader.load(getClass().getResource("/fxml/pocetna.fxml"));
            Scene pocetnaScene = new Scene(pocetnaParent);
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(pocetnaScene);
            window.show();
        }
}
