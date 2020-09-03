package evidencija.dao;

import evidencija.DBconnection;
import evidencija.model.SvrhaVoznje;
import evidencija.model.Voznja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class voznjaDAO {
    private final DBconnection conn = new DBconnection();
    private ResultSet result;
    private PreparedStatement pst;
    private Connection connect;

    public voznjaDAO(){ }


    public ObservableList<Voznja> getListaVoznji(String query) {
        ObservableList<Voznja> listavoznji = FXCollections.observableArrayList();
        try {
            pst = conn.getConnection().prepareStatement(query);
            result = pst.executeQuery();
            LocalDateTime kv;
            String svrha_string;
            LocalDateTime datum_pocetka;
            while (result.next()) {
                if(result.getString("kraj_voznje")==null){
                    kv = LocalDateTime.of(1200,1,1,1,1,1);
                }else {
                    kv = result.getTimestamp("kraj_voznje").toLocalDateTime();
                }
                datum_pocetka = result.getTimestamp("pocetak_voznje").toLocalDateTime();
                svrha_string = String.valueOf(SvrhaVoznje.values()[result.getInt("svrha")]);
                System.out.println(svrha_string);
                Voznja voznja = new Voznja(
                        result.getString("registarski_broj"),
                        datum_pocetka,
                        kv,
                        result.getString("grad"),
                        result.getInt("predjeno_km"),
                        result.getString("korisnik"),
                        svrha_string
                );
                listavoznji.add(voznja);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listavoznji;
    }

    public ObservableList<Voznja> getZatvoriVoznja(String query) {
        ObservableList<Voznja> voznjalist = FXCollections.observableArrayList();
        try {
            pst = conn.getConnection().prepareStatement(query);
            result = pst.executeQuery();
            while (result.next()) {
                Voznja voznja = new Voznja(
                        result.getInt("id"),
                        result.getInt("zavrsna_km"),
                        result.getString("grad"),
                        result.getInt("svrha"),
                        result.getInt("pocetna_km"),
                        result.getString("ime"),
                        result.getString("prezime"),
                        result.getString("registarski_broj"));
                voznjalist.add(voznja);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return voznjalist;
    }



    public int getPocetnoStanje(String query,long id) throws Exception {
        int x = 0;
        pst = conn.getConnection().prepareStatement(query);
        pst.setLong(1,id);
        result = pst.executeQuery();
        while (result.next()) {
            x = result.getInt(1);
            System.out.println(x);

        }
        return x;
    }

    public void insertVoznja(
            String query,
            String pocetak_voznje,
            int pocetna_km,
            int stanje_vozila,
            int svrha,
            String napomena,
            int destinacija_id,
            int korisnik_id,
            int vozilo_id)
            throws Exception{
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.  HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(pocetak_voznje,formatter);
            System.out.println(ldt);
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            //pst.setTimestamp(1, Timestamp.valueOf(ldt));
            pst.setObject(1, ldt);
            pst.setInt(2,pocetna_km);
            pst.setInt(3,stanje_vozila);
            pst.setInt(4,svrha);
            pst.setString(5,napomena);
            pst.setInt(6,destinacija_id);
            pst.setInt(7,korisnik_id);
            pst.setInt(8,vozilo_id);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
        }
        finally {
            conn.close(connect,pst,null);
        }
    }

    public void insertDefaultVoznja(
            String query,
            int pocetna_km,
            int vozilo_id
    )
            throws Exception{
        try {
            connect = conn.getConnection();
            pst = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pst.setObject(1, LocalDateTime.now());
            pst.setObject(2, LocalDateTime.now());
            pst.setInt(3,pocetna_km);
            pst.setInt(4,pocetna_km);
            pst.setString(5,"Novo vozilo");
            pst.setInt(6,vozilo_id);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("greska u upitu");
        }
        finally {
            conn.close(connect,pst,null);
        }
    }


    public void setZatvoriVoznju(
            String query,
            int id_voznja,
            String zavrsetak_voznje,
            int zavrsna_km,
            int predjeno_km,
            int stanje_vozila,
            String napomena) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.  HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(zavrsetak_voznje,formatter);
            connect = conn.getConnection();
            pst = connect.prepareStatement(query);
            pst.setInt(1,zavrsna_km);
            pst.setInt(2, predjeno_km);
            pst.setObject(3, ldt);
            pst.setInt(4,stanje_vozila);
            pst.setString(5,napomena);
            pst.setInt(6,id_voznja);
            pst.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            conn.close(connect,pst,null);
        }
    }
}
