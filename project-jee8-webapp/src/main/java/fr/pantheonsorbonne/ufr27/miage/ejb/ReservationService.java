package fr.pantheonsorbonne.ufr27.miage.ejb;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.SeatUnavailableException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ticket;

public interface ReservationService {

	public Booking createReservation(int flightId, int customerId, String classe, String date) throws SeatUnavailableException, NoSuchFlightException;
	public void cancelReservation(String reservationId) throws NoSuchReservationException;
	public String generateReservationID(int size);
	public Ticket getTicketFromId(String reservationId) throws NoSuchReservationException;

}
