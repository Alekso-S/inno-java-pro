package ru.inno.domain;

import java.util.Objects;

public class User {
    private Long id;
    private String username;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String userName) {
        this.username = userName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
