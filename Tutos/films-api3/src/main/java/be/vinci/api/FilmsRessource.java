package be.vinci.api;

import be.vinci.api.filters.Authorize;
import be.vinci.domain.Film;
import be.vinci.domain.User;
import be.vinci.services.FilmDataService;
import be.vinci.services.FilmDataServiceImpl;
import be.vinci.services.Json;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;

/**
 * Root resource (exposed at "films" path)
 */
@Singleton
@Path("films")
public class FilmsRessource {

    @Inject
    private FilmDataService myFilmDataService;

    // The name of the collection in the DB file
    private static final String COLLECTION_NAME = "films";
    // The JSON parser for the Film class
    private static final Json<Film> jsonDB = new Json<>(Film.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * @return List<Film> that will be returned as a JSON response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll(@DefaultValue("-1") @QueryParam("minimum-duration") int minimumDuration) {
        return myFilmDataService.getAll(minimumDuration);
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * @return Film that will be returned as a JSON response.
     * @param id id of the film
     * @return Film that will be returned as a JSON response.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Film getOne(@PathParam("id") int id) {
        Film filmFound = myFilmDataService.getOne(id);
        if (filmFound == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND).entity("Ressource not found").type("text/plain").build()
            );
        return filmFound;
    }

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * @param film film to create
     * @return Film that will be returned as a JSON response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Film createOne(Film film, @Context ContainerRequest request) {
        User authenticatedUser = (User) request.getProperty("user");
        System.out.println("A new film is added by " + authenticatedUser.getLogin());
        var films = jsonDB.parse(COLLECTION_NAME);
        if (film == null || film.getTitle() == null || film.getTitle().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build()
            );
        return myFilmDataService.createOne(film);
    }

    /**
     * Method handling HTTP DELETE requests. The returned object will be sent
     * @param id id of the film to delete
     * @return Film that will be returned as a JSON response.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Film deleteOne(@PathParam("id") int id) {
        if (id == 0) // default value of an integer => has not been initialized
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info").type("text/plain").build()
            );
        var films = jsonDB.parse(COLLECTION_NAME);
        Film filmToDelete = myFilmDataService.deleteOne(id);
        if (filmToDelete == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND).entity("Ressource not found").type("text/plain").build()
            );
        return filmToDelete;
    }

    /**
     * Method handling HTTP PUT requests. The returned object will be sent
     * @param film film to update
     * @param id id of the film to update
     * @return Film that will be returned as a JSON response.
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Film updateOne(Film film, @PathParam("id") int id) {
        if (id == 0 || film == null || film.getTitle() == null || film.getTitle().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build()
            );
        Film filmToUpdate = myFilmDataService.updateOne(film, id);
        if (filmToUpdate == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND).entity("Ressource not found").type("text/plain").build()
            );
        return filmToUpdate;
    }



}
