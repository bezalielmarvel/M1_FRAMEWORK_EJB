package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.FlightService;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

public class FlightServiceImpl implements FlightService {

	@Inject
	FlightDAO dao;
	
	@Inject
	PriceComputingService service;
	
	@Override
	public List<Vol> getVols(String arrival, String departure, String date) {
		
		
		List<Flight> flights = dao.getFlights(arrival, departure, date);
		
		List<Vol> vols = new ArrayList<Vol>();
		
		
		for (Flight flight : flights) {
			
			HashMap<String, Double> prices = service.calculatePrice(flight);

			Vol v = new ObjectFactory().createVol();
			
			v.setId(flight.getId());
			v.setName(flight.getArrival());
			v.setDeparture(flight.getDeparture());
			v.setArrival(flight.getArrival());
			v.setDate(flight.getDate().toString());
			v.setPrixClasseA(prices.get("A"));
			v.setPrixClasseB(prices.get("B"));			
			v.setPrixClasseC(prices.get("C"));
			
			
			int countAvailable = flight.getSeats().stream()
	                .filter(c -> c.isAvailable() == true)
	                .collect(Collectors.toList()).size()
					;	
			
			v.setPlacesDisponibles(countAvailable);
			
			vols.add(v);
		}
		
		return vols;
	}

}
