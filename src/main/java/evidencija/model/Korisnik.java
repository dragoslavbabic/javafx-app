package evidencija.model;


import java.util.Date;

public class Korisnik {
    private int id;

    private Boolean admin;
    private String ime;
    private String prezime;
    private String korisnicko_ime;
    private String lozinka;



    public Korisnik() {
    }

    public Korisnik(String string) {
    }

    public Korisnik(int id, Boolean admin, String ime, String prezime, String korisnicko_ime, String lozinka) {
        this.id = id;
        this.admin = admin;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
    }

    public Korisnik(int id, String ime, String prezime, String korisnicko_ime) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnicko_ime = korisnicko_ime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}


