package fr.pantheonsorbonne.ufr27.miage.ejb;

import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passenger;

public interface PassengerService {
	
	public List<Passenger> getPassengersOnFlight(int flightNumber, String date) throws NoSuchFlightException;
}
