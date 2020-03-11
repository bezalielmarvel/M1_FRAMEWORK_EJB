package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.ejb.InitializeService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Airport;
import fr.pantheonsorbonne.ufr27.miage.jpa.Company;
import fr.pantheonsorbonne.ufr27.miage.jpa.Customer;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.jpa.Ticket;

public class InitializeServiceImpl implements InitializeService {
	
	@Inject
	EntityManager em;
	
	public void initializeDatabase() {
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		Airport prs = new Airport();
		prs.setId(1);
		prs.setCity("PAR");
		prs.setCountry("France");
		prs.setIATA("CDG");
		
		Airport bdx = new Airport();
		bdx.setId(2);
		bdx.setCity("BDX");
		bdx.setCountry("France");
		bdx.setIATA("BDX");
		
		Company af = new Company();
		af.setId(1);
		af.setName("AF");
		
		LocalDate date = LocalDate.of(2020,3,11);
		
		// FLIGHT 1
		Flight f1 = new Flight();
		f1.setId(6252);
		f1.setArrival(prs);
		f1.setDeparture(bdx);
		f1.setCompany(af);
		f1.setDate(date);
		
		LocalTime departureTimeF1 = LocalTime.of(8,0);
		LocalTime arrivalTimeF1 = LocalTime.of(9,10);
		
		f1.setArrivalTime(arrivalTimeF1);
		f1.setDepartureTime(departureTimeF1);
		f1.setFlightDuration();
		
		/////// FLIGHT 2 ///////
		Flight f2 = new Flight();
		f2.setId(6256);
		f2.setArrival(prs);
		f2.setDeparture(bdx);
		f2.setCompany(af);
		f2.setDate(date);
		
		LocalTime departureTimeF2 = LocalTime.of(10,0);
		LocalTime arrivalTimeF2 = LocalTime.of(11,10);
		
		f2.setArrivalTime(arrivalTimeF2);
		f2.setDepartureTime(departureTimeF2);
		f2.setFlightDuration();
		
		/////// FLIGHT 3 ///////
		Flight f3 = new Flight();
		f3.setId(7624);
		f3.setArrival(prs);
		f3.setDeparture(bdx);
		f3.setCompany(af);
		f3.setDate(date);
		
		LocalTime departureTimeF3 = LocalTime.of(12,0);
		LocalTime arrivalTimeF3 = LocalTime.of(13,10);
		
		f3.setArrivalTime(arrivalTimeF3);
		f3.setDepartureTime(departureTimeF3);
		f3.setFlightDuration();
		
		
		List<Seat> seatsf1 = new ArrayList<Seat>();
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f1);
			s.setAvailable(true);
			s.setClasse("A");
			seatsf1.add(s);	
			em.persist(s);
		}
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f1);
			s.setAvailable(true);
			s.setClasse("B");
			seatsf1.add(s);	
			em.persist(s);
		}
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f1);
			s.setAvailable(true);
			s.setClasse("C");
			seatsf1.add(s);	
			em.persist(s);
		}
		
		f1.setSeats(seatsf1);
		em.persist(f1);
		
		List<Seat> seatsf2 = new ArrayList<Seat>();
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f2);
			s.setAvailable(true);
			s.setClasse("A");
			seatsf2.add(s);	
			em.persist(s);
		}
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f2);
			s.setAvailable(true);
			s.setClasse("B");
			seatsf2.add(s);	
			em.persist(s);
		}
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f2);
			s.setAvailable(true);
			s.setClasse("C");
			seatsf2.add(s);	
			em.persist(s);
		}
		
		f2.setSeats(seatsf2);
		em.persist(f2);
		
		List<Seat> seatsf3 = new ArrayList<Seat>();
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f3);
			s.setAvailable(true);
			s.setClasse("A");
			seatsf3.add(s);	
			em.persist(s);
		}
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f3);
			s.setAvailable(true);
			s.setClasse("B");
			seatsf3.add(s);	
			em.persist(s);
		}
		
		for(int i=0; i<30; i++) {
			Seat s = new Seat();
			s.setFlight(f3);
			s.setAvailable(true);
			s.setClasse("C");
			seatsf3.add(s);	
			em.persist(s);
		}
		
		f3.setSeats(seatsf3);
		em.persist(f3);

		em.persist(prs);
		em.persist(bdx);
		em.persist(af);
	
		et.commit();

		return;
	}
}
