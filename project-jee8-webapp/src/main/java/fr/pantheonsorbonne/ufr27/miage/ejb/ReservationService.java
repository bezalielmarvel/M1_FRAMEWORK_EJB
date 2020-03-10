package fr.pantheonsorbonne.ufr27.miage.ejb;


public interface ReservationService {

	public String createReservation(int flightId, int customerId, String classe);
	//TO:DO EXCEPTIONS
	public void cancelReservation(String reservationId);


}
