package fr.pantheonsorbonne.ufr27.miage.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.dao.PassengerDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.GymService;
import fr.pantheonsorbonne.ufr27.miage.ejb.PassengerService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchPassengerException;
import fr.pantheonsorbonne.ufr27.miage.exception.UserHasDebtException;
import fr.pantheonsorbonne.ufr27.miage.jms.PaymentValidationAckownledgerBean;
import fr.pantheonsorbonne.ufr27.miage.jms.payment.PaymentProcessorBean;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Address;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passenger;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.User;

@Path("/passenger")
public class PassengerEndpoint {

	@Inject
	PassengerService service;
	
	@GET
	@Path("/flight/{flightNumber}/date/{flightDate}")
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Passenger> getPassengersOnFlight(
			@PathParam("flightNumber") int flightNumber,
			@PathParam("flightDate") String flightDate) {
		try {
			return service.getPassengersOnFlight(flightNumber, flightDate);
		} catch (NoSuchFlightException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
//	@GET
//	@Path("/{userId}")
//	public User getUser(@PathParam("userId") int userId) {
//		try {
//			return dao.getUserFromId(userId);
//		} catch (NoSuchPassengerException e) {
//			throw new WebApplicationException(404);
//		}
//	}

//	@Consumes(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	@PUT
//	@Path("/{userId}")
//	public Response updateUserAddress(@PathParam("userId") int userId, Address address) {
//		try {
//			dao.updateUserAddress(userId, address);
//			return Response.ok().build();
//		} catch (NoSuchPassengerException e) {
//			throw new WebApplicationException(404);
//		}
//	}

//	@DELETE
//	@Path("/{userId}")
//	public Response deleteMemberShip(@PathParam("userId") int userId) throws UserHasDebtException {
//		try {
//			service.cancelMemberShip(userId);
//			return Response.ok().build();
//		} catch (
//
//		NoSuchPassengerException e) {
//			throw new WebApplicationException("no such user", 404);
//		}
//	}

}
