package evidencija.dao;

import evidencija.DBconnection;
import evidencija.model.Destinacija;
import evidencija.model.Korisnik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class korisnikDAO {
    private final DBconnection conn = new DBconnection();
    private ResultSet result;
    private PreparedStatement pst;
    private Connection connect;
    Statement st;

    public korisnikDAO(){ }

    public ObservableList<Korisnik> getKorisnici(String query) {
        ObservableList<Korisnik> korisniklist = FXCollections.observableArrayList();
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            result = pst.executeQuery();
            while (result.next()) {
                Korisnik korisnik = new Korisnik(
                        result.getInt("id"),
                        result.getString("ime"),
                        result.getString("prezime"),
                        result.getString("korisnicko_ime"));
                korisniklist.add(korisnik);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return korisniklist;
    }

    public void insertKorisnik(
            String query,
            String ime,
            String prezime,
            String korisnicko_ime,
            String lozinka
    ) throws Exception{
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            System.out.println(prezime);
            pst.setString(1,ime);
            pst.setString(2,prezime);
            pst.setString(3,korisnicko_ime);
            pst.setString(4,lozinka);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
        }
        finally {
            conn.close(connect,pst,null);
        }
    }

    public void updateKorisnik(String query, String ime, String prezime, String korisnicko_ime, int id) throws Exception{
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            System.out.println(prezime);
            pst.setString(1,ime);
            pst.setString(2,prezime);
            pst.setString(3,korisnicko_ime);
            pst.setInt(4,id);

            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
        }
        finally {
            conn.close(connect,pst,null);
        }
    }

    public void obrisiKorisnik(String query, int id) throws Exception{
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            System.out.println(query);
            pst.setInt(1,id);
            pst.execute();
            System.out.println(pst.execute());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greska!");
            alert.setContentText("Korisnik ne moze biti obrisan!");
            alert.showAndWait();
        }
        finally {
            conn.close(connect,pst,null);
        }

    }



}
