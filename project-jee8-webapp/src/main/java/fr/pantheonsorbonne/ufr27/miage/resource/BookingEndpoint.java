package fr.pantheonsorbonne.ufr27.miage.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.SeatUnavailableException;

@Path("/book")
public class BookingEndpoint {

	@Inject
	ReservationService service;
	
	
	/* CREATION D'UNE RESERVATION */
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
	
	
	/* ANNULATION D'UNE RESERVATION */
	@POST
	@Path("/cancel")
	public Response cancelReservation(@QueryParam("reservationId") String reservationId) {
		try {
			service.cancelReservation(reservationId);
			return Response.ok().build();
		} catch (NoSuchReservationException e) {
			return Response.status(404, "No such reservation").build();
		}
	}
		
	
	
	
//	public Booking getReservation(@QueryParam("customerId") int customerId) {
//		return service.getReservation(customerId);
//	}
	
}
