package evidencija.model;


import java.time.LocalDate;
import java.util.Date;

public class Vozilo {
    private int id;
    private String naziv;
    private Integer predjeno_km;
    private String registarski_broj;

    public Vozilo() {
    }

    public Vozilo(String string) {
    }

    public Vozilo(int id, String registarski_broj,String naziv, Integer predjeno_km) {
        this.id = id;
        this.registarski_broj = registarski_broj;
        this.naziv = naziv;
        this.predjeno_km = predjeno_km;
    }

    public Vozilo(int id, String registarski_broj) {
        this.id = id;
        this.registarski_broj = registarski_broj;
    }

    public Vozilo(String registarski_broj,String naziv, Integer predjeno_km) {
        this.registarski_broj = registarski_broj;
        this.naziv = naziv;
        this.predjeno_km = predjeno_km;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public LocalDate getDatum_registracije() {
        return datum_registracije;
    }
*/
  /*  public void setDatum_registracije(LocalDate datum_registracije) {
        this.datum_registracije = datum_registracije;
    }
*/
    public Integer getPredjeno_km() {
        return predjeno_km;
    }

    public void setPredjeno_km(Integer predjeno_km) {
        this.predjeno_km = predjeno_km;
    }

    public String getRegistarski_broj() {
        return registarski_broj;
    }

    public void setRegistarski_broj(String registarski_broj) {
        this.registarski_broj = registarski_broj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }



/*
    @Override
    public String toString(){
        return registarski_broj;
    }
*/

}


