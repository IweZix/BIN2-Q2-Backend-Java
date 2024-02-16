package be.vinci;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
@Path("texts")
public class TextResource {

    /**
     * Get all texts or all texts with a specific level
     * @param level level of the text
     * @return List<Text> that will be returned as a JSON response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Text> getAll(@DefaultValue("") @QueryParam("level") String level) {
        var texts = Json.parse();
        if (!level.isBlank()) {
            System.out.println("GET /texts?level=" + level);
            List<Text> textFiltered = texts.stream()
                    .filter(t -> t.getLevel().equals(level))
                    .toList();
            return textFiltered;
        }
        System.out.println("GET /texts");
        return texts;
    }

    /**
     * Get a specific text
     * @param id id of the text
     * @throws WebApplicationException if the text is not found
     * @return Text that will be returned as a JSON response.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Text getOne(@PathParam("id") int id) {
        System.out.println("GET /texts/" + id);
        var texts = Json.parse();
        Text textFound = texts.stream()
                .filter(t -> t.getId() == id)
                .findAny().orElse(null);
        if (textFound == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("Ressrouce not found")
                            .type("text/plain")
                            .build()
            );
        return textFound;
    }

    /**
     * Create a text
     * @param text text to create
     * @throws WebApplicationException if the text is not valid
     * @return Text that will be returned as a JSON response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Text createOne(Text text) {
        System.out.println("POST /texts");
        if (text == null || text.getContent() == null || text.getContent().isBlank() || text.getLevel() == null || text.getLevel().isBlank() || !text.verifyLevel())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Lacks of mandatory info or  text level")
                            .type("text/plain")
                            .build()
            );
        var texts = Json.parse();
        text.setId(texts.size() + 1);
        text.setContent(StringEscapeUtils.escapeHtml4((text.getContent())));
        text.setLevel(StringEscapeUtils.escapeHtml4((text.getLevel())));
        texts.add(text);
        Json.serialize(texts);
        return text;
    }

    /**
     * Delete a specific text
     * @param id id of the text
     * @throws WebApplicationException if the id is not valid
     * @throws WebApplicationException if the text is not found
     * @return Text that will be returned as a JSON response.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Text deleteOne(@PathParam("id") int id) {
        System.out.println("DELETE /texts/" + id);
        if (id <= 0)
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Lacks of mandatory id info")
                            .type("text/plain")
                            .build()
            );
        var texts = Json.parse();
        Text textToBeDelete = texts.stream()
                .filter(t -> t.getId() == id)
                .findFirst().orElse(null);
        if (textToBeDelete == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("Ressource not found")
                            .type("text/plain")
                            .build()
            );
        texts.remove(textToBeDelete);
        Json.serialize(texts);
        return textToBeDelete;
    }

    /**
     * Update a specific text
     * @param text text to update
     * @param id id of the text
     * @throws WebApplicationException if the id is not valid
     * @throws WebApplicationException if the text is not valid
     * @return Text that will be returned as a JSON response.
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Text updateOne(Text text, @PathParam("id") int id) {
        if (id <= 0 || text == null || text.getContent() == null || text.getLevel() == null || text.getContent().isBlank() || text.getLevel().isBlank() || !text.verifyLevel())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Lacks of mandatory info or unauthorized text level")
                            .type("text/plain")
                            .build()
            );
        var texts = Json.parse();
        Text textToBeUpdate = texts.stream()
                .filter(t -> t.getId() == id)
                .findFirst().orElse(null);
        if (textToBeUpdate == null)
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("Ressource not found")
                            .type("text/plain")
                            .build()
            );
        text.setId(id);
        text.setContent(StringEscapeUtils.escapeHtml4((text.getContent())));
        text.setLevel(StringEscapeUtils.escapeHtml4((text.getLevel())));
        texts.remove(text);
        texts.add(text);
        Json.serialize(texts);
        return text;
    }

}
