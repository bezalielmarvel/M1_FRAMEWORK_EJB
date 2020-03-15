package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.ReservationDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;

public class PriceComputingImpl implements PriceComputingService {

	@Inject
	FlightDAO dao;
	
	@Inject
	ReservationDAO reservationDao;
	
	private static float PRICE = 100;
			
	//RETOURNE HASHMAP CLASSE: PRIX
	@Override
	public HashMap<String, Double> calculatePrice(Flight flight) {

		List<Seat> seats = flight.getSeats();
		
		long unavailableSeats = seats.stream()
                .filter(f -> false == f.isAvailable())
                .count();

		double prxA=0;
		double prxB=0;
		double prxC=0;
		
		if(unavailableSeats == 0) {
			
			prxA = PRICE;
			prxB = PRICE * 1.1;
			prxC = PRICE * 1.21; 
			
		} else {

			List<Reservation> r = reservationDao.getReservationsFromFlight(flight);
			
			//Reservation la plus récente
	        Reservation rt =  Collections.max(r, Comparator.comparing(s -> s.getCreated()));

			double prx = rt.getPrice();

			prxA = prx + 10;
			prxB = (prx + 10) * 1.1 ;
			prxC = (prx + 10) * 1.21;
			
		}
		
		HashMap<String, Double> prices = new HashMap<String, Double>();
		prices.put("A",prxA);
		prices.put("B",prxB);
		prices.put("C",prxC);

		return prices;

	}
	

	
}
