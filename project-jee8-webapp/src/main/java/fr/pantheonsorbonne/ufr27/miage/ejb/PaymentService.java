package fr.pantheonsorbonne.ufr27.miage.ejb;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ticket;

public interface PaymentService {
	public Ticket payReservation(String paymentCode) throws NoSuchReservationException;
}