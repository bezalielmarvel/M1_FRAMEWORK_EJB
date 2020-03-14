package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Aeroport;

@ManagedBean
public class ReservationDAO {

	@Inject
	EntityManager em;
	
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
	
	public List<Reservation> getReservationsFromFlight(Flight f) {

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Reservation> query = builder.createQuery(Reservation.class);
		
		Root<Reservation> i = query.from(Reservation.class);
		
		Join<Reservation, Seat> reservationSeats = i.join("seat");

		query.where(builder.equal(reservationSeats.get("flight"), f));
		
		List<Reservation> flights = em.createQuery(query).getResultList();	  
	    
		return flights;

	}
	
}
