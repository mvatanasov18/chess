package com.example.chess.models;

import java.util.ArrayList;

public class Game {

    private String id;

    private Piece board[][] = new Piece[8][8];

    private ArrayList<String> moves = new ArrayList<String>();

    private int turn;

    public Game() {

        this.id = "nkfadjf"; //Just testing value for id.
        reset_board(); //sets Piece Board[][] to default value.
        this.turn = 0; //0 for white, 1 for black.
    }

    public Game(String id) {
        this.id = id;
        reset_board();
        this.turn = 0;
    }

    public Game(String id, String board, String moves, int turn) {
        this.id = id;
        board_from_string(board);
        moves_from_string(moves);
        this.turn = turn;
    }

    private String getBoard(Piece piece_board[][]) {
        String g = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece_board[i][j] == null)  { g += "."; }
                else                      { g += piece_board[i][j].to_string(); }
            }
            if (i <= 6) { g += "\n"; }
        }
        return g;
    }



    public String getId() {
        return this.id;
    }

    //Gets board as string from this.board.
    public String getBoard() {
        return getBoard(this.board);
    }

    public String getMoves() {
        String str_moves = "";
        for (int i = 0; i < this.moves.size(); i++) {
            str_moves += this.moves.get(i);
            if (i < (this.moves.size() - 1)) { str_moves += ","; }
        }
        return str_moves;
    }

    public int getTurn() {
        return this.turn;
    }

    //-------------------------------------------

    public Piece piece_from_string(String str_piece, int posy, int posx) {
        int piece_color = (str_piece.equals(str_piece.toLowerCase())) ? 0 : 1;
        switch (str_piece.toLowerCase()) {
            case "p":  return   new   Pawn(posy, posx, piece_color);
            case "k":  return   new   King(posy, posx, piece_color);
            case "q":  return   new  Queen(posy, posx, piece_color);
            case "b":  return   new Bishop(posy, posx, piece_color);
            case "n":  return   new Knight(posy, posx, piece_color);
            case "r":  return   new   Rook(posy, posx, piece_color);
            default:   return null;
        }
    }

    public String getBoardUndo(int undo_number) {
        undo_number = (undo_number > this.moves.size()) ? this.moves.size() : undo_number;
        int pos_1x; int pos_1y;  int pos_2x; int pos_2y;
        for (int i = (this.moves.size() - 1); i > (this.moves.size() - 2 - undo_number); i--) {
            //     97 == a, 98 == b, etc.
            pos_1x = ((int) this.moves.get(i).charAt(1)) - 97; pos_1y = Integer.parseInt(this.moves.get(i).substring(2, 3));
            pos_2x = ((int) this.moves.get(i).charAt(4)) - 97; pos_2y = Integer.parseInt(this.moves.get(i).substring(5, 6));
            this.board[pos_1y][pos_1x] = piece_from_string(this.moves.get(i).substring(0, 1), pos_1y, pos_1x);
            this.board[pos_2y][pos_2x] = piece_from_string(this.moves.get(i).substring(3, 4), pos_2y, pos_2x);
        }
        return "";
    }

    public void board_from_string(String str_pure) {
        String bdd[] = str_pure.split("\n");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = piece_from_string(bdd[i].substring(j, j + 1), i, j);
            }
        }
    }

    public void moves_from_string(String str_moves) {
        String arr_moves[] = str_moves.split(",");
        for (int i = 0; i < arr_moves.length; i++) {
            if (arr_moves[i].length() > 1) {
                this.moves.add(arr_moves[i]);
            }
        }
    }

    private void invert_turn() {
        //changes turn from white to black and from white to black.
        turn = Math.abs(turn - 1);
    }

    public boolean is_checkmate() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board[i][j] != null && this.board[i][j].Color == turn) {
                    for (int k = 0; k < 8; k++) {
                        for (int c = 0; c < 8; c++) {
                            if (valid_move(i, j, k, c, true)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean undo_check(int posy, int posx, int new_posy, int new_posx) {
        //Move piece.
        Piece old_spot = this.board[new_posy][new_posx];
        this.board[new_posy][new_posx] = this.board[posy][posx];
        this.board[posy][posx] = null;
        this.board[new_posy][new_posx].update_position(new_posy, new_posx);

        //Get if that color is still in check.
        boolean checked = is_check();

        //undo move.
        this.board[posy][posx] = this.board[new_posy][new_posx];
        this.board[new_posy][new_posx] = old_spot;
        this.board[posy][posx].update_position(posy, posx);

        return checked;
    }

    public boolean is_check() {
        int k_pos[];
        if (turn == 0)  { k_pos = get_position_piece("k"); }
        else            { k_pos = get_position_piece("K"); }
        invert_turn();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (valid_move(i, j, k_pos[0], k_pos[1], false)) { invert_turn(); return true; }
            }
        }

        invert_turn();
        return false;
    }

    public boolean valid_move(int posy, int posx, int n_posy, int n_posx, boolean u_check) {
        if (this.board[posy][posx] == null || this.board[posy][posx].Color != turn || (posy == n_posy && posx == n_posx)) { return false; }
        if (this.board[posy][posx].to_string().toLowerCase().equals("p")) {
            //Checks for pawn move.
            if (this.moves.size() >= 1) {
                String past_move = this.moves.get(this.moves.size() - 1);
                if (Math.abs(posy - n_posy) == 1 && Math.abs(posx - n_posx) == 1 && board[n_posy][n_posx] == null &&
                        past_move.substring(0, 1).toLowerCase().equals("p"))
                {
                    //49 == 1, 97 == 'a'.
                    int pm_1x = (int) past_move.charAt(1) - 'a'; int pm_1y = Integer.parseInt(past_move.substring(2, 3)); int pm_2y = Integer.parseInt(past_move.substring(5, 6));
                    System.out.printf("|-%c-%d-%d-%d-|\nadfadfadfadfaf\n", past_move.charAt(1), pm_1x, pm_1y, pm_2y);
                    if (Math.abs(pm_1y - pm_2y) == 2 && n_posy == ((pm_2y + pm_1y) / 2) && pm_1x == n_posx) {
                        //En passant is valid.
                        System.out.printf("\naa bb cc ::    %d %d\n", pm_2y, pm_1x);
                        this.board[n_posy][n_posx] = this.board[pm_2y][pm_1x];
                        this.board[pm_2y][pm_1x] = null;
                        System.out.println(this.getBoard());
                        this.board[n_posy][n_posx].update_position(n_posy, n_posx);
                        return true;
                        /*
                        if (!undo_check(posy, posx, n_posy, n_posx)) {
                            return true;
                        }
                        else {
                            System.out.println("zzzzzzzxczxczxczqqwe1231938akjzc81248hhasdf");
                            this.board[n_posy][n_posx] = null; this.board[pm_2y][pm_1x] = this.board[n_posy][n_posx];
                            this.board[pm_2y][pm_1x].update_position(pm_2y, pm_1x);
                            return false;
                        }
                        */
                    }
                }
            }
        }
        if (!board[posy][posx].is_valid_move(board, n_posy, n_posx)) { return false; }
        if ( u_check) { return !undo_check(posy, posx, n_posy, n_posx); }

        return true;
    }

    public boolean move_piece(int posy, int posx, int n_posy, int n_posx) {

        //Check if move is valid
        if (!valid_move(posy, posx, n_posy, n_posx, true)) {
            return false;
        }

        //Add move to this.moves
        String table[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String str_move = this.board[posy][posx].to_string() + table[posx] + Integer.toString(posy);

        if (this.board[n_posy][n_posx] == null) {    str_move += ".";                                }
        else                               {    str_move += this.board[n_posy][n_posx].to_string();  }

        str_move += table[n_posx] + Integer.toString(n_posy);
        this.moves.add(str_move);

        //Update Board
        this.board[n_posy][n_posx] = this.board[posy][posx];
        this.board[n_posy][n_posx].update_position(n_posy, n_posx);
        this.board[posy][posx] = null;
        invert_turn();

        //Check if it is checkmate
        if (is_checkmate()) {
            if (turn == 0) { turn = -1; }
            else if (turn == 1) {turn = -2; }
        }

        System.out.println("");
        System.out.println(this.getBoard());
        //Valid Move
        return true;
    }

    private void reset_board() {
        String strboard = "rnbqkbnr\n" +
                "pppppppp\n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "        \n" +
                "PPPPPPPP\n" +
                "RNBQKBNR";

        board_from_string(strboard);
    }

    public int[] get_position_piece(String piece) {
        int[] abc = {-1, -1};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].to_string().equals(piece)) {
                    abc[0] = i; abc[1] = j;
                    return abc;
                }
            }
        }
        return abc;
    }

}
