package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.jpa.Card;
import fr.pantheonsorbonne.ufr27.miage.jpa.Contract;
import fr.pantheonsorbonne.ufr27.miage.jpa.Customer;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;

public class ReservationServiceImpl implements ReservationService {

	@Inject
	EntityManager em;
	
	@Inject
	FlightDAO dao;
	
	@Inject
	PriceComputingService priceService;
	
	private static double price = 100;
	
	private final static Random rand = new Random();

	@Override
	public Booking createReservation(int flightNumber, int customerId, String classe, String date) {

		//todo rejeter si aucune place disponible
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Reservation t = new Reservation();
		t.setCustomer(em.find(Customer.class, customerId));
		
		int flightID = dao.getFlightFromNumberAndDate(flightNumber, date).getId();
		
		Flight f = em.find(Flight.class, flightID);
		
		double price = priceService.calculatePrice(f).get(classe);
		
		List<Seat> seats = f.getSeats().stream()
        .filter(c -> c.isAvailable() == true && classe.equals(c.getClasse()))
        .collect(Collectors.toList());
		
		int seatIndex;
		
		while(true) {
			Random r = new Random();
			int random = r.nextInt((seats.size() - 0) + 1) + 0;
			if(seats.get(random).isAvailable()) {
				seatIndex = random;
				seats.get(random).setAvailable(false);
				break;
			}
		}
		
		em.persist(f);
		
		t.setSeat(seats.get(seatIndex));
		t.setPrice(price);
		t.setPaymentCode();
		
		Booking b = new ObjectFactory().createBooking();
		b.setId(t.getId());
		b.setPaymentCode(t.getPaymentCode());
		b.setUser(t.getCustomer().getId());
		
//		t.setGeneratedId(this.generateReservationID(12));
//		t.setBooking(b);
		em.persist(t);
		
		tx.commit();

		return b;
		
	}
	
//	@Override
//	public Booking getReservation(int customerId) {
//		
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		
//		
//		
//
//	}
	
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
