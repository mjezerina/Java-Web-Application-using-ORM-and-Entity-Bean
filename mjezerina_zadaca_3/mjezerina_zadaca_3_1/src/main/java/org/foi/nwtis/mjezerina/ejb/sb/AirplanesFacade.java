/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mjezerina.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.foi.nwtis.mjezerina.ejb.eb.Airplanes;

/**
 *
 * @author Matija
 */
@Stateless
public class AirplanesFacade extends AbstractFacade<Airplanes> {

    @PersistenceContext(unitName = "NWTiS_DZ3_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AirplanesFacade() {
        super(Airplanes.class);
    }

    /**
     * Metoda koja putem JPQL upita dohvaća broj letova aviona s određenog aerodroma prema parametru ident
     * @param ident
     * @return objekt tipa long koji sadrži broj letova aviona s određenog aerodroma
     */
    public Long dohvatiBrojLetovaAerodroma(String ident) {
        Query query = em.createQuery("SELECT COUNT(m.icao24) FROM Airplanes m WHERE m.estdepartureairport.ident" + "=:ident").setParameter("ident", ident);
        return (Long) query.setParameter("ident", ident).getSingleResult();
    }

}
