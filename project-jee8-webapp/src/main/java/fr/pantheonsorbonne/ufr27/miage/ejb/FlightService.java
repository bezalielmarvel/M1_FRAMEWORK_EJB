package fr.pantheonsorbonne.ufr27.miage.ejb;

import java.text.ParseException;
import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

public interface FlightService {
	
	public List<Vol> getVols(String arrival, String departure, String date) ;

}