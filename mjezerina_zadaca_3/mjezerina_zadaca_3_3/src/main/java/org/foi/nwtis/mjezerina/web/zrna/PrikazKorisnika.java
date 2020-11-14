package org.foi.nwtis.mjezerina.web.zrna;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.mjezerina.ejb.eb.Airports;
import org.foi.nwtis.mjezerina.ejb.eb.Korisnici;
import org.foi.nwtis.mjezerina.ejb.eb.Myairports;
import org.foi.nwtis.mjezerina.ejb.eb.Myairportslog;
import org.foi.nwtis.mjezerina.ejb.sb.AirplanesFacade;
import org.foi.nwtis.mjezerina.ejb.sb.AirportsFacade;
import org.foi.nwtis.mjezerina.ejb.sb.KorisniciFacade;
import org.foi.nwtis.mjezerina.ejb.sb.MyairportsFacade;
import org.foi.nwtis.mjezerina.ejb.sb.MyairportslogFacade;

/**
 *
 * @author Matija
 */
@Named(value = "prikazKorisnika")
@ViewScoped
public class PrikazKorisnika implements Serializable {

    @EJB
    KorisniciFacade korisniciFacade;
    @EJB
    MyairportsFacade myairportsFacade;
    @EJB
    AirportsFacade airportsFacade;
    @EJB
    MyairportslogFacade myairportslogFacade;
    @EJB
    AirplanesFacade airplanesFacade;

    @Getter
    List<Korisnici> korisnici = new ArrayList<>();
    @Getter
    @Setter
    String odabraniKorisnik = null;
    @Getter
    @Setter
    Long brojAerodroma;
    @Getter
    List<Myairports> listaAerodromaKorisnika = new ArrayList<>();
    @Getter
    List<Airports> listaAerodroma = new ArrayList<>();
    Airports aerodrom = null;
    @Getter
    Long brojKorisnikaPrate;
    @Getter
    List<Myairportslog> listaDohvacenihAerodroma = new ArrayList<>();
    @Getter
    @Setter
    String trenutniIdent;

    public PrikazKorisnika() {
    }

    @PostConstruct
    private void dohvatiKorisnike() {
        korisnici = korisniciFacade.findAll();
    }

    /**
     * Metoda koja dohvaća broj aerodroma koje korisnik prati prema parametru korisničko imenu
     * @param korisnickoime
     * @return
     */
    public Long dohvatiBrojAerodroma(String korisnickoime) {
        return myairportsFacade.dohvatiBrojAerodroma(korisnickoime);
    }

    /**
     * Metoda koja za odabranog korisnika iz liste svih korisnika, dohvaća sve nazive aerodroma koje korisnik prati
     * te metodom napuniAerodrome puni listu aerodroma za prikaz u tablici
     */
    public void napuniListuAerodromaKorisnika() {
        listaAerodromaKorisnika = myairportsFacade.dohvatiAerodromeKorisnika(odabraniKorisnik);
        listaAerodroma.clear();
        napuniAerodrome();
    }

    /**
     * Metoda koja prema dohvaćenoj listi tipa myairports dohvaća iz tablice airports objekte tipa Airport te puni listu
     * Aerodroma za prikaz u datatable-u
     */
    public void napuniAerodrome() {
        if (!listaAerodromaKorisnika.isEmpty()) {
            for (Myairports m : listaAerodromaKorisnika) {
                aerodrom = airportsFacade.dohvatiAerodromeKorisnika(m.getIdent().getIdent());
                listaAerodroma.add(aerodrom);
            }
        }
    }

    /**
     * Metoda koja dohvaća broj koliko korisnika prate određeni aerodrom
     *
     * @param ident
     * @return long broj korisnika koji prate aerodrom
     */
    public Long dohvatiBrojKorisnikaPrate(String ident) {
        return myairportsFacade.dohvatiBrojKorisnikaPrate(ident);
    }

    /**
     * Metoda koja dohvača broj koliko korisnika prati određen aerodrom
     * @param ident
     * @return long tip podataka, sadrži broj koliko aerodroma prate korisnici
     */
    public Long dohvatiBrojDatumaPrati(String ident) {
        return myairportslogFacade.dohvatiBrojDanaPreuzimanja(ident);
    }

    /**
     * Metoda koju koristi tablica za prikaz aerodroma korisnika, kao parametar prima
     * ident te dohvaća broj letova aviona s odabranog aerodroma
     * @param ident
     * @return
     */
    public Long dohvatiBrojLetovaSAerodroma(String ident) {
        return airplanesFacade.dohvatiBrojLetovaAerodroma(ident);
    }

    /**
     * Metoda koja puni listu myairportslog tipa prema identu dohvaćenom iz popisa aerodroma
     * @param ident
     */
    public void napuniListuDohvacenihAerodroma(String ident) {
        trenutniIdent = ident;
        listaDohvacenihAerodroma.clear();
        listaDohvacenihAerodroma = myairportslogFacade.dohvatiMyAirportsLog(ident);
    }

    /**
     * Metoda koja formatira datum leta koji prikazuje tablica u željenom formatu
     * @param datum
     * @return formatirani datum tipa string za prikaz u tablici
     */
    public String formatirajFlightDate(Date datum) {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatiraniDatum = simpleDateFormat.format(datum);
        return formatiraniDatum;
    }

    /**
     * Metoda koja formatira datum kada je podatak spremljen iz myairportslog tablice u željeni format
     * @param datum
     * @return formatirani datum tipa string koji se koristi za prikaz u tablici
     */
    public String formatirajStoredDate(Date datum) {
        String pattern = "dd.MM.yyyy HH:mm:ss.SSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatiraniDatum = simpleDateFormat.format(datum);
        return formatiraniDatum;
    }

    /**
     * Metoda koja kao parametre prima ident i datum te briše podatak iz tablice te iz baze podataka
     * @param ident
     * @param datum
     */
    public void obrisiPodatke(String ident, Date datum) {
        myairportslogFacade.obrisiPodatak(trenutniIdent, formatirajZaBrisanje(datum));
        listaDohvacenihAerodroma.clear();
        napuniListuDohvacenihAerodroma(trenutniIdent);
    }

    /**
     * Metoda koja formatira datum iz tipa date u oblik pogodan za brisanje odnosno za metodu obrisiPodatke
     * @param datum
     * @return
     */
    public Date formatirajZaBrisanje(Date datum) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.format(datum);
        return datum;
    }

}
