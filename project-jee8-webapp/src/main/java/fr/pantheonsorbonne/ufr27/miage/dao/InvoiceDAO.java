package fr.pantheonsorbonne.ufr27.miage.dao;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchPassengerException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Contract;
import fr.pantheonsorbonne.ufr27.miage.jpa.Passenger;
import fr.pantheonsorbonne.ufr27.miage.jpa.Invoice;

@ManagedBean
public class InvoiceDAO {

	@Inject
	EntityManager em;

	public Collection<Invoice> getUnpaiedInvoices(int userId) throws NoSuchPassengerException {

		Passenger customer = em.find(Passenger.class, userId);
		if (customer == null) {
			throw new NoSuchPassengerException();
		}
		return customer.getContracts().//
				stream().//
				map(Contract::getInvoices).//
				flatMap(Collection::stream).//
				filter(i -> !i.isPayed()).//
				collect(Collectors.toSet());
	}

	public double getUserDebt(int userId) throws NoSuchPassengerException {

		return this.getUnpaiedInvoices(userId).stream().//
				map(i -> i.getContract().getMonthlyFare()).//
				collect(Collectors.summingDouble(Double::doubleValue));
	}

}
