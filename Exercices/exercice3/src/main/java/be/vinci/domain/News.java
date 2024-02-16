package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.Date;

@JsonDeserialize(as = NewsImpl.class)
public interface News {
    int getId();

    void setId(int id);

    String getTitre();

    void setTitre(String titre);

    String getDescription();

    void setDescription(String description);

    String getContenu();

    void setContenu(String contenu);

    User getAuteur();

    void setAuteur(User auteur);

    String getStatus();

    void setStatus(String status);

    Date getDateHeureCreation();

    void setDateHeureCreation(Date dateHeureCreation);

    Date getDateHeurePeremption();

    void setDateHeurePeremption(Date dateHeurePeremption);

    Page getPage();

    void setPage(Page page);

    int getPosition();

    void setPosition(int position);

    @Override
    String toString();
}
