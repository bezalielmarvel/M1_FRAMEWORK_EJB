package fr.pantheonsorbonne.ufr27.miage.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Airport;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Aeroport;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

@ManagedBean
public class FlightDAO {

	@Inject
	EntityManager em;

	public Vol getFlightFromId(int id) throws NoSuchUserException {

		Flight flight = em.find(Flight.class, id);
		if(flight==null) {
			throw new NoSuchUserException();
		}
		Vol vol = new ObjectFactory().createVol();
//		vol.setName(flight.getName());
		return vol;

	}
	
	public Flight getFlightFromNumberAndDate(int flightNumber, String date) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
		
		Root<Flight> i = query.from(Flight.class);
	    
	    Predicate p1 = builder.equal(i.get("number"), flightNumber);
	    Predicate p2 = builder.equal(i.get("date"), LocalDate.parse(date));	    

	    query.select(i);
	    query.where(builder.and(p1,p2));
	    
		List<Flight> flights = em.createQuery(query).getResultList();
		
		Flight flight = flights.get(0);

		return flight;
		
	}
	
	public List<Seat> getSeatsFromFlight(int id) {
		Flight flight = em.find(Flight.class, id);
		return flight.getSeats();
	}
	
	
	public List<Seat> getSeatsFromFlight(int id, String classe, boolean available) {
		Flight flight = em.find(Flight.class, id);
		return flight.getSeats().stream()
                .filter(c -> classe.equals(c.getClasse()) && available == c.isAvailable())
                .collect(Collectors.toList())
				;
	}
	
	public List<Seat> getSeatsFromFlight(int id, boolean available) {
		Flight flight = em.find(Flight.class, id);
		return flight.getSeats().stream()
                .filter(c -> available == c.isAvailable())
                .collect(Collectors.toList())
				;
	}
		
	
	public List<Seat> getSeatsFromFlight(int id, String classe) {
		Flight flight = em.find(Flight.class, id);
		return flight.getSeats().stream()
                .filter(c -> classe.equals(c.getClasse()))
                .collect(Collectors.toList())
				;
	}
	

	public List<Flight> getFlightFromArrival(String arrival) {
		
		
		int ageMax = 25;

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
		Root<Flight> i = query.from(Flight.class);
		//query.select(i);
		//query.where(builder.equal(i.get("arrival").as(String.class), arrival));
		
		
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
}
