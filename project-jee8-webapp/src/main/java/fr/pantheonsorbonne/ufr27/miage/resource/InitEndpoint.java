package fr.pantheonsorbonne.ufr27.miage.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.InitializeService;

@Path("/init")
public class InitEndpoint {
	
	@Inject
	InitializeService service;
	
	@GET
	public Response init() {
		service.initializeDatabase();
		return Response.ok().build();
	}
}
