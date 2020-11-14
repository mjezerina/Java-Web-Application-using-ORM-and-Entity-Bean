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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.mjezerina.ejb.eb.Airports;
import org.foi.nwtis.mjezerina.ejb.eb.Airports_;


/**
 *
 * @author Matija
 */
@Stateless
public class AirportsFacade extends AbstractFacade<Airports> {

    @PersistenceContext(unitName = "NWTiS_DZ3_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AirportsFacade() {
        super(Airports.class);
    }

    /**
     * Metoda koja putem JPQL upita kao parametar prima ident aerodroma te dohvaća objekte tipa aerodrom
     * @param ident
     * @return objekt tipa Airports 
     */
    public Airports dohvatiAerodromeKorisnika(String ident) {
        Query query = em.createQuery("SELECT m FROM Airports m WHERE m.ident" + "=:ident").setParameter("ident", ident);
        return (Airports) query.setParameter("ident", ident).getSingleResult();
    }

    /**
     * Metoda koja putem JPQL upita dohvaća aerodrome sličnog naziva prema nazivu koji je unesen kao parametar
     * @param naziv
     * @return lista aerodroma tipa Airports
     */
    public List<Airports> dohvatiAerodromeNaziva(String naziv) {
        Query query = em.createQuery("SELECT m FROM Airports m WHERE m.name" + " LIKE '%" + naziv + "%'");
        return query.getResultList();
    }

    /**
     * Metoda koja putem CriteriaBuildApi upita dohvaća aerodrome sličnog naziva unesenom aerodromu kao parametar
     * @param naziv
     * @return lista aerodroma tipa Airports
     */
    public List<Airports> dohvatiAerodromeCapi(String naziv) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Airports> query = criteriaBuilder.createQuery(Airports.class);
        Root<Airports> airport = query.from(Airports.class);
        query.select(airport)
                .where(criteriaBuilder.like(
                         airport.get(Airports_.name).as(String.class),"%" + naziv + "%"));
        return em.createQuery(query).getResultList();
    }

}
