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
    static String moveMode = "moveMode";

    public static void main(String[] args) {
        String silksong = inputCheck(userMode);
    }
    
    public static String inputCheck(String mode) {
        
        Scanner s = new Scanner(System.in);
        Scanner fileScanner = null;
        PrintWriter fileWriter = null;

        // create an empty string to contain the return string value
        String inputtedValue = ""; // String exists to let mode variable be defined dynamically
        
        // for if the function is called to get player names...
        if (mode.equals(userMode)) {
            
            inputtedValue = s.nextLine();
            s.close();
            String name = "";
            int wins = 0;

            try {
                fileScanner = new Scanner(new BufferedReader(new FileReader(userList)));
                fileWriter = new PrintWriter(new FileWriter(userList, true));

                emptyUserFile = !(fileScanner.hasNext());

                
                while (fileScanner.hasNext()) {
                    wins = fileScanner.nextInt();
                    name = (fileScanner.nextLine()).strip();
                 
                    if (!nameAlreadyExists){
                    nameAlreadyExists = (inputtedValue.equals(name));
                }


                    users.add(name);
                    scores.add(wins);
                    // userScores.add(wins + " " + name);
                    
                }

                if (!nameAlreadyExists){
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
                    
                    if (!nameAlreadyExists) {
                        users.add(name);
                        scores.add(0);
                    }

                    sortFile();
                }
            }
            
            s.close();
            return inputtedValue;
        }
        
        s.close();
        return inputtedValue;
        
    }

    public static void sortFile(){
        PrintWriter fileWriter = null;
        String sortedUserScores = "";

        // // https://stackoverflow.com/a/48502066/13160295
        // // comparator from ^
        // Collections.sort(userScores, new Comparator<String>() {
        //     public int compare(String a, String b) {
        //         int n1 = Integer.parseInt(a.split(" ")[0]);
        //         int n2 = Integer.parseInt(b.split(" ")[0]);

        //         return n2 - n1;
        //     } 
        // });
        
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
    }

