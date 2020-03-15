package fr.pantheonsorbonne.ufr27.miage.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchReservationException;
import fr.pantheonsorbonne.ufr27.miage.exception.SeatUnavailableException;

@Path("/ticket")
public class TicketEndpoint {

	@Inject
	ReservationService service;
	
	
	@GET
	@Path("{reservationId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getTicketFromId(@PathParam("reservationId") String reservationId) {
		try {
			return Response.ok(service.getTicketFromId(reservationId)).build();
		} catch (NoSuchReservationException e) {
			return Response.status(404, "No such reservation").build();
		}
	}
	
}
