package fr.pantheonsorbonne.ufr27.miage.resource;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.dao.FlightDAO;
import fr.pantheonsorbonne.ufr27.miage.ejb.FlightService;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchFlightException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

@Path("/flight")
public class FlightEndpoint {
	
	@Inject
	FlightDAO dao;
	
	@Inject
	FlightService service;

	
	@GET
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getVols(
			@QueryParam("departure") String departure, 
			@QueryParam("arrival") String arrival, 
			@QueryParam("date") String date) throws ParseException {
		
			try {
				List<Vol> vols = service.getVols(departure,arrival,date);
		        GenericEntity<List<Vol>> list = new GenericEntity<List<Vol>>(vols) {};
				return Response.ok(list).build();
			} catch (NoSuchFlightException e) {
				return Response.status(404, "No such flight").build();
			}
	}
	
	
	@DELETE
	@Path("/{flightId}")
	public Response deleteFlightById(@PathParam("flightId") int flightId) {
		try {
			dao.deleteFlight(flightId);
			return Response.ok().build();
		} catch (NoSuchFlightException e) {
			return Response.status(404, "No such flight").build();
		}
	}
	
	
	@DELETE
	public Response deleteFlight(
			@QueryParam("flightNumber") int flightNumber,
			@QueryParam("date") String date) {
		try {
			service.deleteFlight(flightNumber, date);
			return Response.ok().build();
		} catch (NoSuchFlightException e) {
			return Response.status(404, "No such flight").build();
		}
	}


	


//	@GET
//	@Path("/{flightId}")
//	public Vol getVolById(@PathParam("flightId") int flightId) {
//		try {
//			return dao.getFlightFromId(flightId);
//		} catch (NoSuchPassengerException e) {
//			throw new WebApplicationException(404);
//		}
//	}
}
