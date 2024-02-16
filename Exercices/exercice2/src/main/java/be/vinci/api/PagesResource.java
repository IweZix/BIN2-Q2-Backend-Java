package be.vinci.api;

import be.vinci.api.filters.AnonymousOrAuth;
import be.vinci.api.filters.Authorize;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.Json;
import be.vinci.services.PageDataService;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
@Path("pages")
public class PagesResource {

    private final PageDataService myPageDataService = new PageDataService();
    private static final String COLLECTION_NAME = "pages";
    private static final Json<Page> jsonDB = new Json<>(Page.class);

    private Page[] defaultPages = {
            new Page(1, "iwezix", "iwezix", "iwezix.xyz", 1, "published")
    };

    private List<Page> pages = new ArrayList<>(Arrays.asList(defaultPages));


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @AnonymousOrAuth
    public List<Page> getAll(@Context ContainerRequest request) {
        System.out.println("GET /pages");
        User authenticatedUser = (User) request.getProperty("user");
        if (authenticatedUser == null) {
            return myPageDataService.getAll();
        }
        return myPageDataService.getAll(authenticatedUser);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @AnonymousOrAuth
    public Page getOne(@PathParam("id") int id, @Context ContainerRequest request) {
        System.out.println("GET /pages/" + id);
        User authenticatedUser = (User) request.getProperty("user");
        if (authenticatedUser == null) {
            return myPageDataService.getOne(id);
        }
        return myPageDataService.getOne(id, authenticatedUser);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page createOne(Page page, @Context ContainerRequest request) {
        System.out.printf("POST /pages %s%n", page);
        if (page == null || page.getTitle() == null || page.getTitle().isBlank() || page.getContent() == null || page.getContent().isBlank() ||
                page.getStatus() == null || page.getURI() == null || page.getURI().isBlank()) {
            throw new WebApplicationException("Lacks of mandatory info", Response.Status.BAD_REQUEST);
        }
        User authenticatedUser = (User) request.getProperty("user");
        return myPageDataService.createOne(page, authenticatedUser);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page deleteOne(@PathParam("id") int id, @Context ContainerRequest request) {
        System.out.println("DELETE /pages/" + id);
        if (id < 1)
            throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
        User authenticatedUser = (User) request.getProperty("user");
        Page pageToDelete = myPageDataService.deleteOne(id, authenticatedUser);
        if (pageToDelete == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND).entity("Resource not found OR this resource isn't your").type("text/plain").build()
            );
        return pageToDelete;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page updateOne(Page page, @PathParam("id") int id, @Context ContainerRequest request) {
        System.out.println("UPDATE /pages/" + id);
        if (id < 1)
            throw new WebApplicationException("Invalid id", Response.Status.BAD_REQUEST);
        User authenticatedUser = (User) request.getProperty("user");
        Page pageToUpdate = myPageDataService.updateOne(page, id, authenticatedUser);
        if (pageToUpdate == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND).entity("Resource not found OR this resource isn't your").type("text/plain").build()
            );
        return pageToUpdate;
    }


}
