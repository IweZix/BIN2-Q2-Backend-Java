package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.PathParam;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataService {

    private static final String COLLECTION_NAME = "pages";
    private static Json<Page> jsonDB = new Json<>(Page.class);
    private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Get all pages from the DB
     * @return a list of pages
     */
    public List<Page> getAll() {
        return jsonDB.parse(COLLECTION_NAME);
    }

    /**
     * get all pages from a user
     * @param user the user
     * @return a list of pages
     */
    public List<Page> getAll(User user) {
        return jsonDB.parse(COLLECTION_NAME).stream()
                .filter(p -> p.getAuthor() == user.getId())
                .toList();
    }

    /**
     * Find one page by its id
     * @param id the id of the page to be returned
     * @return a page
     */
    public Page getOne(@PathParam("id") int id) {
        return jsonDB.parse(COLLECTION_NAME).stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null);
    }

    /**
     * Find one page by its id from a user
     * @param id the id of the page to be returned
     * @param user the user
     * @return a page
     */
    public Page getOne(int id, User user) {
        return jsonDB.parse(COLLECTION_NAME).stream()
                .filter(p -> p.getId() == id && p.getAuthor() == user.getId())
                .findFirst().orElse(null);
    }

    /**
     * Create a new page
     * @param page the page to be created
     * @param user the user
     * @return the created page
     */
    public Page createOne(Page page, User user) {
        var pages = jsonDB.parse(COLLECTION_NAME);
        page.setId(nextItemId());
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setURI(StringEscapeUtils.escapeHtml4(page.getURI()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        page.setStatus(StringEscapeUtils.escapeHtml4(page.getStatus()));
        page.setAuthor(user.getId());
        pages.add(page);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return page;
    }

    public Page updateOne(Page page, int id, User authenticatedUser) {
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageToUpdate = pages.stream()
                .filter(p -> p.getId() == id)
                .filter(p -> p.getAuthor() == authenticatedUser.getId())
                .findFirst().orElse(null);
        if (pageToUpdate == null) {
            return null;
        }
        System.out.println("page to update = " + pageToUpdate);
        page.setId(id);
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setURI(StringEscapeUtils.escapeHtml4(page.getURI()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        page.setStatus(StringEscapeUtils.escapeHtml4(page.getStatus()));
        page.setAuthor(authenticatedUser.getId());
        pages.remove(pageToUpdate);
        pages.add(page);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return page;
    }

    public Page deleteOne(int id, User authenticatedUser) {
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageToDelete = pages.stream()
                .filter(p -> p.getId() == id)
                .filter(p -> p.getAuthor() == authenticatedUser.getId())
                .findFirst().orElse(null);
        pages.remove(pageToDelete);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return pageToDelete;
    }


    public int nextItemId() {
        var pages = jsonDB.parse(COLLECTION_NAME);
        if (pages.isEmpty())
            return 1;
        return pages.get(pages.size() - 1).getId() + 1;
    }

}
