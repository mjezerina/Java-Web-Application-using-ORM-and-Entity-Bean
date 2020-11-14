package org.foi.nwtis.mjezerina.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.foi.nwtis.mjezerina.ejb.eb.Airports;
import org.foi.nwtis.mjezerina.ejb.eb.Korisnici;
import org.foi.nwtis.mjezerina.ejb.sb.AirportsFacade;
import org.foi.nwtis.mjezerina.ejb.sb.KorisniciFacade;
import org.foi.nwtis.mjezerina.ejb.sb.MyairportsFacade;

/**
 *
 * @author Matija
 */
@Named(value = "drugaAktivnost")
@ViewScoped
public class DrugaAktivnost implements Serializable {

    @EJB
    KorisniciFacade korisniciFacade;
    @EJB
    MyairportsFacade myairportsFacade;
    @EJB
    AirportsFacade airportsFacade;

    @Getter
    List<Korisnici> korisnici = new ArrayList<>();
    @Getter
    @Setter
    String odabraniKorisnik = null;
    @Getter
    List<Airports> listaAerodroma = new ArrayList<>();
    @Getter
    @Setter
    String unosNaziva;

    public DrugaAktivnost() {
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
     * Metoda koja dohvaća podatke naziva aerodroma slično unesenom nazivu putem JPQL upita
     * @param uneseniNaziv
     */
    public void napuniListuAerodromaKorisnika(String uneseniNaziv) {
        listaAerodroma.clear();
        listaAerodroma = airportsFacade.dohvatiAerodromeNaziva(uneseniNaziv);
    }

    /**
     * Metoda koja dohvaća podatke naziva aerodroma slično unesenom nazivu putem CAPI upita
     * @param uneseniNaziv
     */
    public void napuniListuAerodromaKorisnikaCapi(String uneseniNaziv) {
        listaAerodroma.clear();
        listaAerodroma = airportsFacade.dohvatiAerodromeCapi(unosNaziva);
    }

    /**
     * Metoda pomoću koje dohvaćamo odabranog korisnika
     * @param korIme
     */
    public void odabirKorisnika(String korIme) {
        odabraniKorisnik = korIme;
    }

    /**
     * Metoda koja prima string coordinates iz tablice airports i parsira te koordinate i vraća long podatak vrijednosti longitude.
     * @param coordinates
     * @return
     */
    public float parsirajLongitudu(String coordinates) {
        String[] polje = coordinates.split(", ");
        String longitude = polje[0];
        float longituda = Float.parseFloat(longitude);
        return longituda;
    }

    /**
     * Metoda koja prima string coordinates iz tablice airports i parsira te koordinate i vraća long podatak vrijednosti latitude.
     * @param coordinates
     * @return
     */
    public float parsirajLatitudu(String coordinates) {
        String[] polje = coordinates.split(", ");
        String latitude = polje[1];
        float latituda = Float.parseFloat(latitude);
        return latituda;
    }

}
