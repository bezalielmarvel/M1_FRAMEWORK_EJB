package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;

@ManagedBean
public class ReservationDAO {

	@Inject
	EntityManager em;
	
	//to do exception no such reservation
	public Reservation getReservationFromId(String id) throws NoSuchReservationException  {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Reservation> query = builder.createQuery(Reservation.class);
		
		Root<Reservation> i = query.from(Reservation.class);
	    
	    Predicate p1 = builder.equal(i.get("generatedId"), id);

	    query.select(i);
	    query.where(p1);
	    
		List<Reservation> reservation = em.createQuery(query).getResultList();
		
	    if (reservation.isEmpty()) {
	        throw new NoSuchReservationException();
	    } 
	    
		return reservation.get(0);

	}
	
}
