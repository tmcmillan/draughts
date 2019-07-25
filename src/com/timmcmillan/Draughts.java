package com.timmcmillan;

import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Draughts {

    //initiate a row of leters that will be used to construct the board.
    String[] rowLetters = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
    Scanner scanner = new Scanner(System.in);

    /**
     * newGame is a method which contains many other methods and enables the game to be played
     */
    public void newGame() {

        //get the player's names
        System.out.println("\nWelcome to the game!");
        String playingWhite = welcomePlayer("white");
        String playingBlack = welcomePlayer("black");
        System.out.println("Hi " + playingWhite + " and " + playingBlack + "! lets play draughts!");

        // continue playing for as long as we don't quit
        boolean quit = false;

        while (!quit) {
            //the positions of the pieces are stored in the 2d array "piecePositions"
            // the Board function is called, which inserts the pieces onto the board (saved in piecePositions)
            Piece[][] piecePositions = Board(playingWhite);

            //the piecePositions are then illustrated by drawing a representation of the board
            drawBoard(piecePositions);

            //we start with white going first
            boolean whitesTurn = true;
            String colourToMove;

            // playing until the game is over (when one of the players does not have any pieces remaining
            boolean gameOver = false;

            while (!gameOver) {

                //calls a function colourToMove, which interprets the boolean whitesTurn and returns a String, either "White" or "Black"
                colourToMove = colourToMove(whitesTurn);

                //makeMove takes the current piece positions, and the colour whos turn it is to move.
                //it takes input from the user, and if this input is valid, it will update the piecesPositions argument
                //it returns a boolean, which tells us whether the pieces have moved (i.e. whether the input was valid)

                boolean piecesHaveMoved = makeMove(piecePositions, colourToMove);

                //re-draw the board to illustrate the updated positions
                drawBoard(piecePositions);

                //if the pieces have moved (i.e the player's input was valid), it is now the other player's turn.
                //if the pieces haven't moved (i.e. the player's input was not valid), give them another go
                if (piecesHaveMoved) {
                    whitesTurn = (!whitesTurn);
                } else {
                    System.out.println("That was an invalid move, have another go");
                }

                quit = true;
            }
        }
    }


    /**
     *
     * @param colour the colour of pieces that this player will be using
     * @return a String, the player using that colour
     */
    public String welcomePlayer(String colour) {
        System.out.println("\nEnter the name of the player playing " + colour);
        String playerName = scanner.nextLine();

        return playerName;
    }

    /**
     *
     * @param playerName is only necessary to wind Roma up. It gives him a different number of pieces
     * @return a 2-D array, of pieces. The array either contains null values, or pieces.
     * Pieces have a set colour, and a boolean value corresponding to whether or not they are a queen.
     */
    public Piece[][] Board(String playerName) {


        Piece[][] piecePositions = new Piece[8][8];

        if(playerName.equals("roma")) {
            //initialise board black pieces
            for (int i = 0; i < 3; i += 2) {
                for (int j = 1; j < 8; j += 2) {
                    piecePositions[i][j] = new Piece("Black", false);
                }
                for (int j = 0; j < 8; j += 2) {
                    piecePositions[1][j] = new Piece("Black", false);
                }
            }

            //initialise board white pieces
                for (int j = 1; j < 8; j += 2) {
                    piecePositions[6][j] = new Piece("White", false);
                }
            }
        else {
            //initialise board black pieces
            for (int i = 0; i < 3; i += 2) {
                for (int j = 1; j < 8; j += 2) {
                    piecePositions[i][j] = new Piece("Black", false);
                }
                for (int j = 0; j < 8; j += 2) {
                    piecePositions[1][j] = new Piece("Black", false);
                }
            }

            //initialise board white pieces
            for (int i = 5; i < 8; i += 2) {
                for (int j = 0; j < 8; j += 2) {
                    piecePositions[i][j] = new Piece("White", false);
                }
                for (int j = 1; j < 8; j += 2) {
                    piecePositions[6][j] = new Piece("White", false);
                }
            }
        }
        return piecePositions;
    }

    /**
     *
     * @param piecePositions the location of the pieces from the 2-d array piecePositions
     *  the method utilises the piece function, which takes the input of the 2-d array piecePositions, the row index and the
     *  column index. It returns either a "W" if that location holds a white piece, "B" if it holds
     *  a black piece, or " " if it is null (i.e. no piece at that location).
     */
    public void drawBoard(Piece[][] piecePositions) {
        System.out.println("                                                                                                               \n" +
                "           1        2        3        4        5        6        7        8   ");
        for (int i = 0; i < 4; i++) {
            System.out.println("       * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                    "       * * * * *         * * * * *         * * * * *         * * * * *         *\n" +
                    "    " + rowLetters[2 * i] + "  * * * * *   " + piece(piecePositions, 2 * i, 1) + "   * * * * *   " + piece(piecePositions, 2 * i, 3) + "   * * * * *   " + piece(piecePositions, 2 * i, 5) + "   * * * * *   " + piece(piecePositions, 2 * i, 7) + "   *\n" +
                    "       * * * * *         * * * * *         * * * * *         * * * * *         *\n" +
                    "       * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                    "       *         * * * * *         * * * * *         * * * * *         * * * * *\n" +
                    "    " + rowLetters[2 * i + 1] + "  *   " + piece(piecePositions, 2 * i + 1, 0) + "   * * * * *   " + piece(piecePositions, 2 * i + 1, 2) + "   * * * * *   " + piece(piecePositions, 2 * i + 1, 4) + "   * * * * *   " + piece(piecePositions, 2 * i + 1, 6) + "   * * * * *\n" +
                    "       *         * * * * *         * * * * *         * * * * *         * * * * *");
        }
        System.out.println("       * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    /**
     *
     * @param piecePositions 2-d array of pieces
     * @param rowIndex index of the row location
     * @param columnIndex index of the column location
     * @return either a "W" if that location holds a white piece, "B" if it hold
     * a black piece, or " " if it is null (i.e. no piece at that location)
     */
    public String piece(Piece[][] piecePositions, int rowIndex, int columnIndex) {

        if (piecePositions[rowIndex][columnIndex] == null) {
            return "   ";
        }

        if (piecePositions[rowIndex][columnIndex].getPieceColour().equals("White")) {
            if(piecePositions[rowIndex][columnIndex].isAQueen()) {
                return "wWw";
            } else {
                return " W ";
            }
        } else {
            if(piecePositions[rowIndex][columnIndex].isAQueen()) {
            return "bBb";
        } else {
                return " B ";
            }
        }

    }

    /**
     *
     * @param whitesTurn boolean, equals true if it is white's turn to move
     * @return String of "White" or "Black", whoever's turn it is to move
     */
    public String colourToMove(boolean whitesTurn) {
        if(whitesTurn) {
            return "White";
        } else {
            return "Black";
        }
    }

    /**
     *
     * @param piecePositions the 2d array of pieces
     * @param colourToMove which colour is to move
     * @param moveFromRowInt the rowindex of the piece to be moved
     * @param moveFromColumnInt the columnindex of the piece to be moved
     * @param moveToRowInt the rowindex of the desired location
     * @param moveToColumnInt the columnindex of the desired location
     * @return an updated 2d array of pieces
    */
    public Piece[][] movePiece(Piece[][] piecePositions, String colourToMove, int moveFromRowInt, int moveFromColumnInt, int moveToRowInt, int moveToColumnInt){

        //new piece at the desired location, of the same colour and type as the piece that has been moved
        // remove the previous location of the piece
        if((colourToMove.equals("White") && moveToRowInt==0) || (colourToMove.equals("Black") && moveToRowInt==7)) {
            piecePositions[moveToRowInt][moveToColumnInt] = new Piece(colourToMove, true);
        }
        else {
            piecePositions[moveToRowInt][moveToColumnInt] = new Piece(colourToMove, piecePositions[moveFromRowInt][moveFromColumnInt].isAQueen());
        }

        piecePositions[moveFromRowInt][moveFromColumnInt] = null;

        return  piecePositions;
    }

    /**
     *
     * @param piecePositions the 2d array of pieces
     * @param colourToMove which colour is to move
     * @param moveFromRowInt the rowindex of the piece to be moved
     * @param moveFromColumnInt the columnindex of the piece to be moved
     * @param moveToRowInt the rowindex of the desired location
     * @param moveToColumnInt the columnindex of the desired location
     * @return a boolean. True if the desired move is valid of a pawn, false if not
     */
    public boolean isValidPawnMove (Piece[][] piecePositions, String colourToMove, int moveFromRowInt, int moveFromColumnInt, int moveToRowInt, int moveToColumnInt) {

        //default value of isValid move is false
        // utilises methods hasFowardLeftNeighbour and hasFowardRightNeighbour. These check whether the piece to be
        // moved has an adjacent opposing piece (forward left / forward right)

        boolean isValidMove = false;
        boolean hasFowardLeftNeighbour = hasFowardLeftNeighbour(piecePositions, colourToMove, moveFromRowInt, moveFromColumnInt);
        boolean hasFowardRightNeighbour = hasFowardRightNeighbour(piecePositions, colourToMove, moveFromRowInt, moveFromColumnInt);

        int forwardDirection;
        int leftDirection;

        //the forward and left directions differ according to whether the white or black pieces are moving
        // for example, when a white piece goes forward, the index of the rows decreases by 1.
        if (colourToMove == "White") {
            forwardDirection = -1;
            leftDirection = -1;
        } else {
            forwardDirection = 1;
            leftDirection = 1;
        }

        //4 possible moves for pawns. move forwardleft 1 or 2, or move forwardright 1 or 2
        boolean moveForwardLeftOne = (moveToRowInt == moveFromRowInt + forwardDirection) && (moveToColumnInt == moveFromColumnInt + leftDirection);
        boolean moveForwardRightOne = (moveToRowInt == moveFromRowInt + forwardDirection) && (moveToColumnInt == moveFromColumnInt - leftDirection);
        boolean moveForwardLeftTwo = (moveToRowInt == moveFromRowInt + forwardDirection * 2) && (moveToColumnInt == moveFromColumnInt + leftDirection * 2);
        boolean moveForwardRightTwo = (moveToRowInt == moveFromRowInt + forwardDirection * 2) && (moveToColumnInt == moveFromColumnInt - leftDirection * 2);

        //if we have been given an invalid piece location or destination (e.g. outside of the board), isValidMove is false.
        if (moveFromRowInt == -1 || moveToRowInt == -1 || moveFromColumnInt < 0 || moveFromColumnInt >= 8 || moveToColumnInt < 0 || moveToColumnInt >= 8 ||
                moveFromRowInt == moveToRowInt || moveFromColumnInt == moveToColumnInt) {
            System.out.println("invalid input");
            isValidMove = false;
        } else
            // if the colour of the piece to be moved is not the same as the colour who's turn it is to move
            {
            if (!piecePositions[moveFromRowInt][moveFromColumnInt].getPieceColour().equals(colourToMove)) {
                System.out.println("You cannot move your opponents piece!");
            } else if
            // if there is no piece on the square specified to move from
            (piecePositions[moveFromRowInt][moveFromColumnInt] == null) {
                System.out.println("There's no piece to move from that square!");
            } else if (piecePositions[moveToRowInt][moveToColumnInt] != null)
                // if the destination is not empty.
                {
                    System.out.println("The destination square is occupied, please try again");
                } else

                    //if the current location is valid, the appropriate colour, and the destination location is valid and empty
                    {
                        //there is no neighbours, the piece can move only one row and one column
                        if (!hasFowardLeftNeighbour && !hasFowardRightNeighbour) {
                            if (moveToRowInt == moveFromRowInt + forwardDirection && Math.abs(moveFromColumnInt - moveToColumnInt) == 1) {
                                isValidMove = true;
                            }
                        }
                        // there is one neighbour on the left, none on the right. can either move 2 left or one right
                        else if (hasFowardLeftNeighbour && !hasFowardRightNeighbour) {
                            if (moveForwardLeftTwo || moveForwardRightOne) {
                                isValidMove = true;
                            }
                        }
                        // there is no neighbour on the left, one on the right. can either move one left or 2 right
                        else if (!hasFowardLeftNeighbour && hasFowardRightNeighbour) {
                            if (moveForwardLeftOne || moveForwardRightTwo) {
                                isValidMove = true;
                            }
                        }
                        // there are neighbours on both sides. Can either move 2 left or right
                        else { // (hasFowardLeftNeighbour && hasFowardRightNeighbour) {
                            if (moveForwardLeftTwo || moveForwardRightTwo)
                            {
                                isValidMove = true;
                            }
                        }
                    }
                    }
        return isValidMove;
        }

    /**
     *
     piecePositions the 2d array of pieces
     * @param colourToMove which colour is to move
     * @param moveFromRowInt the rowindex of the piece to be moved
     * @param moveFromColumnInt the columnindex of the piece to be moved
     * @return a boolean. True if the piece to be moved adjacent opposing piece forward left
     */
    public boolean hasFowardLeftNeighbour(Piece[][] piecePositions, String colourToMove, int moveFromRowInt, int moveFromColumnInt) {

        int forwardDirection;
        int leftDirection;

        boolean hasForwardLeftNeighbour = false;

        if (colourToMove == "White") {
            forwardDirection = -1;
            leftDirection = -1;
        } else {
            forwardDirection = 1;
            leftDirection = 1;
        }

        if(moveFromColumnInt + leftDirection <0 || moveFromColumnInt + leftDirection >7  || moveFromRowInt + forwardDirection < 0 || moveFromRowInt + forwardDirection >7){
            hasForwardLeftNeighbour = false;
        } else {
            if (piecePositions[moveFromRowInt + forwardDirection][moveFromColumnInt + leftDirection] == null) {
                hasForwardLeftNeighbour = false;
            } else if (piecePositions[moveFromRowInt + forwardDirection][moveFromColumnInt + leftDirection].getPieceColour() != colourToMove) {
                hasForwardLeftNeighbour = true;
            } else {
                hasForwardLeftNeighbour = false;
            }
        }

        return hasForwardLeftNeighbour;
    }

    /**
     *
     piecePositions the 2d array of pieces
     * @param colourToMove which colour is to move
     * @param moveFromRowInt the rowindex of the piece to be moved
     * @param moveFromColumnInt the columnindex of the piece to be moved
     * @return a boolean. True if the piece to be moved adjacent opposing piece forward right
     */
    public boolean hasFowardRightNeighbour(Piece[][] piecePositions, String colourToMove, int moveFromRowInt, int moveFromColumnInt) {

        int forwardDirection;
        int rightDirection;

        boolean hasForwardRightNeighbour = false;

        if (colourToMove == "White") {
            forwardDirection = -1;
            rightDirection = 1;
        } else {
            forwardDirection = 1;
            rightDirection = -1;
        }

        if(moveFromColumnInt + rightDirection <0 || moveFromColumnInt + rightDirection >7 || moveFromRowInt + forwardDirection < 0 || moveFromRowInt + forwardDirection >7) {
            hasForwardRightNeighbour = false;
        } else {

            if (piecePositions[moveFromRowInt + forwardDirection][moveFromColumnInt + rightDirection] == null) {
                hasForwardRightNeighbour = false;
            } else if (piecePositions[moveFromRowInt + forwardDirection][moveFromColumnInt + rightDirection].getPieceColour() != colourToMove) {
                hasForwardRightNeighbour = true;
            } else {
                hasForwardRightNeighbour = false;
            }
        }

        return hasForwardRightNeighbour;
    }

    /**
     *
     /**
     *
     piecePositions the 2d array of pieces
     * @param colourToMove which colour is to move
     * @param moveFromRowInt the rowindex of the piece to be moved
     * @param moveFromColumnInt the columnindex of the piece to be moved
     * @return a boolean. True if the piece to be moved adjacent opposing piece backward left

     */
    public boolean hasBackwardLeftNeighbour(Piece[][] piecePositions, String colourToMove, int moveFromRowInt, int moveFromColumnInt, int moveToRowInt, int moveToColumnInt) {

        int backwardDirection;
        int leftDirection;

        boolean hasBackwardLeftNeighbour = false;

        if (colourToMove == "White") {
            backwardDirection = 1;
            leftDirection = -1;
        } else {
            backwardDirection = -1;
            leftDirection = 1;
        }

        if (piecePositions[moveFromRowInt + backwardDirection][moveFromColumnInt + leftDirection] == null) {
            hasBackwardLeftNeighbour = false;
        } else if (piecePositions[moveFromRowInt + backwardDirection][moveFromColumnInt + leftDirection].getPieceColour() != colourToMove) {
            hasBackwardLeftNeighbour = true;
        } else {
            hasBackwardLeftNeighbour = false;
        }
        return hasBackwardLeftNeighbour;
    }

    /**
     *
     /**
     *
     piecePositions the 2d array of pieces
     * @param colourToMove which colour is to move
     * @param moveFromRowInt the rowindex of the piece to be moved
     * @param moveFromColumnInt the columnindex of the piece to be moved
     * @return a boolean. True if the piece to be moved adjacent opposing piece backward right
     */
    public boolean hasBackwardRightNeighbour(Piece[][] piecePositions, String colourToMove, int moveFromRowInt, int moveFromColumnInt, int moveToRowInt, int moveToColumnInt) {

        int backwardDirection;
        int rightDirection;

        boolean hasBackwardRightNeighbour = false;

        if (colourToMove == "White") {
            backwardDirection = 1;
            rightDirection = 1;
        } else {
            backwardDirection = -1;
            rightDirection = -1;
        }

        if (piecePositions[moveFromRowInt + backwardDirection][moveFromColumnInt + rightDirection] == null) {
            hasBackwardRightNeighbour = false;
        } else if (piecePositions[moveFromRowInt + backwardDirection][moveFromColumnInt + rightDirection].getPieceColour() != colourToMove) {
            hasBackwardRightNeighbour = true;
        } else {
            hasBackwardRightNeighbour = false;
        }

        return hasBackwardRightNeighbour;
    }

    /**
     * uses a scanner to get the row:column referrance of the piece that the user would like to move
     * and the desired location
     * @param piecePositions 2d array of pieces
     * @param colourToMove the colour whos turn it is to move
     * @return a boolean if the move was valid. Also updates the piecePositions (piece locations)
     */
    public boolean makeMove(Piece[][] piecePositions, String colourToMove) {

        boolean isValidMove = false;

        System.out.println(colourToMove + " to move");

        System.out.println(colourToMove + ": select piece to move");
        String moveFrom = scanner.nextLine();
        System.out.println(colourToMove + ": select square to move to");
        String moveTo = scanner.nextLine();

        char moveFromRow = Character.toUpperCase(moveFrom.charAt(0));
        int moveFromRowInt = convertRowLetterToNumber(moveFromRow);
        int moveFromColumnInt = Character.getNumericValue(moveFrom.charAt(1)) - 1;

        char moveToRow = Character.toUpperCase(moveTo.charAt(0));
        int moveToRowInt = convertRowLetterToNumber(moveToRow);
        int moveToColumnInt = Character.getNumericValue(moveTo.charAt(1)) - 1;

        if (moveFromRowInt == -1 || moveToRowInt == -1 || moveFromColumnInt < 0 || moveFromColumnInt >= 8 || moveToColumnInt < 0 || moveToColumnInt >= 8 ||
        moveFromRowInt == moveToRowInt || moveFromColumnInt == moveToColumnInt) {
        }
        else {
            if (piecePositions[moveFromRowInt][moveFromColumnInt].isAQueen() == false) {
                isValidMove = isValidPawnMove(piecePositions, colourToMove, moveFromRowInt, moveFromColumnInt, moveToRowInt, moveToColumnInt);
            }

            if (isValidMove) {
                movePiece(piecePositions, colourToMove, moveFromRowInt, moveFromColumnInt, moveToRowInt, moveToColumnInt);

                if (Math.abs(moveFromColumnInt - moveToColumnInt) == 2) {
                    piecePositions[moveFromRowInt + ((moveToRowInt - moveFromRowInt) / 2)][moveFromColumnInt + ((moveToColumnInt - moveFromColumnInt) / 2)] = null;
                }
            } else {
                System.out.println("That move was invalid!");
            }
        }

        return isValidMove;
    }

    /**
     * A switch to convert the rowletter inputted by the user into an int that can be used as an index for the
     * piecePosition 2d array
     * @param rowLetter inputted by the user
     * @return an int that can be used as an index for the piecePosition 2d array
     */
    public int convertRowLetterToNumber(char rowLetter) {

        int rowNumber = -1;

        switch(rowLetter) {
            case 'A':
                rowNumber = 0;
                break;
            case 'B':
                rowNumber = 1;
            break;
            case 'C':
                rowNumber = 2;
            break;
            case 'D':
                rowNumber = 3;
            break;
            case 'E':
                rowNumber = 4;
            break;
            case 'F':
                rowNumber = 5;
            break;
            case 'G':
                rowNumber = 6;
            break;
            case 'H':
                rowNumber = 7;
            break;
        }
        return rowNumber;
    }


}
