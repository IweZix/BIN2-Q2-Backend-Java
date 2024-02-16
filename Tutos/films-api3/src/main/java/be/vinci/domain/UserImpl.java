package be.vinci.domain;

import be.vinci.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class UserImpl implements User {
    @JsonView(Views.Public.class)
    private int id;
    @JsonView(Views.Public.class)
    private String login;
    @JsonView(Views.Internal.class)
    private String password;
    @JsonView(Views.Internal.class)
    private int age;
    @JsonView(Views.Internal.class)
    private boolean married;

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean isMarried() {
        return married;
    }

    @Override
    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
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
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    @Override
    public String toString() {
        return "{id:" + id + ", login:" + login + ", password:" + password + "}";
    }

}
