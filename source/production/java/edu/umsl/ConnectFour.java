package edu.umsl;

import java.util.Scanner;

public class ConnectFour {
    // true -> player1; false -> player2
    private boolean playerOneTurn = true;
    private int rowIndex = 0;
    private int colIndex = 0;
    private int numTurns = 0;
    private int[][] gameBoard = new int[6][7];  // [rows][col]
    private  boolean gameOver = false;


    void togglePlayerOneTurn() { this.playerOneTurn = !this.playerOneTurn; }

    void incrementNumTurns() { this.numTurns++; }


    public void displayBoard() {
        System.out.println(" 0 1 2 3 4 5 6");
        for (int[] ints : this.gameBoard) {
            for (int j = 0; j < this.gameBoard[0].length; j++) {
                switch (ints[j]) {
                    case 1 -> System.out.print("|R");
                    case 2 -> System.out.print("|Y");
                    default -> System.out.print("| ");
                }
                if (j == this.gameBoard[0].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }
    // add player value to board array
    private void assignToken() {
        this.gameBoard[this.rowIndex][this.colIndex] = (this.playerOneTurn)?  1 : 2;
    }

    // if col choice is valid, set the rowIndex and colIndex and return true
    // if invalid, return false
    private boolean validColChoice(int colChoice){
        for(int i = 5; i > -1; i--){
            if(this.gameBoard[i][colChoice] == 0){
                this.rowIndex = i;
                this.colIndex = colChoice;
                return true;
            }
        }
        return false;
    }

    // get players rowChoice, ensures choice is valid
    public void getPlayerChoice() {
        int colChoice;
        boolean properInput = false;
        String playersMarker = (this.playerOneTurn)? "RED" : "Yellow";
        while(!properInput){
            Scanner input = new Scanner(System.in);
            try {
                System.out.print("choose the column (0 - 6) to drop the " + playersMarker + " tile" + ": ");
                colChoice = input.nextInt();
                if (colChoice < 0 || colChoice > 6){
                    System.out.println("Number must be between 0 and 6");
                }
                else {
                    if(this.validColChoice(colChoice)){
                        properInput = true;
                    }
                    else System.out.println("That column is full, please try again");
                }
            } catch (Exception ex) {
                System.out.println("There is a problem with your input please try again");
            }
        }
    }

    // returns true if four in a row of a players token, false if not.
    // https://stackoverflow.com/questions/33181356/connect-four-game-checking-for-wins-js  (modified first result)
    private static boolean checkLine(int a, int b, int c, int d){
        return ((a != 0) && (a == b) && (a == c) && (a==d));
    }

    // If winner, we set gameOver = true and announce winner
    // announce draw if no winner and board is full
    // other wise, nothing happens
    public void checkWinner() {
        int[][] gb = this.gameBoard;
        // Check down
        for (int r = 0; r < 3 && !this.gameOver; r++){
            for (int c = 0; c < 7 && !this.gameOver; c++){
                if (checkLine(gb[r][c], gb[r+1][c], gb[r+2][c], gb[r+3][c])){
                    this.gameOver = true;
                }
            }
        }
        // Check right
        for (int r = 0; r < 6 && !this.gameOver; r++){
            for (int c = 0; c < 4 && !this.gameOver; c++){
                if (checkLine(gb[r][c], gb[r][c+1], gb[r][c+2], gb[r][c+3])){
                    this.gameOver = true;
                }
            }
        }
        // Check down-right
        for (int r = 0; r < 3 && !this.gameOver; r++){
            for (int c = 0; c < 4 && !this.gameOver; c++){
                if (checkLine(gb[r][c], gb[r+1][c+1], gb[r+2][c+2], gb[r+3][c+3])){
                    this.gameOver = true;
                }
            }
        }
        // Check down-left
        for (int r = 3; r < 6 && !this.gameOver; r++){
            for (int c = 0; c < 4 && !this.gameOver; c++){
                if (checkLine(gb[r][c], gb[r-1][c+1], gb[r-2][c+2], gb[r-3][c+3])){
                    this.gameOver = true;
                }
            }
        }
        if(this.gameOver){
            String winningMessage = (this.playerOneTurn)? "Player 1 Wins!" : "Player 2 Wins!";
            System.out.println(winningMessage);
        }
        // no winner, and 42 moves have been made -> we have a draw
        else if (this.numTurns == 42){
            this.gameOver = true;
            System.out.println("It's a draw");
        }
    }


    // playGame executes while !gameOver
    public void playGame() {
        while(!this.gameOver){
            this.getPlayerChoice();
            this.assignToken();
            this.displayBoard();
            this.incrementNumTurns();
            this.checkWinner();
            this.togglePlayerOneTurn();
        }
    }


    public static void main(String[] args){

        boolean playAgain = true;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Would you like to play connect four? (y/n): ");
            try{
                char ans = input.next().charAt(0);
                if (ans == 'y'){
                    ConnectFour connectFour = new ConnectFour();
                    connectFour.displayBoard();
                    connectFour.playGame();
                }
                else if (ans == 'n'){
                    playAgain = false;
                }
                else {
                    System.out.println("There is a problem with your input please try again");
                }
            }catch (Exception ex){
                System.out.println("There is a problem with your input please try again");
            }
        }while (playAgain);

    }
}
