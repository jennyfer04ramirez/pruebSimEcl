package ec.edu.ups.ppw.prueb.service;

import ec.edu.ups.ppw.prueb.bussiness.GestionPerson;
import ec.edu.ups.ppw.prueb.model.Person;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/personas")
public class ServicioPerson {

    @Inject
    private GestionPerson gestionPerson;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonas() {
        try {
            List<Person> personas = gestionPerson.getPerson();
            return Response.ok(personas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonByCedula(@PathParam("cedula") String cedula) {
        try {
            Person persona = gestionPerson.getPersonByCedula(cedula);
            if (persona != null) {
                return Response.ok(persona).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarPersona(Person person) {
        try {
            gestionPerson.getPerson().add(person);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar persona").build();
        }
    }
}
