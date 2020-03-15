package fr.pantheonsorbonne.ufr27.miage.ejb;

import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

public interface FlightService {
	
	public List<Vol> getVols(String arrival, String departure, String date) throws NoSuchFlightException ;
	public void deleteFlight(int flightNumber, String date) throws NoSuchFlightException;

}
