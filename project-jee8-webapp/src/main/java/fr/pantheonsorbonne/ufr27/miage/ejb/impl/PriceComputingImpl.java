package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.jpa.Ticket;

public class PriceComputingImpl implements PriceComputingService {

	@Inject
	FlightDAO dao;
	
	private static float PRICE = 100;
	
	private static float TOTAL_SEATS = 90;
	
	private static float SEATS_BY_CLASSE = 30;

	
	@Override
	public HashMap<String, Double> calculatePrice(Flight flight) {

		List<Seat> seats = flight.getSeats();
		
		long unavailableSeats = seats.stream()
                .filter(f -> false == f.isAvailable())
                .count();
						
		double prxA = PRICE + unavailableSeats * 10;
		double prxB = prxA * 1.10;
		double prxC = prxB * 1.10;
		
		HashMap<String, Double> prices = new HashMap<String, Double>();
		prices.put("A",prxA);
		prices.put("B",prxB);
		prices.put("C",prxC);

		return prices;

	}

}
