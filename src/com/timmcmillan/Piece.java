package com.timmcmillan;

public class Piece {

    private String pieceColour;
    private boolean isAQueen;

    public Piece(String pieceColour, boolean isAQueen) {
        this.pieceColour = pieceColour;
        this.isAQueen = isAQueen;
    }

    public String getPieceColour() {
        return pieceColour;
    }

    public boolean isAQueen() {
        return isAQueen;
    }
}
