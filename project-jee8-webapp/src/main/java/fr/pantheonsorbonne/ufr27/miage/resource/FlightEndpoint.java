package fr.pantheonsorbonne.ufr27.miage.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jms.PaymentValidationAckownledgerBean;
import fr.pantheonsorbonne.ufr27.miage.jms.payment.PaymentProcessorBean;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

@Path("/flight")
public class FlightEndpoint {
	
	@Inject
	FlightDAO dao;

	@Inject
	PaymentValidationAckownledgerBean b1_;

	@Inject
	PaymentProcessorBean b2_;

	@GET
	@Path("/{flightId}")
	public Vol getVol(@PathParam("flightId") int flightId) {
		try {
			return dao.getFlightFromId(flightId);
		} catch (NoSuchUserException e) {
			throw new WebApplicationException(404);
		}
	}
	

	@GET
	public List<Vol> getVolByArrival(@QueryParam("arrival") String arrival) {
		
			return dao.getFlightFromArrival(arrival);
		
	}
	
}
