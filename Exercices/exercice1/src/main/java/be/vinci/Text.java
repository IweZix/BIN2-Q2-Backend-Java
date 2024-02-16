package be.vinci;

import java.util.Arrays;
import java.util.Objects;

public class Text {

    private int id;
    private String content;
    private String level;

    private final static String[] LEVELS = {"easy", "medium", "hard"};

    public Text() {
    }

    public Text(int id, String content, String level) {
        this.id = id;
        this.content = content;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean verifyLevel() {
        return Arrays.asList(LEVELS).contains(this.level);
    }

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return id == text.id && Objects.equals(content, text.content) && Objects.equals(level, text.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, level);
    }
}
