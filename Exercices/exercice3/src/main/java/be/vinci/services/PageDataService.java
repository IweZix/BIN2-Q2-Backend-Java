package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;
import jakarta.ws.rs.PathParam;

import java.util.List;

public interface PageDataService {
    /**
     * Get all pages from the DB
     *
     * @return a list of pages
     */
    List<Page> getAll();

    /**
     * get all pages from a user
     *
     * @param user the user
     * @return a list of pages
     */
    List<Page> getAll(User user);

    /**
     * Find one page by its id
     *
     * @param id the id of the page to be returned
     * @return a page
     */
    Page getOne(@PathParam("id") int id);

    /**
     * Find one page by its id from a user
     *
     * @param id   the id of the page to be returned
     * @param user the user
     * @return a page
     */
    Page getOne(int id, User user);

    /**
     * Create a new page
     *
     * @param page the page to be created
     * @param user the user
     * @return the created page
     */
    Page createOne(Page page, User user);

    Page updateOne(Page page, int id, User authenticatedUser);

    Page deleteOne(int id, User authenticatedUser);

    int nextItemId();
}
