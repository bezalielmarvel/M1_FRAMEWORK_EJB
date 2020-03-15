package fr.pantheonsorbonne.ufr27.miage.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchPassengerException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Airport;
import fr.pantheonsorbonne.ufr27.miage.jpa.Company;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Aeroport;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

@ManagedBean
public class FlightDAO {

	@Inject
	EntityManager em;

	public Vol getFlightFromId(int id) throws NoSuchPassengerException {

		Flight flight = em.find(Flight.class, id);
		if(flight==null) {
			throw new NoSuchPassengerException();
		}
		Vol vol = new ObjectFactory().createVol();
		return vol;

	}
	
	
	public Flight getFlightFromNumberAndDate(int flightNumber, String date) throws NoSuchFlightException {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
		
		Root<Flight> i = query.from(Flight.class);
	    
	    Predicate p1 = builder.equal(i.get("number"), flightNumber);
	    Predicate p2 = builder.equal(i.get("date"), LocalDate.parse(date));	    

	    query.select(i);
	    query.where(builder.and(p1,p2));
	    
		List<Flight> flights = em.createQuery(query).getResultList();
		
		if(flights.size() == 0) {
			throw new NoSuchFlightException();
		}
		
		Flight flight = flights.get(0);

		return flight;
		
	}
	


	public List<Flight> getFlightFromArrival(String arrival) {
		
		int ageMax = 25;

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
		Root<Flight> i = query.from(Flight.class);
		
		
		Join<Flight, Aeroport> airport = i.join("arrival");
		query.where(builder.equal(airport.get("IATA"), arrival));
	
		
		List<Flight> flights = em.createQuery(query).getResultList();

		return flights;
		
	}
	

	public List<Flight> getFlights(String arrival, String departure, String date) {
		
		int ageMax = 25;

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
		
		Root<Flight> i = query.from(Flight.class);
		
		
	    Join<Flight, Aeroport> joinOrg = i.join("arrival");
	    Join<Flight, Aeroport> joinRoles = i.join("departure");
	    
	    
	    Predicate p1 = builder.equal(joinRoles.get("IATA"),arrival);
	    Predicate p2 = builder.equal(joinOrg.get("IATA"), departure);
	    Predicate p3 = builder.equal(i.get("date"), LocalDate.parse(date));

	    query.where(builder.and(p1, p2, p3));
		
		
		List<Flight> flights = em.createQuery(query).getResultList();
		
		return flights;

	}

	
	public void deleteFlight(int flightId) throws NoSuchFlightException {
		
		em.getTransaction().begin();
		Flight f = em.find(Flight.class, flightId);
		
		if(f==null) {
			throw new NoSuchFlightException();
		}
		
		List<Seat> seats = f.getSeats();
		
		for(Seat s : seats) {
			em.remove(s);
		}
		
		em.remove(f);
		em.getTransaction().commit();
		
		return;
		
	}
}