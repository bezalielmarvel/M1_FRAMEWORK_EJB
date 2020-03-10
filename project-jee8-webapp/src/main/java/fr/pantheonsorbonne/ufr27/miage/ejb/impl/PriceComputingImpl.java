package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.jpa.Ticket;

public class PriceComputingImpl implements PriceComputingService {

	@Inject
	FlightDAO dao;
	
	private static float price = 100;
	
	private static float totalSeats = 90;
	
	private static float seatsByClasse = 30;

	
	@Override
	public void calculatePrice(Ticket ticket) {

		List<Seat> seats = dao.getSeatsFromFlight(ticket.getId());
				
		long countAvailableA = dao.getSeatsFromFlight(ticket.getId(), "A", false).size();
		
		float prxA = price + countAvailableA * 10;
		
		//compte le nombre de places dans chaque classe
		long countA = seats.stream()
                .filter(c -> "A".equals(c.getClasse()))
                .count();
		
		
		long countB = seats.stream()
                .filter(c -> "B".equals(c.getClasse()))
                .count();
		
		
		long countC = seats.stream()
                .filter(c -> "C".equals(c.getClasse()))
                .count();
		
		long countSeats = countA + countB + countC;

	}

}
