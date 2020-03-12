package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import fr.pantheonsorbonne.ufr27.miage.jpa.Flight;
import fr.pantheonsorbonne.ufr27.miage.jpa.Payment;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;

import javax.inject.Inject;

@ManagedBean
public class PaymentDAO {

	@Inject
	EntityManager em;

	public boolean isPaymentValidated(int paymentId) {

		Payment p = em.find(Payment.class, paymentId);
		if (p == null) {
			throw new NoSuchElementException("No Such Payment");
		}
		return p.isValidated();

	}
	
	public Reservation getReservationFromPaymentCode(String paymentCode) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Reservation> query = builder.createQuery(Reservation.class);
		
		Root<Reservation> i = query.from(Reservation.class);
	    
	    Predicate p1 = builder.equal(i.get("paymentCode"), paymentCode);    

	    query.select(i);
	    query.where(builder.and(p1));
	    
		List<Reservation> reservations = em.createQuery(query).getResultList();
		
		Reservation reservation = reservations.get(0);

		return reservation;
		
	}

}
