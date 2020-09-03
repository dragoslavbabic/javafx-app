package evidencija.dao;

import evidencija.DBconnection;
import evidencija.model.Destinacija;
import evidencija.model.Vozilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class destinacijaDAO {
    private final DBconnection conn = new DBconnection();
    private ResultSet result;
    private PreparedStatement pst;
    private Connection connect;

    public destinacijaDAO(){ }

    public ObservableList<Destinacija> getDestinacija(String query) {
        ObservableList<Destinacija> destinacijalist = FXCollections.observableArrayList();
        try {
            pst = conn.getConnection().prepareStatement(query);
            result = pst.executeQuery();
            while (result.next()) {
                Destinacija destinacija = new Destinacija(result.getInt("id"), result.getString("grad"));
                destinacijalist.add(destinacija);
                //System.out.println(destinacija);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return destinacijalist;
    }
}
