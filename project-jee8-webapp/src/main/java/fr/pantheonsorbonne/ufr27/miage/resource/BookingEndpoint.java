package fr.pantheonsorbonne.ufr27.miage.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.GymService;
import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.exception.SeatUnavailableException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Address;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Booking;

@Path("/book")
public class BookingEndpoint {

	@Inject
	ReservationService service;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createReservation(
			@QueryParam("flightNumber") int flightNumber, 
			@QueryParam("customerId") int customerId, 
			@QueryParam("classe") String classe,
			@QueryParam("date") String date) {
		try {
		     return Response.ok(service.createReservation(flightNumber, customerId, classe, date)).build();
		} catch (SeatUnavailableException e) {
			return Response.status(404, "Seat unavailable").build();
		} catch (NoSuchFlightException e) {
			return Response.status(404, "No such flight").build();
		}
	}
	
	@POST
	@Path("/cancel")
	public Response cancelReservation(@QueryParam("flightId") String flightId) {
		try {
			service.cancelReservation(flightId);
			return Response.ok().build();
		} catch (NoSuchReservationException e) {
			return Response.status(404, "No such reservation").build();
		}
	}
		
//	public Booking getReservation(@QueryParam("customerId") int customerId) {
//		return service.getReservation(customerId);
//	}
	
}
