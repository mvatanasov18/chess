package com.example.chess.models;


public class Pawn extends Piece {

    public String to_string() {
        if (Color == 0) { return "p"; }
        else            { return "P"; }
    }

    public Pawn(int PositionY, int PositionX, int C) {
        super(PositionY, PositionX, C);

    }

    public boolean is_valid_move(Piece[][] board, int new_posy, int new_posx) {
        if (!position_valid(new_posy, new_posx)) { return false; }

        int dy = new_posy - Position[0];
        int dx = new_posx - Position[1];

        if ((Color == 0 && dy == 2 && dx == 0 && Position[0] == 1 && board[2][new_posx] == null && board[3][new_posx] == null) ||
                (Color == 1 && dy == -2 && dx == 0 && Position[0] == 6 && board[5][new_posx] == null && board[4][new_posx] == null)) {
            return true;
        }

        //Make sure pawns aren't going backwards.
        if ((Color == 0 && dy < 0) || (Color == 1 && dy > 0)) { return false; }

        //Check if distance is beyond bounds of regular move.
        if (dy > 1 || dx > 1 || dy < -1 || dx < -1 || dy == 0) { return false; }

        //Check capture.
        if (Math.abs(dx) == 1) {
            return board[new_posy][new_posx] != null && board[new_posy][new_posx].Color != Color;
        }

        //Only valid move left is non-capture
        return board[new_posy][new_posx] == null;
    }

}

