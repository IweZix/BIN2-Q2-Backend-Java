package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface User {
    int getAge();

    void setAge(int age);

    boolean isMarried();

    void setMarried(boolean married);

    String getLogin();

    void setLogin(String login);

    int getId();

    void setId(int id);

    String getPassword();

    void setPassword(String password);

    boolean checkPassword(String password);

    String hashPassword(String password);

    @Override
    String toString();
}
