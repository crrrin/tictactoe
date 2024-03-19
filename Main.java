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
    
    static boolean emptyUserFile = false;
    static String userList = "users.txt";
    static ArrayList<String> users = new ArrayList<String>();
    static ArrayList<Integer> scores = new ArrayList<Integer>();
    static boolean nameAlreadyExists = false;
    static String userMode = "userMode";
    static String menuMode = "menuMode";
    static String moveMode = "moveMode";
    static boolean playAgain = true;

    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        String silksong = inputCheck(s, userMode);

        while (playAgain) {
            menu(s);
        }

        s.close();
        
    }
    
    public static void menu(Scanner s) { 
        
        System.out.println("⋆ Main Menu ⋆\n1. Play\n2. How To Play\n3. Leaderboard");
        
        inputCheck(s, menuMode);

    }

    public static void scoreIncrement(String user){
    scores.set(users.indexOf(user), (scores.get(users.indexOf(user)) + 1));
    sortFile();
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
                    choice = s.nextInt();
                } catch (Exception e) {
                    choice = -1;
                    System.out.println("Selection: ");
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
        try
        {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}

