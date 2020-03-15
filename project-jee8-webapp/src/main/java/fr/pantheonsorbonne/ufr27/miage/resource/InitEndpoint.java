package fr.pantheonsorbonne.ufr27.miage.resource;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.ejb.InitializeService;

@Path("/init")
public class InitEndpoint {
	
	@Inject
	InitializeService service;
	
	/* INITIALISATION DE LA BASE DE DONNEES */ 
	@POST
	public Response init() {
		service.initializeDatabase();
		return Response.ok().build();
	}
}
