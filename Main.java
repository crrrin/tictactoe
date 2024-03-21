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
    
    public static boolean firstRun = true;
    
    public static String userList = "users.txt";
    public static ArrayList<String> users = new ArrayList<String>();
    public static ArrayList<Integer> scores = new ArrayList<Integer>();
    public static boolean emptyUserFile = false;
    public static boolean nameAlreadyExists = false;
    
    public static int numCurrentUser = 1;
    public static String user1;
    public static String user2;
    public static boolean resetUsers = true;
    public static int move;
    public static int moveCount = 0;
    
    public static String xUser;
    public static String oUser;
    public static String winner;

    public static String userMode = "userMode";
    public static String menuMode = "menuMode";
    public static String moveMode = "moveMode";
    public static String replayMode = "replayMode";
    public static String resetUsersMode = "resetUsersMode";
    
    public static boolean winyet = false;
    
    public static boolean playAgain = true;
    
    public static String gameBoard[][] = {
        {"1", "2", "3"},
        {"4", "5", "6"},
        {"7", "8", "9"}
    }; 
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        
        ArrayWriteFromFile();
        
        while (playAgain) {
            menu(s);
        }
        
        s.close();
        
    }
    
    public static void refreshBoard(){
        for (int i = 1; i <= 9; i++){
            gameBoard[(i-1)/3][(i-1)%3] = Integer.toString(i);
        }
    }
    
    public static void menu(Scanner s) { 
        
        clearConsole();
        System.out.println("~ Main Menu ~\n1. Play\n2. How To Play\n3. Leaderboard");

        int choice = Integer.parseInt(inputCheck(s, menuMode));

        if (choice == 1) {
            numCurrentUser = 1;
            game(s);
        } else if (choice == 2) {
            instructions(s);
        } else {
            leaderboard(s);
        }

    }

    public static void leaderboard(Scanner s) {

        int displayScore = 1;

        clearConsole();

        System.out.println(
            "\n" + //
            "\n" + //
            "  _      ______          _____  ______ _____  ____   ____          _____  _____  \n" + //
            " | |    |  ____|   /\\   |  __ \\|  ____|  __ \\|  _ \\ / __ \\   /\\   |  __ \\|  __ \\ \n" + //
            " | |    | |__     /  \\  | |  | | |__  | |__) | |_) | |  | | /  \\  | |__) | |  | |\n" + //
            " | |    |  __|   / /\\ \\ | |  | |  __| |  _  /|  _ <| |  | |/ /\\ \\ |  _  /| |  | |\n" + //
            " | |____| |____ / ____ \\| |__| | |____| | \\ \\| |_) | |__| / ____ \\| | \\ \\| |__| |\n" + //
            " |______|______/_/    \\_\\_____/|______|_|  \\_\\____/ \\____/_/    \\_\\_|  \\_\\_____/ \n");

        if (scores.size() == 0) {
            System.out.println("No highscores have been logged- Play a round to see your highscore listed here!");
            s.nextLine();
            return;
        }



        for (int i = 0; i < scores.size() && i < 10; i++){

            try {

                if (i == 0) {System.out.println(displayScore + ". " + users.get(i) + ": " + scores.get(i));}

                if (i > 0) {
                    if (scores.get(i) == scores.get(i-1)){
                        System.out.println(displayScore + ". " + users.get(i) + ": " + scores.get(i));
                    } else {
                        displayScore = i + 1;
                        System.out.println(displayScore + ". " + users.get(i) + ": " + scores.get(i));
                    }
                }
                
                
            } catch (Exception e) {}
            
        }
        
        s.nextLine();
        
    }


    public static void game(Scanner s) {

        while (playAgain) {
            winyet = false;
            numCurrentUser = 1;
            moveCount = 0;
            
            refreshBoard();
            
            if (resetUsers){
                user1 = inputCheck(s, userMode);

                numCurrentUser += 1;

                user2 = inputCheck(s, userMode);
            }

            randomizeFirstPlayer();
            
            while (moveCount < 9 && !winyet){

                clearConsole();
                printBoard();

                move = Integer.parseInt(inputCheck(s, moveMode));

                if (moveCount%2 == 0) {
                    gameBoard[(move-1)/3][(move-1)%3] = "X";
                } else {
                    gameBoard[(move-1)/3][(move-1)%3] = "O";
                }
                
                if (moveCount > 3) {checkWin();}

                moveCount++;

            }

            if (!winyet) {
                winner = "tie";
            }

            clearConsole();
            printBoard();

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

            replayCheck(s);

            if (playAgain) {resetUsersCheck(s);}

        }
    }

    public static void replayCheck(Scanner s) {
        if (inputCheck(s, replayMode).equals("n")){
            playAgain = false;
        }
    }

    public static void resetUsersCheck(Scanner s) {
        if (inputCheck(s, resetUsersMode).equals("n")){
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
            winner = gameBoard[1][1];
            return;
        }
        
        // column win checking
        for (int column = 0; column < 3; column++){
            if (gameBoard[0][column] == gameBoard[1][column] && gameBoard[1][column] == gameBoard[2][column]){
                winyet = true;
                winner = gameBoard[0][column];
                return;
            }
        }

        // row win checking
        for (int row = 0; row < 3; row++){
            if (gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][1] == gameBoard[row][2]){
                winyet = true;
                winner = gameBoard[row][0];
                return;
            }
        }       
    }

    public static void randomizeFirstPlayer() {
        Random rand = new Random();

        if (rand.nextBoolean()) {
            xUser = user1;
            oUser = user2;
        } else {
            xUser = user2;
            oUser = user1;
        }

    }

    public static void scoreIncrement(String user){
        int tempScore;
        String tempUser;
        
        int index = users.indexOf(user);
    


        tempScore = scores.get(index);
        tempUser = users.get(index);

        scores.remove(index);
        users.remove(index);

        scores.add(tempScore + 1);
        users.add(tempUser);

        sortFile();
    }

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
            fileScanner = new Scanner(new BufferedReader(new FileReader(userList)));
            fileWriter = new PrintWriter(new FileWriter(userList, true));

            String userName = "";
            int wins = 0;


            emptyUserFile = !(fileScanner.hasNext());

            
            while ((fileScanner.hasNext()) && firstRun) {
                wins = fileScanner.nextInt();
                   
                userName = (fileScanner.nextLine()).strip();
                
                users.add(userName);
                scores.add(wins);
                
            } 

        } catch (Exception e) {
        } finally {
            firstRun = false;

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
        String inputtedValue = ""; // String exists to let mode variable be defined dynamically
        
        // for if the function is called to get player names...
        if (mode.equals(userMode)) {
            
            while ((inputtedValue.strip()).equals("")) {
                System.out.print("Choose a username for Player " + numCurrentUser + ": ");
                inputtedValue = s.nextLine();
            }

            while ((numCurrentUser == 2) && inputtedValue.equals(user1)) {

            System.out.print("User taken by Player 1! Choose a different username for Player " + numCurrentUser + ": ");
            inputtedValue = s.nextLine();

            }
            
            nameAlreadyExists = users.contains(inputtedValue);
            
            if (!nameAlreadyExists){
                
                // if (!nameAlreadyExists) {
                    users.add(inputtedValue);
                    scores.add(0);
                // }

                try {
                    fileScanner = new Scanner(new BufferedReader(new FileReader(userList)));
                    fileWriter = new PrintWriter(new FileWriter(userList, true));

                    if (emptyUserFile) {
                        fileWriter.print("0 " + inputtedValue);
                    } else {
                        fileWriter.print("\n" + "0 " + inputtedValue);
                    }
                } catch (Exception e) {} finally {
                    if (fileScanner != null){
                        fileScanner.close();
                    }
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                }
            }


            ArrayWriteFromFile();
            
            return inputtedValue;
        }

        if (mode.equals(menuMode)) {

            String choice = "";

            while (!(choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
                
                try {
                    System.out.print("Input a number from 1-3: ");
                    choice = s.nextLine();
                    if (!(Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= 3)) throw new Exception("💩");
                } catch (Exception e) {
                    System.out.println(e);
                    choice = "";
                    // s.nextLine();
                    clearConsole();
                    System.out.println("~ Main Menu ~\n1. Play\n2. How To Play\n3. Leaderboard");

                }
            }

            return choice;

        }

        if (mode.equals(moveMode)){

            int choice = -1;

            while (!(choice >= 1 && choice <= 9))
            {
                try {
                    if ((moveCount % 2) == 0){
                        System.out.print("It's " + xUser + "'s (X) turn! Choose an available space from 1-9: ");
                    } else {
                        System.out.print("It's " + oUser + "'s (O) turn! Choose an available space from 1-9: ");
                    }
                    
                    choice = s.nextInt();
                    if (!(choice >= 1 && choice <= 9)
                        || gameBoard[(choice-1)/3][(choice-1)%3] == "X"
                        || gameBoard[(choice-1)/3][(choice-1)%3] == "O") 
                            throw new Exception("choice was not in range 1-9");
                
                        return Integer.toString(choice);

                } catch (Exception e) {
                    choice = -1;
                    s.nextLine();
                    clearConsole();
                    printBoard();

                }
            }
        }
        
        if (mode.equals(replayMode)){
            String choice = "";

            while (!((choice.toLowerCase()).equals("y") || (choice.toLowerCase()).equals("n"))) {

                System.out.print("Would you like to play again? Input y/n: ");
                choice = s.nextLine();
                }

                return choice;
        }

        if (mode.equals(resetUsersMode)){
            String choice = "";

            while (!((choice.toLowerCase()).equals("y") || (choice.toLowerCase()).equals("n"))) {

                System.out.print("Would you like to continue playing with the same usernames? Input y/n: ");
                choice = s.nextLine();
                }

                return choice;
        }

        return "How did you get here";
        
    }

    public static void sortFile(){
        PrintWriter fileWriter = null;
        String sortedUserScores = "";
        String tempUser;
        int tempScore;

        for (int i = (scores.size() - 1); i > 0 && scores.get(i) > scores.get(i-1); i--) {
                tempScore = scores.get(i-1);
                scores.set(i-1, scores.get(i));
                scores.set(i, tempScore);
            
                tempUser = users.get(i-1);
                users.set(i-1, users.get(i));
                users.set(i, tempUser);
        }
     
        for (int i = 0; i < scores.size(); i++){    
            sortedUserScores += (scores.get(i) + " " + users.get(i));
            if (i < (scores.size() - 1)) {
                sortedUserScores += "\n";
            }
        }

        try {
            fileWriter = new PrintWriter(new FileWriter(userList, false));

            fileWriter.print(sortedUserScores);

        } catch (Exception e) {
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }

        }


    public static void clearConsole()
    {
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}

