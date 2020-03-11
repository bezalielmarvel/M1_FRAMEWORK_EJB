package fr.pantheonsorbonne.ufr27.miage.resource;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.InitializeService;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.User;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Vol;

@Path("/init")
public class InitEndpoint {
	
	@Inject
	InitializeService service;
	
	@GET
//	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response init() {
		service.initializeDatabase();
		return Response.ok().build();
	}
}
