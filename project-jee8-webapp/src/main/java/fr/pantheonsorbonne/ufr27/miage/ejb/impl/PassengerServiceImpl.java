package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.ReservationDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PassengerService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Address;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passenger;

public class PassengerServiceImpl implements PassengerService {
	
	@Inject
	ReservationDAO reservationDao;
	
	@Inject
	FlightDAO flightDao;
	
	@Override
	public List<Passenger> getPassengersOnFlight(int flightNumber, String date) throws NoSuchFlightException {

		List<Passenger> passengers = new ArrayList<Passenger>();
			
			Flight f = flightDao.getFlightFromNumberAndDate(flightNumber, date);
			
			//Liste des réservations du vol
			List<Reservation> reservations = reservationDao.getReservationsFromFlight(f);
			
			for (Reservation r : reservations) {
				Passenger p = new ObjectFactory().createPassenger();
				p.setId(r.getPassenger().getId());
				p.setFirstName(r.getPassenger().getFname());
				p.setLastName(r.getPassenger().getLname());
				p.setGeneratedID(r.getGeneratedId());
				p.setSeatNumber(r.getSeat().getNumber());
				p.setClasse(r.getSeat().getClasse());
				
				Address a = r.getPassenger().getContactInfo().getAddress();
				p.setAddress(
						a.getStreetNumber() + " " + 
						a.getStreetName() + " " + 
						a.getZipCode() + " " + 
						a.getCountry());		
				
				passengers.add(p);
			}
		return passengers;
	}

}
