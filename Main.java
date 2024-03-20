/*
Problem Set Unit 1 (Tic-Tac-Toe)
Author: ._.
Date Created: March 11, 2024
Date Last Modified: March 11, 2024
*/

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    
    public static boolean emptyUserFile = false;
    public static String userList = "users.txt";
    public static ArrayList<String> users = new ArrayList<String>();
    public static ArrayList<Integer> scores = new ArrayList<Integer>();
    public static boolean nameAlreadyExists = false;
    public static String userMode = "userMode";
    public static String menuMode = "menuMode";
    public static String moveMode = "moveMode";
    public static boolean playAgain = true;
    public static boolean winyet = false;
    public static String gameBoard[][] = {
        {"1", "2", "3"},
        {"4", "5", "6"},
        {"7", "8", "9"}
    };

    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        
        while (playAgain) {
            menu(s);
        }
        
        String silksong = inputCheck(s, userMode);
        s.close();
        
    }
    
    public static void menu(Scanner s) { 
        
        clearConsole();
        System.out.println("★ Main Menu ★\n1. Play\n2. How To Play\n3. Leaderboard");

        int choice = Integer.parseInt(inputCheck(s, menuMode));

        if (choice == 1) {
            game(s);
        } else if (choice == 2) {
            instructions();
        } else {
            leaderboard();
        }

    }

    public static void leaderboard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leaderboard'");
    }

    public static void game(Scanner s) {
        // TODO Auto-generated method stub
        printBoard();

        int moves = 0;


        inputCheck(s, moveMode);

        while (moves < 9 && !winyet){

            if 


        }



    }

    public static void scoreIncrement(String user){
    scores.set(users.indexOf(user), (scores.get(users.indexOf(user)) + 1));
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

    public static void instructions() {
        clearConsole();
        System.out.println("Compete against a friend (or yourself) in order to get three X's or O's in a row!");
        System.out.println("Whoever is first to get three of their symbol, be it an X or an O, in a row, column, or diagonal wins!");
        System.out.println("When prompted to move, select the number on the grid, from 1-9, at the location that you wish to play.");
        System.out.println("If said move is possible, the move will be completed.");
        System.out.println("If said move is NOT possible, you will be prompted to make a valid move.");

    }


    public static String inputCheck(Scanner s, String mode) {
        Scanner fileScanner = null;
        PrintWriter fileWriter = null;

        // create an empty string to contain the return string value
        String inputtedValue = ""; // String exists to let mode variable be defined dynamically
        
        // for if the function is called to get player names...
        if (mode.equals(userMode)) {
            
            inputtedValue = s.nextLine();
            String userName = "";
            int wins = 0;

            try {
                fileScanner = new Scanner(new BufferedReader(new FileReader(userList)));
                fileWriter = new PrintWriter(new FileWriter(userList, true));

                emptyUserFile = !(fileScanner.hasNext());

                
                while (fileScanner.hasNext()) {
                    wins = fileScanner.nextInt();
                    userName = (fileScanner.nextLine()).strip();
                 
                    if (!nameAlreadyExists){
                    nameAlreadyExists = (inputtedValue.equals(userName));
                }

                    users.add(userName);
                    scores.add(wins);
                    // userScores.add(wins + " " + name);
                    
                }

                if (!nameAlreadyExists){
                    
                    if (!nameAlreadyExists) {
                        users.add(inputtedValue);
                        scores.add(0);
                    }

                    if (emptyUserFile) {
                        fileWriter.print("0 " + inputtedValue);
                    } else {
                        fileWriter.print("\n" + "0 " + inputtedValue);
                    }
                }

            } catch (Exception e) {
            } finally {
                if (fileScanner != null){
                    fileScanner.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                    
                    sortFile();
                }
            }
            
            return inputtedValue;
        }

        if (mode.equals(menuMode)) {

            int choice = -1;

            while (!(choice >= 1 && choice <= 3)) {
                
                try {
                    System.out.print("Input a number from 1-3: ");
                    choice = s.nextInt();
                    if (!(choice >= 1 && choice <= 3)) throw new Exception("💩");
                } catch (Exception e) {
                    choice = -1;
                    s.nextLine();
                    clearConsole();
                    System.out.println("★ Main Menu ★\n1. Play\n2. How To Play\n3. Leaderboard");

                }
            }

            return Integer.toString(choice);

        }

        if (mode.equals(moveMode)){

            int choice = -1;

            while (!(choice >= 1 && choice <= 9))

            {
                try {
                System.out.print("Select an available grid space from 1-9: ");
                choice = s.nextInt();
                if (!(choice >= 1 && choice <= 9)
                    || gameBoard[(choice-1)/3][(choice-1)%3] == "X"
                    || gameBoard[(choice-1)/3][(choice-1)%3] == "O") 
                        throw new Exception("💩");
                        // TODO make this exception descriptive
            
                    return Integer.toString(choice);

                } catch (Exception e) {
                    choice = -1;
                    s.nextLine();
                    clearConsole();
                    printBoard();

                }
            }
        }
        
        return inputtedValue;
        
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


    public final static void clearConsole()
    {
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}

