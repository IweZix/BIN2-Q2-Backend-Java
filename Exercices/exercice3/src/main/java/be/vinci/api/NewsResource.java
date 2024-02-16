package be.vinci.api;

import be.vinci.api.filters.Authorize;
import be.vinci.domain.News;
import be.vinci.domain.User;
import be.vinci.services.NewsDataService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ContainerRequest;

import java.text.ParseException;
import java.util.List;

@Singleton
@Path("news")
public class NewsResource {

    @Inject
    private NewsDataService myNewsDataService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getAll(@Context ContainerRequest request) {
        System.out.println("GET /news");
        User authenticatedUser = (User) request.getProperty("user");
        if (authenticatedUser == null) {
            return myNewsDataService.getAll();
        }
        return myNewsDataService.getAll(authenticatedUser);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public News createOne(News news, @Context ContainerRequest request) throws ParseException {
        System.out.printf("POST /news %s%n", news);
        User authenticatedUser = (User) request.getProperty("user");
        return myNewsDataService.createOne(news, authenticatedUser);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public News updateOne(News news, @PathParam("id") int id, @Context ContainerRequest request) {
        System.out.printf("PUT /news/%d %s%n", news.getId(), news);
        User authenticatedUser = (User) request.getProperty("user");
        return myNewsDataService.updateOne(news, id, authenticatedUser);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public News deleteOne(@PathParam("id") int id, @Context ContainerRequest request) {
        System.out.printf("DELETE /news/%d%n", id);
        User authenticatedUser = (User) request.getProperty("user");
        return myNewsDataService.deleteOne(id, authenticatedUser);
    }

    @PUT
    @Path("/associate/{pageId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public News associateOne(@PathParam("id") int id, @PathParam("pageId") int pageId, @Context ContainerRequest request) {
        System.out.printf("PUT /news/%d/associate/%d%n", id, pageId);
        User authenticatedUser = (User) request.getProperty("user");
        return myNewsDataService.associateOne(id, pageId, authenticatedUser);
    }

    @GET
    @Path("/allFromPage/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> getAllFromPage(@PathParam("id") int id) {
        System.out.printf("GET /news/page/%d%n", id);
        return myNewsDataService.getAllFromPage(id);
    }
}
