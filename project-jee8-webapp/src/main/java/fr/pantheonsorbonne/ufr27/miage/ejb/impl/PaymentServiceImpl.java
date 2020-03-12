package fr.pantheonsorbonne.ufr27.miage.ejb.impl;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.dao.InvoiceDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.PaymentDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.PaymentService;
import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoDebtException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Customer;
import fr.pantheonsorbonne.ufr27.miage.jpa.Payment;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ccinfo;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ticket;

@ApplicationScoped
@ManagedBean
public class PaymentServiceImpl implements PaymentService {

	@Inject
	EntityManager em;
	
	@Inject
	PaymentDAO paymentDao;
	
	@Inject
	ReservationService service;

	@Inject
	InvoiceDAO invoiceDao;

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("PaymentQueue")
	private Queue queue;

	private Connection connection;

	private Session session;

	
	private MessageProducer messageProducer;

//	@PostConstruct
//	private void init() {
//
//		try {
//			connection = connectionFactory.createConnection("nicolas", "nicolas");
//			connection.start();
//			session = connection.createSession();
//			messageProducer = session.createProducer(queue);
//		} catch (JMSException e) {
//			throw new RuntimeException("failed to create JMS Session", e);
//		}
//	}
//
//	@Override
//	public int initiatePayAllDebts(Ccinfo info, int userId) throws NoDebtException, NoSuchUserException {
//		em.getTransaction().begin();
//		try {
//
//			double amount = invoiceDao.getUserDebt(userId);
//			if (amount <= 0) {
//				throw new NoDebtException();
//			}
//			Payment p = new Payment();
//			p.setAmount(amount);
//			p.setValidated(false);
//			p.getInvoices().addAll(invoiceDao.getUnpaiedInvoices(userId));
//			em.persist(p);
//
//			Message message = session.createMessage();
//			message.setStringProperty("ccnumber", info.getNumber());
//			message.setStringProperty("date", info.getValidityDate());
//			message.setIntProperty("ccv", info.getCcv());
//			message.setDoubleProperty("amount", amount);
//			message.setIntProperty("userId", userId);
//			message.setIntProperty("paymentId", p.getId());
//			messageProducer.send(message);
//			em.getTransaction().commit();
//			return p.getId();
//
//		} catch (JMSException e) {
//			em.getTransaction().rollback();
//			throw new RuntimeException("failed to initiate payment", e);
//		}
//	}
//
//	@Override
//	public int initiatePayment(Ccinfo info, int userId, int invoiceId, double amount) {
//		em.getTransaction().begin();
//		try {
//
//			Payment p = new Payment();
//			p.setAmount(amount);
//			p.setValidated(false);
//			em.persist(p);
//
//			Message message = session.createMessage();
//			message.setStringProperty("ccnumber", info.getNumber());
//			message.setStringProperty("date", info.getValidityDate());
//			message.setIntProperty("ccv", info.getCcv());
//			message.setDoubleProperty("amount", amount);
//			message.setIntProperty("userId", userId);
//			message.setIntProperty("paymentId", p.getId());
//			messageProducer.send(message);
//			em.getTransaction().commit();
//			return p.getId();
//
//		} catch (JMSException e) {
//			em.getTransaction().rollback();
//			throw new RuntimeException("failed to initiate payment", e);
//		}
//		
//	}

	@Override
	public Ticket payReservation(String paymentCode) {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Reservation r = paymentDao.getReservationFromPaymentCode(paymentCode);
		
		r.setIssued(true);
		r.setGeneratedId(service.generateReservationID(20));
		
		Ticket t = new ObjectFactory().createTicket();
		
		t.setGeneratedId(r.getGeneratedId());
		t.setArrivalAirport(r.getSeat().getFlight().getArrival());
		t.setArrivalTime(r.getSeat().getFlight().getArrivalTime().toString());
		t.setClasse(r.getSeat().getClasse());
		t.setDate(r.getSeat().getFlight().getDate().toString());
		t.setDepartureAirport(r.getSeat().getFlight().getDeparture());
		t.setDepartureTime(r.getSeat().getFlight().getDepartureTime().toString());
		t.setFlightCompany(r.getSeat().getFlight().getCompany().getName());
		t.setFlightNumber(r.getSeat().getFlight().getNumber());
		t.setPrix(r.getPrice());
		t.setSeat(r.getSeat().getNumber());
		t.setUserFname(r.getCustomer().getFname());
		t.setUserLname(r.getCustomer().getLname());
		
		em.persist(r);
		
		tx.commit();
				
		return t;
	}

}
