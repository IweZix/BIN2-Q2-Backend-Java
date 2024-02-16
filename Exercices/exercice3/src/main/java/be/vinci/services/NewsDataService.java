package be.vinci.services;

import be.vinci.domain.News;
import be.vinci.domain.Page;
import be.vinci.domain.User;

import java.text.ParseException;
import java.util.List;

public interface NewsDataService {
    List<News> getAll();

    public List<News> getAll(User user);

    public News createOne(News news, User user) throws ParseException;

    public News updateOne(News news, int id, User user);

    public News deleteOne(int id, User user);

    public News associateOne(int news, int page, User user);

    public List<News> getAllFromPage(int page);
}
