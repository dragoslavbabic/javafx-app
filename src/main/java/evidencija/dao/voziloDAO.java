package evidencija.dao;

import evidencija.DBconnection;
import evidencija.model.Vozilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class voziloDAO {
    private final DBconnection conn = new DBconnection();
    private ResultSet result;
    private PreparedStatement pst;
    private Connection connect;

    public voziloDAO(){ }

    public ObservableList<Vozilo> getAllVozilo(String query) {
        ObservableList<Vozilo> vozilolist = FXCollections.observableArrayList();
        try {
            pst = conn.getConnection().prepareStatement(query);
            result = pst.executeQuery();
            while (result.next()) {
                Vozilo vozilo = new Vozilo(
                        result.getInt("id"),
                        result.getString("registarski_broj"),
                        result.getString("naziv")
                        ,result.getInt("predjeno_km"));
                vozilolist.add(vozilo);
                System.out.println(vozilolist);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vozilolist;
    }


    public int insertVozilo(
            String query,
            String registrarski_broj,
            String naziv,
            int predjeno_km
    ) throws Exception{
        int voziloIDKey = 0;
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(naziv);
            pst.setString(1,registrarski_broj);
            pst.setString(2,naziv);
            pst.setInt(3,predjeno_km);
            //pst.executeUpdate();
            pst.executeUpdate();
            ResultSet voziloID = pst.getGeneratedKeys();
            voziloID.next();
            voziloIDKey = voziloID.getInt(1);
            System.out.println(voziloIDKey);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
        }
        finally {
            conn.close(connect,pst,null);
        }            return voziloIDKey;

    }

    public void updateVozilo(
            String query,
            String registrarski_broj,
            String naziv,
            int predjeno_km,
            int id
    ) throws Exception{
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            System.out.println(naziv);
            //pst.setTimestamp(1, Timestamp.valueOf(ldt));
            pst.setString(1,registrarski_broj);
            pst.setString(2,naziv);
            pst.setInt(3,predjeno_km);
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

    public void updateVoziloZauzeto(
            String query,
            int id
    ) throws Exception{
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            System.out.println(id);
            //pst.setTimestamp(1, Timestamp.valueOf(ldt));
            pst.setInt(1,id);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
        }
        finally {
            conn.close(connect,pst,null);
        }
    }


    public void obrisiVozilo(String query, int id) throws Exception{
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
