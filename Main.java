/*
Problem Set Unit 1 (Tic-Tac-Toe)
Author: ._.
Date Created: March 11, 2024
Date Last Modified: March 11, 2024
*/

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random; 


public class Main {
    
    public static boolean firstRun = true; //bool to ensure arraylists for user/score are only pulled from file once
    
    
    public static String userList = "users.txt"; // file of scores/names
    
    // parallel arraylists of users/scores 
    public static ArrayList<String> users = new ArrayList<String>(); 
    public static ArrayList<Integer> scores = new ArrayList<Integer>();

    public static boolean emptyUserFile = false; // for knowing whether to start with newline char or not when writing to file
    public static boolean nameAlreadyExists = false; // for checking whether or not to add name to array
    
    public static int numCurrentUser = 1; //determines current user for assigning names
    
    //holds usernames
    public static String user1; 
    public static String user2;

    public static boolean resetUsers = true; // whether or not user wants to replay at the end
    public static int move; //stores grid space selection from current user's turn
    public static int moveCount = 0; //stores how many turns have passed
    
    //holds usernames after randomization
    public static String xUser; 
    public static String oUser;

    public static String winner; //stores winner after a winner is declared

    // input check modes, mostly for convenience
    public static String userMode = "userMode";
    public static String menuMode = "menuMode";
    public static String moveMode = "moveMode";
    public static String replayMode = "replayMode";
    public static String resetUsersMode = "resetUsersMode";
    
    public static boolean winyet = false; // if a win has occurred in current round yet
    
    public static boolean playAgain = true; // whether the user wants to play again
    
    // 2d array of game board
    public static String gameBoard[][] = {
        {"1", "2", "3"},
        {"4", "5", "6"},
        {"7", "8", "9"}
    }; 
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        
        ArrayWriteFromFile(); // reads file & writes to arraylists
        
        while (playAgain) { // main game loop- stop if they dont want to play again
            menu(s);
        }
        
        s.close(); // close scanner
    }
    

    // clear board (for after game)
    public static void refreshBoard(){

        // iterate through each index and reassign digits 1-9
        for (int i = 1; i <= 9; i++){
            // vertical position can be obtained by the "index" divided by 3;
            //horizontal position by the "index" mod 3
            gameBoard[(i-1)/3][(i-1)%3] = Integer.toString(i); 
        }
    }
    
    // mainmenu function, is looped from main if playagain is true
    public static void menu(Scanner s) { 
        
        clearConsole();
        System.out.println("~ Main Menu ~\n1. Play\n2. How To Play\n3. Leaderboard");

        int choice = Integer.parseInt(inputCheck(s, menuMode)); // user's choice is pulled from input function

        // if they choose game, current user is 1 (to ensure that username is assigned to user 1 every reset),
        // then go to game function
        if (choice == 1) {
            numCurrentUser = 1;
            game(s); 
        } else if (choice == 2) { // if 2 go to instructions
            instructions(s);
        } else { // else (if 3) go to leaderboard
            leaderboard(s);
        }

    }

    public static void leaderboard(Scanner s) {

        // variable for ranking (i.e., if users are tied they will all be listed as being the same rank until there is a user that is not tied)
        int displayScore = 1; 

        clearConsole();

        System.out.println( // do you like my ascii art, i stole it https://patorjk.com/software/taag/
            "\n" + //
            "\n" + //
            "  _      ______          _____  ______ _____  ____   ____          _____  _____  \n" + //
            " | |    |  ____|   /\\   |  __ \\|  ____|  __ \\|  _ \\ / __ \\   /\\   |  __ \\|  __ \\ \n" + //
            " | |    | |__     /  \\  | |  | | |__  | |__) | |_) | |  | | /  \\  | |__) | |  | |\n" + //
            " | |    |  __|   / /\\ \\ | |  | |  __| |  _  /|  _ <| |  | |/ /\\ \\ |  _  /| |  | |\n" + //
            " | |____| |____ / ____ \\| |__| | |____| | \\ \\| |_) | |__| / ____ \\| | \\ \\| |__| |\n" + //
            " |______|______/_/    \\_\\_____/|______|_|  \\_\\____/ \\____/_/    \\_\\_|  \\_\\_____/ \n");

        // if users file is empty
        if (scores.size() == 0) { 
            System.out.println("No highscores have been logged- Play a round to see your highscore listed here!");
            System.out.println("Hit enter to return to the main menu.");
            s.nextLine();
            return;
        }



        for (int i = 0; i < scores.size() && i < 10; i++){ // go through array until reach 10 users or end of array

            try {

                //first user will always simply be 1.
                if (i == 0) {System.out.println(displayScore + ". " + users.get(i) + ": " + scores.get(i));}

                // users after have the chance of tying with previous user; if tied, dont increment their rank
                // only increment rank once there is a user who is NOT tied with previous user(s)
                if (i > 0) {
                    if (scores.get(i) == scores.get(i-1)){ // case for if tied
                        System.out.println(displayScore + ". " + users.get(i) + ": " + scores.get(i));
                    } else { // case for if not tied
                        displayScore = i + 1;
                        System.out.println(displayScore + ". " + users.get(i) + ": " + scores.get(i));
                    }
                }
                
            } catch (Exception e) {}
            
        }
        System.out.println("\nHit enter to return to the main menu.");

        s.nextLine(); // wait for user to hit enter
        
    }


    public static void game(Scanner s) {

        while (playAgain) { // if the user wants to play again; playAgain is initially true
            winyet = false; // has a win occurred yet this round
            numCurrentUser = 1; // who is the current user still requiring a name (for assigning names to user1/user2)
            moveCount = 0; // how many moves have passed this match
            
            refreshBoard();
            
            if (resetUsers){
                user1 = inputCheck(s, userMode); // get user1 from inputcheck

                numCurrentUser += 1;

                user2 = inputCheck(s, userMode); // get user2 from input check
            }

            randomizeFirstPlayer(); // determine which player goes first/is X
            
            while (moveCount < 9 && !winyet){

                clearConsole();
                printBoard();

                move = Integer.parseInt(inputCheck(s, moveMode)); // get move

                if (moveCount%2 == 0) { // if it's x's move, put x in the requested space
                    gameBoard[(move-1)/3][(move-1)%3] = "X";
                } else { // if it's o's move, put o in the requested space
                    gameBoard[(move-1)/3][(move-1)%3] = "O";
                }
                
                if (moveCount > 3) {checkWin();} // only check wins from the 5th move onwards (a win is only possible after x goes 3 times, or 5 rounds [x,o,x,o,x])

                moveCount++; // increment move number

            }

            if (!winyet) { // if while loop above ends and winyet is still false, declare the game a tie
                winner = "tie";
            }

            clearConsole();
            printBoard();

            // set the winner and increment their score in the scores array or simply declare a tie
            if (winner == "X"){
                System.out.println(xUser + " (X) Wins!");
                scoreIncrement(xUser);
            } else if (winner == "O") {
                System.out.println(oUser + " (O) Wins!");
                scoreIncrement(oUser);
            } else {
                System.out.println("Tie game!");
            }

            s.nextLine();

            replayCheck(s); // ask if they want to play again

            if (playAgain) {resetUsersCheck(s);} // if they want to play again, ask if they want to continue as the same users or input new usernames

        }
    }

    public static void replayCheck(Scanner s) {
        if (inputCheck(s, replayMode).equals("n")){ // if they say they dont want to play again, make playagain false
            playAgain = false;
        }
    }

    public static void resetUsersCheck(Scanner s) {
        if (inputCheck(s, resetUsersMode).equals("n")){ // if they want to reset users, make it true, if not make it false
            resetUsers = true;
        } else {
            resetUsers = false;
        }
    }

    public static void checkWin(){
        
        // diagonal win checking
        if((gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]
        || (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]))){
            winyet = true;
            winner = gameBoard[1][1]; // winner will always be in the middle square for both diagonal cases
            return;
        }
        
        for (int i = 0; i < 3; i++){
            // column win checking
            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i]){ // check the entire column in position 1, 2 and 3
                winyet = true;
                winner = gameBoard[0][i];
                return;
            }
            // row win checking
            else if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2]){ // check the entire row in position 1, 2 and 3
                winyet = true;
                winner = gameBoard[i][0];
                return;
            }
        }
    }

    public static void randomizeFirstPlayer() {
        Random rand = new Random();

        // set x/o users randomly
        if (rand.nextBoolean()) {
            xUser = user1;
            oUser = user2;
        } else {
            xUser = user2;
            oUser = user1;
        }

    }

    public static void scoreIncrement(String user){
        // temporary variables for swapping in arraylist
        int tempScore;
        String tempUser;
        
        // index of user to increment
        int index = users.indexOf(user);
    
        //remove from list and add to end of list
        tempScore = scores.get(index);
        tempUser = users.get(index);
        scores.remove(index);
        users.remove(index);
        scores.add(tempScore + 1); // add to end to allow for sorting to work
        users.add(tempUser);

        sortFile(); //sort the file
    }

    // print the board status inside of a board design
    public static void printBoard() {
        System.out.println(
            "+-~-+-~-+-~-+\n" +
            "| " + gameBoard[0][0] + " | " + gameBoard[0][1] + " | " + gameBoard[0][2] + " |\n" +
            "+-~-+-~-+-~-+\n" +
            "| " + gameBoard[1][0] + " | " + gameBoard[1][1] + " | " + gameBoard[1][2] + " |\n" +
            "+-~-+-~-+-~-+\n" +
            "| " + gameBoard[2][0] + " | " + gameBoard[2][1] + " | " + gameBoard[2][2] + " |\n" +
            "+-~-+-~-+-~-+");
    }

    public static void instructions(Scanner s) {
        clearConsole();
        System.out.println("Compete against a friend (or yourself) in order to get three X's or O's in a row!");
        System.out.println("Whoever is first to get three of their symbol, be it an X or an O, in a row, column, or diagonal wins!");
        System.out.println("When prompted to move, select the number on the grid, from 1-9, at the location that you wish to play.");
        System.out.println("If said move is possible, the move will be completed.");
        System.out.println("If said move is NOT possible, you will be prompted to make a valid move.");

        System.out.println("Hit enter to return to the main menu.");

        s.nextLine();

    }


    public static void ArrayWriteFromFile(){
        Scanner fileScanner = null;
        PrintWriter fileWriter = null;

        try {
            // create scanner and printwriter objects
            fileScanner = new Scanner(new BufferedReader(new FileReader(userList)));
            fileWriter = new PrintWriter(new FileWriter(userList, true));

            // create user/score variables
            String userName = "";
            int wins = 0;


            emptyUserFile = !(fileScanner.hasNext()); // if file is empty

            
            while ((fileScanner.hasNext()) && firstRun) { // only run the first time (firstrun becomes false shortly after)
                
                wins = fileScanner.nextInt(); // read number of wins   
                userName = (fileScanner.nextLine()).strip(); // read name and strip and leading/trailing spaces
                
                // add to parallel arraylists
                users.add(userName);
                scores.add(wins);
                
            } 

        } catch (Exception e) {
        } finally {

            firstRun = false; // make firstrun false

            // and close scanner + printwriter
            if (fileScanner != null){
                fileScanner.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    public static String inputCheck(Scanner s, String mode) {
        Scanner fileScanner = null;
        PrintWriter fileWriter = null;

        // create an empty string to contain the return string value
        String inputtedValue = "";
        
        // for if the function is called to get player names...
        if (mode.equals(userMode)) {
            
            while ((inputtedValue.strip()).equals("")) {
                System.out.print("Choose a username for Player " + numCurrentUser + ": ");
                inputtedValue = s.nextLine(); // get username
            }

            // if player 2 inputs the same name as player 1 refuse and ask for another name
            while ((numCurrentUser == 2) && (inputtedValue.strip()).equals(user1.strip())) {
                System.out.print("User taken by Player 1! Choose a different username for Player " + numCurrentUser + ": ");
                inputtedValue = s.nextLine();
            }
            
            nameAlreadyExists = users.contains(inputtedValue.strip()); // check if the name previously existed in the file
            
            if (!nameAlreadyExists){ // if it didnt previously exist in the file, add it to the arraylists, if not it will already be in there
                
                    users.add(inputtedValue);
                    scores.add(0);

                try {
                    fileScanner = new Scanner(new BufferedReader(new FileReader(userList)));
                    fileWriter = new PrintWriter(new FileWriter(userList, true));

                    // append new names to the file with a score of 0
                    if (emptyUserFile) { // if it's the first line in the file, we dont need to start with a newline character.
                        fileWriter.print("0 " + inputtedValue);
                    } else {
                        fileWriter.print("\n" + "0 " + inputtedValue); // if its not the firstline, put a newline in first
                    }
                } catch (Exception e) {} finally {
                    // close scanner/printwriter
                    if (fileScanner != null){
                        fileScanner.close();
                    }
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                }
            }

            ArrayWriteFromFile(); // write to file from arrays now that new users are added

            return inputtedValue; // return the name that was inputted
        }

        if (mode.equals(menuMode)) { // ensure that input is only 1, 2, or 3 for mainmenu

            String choice = "";

            // keep looping until valid input
            while (!(choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
                
                try {
                    System.out.print("Input a number from 1-3: ");
                    choice = s.nextLine();
                    if (!(Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= 3)) throw new Exception("choice was not in range 1-3");
                } catch (Exception e) {
                    choice = "";
                    clearConsole();
                    System.out.println("~ Main Menu ~\n1. Play\n2. How To Play\n3. Leaderboard");

                }
            }
            return choice;
        }

        if (mode.equals(moveMode)){ // for taking moves when the game has begun

            int choice = -1; // default value

            while (!(choice >= 1 && choice <= 9)) // until input is valid
            {
                try {
                    if ((moveCount % 2) == 0){ // X's turn if movecount is even (starts at 0)
                        System.out.print("It's " + xUser + "'s (X) turn! Choose an available space from 1-9: ");
                    } else { // O's turn if odd
                        System.out.print("It's " + oUser + "'s (O) turn! Choose an available space from 1-9: ");
                    }
                    
                    choice = s.nextInt(); // take input

                    // ensure that the input is valid AND that the move doesn't overwrite any previous moves
                    if (!(choice >= 1 && choice <= 9)
                        || gameBoard[(choice-1)/3][(choice-1)%3] == "X"
                        || gameBoard[(choice-1)/3][(choice-1)%3] == "O") 
                            throw new Exception("choice was not in range 1-9");
                
                        return Integer.toString(choice); // return the choice

                } catch (Exception e) {
                    // if input is invalid, reset and keep asking
                    choice = -1;
                    s.nextLine();
                    clearConsole();
                    printBoard();

                }
            }
        }
        
        if (mode.equals(replayMode)){ // check if the user wants to play again
            String choice = "";

            // only take input y/n
            while (!((choice.toLowerCase()).equals("y") || (choice.toLowerCase()).equals("n"))) {

                System.out.print("Would you like to play again? Input y/n: ");
                choice = s.nextLine();
                }

                return choice;
        }

        if (mode.equals(resetUsersMode)){ // check if the user wants to continue with existing usernames or not
            String choice = "";

            // only take input y/n
            while (!((choice.toLowerCase()).equals("y") || (choice.toLowerCase()).equals("n"))) {

                System.out.print("Would you like to continue playing with the same usernames? Input y/n: ");
                choice = s.nextLine();
                }

                return choice;
        }

        return "How did you get here";
        
    }

    // sort the file by sorting the arraylist and printing to the file
    public static void sortFile(){
        PrintWriter fileWriter = null;
        String sortedUserScores = "";
        String tempUser;
        int tempScore;

        // go from the bottom of the arraylist and compare it with the value in front of it
        for (int i = (scores.size() - 1); i > 0 && scores.get(i) > scores.get(i-1); i--) {
            // if its greater, swap the values in both user and score arraylists
                tempScore = scores.get(i-1);
                scores.set(i-1, scores.get(i));
                scores.set(i, tempScore);
            
                tempUser = users.get(i-1);
                users.set(i-1, users.get(i));
                users.set(i, tempUser);
        }

        // put everything to be written to the file in a string
        for (int i = 0; i < scores.size(); i++){    
            sortedUserScores += (scores.get(i) + " " + users.get(i));
            if (i < (scores.size() - 1)) {
                sortedUserScores += "\n";
            }
        }

        // put above string into the file, overwrite existing file as it is already in the arraylist at this point
        try {
            fileWriter = new PrintWriter(new FileWriter(userList, false));

            fileWriter.print(sortedUserScores);

        } catch (Exception e) {
        } finally {
            // close the printwriter
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    // "clear" the console by printing a bunch of newlines :P
    public static void clearConsole()
    {
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}