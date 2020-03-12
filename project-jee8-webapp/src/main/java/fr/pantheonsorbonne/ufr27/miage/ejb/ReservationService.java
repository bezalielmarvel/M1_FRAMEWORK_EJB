package fr.pantheonsorbonne.ufr27.miage.ejb;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Booking;

public interface ReservationService {

	public Booking createReservation(int flightId, int customerId, String classe, String date);
	//TO:DO EXCEPTIONS
	public void cancelReservation(String reservationId);
	public String generateReservationID(int size);


}
