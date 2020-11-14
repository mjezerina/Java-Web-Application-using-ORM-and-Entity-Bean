/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mjezerina.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.foi.nwtis.mjezerina.ejb.eb.Myairports;
import org.foi.nwtis.podaci.Aerodrom;

/**
 *
 * @author Matija
 */
@Stateless
public class MyairportsFacade extends AbstractFacade<Myairports> {

    @PersistenceContext(unitName = "NWTiS_DZ3_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyairportsFacade() {
        super(Myairports.class);
    }

    /**
     * Metoda koja putem JPQL upita sa parametrom username dohvaća broj aerodroma koje odabrani korisnik prati
     * @param username
     * @return
     */
    public Long dohvatiBrojAerodroma(String username) {
        Query query = em.createQuery("SELECT COUNT(m.ident) FROM Myairports m WHERE m.username.korIme" + "=:username").setParameter("username", username);
        return (Long)query.setParameter("username", username).getSingleResult();
    }
    
    /**
     * Metoda koja putem JPQL upita sa parametrom username dohvaća Listu tipa Myairports koja sadrži nazive svih aerodrome koje korisnik prati
     * @param username
     * @return Lista tipa myairports
     */
    public List<Myairports> dohvatiAerodromeKorisnika(String username){
        Query query = em.createQuery("SELECT m FROM Myairports m WHERE m.username.korIme" + "=:username").setParameter("username", username);
        return query.setParameter("username", username).getResultList();
    }
    
    /**
     * Metoda koja putem JPQL upita sa parametrom ident dohvaća broj korisnika koji prate određeni aerodrom
     * @param ident
     * @return objekt tipa long
     */
    public Long dohvatiBrojKorisnikaPrate(String ident){
        Query query = em.createQuery("SELECT COUNT(m.username) FROM Myairports m WHERE m.ident.ident" + "=:ident").setParameter("ident", ident);
        return (Long)query.setParameter("ident", ident).getSingleResult();
    }

}
