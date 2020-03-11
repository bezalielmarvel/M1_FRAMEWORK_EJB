package fr.pantheonsorbonne.ufr27.miage.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.FlightService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jms.PaymentValidationAckownledgerBean;
import fr.pantheonsorbonne.ufr27.miage.jms.payment.PaymentProcessorBean;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

@Path("/flight")
public class FlightEndpoint {
	
	@Inject
	FlightDAO dao;
	
	@Inject
	FlightService service;

	@GET
	@Path("/{flightId}")
	public Vol getVol(@PathParam("flightId") int flightId) {
		try {
			return dao.getFlightFromId(flightId);
		} catch (NoSuchUserException e) {
			throw new WebApplicationException(404);
		}
	}
	

	//@GET
	//public List<Vol> getVolByArrival(@QueryParam("arrival") String arrival) {
			//return service.getVols(arrival);	
	//}
	
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@GET
	public List<Vol> getVols(
			@QueryParam("arrival") String arrival, 
			@QueryParam("departure") String departure, 
			@QueryParam("date") String date) throws ParseException {
			return service.getVols(arrival,departure,date);	
	}
	

}
