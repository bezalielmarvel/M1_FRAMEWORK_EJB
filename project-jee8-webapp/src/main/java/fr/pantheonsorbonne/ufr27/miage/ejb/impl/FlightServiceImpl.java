package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.FlightService;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Airport;
import fr.pantheonsorbonne.ufr27.miage.jpa.Company;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
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
			v.setNumber(flight.getNumber());
			v.setCompany(flight.getCompany().getName());
			v.setDepartureAirport(flight.getDeparture());
			v.setArrivalAirport(flight.getArrival());
			v.setDepartureTime(flight.getDepartureTime().toString());
			v.setArrivalTime(flight.getArrivalTime().toString());
			v.setDate(flight.getDate().toString());
			v.setDuration(flight.getFlightDuration());
			v.setPrixClasseA(prices.get("A"));
			v.setPrixClasseB(prices.get("B"));			
			v.setPrixClasseC(prices.get("C"));
			
			
			int countAvailable = flight.getSeats().stream()
	                .filter(c -> c.isAvailable() == true)
	                .collect(Collectors.toList()).size()
					;	
			
			v.setNbTotalPlacesDisponibles(countAvailable);
			
			String typeAvailable = flight.getSeats().stream()
					.filter(c -> c.isAvailable() == true).toString();
			
			int cptA = 0;
			int cptB = 0;
			int cptC = 0;
			
			for (Seat s : flight.getSeats()) {
	            if	(s.isAvailable()) {
	            	if (s.getClasse() == "A") {
	            		cptA++;
	            	}
	            	if (s.getClasse() == "B") {
	            		cptB++;
	            	}
	            	if (s.getClasse() == "C") {
	            		cptC++;
	            	}
	            }
	            continue;     	
	        }
			
			v.setNbPlacesTypeA(cptA);
			v.setNbPlacesTypeB(cptB);
			v.setNbPlacesTypeC(cptC);
			
			vols.add(v);
		}
		
		return vols;
	}

}
