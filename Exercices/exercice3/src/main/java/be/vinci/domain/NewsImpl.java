package be.vinci.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class NewsImpl implements News {

    private int id;
    private String titre, description, contenu, status;
    private User auteur;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY hh:mm:ss")
    private Date dateHeureCreation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY hh:mm:ss")
    private Date dateHeurePeremption; // un mois plus tard
    private Page page; // null par defaut
    private int position;

    private final static String[] STATUS = {"hidden", "published"};

    public NewsImpl() {
    }

    public NewsImpl(int id, String titre, String description, String contenu, User auteur, String status, int position) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.contenu = contenu;
        this.auteur = auteur;
        this.status = status;
        this.dateHeureCreation = null;
        this.dateHeurePeremption = null;
        this.page = null;
        this.position = position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitre() {
        return titre;
    }

    @Override
    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getContenu() {
        return contenu;
    }

    @Override
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public User getAuteur() {
        return auteur;
    }

    @Override
    public void setAuteur(User auteur) {
        this.auteur = auteur;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Date getDateHeureCreation() {
        return dateHeureCreation;
    }

    @Override
    public void setDateHeureCreation(Date dateHeureCreation) {
        this.dateHeureCreation = dateHeureCreation;
    }

    @Override
    public Date getDateHeurePeremption() {
        return dateHeurePeremption;
    }

    @Override
    public void setDateHeurePeremption(Date dateHeurePeremption) {
        this.dateHeurePeremption = dateHeurePeremption;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", status='" + status + '\'' +
                ", dateHeureCreation=" + dateHeureCreation +
                ", dateHeurePeremption=" + dateHeurePeremption +
                ", page=" + page +
                ", position=" + position +
                '}';
    }
}
