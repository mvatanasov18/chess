package com.example.chess.controllers;


import com.example.chess.models.Game;
import com.example.chess.repositories.GameRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chess")
public class ChessController{

    private GameRepository gameRepo;

    public ChessController(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    @GetMapping("/makeNewChess")
    public String makeNewChessGameGET(HttpServletResponse response)
    {

        return "MakeGame";
    }

    @GetMapping("/getChessById")
    public String getChessGameGET()
    {
        return "chess_get_id";
    }

    @PostMapping("/makeNewChess")
    public ModelAndView makeNewChessGamePOST() {
        String id = this.gameRepo.makeRandomId();
        Game new_game = new Game(id);
        this.gameRepo.save(new_game);
        return new ModelAndView("redirect:/chess/Game?id=" + id);
    }

    @PostMapping("/getChessById")
    public ModelAndView getChessGamePOST(@RequestParam("id") String id) {
        Game g = gameRepo.findOne(id);
        if (g == null) {
            return new ModelAndView("redirect:/chess/error");
        }
        else {
            return new ModelAndView("redirect:/chess/Game?id=" + id);
        }
    }

    @GetMapping("/Game")
    public String Game(@RequestParam("id") String id, Model model) {
        Game g = this.gameRepo.findOne(id);
        if (g == null) {
            return "Error";
        }
        else {
            model.addAttribute("game", g);
            return "Game";
        }
    }
}
