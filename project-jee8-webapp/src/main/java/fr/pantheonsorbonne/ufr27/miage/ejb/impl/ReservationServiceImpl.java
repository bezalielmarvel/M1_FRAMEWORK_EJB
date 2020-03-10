package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.util.Random;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Card;
import fr.pantheonsorbonne.ufr27.miage.jpa.Contract;
import fr.pantheonsorbonne.ufr27.miage.jpa.Customer;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.jpa.Ticket;

public class ReservationServiceImpl implements ReservationService {

	@Inject
	EntityManager em;
	
	private static double price = 100;
	
	private final static Random rand = new Random();

	@Override
	public String createReservation(int flightId, int customerId, String classe) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Ticket t = new Ticket();
		t.setCustomer(em.find(Customer.class, customerId));
		
		Flight f = em.find(Flight.class, flightId);
		f.getSeats().stream()
        .filter(c -> c.isAvailable() == true)
        .collect(Collectors.toList()).get(1).setAvailable(false);
		em.persist(f);
		
		t.setSeat(f.getSeats().get(1));
		t.setPrice((float) this.calculatePrice(f, classe));
		t.setGeneratedId(this.generateReservationID(12));
		em.persist(t);
		
		tx.commit();

		return "RESERVATION FAITE";
		
	}

	@Override
	public void cancelReservation(String reservationId) {
		// TODO Auto-generated method stub

	}
	
	public String generateReservationID(int size) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append((char) ('A' + rand.nextInt(25)));
		}

		return builder.toString();	
	}
	
	private double calculatePrice(Flight f, String classe) {
		
		int countUnavailable = f.getSeats().stream()
                .filter(c -> c.isAvailable() == false)
                .collect(Collectors.toList()).size();
		
		double prxA = price + countUnavailable * 10;
		
		if (classe == "A") {
			return prxA;
		} else if(classe == "B") {
			return prxA * 1.10;
		} else if(classe == "C") {
			return prxA * 1.21;
		}
		
		return prxA;
				
	}

}
