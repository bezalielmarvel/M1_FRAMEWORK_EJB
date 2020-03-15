package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.ReservationDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PriceComputingService;
import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.SeatUnavailableException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Passenger;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.jpa.Seat;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ticket;

public class ReservationServiceImpl implements ReservationService {

	@Inject
	EntityManager em;
	
	@Inject
	FlightDAO dao;
	
	@Inject
	ReservationDAO bookingDao;
	
	@Inject
	PriceComputingService priceService;
		
	
	private final static String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private final static String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private final static String NUMBER = "0123456789";
	private final static String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	private final static SecureRandom random = new SecureRandom();
	    
	    
	@Override
	public Booking createReservation(int flightNumber, int customerId, String classe, String date) throws SeatUnavailableException, NoSuchFlightException {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Flight f  = dao.getFlightFromNumberAndDate(flightNumber, date);
		
		double price = priceService.calculatePrice(f).get(classe);
		
		List<Seat> seats = f.getSeats().stream()
        .filter(c -> c.isAvailable() == true && classe.equals(c.getClasse()))
        .collect(Collectors.toList());

		//si aucune place n'est disponible
		if(seats.size() == 0) {
			throw new SeatUnavailableException();
		}
		
		Seat s = seats.get(0);
		s.setAvailable(false);
		em.persist(f);

		Reservation t = new Reservation();
		t.setPassenger(em.find(Passenger.class, customerId));	
		t.setSeat(s);
		t.setPrice(price);
		t.setPaymentCode();
		t.setGeneratedId(this.generateReservationID(12));
		em.persist(t);
		
		Booking b = new ObjectFactory().createBooking();
		b.setId(t.getId());
		b.setPaymentCode(t.getPaymentCode());
		b.setPassengerId(t.getPassenger().getId());
		
		tx.commit();

		return b;
		
	}

	
	@Override
	public void cancelReservation(String reservationId) throws NoSuchReservationException  {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Reservation reservation = bookingDao.getReservationFromId(reservationId);
		
		Seat s = reservation.getSeat();
		s.setAvailable(true);
		em.persist(s);
		
		em.remove(reservation);
		
		tx.commit();
		
	}
	
	
	public String generateReservationID(int length) {

	    if (length < 1) throw new IllegalArgumentException();

	    StringBuilder sb = new StringBuilder(length);
	    
	    for (int i = 0; i < length; i++) {
	        int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
	        char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

	        sb.append(rndChar);
	    }

	    return sb.toString();
		    
	}


	@Override
	public Ticket getTicketFromId(String reservationId) throws NoSuchReservationException {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Reservation r = bookingDao.getReservationFromId(reservationId);
		
		Ticket t = new ObjectFactory().createTicket();
		
		t.setGeneratedId(r.getGeneratedId());
		t.setArrivalAirport(r.getSeat().getFlight().getArrival());
		t.setArrivalTime(r.getSeat().getFlight().getArrivalTime().toString());
		t.setClasse(r.getSeat().getClasse());
		t.setDate(r.getSeat().getFlight().getDate().toString());
		t.setDepartureAirport(r.getSeat().getFlight().getDeparture());
		t.setDepartureTime(r.getSeat().getFlight().getDepartureTime().toString());
		t.setFlightCompany(r.getSeat().getFlight().getCompany().getName());
		t.setFlightNumber(r.getSeat().getFlight().getNumber());
		t.setPrix(r.getPrice());
		t.setSeat(r.getSeat().getNumber());
		t.setUserFname(r.getPassenger().getFname());
		t.setUserLname(r.getPassenger().getLname());
		
		em.persist(r);
		
		tx.commit();
				
		return t;
	}


}
