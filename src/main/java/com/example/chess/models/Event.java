package com.example.chess.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Events")
public class Event {
    @Id
    private String id;
    @Column(name="Name")
    private String name;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name="place")
    private String place;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", place='" + place + '\'' +
                ", user=" + user +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event() {
        this.id= UUID.randomUUID().toString();
    }

    public Event(String id, String name, LocalDateTime dateTime, String place) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
