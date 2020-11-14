package org.foi.nwtis.mjezerina.konfiguracije.bp;

import java.util.Date;
import java.util.Properties;
import org.foi.nwtis.mjezerina.konfiguracije.Konfiguracija;

public interface BP_Sucelje {

    String getAdminDatabase();

    String getAdminPassword();

    String getAdminUsername();

    String getDriverDatabase();

    String getDriverDatabase(String bp_url);

    Properties getDriversDatabase();

    String getServerDatabase();

    String getUserDatabase();

    String getUserPassword();

    String getUserUsername();
    
    String getLocationIQToken();
    
    String getOpenWeatherMapApiKey();
    
    String getOpenSkyNetworkKorisnik();
    
    String getOpenSkyNetworkLozinka();
    
    String getPreuzimanjeStatus();
    
    String getPreuzimanjePocetak();
    
    String getPreuzimanjeKraj();
    
    String getPreuzimanjeCiklus();
    
    String getPreuzimanjePauza();
    
    
    Konfiguracija getKonfig();
}
