package fr.pantheonsorbonne.ufr27.miage.ejb;

import fr.pantheonsorbonne.ufr27.miage.jpa.Ticket;

public interface PriceComputingService {
	
	public void calculatePrice(Ticket ticket);

}
