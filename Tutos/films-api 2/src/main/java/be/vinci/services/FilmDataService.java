package be.vinci.services;

import be.vinci.domain.Film;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class FilmDataService {

    // The name of the collection in the DB file
    private static final String COLLECTION_NAME = "films";
    // The JSON parser for the Film class
    private static final Json<Film> jsonDB = new Json<>(Film.class);

    /**
     * Get all films from the DB
     * @param minimumDuration the minimum duration of the films to be returned
     * @return a list of films
     */
    public List<Film> getAll(int minimumDuration) {
        var films = jsonDB.parse(COLLECTION_NAME);
        if (minimumDuration != -1) {
            return films.stream()
                    .filter(film -> film.getDuration() >= minimumDuration)
                    .toList();
        }
        return films;
    }

    /**
     * Get one film from the DB
     * @param id the id of the film to be returned
     * @return a film
     */
    public Film getOne(int id) {
        var films = jsonDB.parse(COLLECTION_NAME);
        return films.stream()
                .filter(film -> film.getId() == id)
                .findAny().orElse(null);
    }

    /**
     * Create a film in the DB
     * @param film the film to be created
     * @return the created film
     */
    public Film createOne(Film film) {
        var films = jsonDB.parse(COLLECTION_NAME);
        film.setId(nextFilmId());
        film.setTitle(StringEscapeUtils.escapeHtml4(film.getTitle()));
        film.setLink(StringEscapeUtils.escapeHtml4(film.getLink()));
        films.add(film);
        jsonDB.serialize(films, COLLECTION_NAME);
        return film;
    }

    /**
     * Delete a film from the DB
     * @param id the id of the film to be deleted
     * @return the deleted film
     */
    public Film deleteOne(int id) {
        var films = jsonDB.parse(COLLECTION_NAME);
        Film filmToDelete = films.stream()
                .filter(film -> film.getId() == id)
                .findAny().orElse(null);
        films.remove(filmToDelete);
        jsonDB.serialize(films, COLLECTION_NAME);
        return filmToDelete;
    }

    /**
     * Update a film in the DB
     * @param film the film to be updated
     * @param id the id of the film to be updated
     * @return the updated film
     */
    public Film updateOne(Film film, int id) {
        var films = jsonDB.parse(COLLECTION_NAME);
        Film filmToUpdate = films.stream()
                .filter(f -> f.getId() == id)
                .findAny().orElse(null);
        film.setId(id);
        film.setTitle(StringEscapeUtils.escapeHtml4(film.getTitle()));
        film.setLink(StringEscapeUtils.escapeHtml4(film.getLink()));
        films.remove(film); // thanks to equals(), films is found via its id
        films.add(film);
        jsonDB.serialize(films, COLLECTION_NAME);
        return film;
    }

    /**
     * Get the next id for a film
     * @return the next id for a film
     */
    public int nextFilmId() {
        var films = jsonDB.parse(COLLECTION_NAME);
        if (films.isEmpty())
            return 1;
        return films.get(films.size() - 1).getId() + 1;
    }
}

