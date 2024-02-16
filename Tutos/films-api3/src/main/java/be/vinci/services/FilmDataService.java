package be.vinci.services;

import be.vinci.domain.Film;

import java.util.List;

public interface FilmDataService {
    /**
     * Get all films from the DB
     *
     * @param minimumDuration the minimum duration of the films to be returned
     * @return a list of films
     */
    List<Film> getAll(int minimumDuration);

    /**
     * Get one film from the DB
     *
     * @param id the id of the film to be returned
     * @return a film
     */
    Film getOne(int id);

    /**
     * Create a film in the DB
     *
     * @param film the film to be created
     * @return the created film
     */
    Film createOne(Film film);

    /**
     * Delete a film from the DB
     *
     * @param id the id of the film to be deleted
     * @return the deleted film
     */
    Film deleteOne(int id);

    /**
     * Update a film in the DB
     *
     * @param film the film to be updated
     * @param id   the id of the film to be updated
     * @return the updated film
     */
    Film updateOne(Film film, int id);

    /**
     * Get the next id for a film
     *
     * @return the next id for a film
     */
    int nextFilmId();
}
