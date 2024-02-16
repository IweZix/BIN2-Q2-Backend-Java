package be.vinci.services;

import be.vinci.domain.News;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * getAll -> everybody & "published"
 *        -> athentified
 * createOne -> authentified
 * updateOne -> authentified & author of news
 * deleteOne -< authentified & author of news
 * associateOne -> authentified & author of news
 * getAllFromPage -> everybody & "published"
 *                -> authentified & "hidden"
 */

public class NewsDataServiceImpl implements NewsDataService {

    private static final String COLLECTION_NAME = "news";
    private static final String COLLECTION_NAME_PAGE = "pages";
    private static Json<News> jsonDB = new Json<>(News.class);
    private static Json<Page> jsonDBPage = new Json<>(Page.class);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Get all news from the DB
     * @return a list of news
     */
    @Override
    public List<News> getAll() {
        return jsonDB.parse(COLLECTION_NAME).stream()
                .filter(n -> n.getStatus().equals("published"))
                .toList();
    }

    /**
     * Get all news of a user
     * @param user the user
     * @return a list of news
     */
    public List<News> getAll(User user) {
        return jsonDB.parse(COLLECTION_NAME).stream()
                .filter(n -> n.getAuteur().getId() == user.getId())
                .toList();
    }

    /**
     * Create a news
     * @param news the news to create
     * @return the created news
     */
    public News createOne(News news, User user) throws ParseException {
        var newsList = jsonDB.parse(COLLECTION_NAME);
        news.setId(nextItemId());
        news.setTitre(StringEscapeUtils.escapeHtml4(news.getTitre()));
        news.setDescription(StringEscapeUtils.escapeHtml4(news.getDescription()));
        news.setContenu(StringEscapeUtils.escapeHtml4(news.getContenu()));
        news.setAuteur(user);
        news.setPosition(news.getPosition());
        news.setStatus(StringEscapeUtils.escapeHtml4(news.getStatus()));
        // !

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toParse = sdf.format(new Date());
        Date date = sdf.parse(toParse);
        news.setDateHeureCreation(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        Date date2 = c.getTime();
        news.setDateHeurePeremption(date2);

        newsList.add(news);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return news;
    }

    /**
     * Update a news
     * @param news the news to update
     * @return the updated news
     */
    public News updateOne(News news, int id, User user) {
        var newsList = jsonDB.parse(COLLECTION_NAME);
        News newsToUpdate = newsList.stream()
                .filter(n -> n.getId() == id)
                .filter(n -> n.getAuteur().getId() == user.getId())
                .findFirst().orElse(null);
        if (newsToUpdate == null)
            return null;

        newsToUpdate.setTitre(StringEscapeUtils.escapeHtml4(news.getTitre()));
        newsToUpdate.setDescription(StringEscapeUtils.escapeHtml4(news.getDescription()));
        newsToUpdate.setContenu(StringEscapeUtils.escapeHtml4(news.getContenu()));
        newsToUpdate.setPosition(news.getPosition());
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return newsToUpdate;
    }

    /**
     * Delete a news
     * @param id the id of the news to delete
     * @return the deleted news
     */
    public News deleteOne(int id, User user) {
        var newsList = jsonDB.parse(COLLECTION_NAME);
        News newsToDelete = newsList.stream()
                .filter(n -> n.getId() == id)
                .filter(n -> n.getAuteur().getId() == user.getId())
                .findFirst().orElse(null);
        if (newsToDelete == null)
            return null;
        newsList.remove(newsToDelete);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return newsToDelete;
    }

    /**
     * Associate a news to a page
     * @param news the news to associate
     * @param page the id of the page
     * @return the associated news
     */
    public News associateOne(int news, int page, User user) {
        var newsList = jsonDB.parse(COLLECTION_NAME);
        News newsToAssociate = newsList.stream()
                .filter(n -> n.getId() == news)
                .findFirst().orElse(null);
        if (newsToAssociate == null)
            return null;
        var pageList = jsonDBPage.parse(COLLECTION_NAME_PAGE);
        Page pageToAssociate = pageList.stream()
                .filter(p -> p.getId() == page)
                .findFirst().orElse(null);
        if (pageToAssociate == null)
            return null;
        newsToAssociate.setPage(pageToAssociate);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return newsToAssociate;
    }

    /**
     * Get all news from a page
     * @param page the id of the page
     * @return a list of news
     */
    public List<News> getAllFromPage(int page) {
        return jsonDB.parse(COLLECTION_NAME).stream()
                .filter(n -> n.getPage().getId() == page)
                .toList();
    }

    /**
     * Get the next id for a news
     * @return the next id
     */
    public int nextItemId() {
        var news = jsonDB.parse(COLLECTION_NAME);
        if (news.isEmpty())
            return 1;
        return news.get(news.size() - 1).getId() + 1;
    }
}
