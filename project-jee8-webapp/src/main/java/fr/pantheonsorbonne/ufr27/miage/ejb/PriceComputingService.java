package fr.pantheonsorbonne.ufr27.miage.ejb;

import java.util.HashMap;

import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;

public interface PriceComputingService {
	
	public HashMap<String,Double> calculatePrice(Flight flight);
	
}
