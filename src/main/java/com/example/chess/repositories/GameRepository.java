package com.example.chess.repositories;


import com.example.chess.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GameRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public GameRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String makeRandomId() {
        String new_id = "";
        String[] valid_char = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".split("");

        do {
            for (int i = 0; i < 15; i++) {
                new_id += valid_char[(int) Math.floor(Math.random() * valid_char.length)];
            }
        } while (findOne(new_id) != null);

        return new_id;
    }

    public Game save(Game game) {
        if (findOne(game.getId()) != null) {
            System.out.println("tuk sum");
            return null;
        }

        int res=jdbc.update("INSERT INTO Game (id, board, turn, moves) VALUES (?, ?, ?, ?)",
                game.getId(), game.getBoard(), game.getTurn(), game.getMoves());
        return game;
    }

    public void updateGame(Game game) {
        jdbc.update("UPDATE game SET board= ?, turn = ?, moves = ? WHERE id = ?", game.getBoard(), game.getTurn(), game.getMoves(), game.getId());
    }

    public Game findOne(String id) {
        try {
            return jdbc.queryForObject("SELECT id, board, turn, moves FROM Game WHERE id = ?", this::mapRowToGame, id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No result found for that query. find one");
            return null;
        }
    }

    private Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {
        if (!rs.isBeforeFirst()) {
            return new Game(rs.getString("id"), rs.getString("board"), rs.getString("moves"), rs.getInt("turn"));
        }
        else {
            System.out.println("No results found");
            return null;
        }

    }


}
