package fr.pantheonsorbonne.ufr27.miage.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.GymService;
import fr.pantheonsorbonne.ufr27.miage.ejb.ReservationService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Address;

@Path("/book")
public class BookingEndpoint {

	@Inject
	ReservationService service;
	
	@POST
	public Response createReservationc(@QueryParam("flightId") int flightId, @QueryParam("customerId") int customerId, @QueryParam("classe") String classe) {
		service.createReservation(flightId, customerId, classe);
		return Response.ok().build();
	}
	
}
