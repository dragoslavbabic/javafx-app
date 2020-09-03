package evidencija.dao;

import evidencija.DBconnection;
import evidencija.model.Vozilo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDAO {
    private final DBconnection conn = new DBconnection();
    private ResultSet result;
    private PreparedStatement pst;
    private Connection connect;

    public loginDAO(){ }

    public boolean validacija (
            String query,
            String korisnicko_ime,
            String lozinka
    ){
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            //System.out.println(prezime);
            //pst.setTimestamp(1, Timestamp.valueOf(ldt));
            pst.setString(1,korisnicko_ime);
            pst.setString(2,lozinka);
            result = pst.executeQuery();
            if (result.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
