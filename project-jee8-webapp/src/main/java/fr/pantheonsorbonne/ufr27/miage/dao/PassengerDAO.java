package fr.pantheonsorbonne.ufr27.miage.dao;



import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ManagedBean
public class PassengerDAO {

	@Inject
	EntityManager em;
	
	
	
//	public User getUserFromId(int id) throws NoSuchPassengerException {
//
//		Passenger customer = em.find(Passenger.class, id);
//		if(customer==null) {
//			throw new NoSuchPassengerException();
//		}
//		User user = new ObjectFactory().createUser();
//		user.setFname(customer.getLname());
//		user.setLname(customer.getLname());
//		user.setMembershipId(customer.getId());
//		return user;
//
//	}

//	public void updateUserAddress(int userId, Address address) throws NoSuchPassengerException {
//		em.getTransaction().begin();
//		Passenger customer = em.find(Passenger.class, userId);
//		if(customer==null) {
//			throw new NoSuchPassengerException();
//		}
//		fr.pantheonsorbonne.ufr27.miage.jpa.Address customerAddress = customer.getAddress();
//		customerAddress.setCountry(address.getCountry());
//		customerAddress.setStreetNumber(address.getStreetNumber());
//		customerAddress.setStreetName(address.getStreetName());
//		customerAddress.setZipCode(address.getZipCode());
//
//		em.merge(address);
//		em.getTransaction().commit();
//
//	}

}
