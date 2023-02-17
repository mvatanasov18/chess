package com.example.chess.repositories;

import com.example.chess.models.Event;
import com.example.chess.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public int save(User user) {
        String query = "INSERT INTO Users(Id,Username,password,first_name,last_name,sesiq) VALUES(?,?,?,?,?,?)";
        return jdbc.update(query,
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getSesiq());
    }

    public int update(User user) {
        String query = "UPDATE Users SET Username=? ,password=?,first_name=?,last_name=?,sesiq=? WHERE Id=?";
        return jdbc.update(query,
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getSesiq(),
                user.getId());
    }

    public User findByUsername(String username) {
        try {
            String query = "SELECT id,Username, password, first_name,last_name,sesiq FROM Users WHERE Username = ?";
                return jdbc.queryForObject(query, this::mapRowToUser, username);

        } catch (Exception e) {
            return null;
        }
    }

    public User findBySession(String session) {
        String query = "SELECT id,Username, password, first_name,last_name,sesiq FROM Users WHERE sesiq=?";

        return jdbc.queryForObject(query, this::mapRowToUser, session);
    }

    public void saveSessionByUsername(String session, String username) {
        String query = "UPDATE Users SET sesiq=? WHERE username=?";
        jdbc.update(query, session, username);
    }

    public void setEventsForUser(User user){
        String query="SELECT e.id, e.Name, e.date_time, e.place " +
                "FROM Users as u " +
                "INNER JOIN Events as e " +
                "ON u.id=e.user_id " +
                "WHERE u.Id=?";
        try {
            System.out.println(jdbc.queryForObject(query, this::mapRowToEvent, user.getId()));
        }catch (Exception e){
            System.out.println("sushtoto");
        }
    }
    private Event mapRowToEvent(ResultSet rs, int rowNum) throws SQLException{
        if(rs.isBeforeFirst()){
            return new Event(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getString("place")
                    );

        }
        return null;
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.isBeforeFirst()) {
            return new User(rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("sesiq"));
        } else {
            System.out.println("No results found");
            return null;
        }

    }
}
