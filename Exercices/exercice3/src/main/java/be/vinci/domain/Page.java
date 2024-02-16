package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PageImpl.class)
public interface Page {
    int getId();

    void setId(int id);

    String getTitle();

    void setTitle(String titre);

    String getURI();

    void setURI(String URI);

    String getContent();

    void setContent(String content);

    int getAuthor();

    void setAuthor(int author);

    String getStatus();

    void setStatus(String status);

    @Override
    String toString();
}
