package evidencija.controller;

import evidencija.DBconnection;
import evidencija.dao.loginDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable{

    @FXML
    private TextField tfKorisnickoIme;

    @FXML
    private TextField tfLozinka;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            tfKorisnickoIme.requestFocus();
            Platform.runLater(() -> tfKorisnickoIme.requestFocus());
        }

        @FXML
        public void loginKorisnik(ActionEvent e) throws Exception {
            Preferences pref = Preferences.userRoot();
            loginDAO daoLogin = new loginDAO();
            String query = "SELECT * FROM korisnik WHERE korisnicko_ime = ? and lozinka = ?";
            boolean validacija = daoLogin.validacija(query, tfKorisnickoIme.getText(), tfLozinka.getText());
            if (!validacija) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Autentifikacija");
                alert.setHeaderText(null);
                alert.setContentText("Pogrešno korisničko ime ili lozinka!");
                alert.showAndWait();
            } else {
                promeniNaPocetnuStranu(e);
                pref.put("currentUser", tfKorisnickoIme.getText());
                System.out.println(pref.get("currentUser", "value"));
            }
        }

        @FXML
        public void promeniNaPocetnuStranu(ActionEvent e) throws Exception {
            Parent pocetnaParent = FXMLLoader.load(getClass().getResource("/fxml/pocetna.fxml"));
            Scene pocetnaScene = new Scene(pocetnaParent);
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.setScene(pocetnaScene);
            window.show();
        }
    }

