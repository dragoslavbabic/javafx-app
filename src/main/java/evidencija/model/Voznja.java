package evidencija.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.animation.Timeline;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Voznja {
    private long id;

    private LocalDateTime pocetak_voznje;
    private LocalDateTime kraj_voznje;
    private int pocetna_km;
    private int zavrsna_km;
    private int predjeno_km;
    private int stanje_vozila;
    private int svrha;
    private String svrha_string;
    private String napomena;
    private int destinacija_id;
    private int korisnik_id;
    private int vozilo_id;
    private String grad;
    private String ime;
    private String prezime;


    private String korisnik;
    private String registarski_broj;



    public Voznja(long id, LocalDateTime pocetak_voznje, LocalDateTime kraj_voznje, int pocetna_km, int zavrsna_km, int predjeno_km, int stanje_vozila, int svrha, String napomena, int destinacija_id, int korisnik_id, int vozilo_id) {
        this.id = id;
        this.pocetak_voznje = pocetak_voznje;
        this.kraj_voznje = kraj_voznje;
        this.pocetna_km = pocetna_km;
        this.zavrsna_km = zavrsna_km;
        this.predjeno_km = predjeno_km;
        this.stanje_vozila = stanje_vozila;
        this.svrha = svrha;
        this.napomena = napomena;
        this.destinacija_id = destinacija_id;
        this.korisnik_id = korisnik_id;
        this.vozilo_id = vozilo_id;
    }

    public Voznja(int id, int zavrsna_km) {
    }



    public Voznja(int id, int zavrsna_km, String grad, int svrha, int pocetna_km, String ime, String prezime, String registarski_broj) {
        this.id = id;
        this.zavrsna_km = zavrsna_km;
        this.grad = grad;
        this.svrha = svrha;
        this. pocetna_km = pocetna_km;
        this.ime = ime;
        this.prezime = prezime;
        this.registarski_broj = registarski_broj;
    }

    //public Voznja(int id,String registarski_broj, LocalDateTime pocetak_voznje, LocalDateTime kraj_voznje, String grad, int predjeno_km, String ime, int svrha) {
      public Voznja(String registarski_broj,LocalDateTime pocetak_voznje, LocalDateTime kraj_voznje,String grad, int predjeno_km, String korisnik, String svrha_string) {
            this.kraj_voznje = kraj_voznje;
          this.registarski_broj = registarski_broj;
          this.pocetak_voznje = pocetak_voznje;
          this.grad = grad;
          this.predjeno_km = predjeno_km;
          this.korisnik = korisnik;
          this.svrha_string = svrha_string;



/*
          this.id = id;
        this.kraj_voznje = kraj_voznje;
        this.grad = grad;
        this.zavrsna_km = zavrsna_km;
        this.ime = ime;
        this.svrha = svrha;
*/

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getPocetak_voznje() {
        return pocetak_voznje;
    }

    public void setPocetak_voznje(LocalDateTime pocetak_voznje) {
        this.pocetak_voznje = pocetak_voznje;
    }

    public LocalDateTime getKraj_voznje() {
        return kraj_voznje;
    }

    public void setKraj_voznje(LocalDateTime kraj_voznje) {
        this.kraj_voznje = kraj_voznje;
    }

    public int getPocetna_km() {
        return pocetna_km;
    }

    public void setPocetna_km(int pocetna_km) {
        this.pocetna_km = pocetna_km;
    }

    public int getZavrsna_km() {
        return zavrsna_km;
    }

    public void setZavrsna_km(int zavrsna_km) {
        this.zavrsna_km = zavrsna_km;
    }

    public int getPredjeno_km() {
        return predjeno_km;
    }

    public void setPredjeno_km(int predjeno_km) {
        this.predjeno_km = predjeno_km;
    }

    public int getStanje_vozila() {
        return stanje_vozila;
    }

    public void setStanje_vozila(int stanje_vozila) {
        this.stanje_vozila = stanje_vozila;
    }

    public int getSvrha() {
        return svrha;
    }

    public void setSvrha(int svrha) {
        this.svrha = svrha;
    }

    public String getSvrha_string() {
        return svrha_string;
    }

    public void setSvrha_string(String svrha_string) {
        this.svrha_string = svrha_string;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public int getDestinacija_id() {
        return destinacija_id;
    }

    public void setDestinacija_id(int destinacija_id) {
        this.destinacija_id = destinacija_id;
    }

    public int getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(int korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    public int getVozilo_id() {
        return vozilo_id;
    }

    public void setVozilo_id(int vozilo_id) {
        this.vozilo_id = vozilo_id;
    }
    public String getGrad() {
        return grad;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }


    public String getRegistarski_broj() {
        return registarski_broj;
    }

}
