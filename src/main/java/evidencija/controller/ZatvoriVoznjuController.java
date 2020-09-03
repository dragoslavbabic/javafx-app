package evidencija.controller;
import evidencija.DBconnection;
import evidencija.dao.destinacijaDAO;
import evidencija.dao.korisnikDAO;
import evidencija.dao.voziloDAO;
import evidencija.dao.voznjaDAO;
import evidencija.model.Korisnik;
import evidencija.model.StanjeVozila;
import evidencija.model.SvrhaVoznje;
import evidencija.model.Voznja;
import javafx.application.Platform;
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
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ZatvoriVoznjuController implements Initializable {

    @FXML
    private Label lbVoznjaID;

    @FXML
    private Label lbVozilo;

    @FXML
    private TextArea taNapomena;

    @FXML
    private TextField tfDatumZavrsetka;

    @FXML
    private TextField tfSvrhaVoznje;

    @FXML
    private TextField tfPocetnoStanjeKM;

    @FXML
    private TextField tfDestinacija;

    @FXML
    private ComboBox<StanjeVozila> cbStanjeVozila;

    @FXML
    private TextField tfZaposleni;

    @FXML
    private TextField tfZavrsnoStanjeKM;

    voziloDAO daoVozilo;
    destinacijaDAO daoDst;
    voznjaDAO daoVoznja;
    korisnikDAO daoKorisnik;
    SvrhaVoznje svrhaVoznje;

    private DBconnection conn = new DBconnection();
    private final LocalDateTime date = LocalDateTime.now();
    private final Preferences pref = Preferences.userRoot();
    private final String korisnik = pref.get("currentUser","user");
    private final String queryVoznja = "SELECT dst.grad,v.zavrsna_km, v.svrha, v.pocetna_km, v.id, k.ime, k.prezime, vz.registarski_broj FROM voznja v LEFT JOIN vozilo vz ON v.vozilo_id = vz.id LEFT JOIN destinacija dst ON v.destinacija_id = dst.id LEFT JOIN korisnik k ON v.korisnik_id = k.id WHERE v.zavrsna_km IS NULL AND k.korisnicko_ime ='" + korisnik +"'";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> tfZavrsnoStanjeKM.requestFocus());
        daoVoznja = new voznjaDAO();
        ObservableList<Voznja> zatvaranjeVoznje = daoVoznja.getZatvoriVoznja(queryVoznja);
        lbVozilo.setText("Vozilo: " + zatvaranjeVoznje.get(0).getRegistarski_broj());
        tfDestinacija.setText(zatvaranjeVoznje.get(0).getGrad());
        int svrha = zatvaranjeVoznje.get(0).getSvrha();
        tfSvrhaVoznje.setText(String.valueOf(SvrhaVoznje.values()[svrha]));
        tfPocetnoStanjeKM.setText(String.valueOf(zatvaranjeVoznje.get(0).getPocetna_km()));
        OtvoriVoznjuController ovc = new OtvoriVoznjuController();
        tfDatumZavrsetka.setText(ovc.getDate(date));
        tfZaposleni.setText(zatvaranjeVoznje.get(0).getIme() + " " + zatvaranjeVoznje.get(0).getPrezime());
        lbVoznjaID.setText(String.valueOf(zatvaranjeVoznje.get(0).getId()));
        cbStanjeVozila.getItems().setAll(StanjeVozila.values());
        cbStanjeVozila.getSelectionModel().selectFirst();
        tfZavrsnoStanjeKM.requestFocus();

    }

    public void zatvoriVoznju(ActionEvent e) throws Exception {
        String query = "UPDATE voznja SET zavrsna_km = ?,  predjeno_km = ?,kraj_voznje = ?,stanje_vozila = ?,napomena = ? WHERE id = ?";
        String dz = tfDatumZavrsetka.getText();
        int pskm = Integer.parseInt(tfPocetnoStanjeKM.getText());
        int zskm = Integer.parseInt(tfZavrsnoStanjeKM.getText());
        int predjeno_km = zskm - pskm;
        System.out.println(predjeno_km);
        int stanje = cbStanjeVozila.getSelectionModel().getSelectedIndex();
        String napomena = taNapomena.getText();
        int id_voznja = Integer.parseInt(lbVoznjaID.getText());
        daoVoznja.setZatvoriVoznju(query,id_voznja, dz, zskm,predjeno_km, stanje, napomena);
        promeniNaPocetnuStranu(e);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText("Vožnja je uspešno zatvorena!");
        alert.showAndWait();
        System.out.println("ok");
    }

    public void promeniNaPocetnuStranu(ActionEvent e) throws Exception{
        Parent pocetnaParent = FXMLLoader.load(getClass().getResource("/fxml/pocetna.fxml"));
        Scene pocetnaScene = new Scene(pocetnaParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(pocetnaScene);
        window.show();
    }
}
