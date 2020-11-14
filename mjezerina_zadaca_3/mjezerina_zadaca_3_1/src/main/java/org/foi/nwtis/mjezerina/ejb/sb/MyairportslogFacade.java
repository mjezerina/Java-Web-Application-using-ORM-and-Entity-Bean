/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mjezerina.ejb.sb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.foi.nwtis.mjezerina.ejb.eb.Myairportslog;

/**
 *
 * @author Matija
 */
@Stateless
public class MyairportslogFacade extends AbstractFacade<Myairportslog> {

    @PersistenceContext(unitName = "NWTiS_DZ3_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyairportslogFacade() {
        super(Myairportslog.class);
    }

    /**
     * Metoda koja pomoću JPQL upita dohvaća zbroj dana preuzimanja aerodroma prema parametru ident aerodroma
     * @param ident
     * @return objekt tipa long, sadrži broj aerodroma koje korisnici prate
     */
    public Long dohvatiBrojDanaPreuzimanja(String ident) {
        Query query = em.createQuery("SELECT COUNT(m.stored) FROM Myairportslog m WHERE m.airports.ident" + "=:ident").setParameter("ident", ident);
        return (Long) query.setParameter("ident", ident).getSingleResult();
    }

    /**
     * Metoda koja pomoću JPQL upita dohvaća listu myairports log prema ident parametru
     * @param ident
     * @return kolekcija List tipa myairportslog
     */
    public List<Myairportslog> dohvatiMyAirportsLog(String ident) {
        Query query = em.createQuery("SELECT m FROM Myairportslog m WHERE m.airports.ident" + "=:ident").setParameter("ident", ident);
        return query.setParameter("ident", ident).getResultList();
    }
    
    /**
     * Metoda koja pomoću JPQL upita za uneseni parametar ident i datum briše podatak iz tablice myairportslog
     * @param ident
     * @param datum
     */
    public void obrisiPodatak(String ident, Date datum){
        Query query = em.createQuery("DELETE FROM Myairportslog m WHERE m.airports.ident" + "=:ident" + " AND m.myairportslogPK.flightdate" + "=:datum").setParameter("ident", ident).setParameter("datum", datum);
        query.executeUpdate();
    }

}
