package be.vinci.domain;

import java.util.Objects;

public class Page {

    private int id, author;
    private String title, URI, content;
    private String status;

    private final static String[] STATUS = {"hidden", "published"};

    public Page() {
    }

    public Page(int id, String title, String URI, String content, int author, String status) {
        this.id = id;
        this.title = title;
        this.URI = URI;
        this.content = content;
        this.author = author;
        if (status != null && !status.equals(STATUS[0]) && !status.equals(STATUS[1])) {
            this.status = STATUS[0];
        } else {
            this.status = status;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titre) {
        this.title = titre;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return id == page.id && Objects.equals(title, page.title) && Objects.equals(URI, page.URI) && Objects.equals(content, page.content) && Objects.equals(author, page.author) && Objects.equals(status, page.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, URI, content, author, status);
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", titre='" + title + '\'' +
                ", URI='" + URI + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", status='" + status + '\'' +
                '}';
    }
}
