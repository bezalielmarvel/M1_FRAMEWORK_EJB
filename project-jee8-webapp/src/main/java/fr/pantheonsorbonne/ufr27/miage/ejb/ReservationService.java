package fr.pantheonsorbonne.ufr27.miage.ejb;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.SeatUnavailableException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Booking;

public interface ReservationService {

	public Booking createReservation(int flightId, int customerId, String classe, String date) throws SeatUnavailableException, NoSuchFlightException;
	public void cancelReservation(String reservationId) throws NoSuchReservationException;
	public String generateReservationID(int size);


}
