this took too long. 😡
# Problem Set Unit 1 - Tic-Tac-Toe


Tic-Tac-Toe is a classic logic game familiar to most of us. Players take turns placing X's and O's on a 3x3 grid until one player achieves three in a row horizontally, vertically, or diagonally.

**Your task is to create a text-based, two-player version of Tic-Tac-Toe. It must include the following:**

*   **A menu with three options:** Start, How to Play, and High Scores.  
    
*   **High Scores:** This should display the top 10 users who have played the game previously, along with their number of wins. Users should be sorted from most wins to least wins.
*   **How to Play Area:** This area should provide instructions on how to play Tic-Tac-Toe. It should return to the main menu after the user presses Enter (use your input function to do this).
*   **Game:** This part should ask users for their usernames. Once both usernames are entered, the game should begin. Player X goes first, and the player who plays X should be chosen randomly (use the java.util.Random module for this). -Players take turns placing X and O until either someone wins by getting three in a row or the entire game board is filled. The user who wins should have their score incremented by 1. -After the game ends, prompt the user to either play again or exit.
*   **Exiting:** If the players exit, their scores should be added to a file containing usernames and their scores. If a user already exists in the file, their scores from this game and previous games should be added together. Remember to both read and write to the file to ensure you are updating scores that already exist.
*   **Input Validation:** If a player inputs an invalid input, you **must** ask them again for a new input until they give a valid input. Use try catch as well as a loop to solve this issue.

**Remember, this is a problem set:**

*   **Document and comment your code thoughtfully.** Aim for a balance between over-commenting and under-commenting.
*   **Comment code blocks, functions, and hard-to-read code.** Well-named variables can often reduce the need for excessive commenting.
*   **Refer to practice problem 1.1 for documentation examples.**

**Additional Tips:**

*   **Play your code!** Strive to create a program that works efficiently and avoids repetition.
*   **Use functions to avoid repetitive code.** If you find yourself writing similar code with only slight variations, consider creating a function instead.
*   **Avoid "magic numbers."** Replace them with meaningful variables. For example, instead of using 3 for both the length and width of the game board, use the already defined array length of 3.
*   **Apply the concepts and knowledge gained throughout Unit 1 and additional reviews.**

**Here is an example of what a run of the program might looks like:**

```java
1. Start
2. How to Play
3. High Scores
 
Please enter your choice: 1
 
Player 1, enter your username: John
Player 2, enter your username: Alice
 
It's John's turn! (X)
Choose a position (1-9): 5
 
+---+---+---+
|   |   |   | 
+---+---+---+
|   | X |   |
+---+---+---+
|   |   |   |
+---+---+---+
 
It's Alice's turn! (O)
Choose a position (1-7, 8, and 9 are occupied): 2
 
+---+---+---+
|   | O |   | 
+---+---+---+
|   | X |   |
+---+---+---+
|   |   |   |
+---+---+---+
```

**(Game continues...)**

```java
+---+---+---+
| O | O | X | 
+---+---+---+
|   | X |   |
+---+---+---+
| X |   |   |
+---+---+---+
 
Game Over! John wins!
 
Play again? (y/n): n
 
Thank you for playing!
```

**Good luck!**