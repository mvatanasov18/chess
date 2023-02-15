package com.example.chess.controllers;

import com.example.chess.models.Game;
import com.example.chess.repositories.GameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chess/ajax")
public class ChessControllerAjax {

    private GameRepository gameRepo;

    public ChessControllerAjax(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    @RequestMapping(value = "/makeNewChess", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Game makeNewChessGame(@RequestBody Game game) {
        gameRepo.save(game);
        return game;
    }

    @RequestMapping(value = "/getChessById", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Game getChessGame(@RequestBody Game game) {
        return gameRepo.findOne(game.getId());
    }

    @RequestMapping(value = "/movePiece", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Game movePiece(@RequestParam("id") String id,
                                        @RequestParam("move1") String move1,
                                        @RequestParam("move2") String move2) {
        int[] im1 = {Integer.parseInt(move1.split("")[0]), Integer.parseInt(move1.split("")[1])};
        int[] im2 = {Integer.parseInt(move2.split("")[0]), Integer.parseInt(move2.split("")[1])};

        Game g = this.gameRepo.findOne(id);
        if (g.move_piece(im1[0], im1[1], im2[0], im2[1])) {
            this.gameRepo.updateGame(g);
            return g;
        }
        else {
            return null;
        }
    }
}