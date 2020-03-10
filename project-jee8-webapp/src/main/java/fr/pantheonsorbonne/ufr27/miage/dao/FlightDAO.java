package fr.pantheonsorbonne.ufr27.miage.dao;

import java.util.ArrayList;
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
		vol.setName(flight.getName());
		return vol;

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
	

	public List<Vol> getFlightFromArrival(String arrival) {
		
		
		int ageMax = 25;

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
		Root<Flight> i = query.from(Flight.class);
		//query.select(i);
		//query.where(builder.equal(i.get("arrival").as(String.class), arrival));
		
		
		Join<Flight, Aeroport> airport = i.join("arrival");
		query.where(builder.equal(airport.get("IATA"), arrival));
		
		
		
		List<Flight> flights = em.createQuery(query).getResultList();
		List<Vol> vols = new ArrayList<Vol>();
		for (Flight flight : flights) {
			Vol v = new ObjectFactory().createVol();
			v.setId(flight.getId());
			v.setName(flight.getArrival());
			v.setDeparture(flight.getDeparture());
			v.setArrival(flight.getArrival());
			v.setDate(flight.getDate().toString());
			
			int countAvailable = flight.getSeats().stream()
	                .filter(c -> c.isAvailable() == true)
	                .collect(Collectors.toList()).size()
					;	
			
			v.setPlacesDisponibles(countAvailable);
			
			vols.add(v);
		}
		
		// TODO Auto-generated method stub
		return vols;
	}
}
