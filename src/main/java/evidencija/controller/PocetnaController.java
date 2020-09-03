package evidencija.controller;

import evidencija.dao.loginDAO;
import evidencija.dao.voznjaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.prefs.Preferences;


public class PocetnaController {

    private final Preferences pref = Preferences.userRoot();
    private final String korisnik = pref.get("currentUser","user");
    loginDAO daoLogin = new loginDAO();

    public void Vozilo(ActionEvent e) throws Exception{
        Parent voziloParent = FXMLLoader.load(getClass().getResource("/fxml/vozilo.fxml"));
        Scene voziloScene = new Scene(voziloParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(voziloScene);
        window.show();
    }

    public void OtvoriVoznju(ActionEvent e) throws Exception{
        String query = "SELECT * FROM voznja JOIN korisnik ON voznja.korisnik_id = korisnik.id" +
                " WHERE zavrsna_km IS NULL AND korisnik.korisnicko_ime = ? AND lozinka != ? LIMIT 1 ";
        boolean validacija = daoLogin.validacija(query, korisnik, "");
        if (validacija) {
            System.out.println("BAD");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText("Već imate otvorenu vožnju!");
            alert.showAndWait();

        } else {
        Parent otvoriVoznjuParent = FXMLLoader.load(getClass().getResource("/fxml/otvori_voznju.fxml"));
        Scene otvoriVoznjuScene = new Scene(otvoriVoznjuParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(otvoriVoznjuScene);
        window.show();
        }
    }

    public void ZatvoriVoznju(ActionEvent e) throws Exception {
        String query = "SELECT * FROM voznja JOIN korisnik ON voznja.korisnik_id = korisnik.id" +
                " WHERE zavrsna_km IS NULL AND korisnik.korisnicko_ime = ? AND lozinka != ? LIMIT 1 ";
        boolean validacija = daoLogin.validacija(query, korisnik, "");
        if (!validacija) {
            System.out.println("BAD");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INFO");
            alert.setHeaderText(null);
            alert.setContentText("Nemate otvorenu vožnju!");
            alert.showAndWait();
        } else {
            Parent zatvoriVoznjuParent = FXMLLoader.load(getClass().getResource("/fxml/zatvori_voznju.fxml"));
            Scene zatvoriVoznjuScene = new Scene(zatvoriVoznjuParent);
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(zatvoriVoznjuScene);
            window.show();
        }
    }
    public void Korisnici(ActionEvent e) throws Exception{
        Parent korisniciParent = FXMLLoader.load(getClass().getResource("/fxml/korisnici.fxml"));
        Scene korisniciScene = new Scene(korisniciParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(korisniciScene);
        window.show();
    }

    public void ListaVoznji(ActionEvent e) throws Exception{
        Parent korisniciParent = FXMLLoader.load(getClass().getResource("/fxml/lista_voznji.fxml"));
        Scene korisniciScene = new Scene(korisniciParent);
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        window.setScene(korisniciScene);
        window.show();
    }
}
