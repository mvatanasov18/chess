package com.example.chess.repositories;

import com.example.chess.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository  {
    @Autowired
    private JdbcTemplate jdbc;

    public int save(User user){
        String query="INSERT INTO Users(Id,Username,password,first_name,last_name,session) VALUES(?,?,?,?,?,?)";
        return jdbc.update(query,
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getSession());
    }
   public  User findByUsername(String username){
       String query="SELECT id,Username, password, first_name,last_name,session FROM Users WHERE Username=?";
      return  jdbc.queryForObject(query,this::mapRowToUser,username);
   }
    public  User findBySession(String session){
        String query="SELECT id,Username, password, first_name,last_name,session FROM Users WHERE session=?";
        return jdbc.queryForObject(query,this::mapRowToUser,session);
    }

    public void saveSessionByUsername(String session,String username){
        String query="UPDATE Users SET session=? WHERE username=?";
        jdbc.update(query,session,username);
    }
    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.isBeforeFirst()) {
            return new User(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("first_name")
            ,rs.getString("last_name"));
        }
        else {
            System.out.println("No results found");
            return null;
        }

    }
}
