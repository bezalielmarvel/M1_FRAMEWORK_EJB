package fr.pantheonsorbonne.ufr27.miage.ejb;

import java.util.HashMap;

import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Ticket;

public interface PriceComputingService {
	
	public HashMap<String,Double> calculatePrice(Flight flight);

}
