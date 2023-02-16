package com.example.chess.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {
    @Id
    private String id;
    @Column(columnDefinition = "varchar(100)",unique = true)
    private String username;
    @Column(columnDefinition = "varchar(100)")
    private String password;
    @Column(columnDefinition = "nvarchar(100)",name = "firstName")
    private String firstName;
    @Column(columnDefinition = "nvarchar(100)",name = "lastName")
    private String lastName;

    @Column(columnDefinition = "varchar(36)",unique = true)
    private String sesiq;

    @OneToMany(mappedBy = "user")
    private Set<Event> events;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", session='" + sesiq + '\'' +
                '}';
    }

    public String getSesiq() {
        return sesiq;
    }

    public void setSesiq(String sesiq) {
        this.sesiq = sesiq;
    }

    public User(){
id= UUID.randomUUID().toString();
    }

    public User(String id, String username, String password, String firstName, String lastName,String sesiq) {
        this.id= id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sesiq = sesiq;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
