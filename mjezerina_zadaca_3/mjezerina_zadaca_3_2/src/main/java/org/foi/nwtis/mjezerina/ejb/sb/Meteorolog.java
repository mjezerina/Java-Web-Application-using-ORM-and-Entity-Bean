package org.foi.nwtis.mjezerina.ejb.sb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.foi.nwtis.mjezerina.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.rest.klijenti.OWMKlijent;
import org.foi.nwtis.rest.podaci.MeteoPodaci;

/**
 *
 * @author Matija
 */
@Stateless
public class Meteorolog implements MeteorologLocal {

    @Inject
    ServletContext context;

    public MeteoPodaci dajMeteoPodatke(String latitude,String longitude) {
        BP_Konfiguracija bpk = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        String apikey = bpk.getKonfig().dajPostavku("OpenWeatherMap.apikey");
        OWMKlijent oWMKlijent = new OWMKlijent(apikey);
        try {
            MeteoPodaci meteoPodaci = oWMKlijent.getRealTimeWeather(latitude, longitude);
            return meteoPodaci;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
