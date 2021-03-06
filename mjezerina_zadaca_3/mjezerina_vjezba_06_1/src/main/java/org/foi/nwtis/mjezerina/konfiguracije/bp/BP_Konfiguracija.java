package org.foi.nwtis.mjezerina.konfiguracije.bp;

import java.util.Date;
import java.util.Properties;
import org.foi.nwtis.mjezerina.konfiguracije.Konfiguracija;
import org.foi.nwtis.mjezerina.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.mjezerina.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mjezerina.konfiguracije.NemaKonfiguracije;

public class BP_Konfiguracija implements BP_Sucelje {

    private String nazivDatoteke = "";
    private Konfiguracija konfig = null;

    public BP_Konfiguracija(String nazivDatoteke) throws NeispravnaKonfiguracija, NemaKonfiguracije {
        this.nazivDatoteke = nazivDatoteke;
        konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);

    }

    @Override
    public String getAdminDatabase() {
        return konfig.dajPostavku("admin.database");
    }

    @Override
    public String getAdminPassword() {
        return konfig.dajPostavku("admin.password");
    }

    @Override
    public String getAdminUsername() {
        return konfig.dajPostavku("admin.username");
    }

    @Override
    public String getDriverDatabase() {
        String serverDatabase = getServerDatabase();
        return getDriverDatabase(serverDatabase);
    }

    @Override
    public String getDriverDatabase(String bp_url) {
        String[] p = bp_url.split(":");
        return konfig.dajPostavku("jdbc." + p[1]);
    }

    @Override
    public Properties getDriversDatabase() {
        Properties p = new Properties();
        for (Object o : konfig.dajSvePostavke().keySet()) {
            String k = (String) o;
            if (k.startsWith("jdbc")) {
                String v = konfig.dajPostavku(k);
                p.setProperty(k, v);
            }
        }
        return p;
    }

    @Override
    public String getServerDatabase() {
        return konfig.dajPostavku("server.database");
    }

    @Override
    public String getUserDatabase() {
        return konfig.dajPostavku("user.database");
    }

    @Override
    public String getUserPassword() {
        return konfig.dajPostavku("user.password");
    }

    @Override
    public String getUserUsername() {
        return konfig.dajPostavku("user.username");
    }

    @Override
    public String getLocationIQToken() {
        return konfig.dajPostavku("LocationIQ.token");
    }

    @Override
    public String getOpenWeatherMapApiKey() {
        return konfig.dajPostavku("OpenWeatherMap.apikey");
    }

    @Override
    public String getOpenSkyNetworkKorisnik() {
        return konfig.dajPostavku("OpenSkyNetwork.korisnik");
    }

    @Override
    public String getOpenSkyNetworkLozinka() {
        return konfig.dajPostavku("OpenSkyNetwork.lozinka");
    }

    @Override
    public String getPreuzimanjeStatus() {
        return konfig.dajPostavku("preuzimanje.status");
    }

    @Override
    public String getPreuzimanjePocetak() {
        return konfig.dajPostavku("preuzimanje.pocetak");
    }

    @Override
    public String getPreuzimanjeKraj() {
        return konfig.dajPostavku("preuzimanje.kraj");
    }

    @Override
    public String getPreuzimanjeCiklus() {
        return konfig.dajPostavku("preuzimanje.ciklus");
    }

    @Override
    public String getPreuzimanjePauza() {
        return konfig.dajPostavku("preuzimanje.pauza");
    }
    
    

    public Konfiguracija getKonfig() {
        return konfig;
    }

}
