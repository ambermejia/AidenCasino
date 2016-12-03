/* Name: Zhanming Zhang
   Email: zmzhang@brandeis.edu
   Date: 2 Dec 2016
   PA7 - Deck Class - This program allows the user to play two games, War and blackjack
   The user will input his or her initial money, if all money runs out, game stop.
   User can also choose when to stop.
   Bugs: none
*/

import java.util.*;
public class Casino{
  public static void main(String[] args){
    Scanner console = new Scanner(System.in);
    // Ask the user the initial total
    System.out.println("How much money do you have? ");
    double total = console.nextInt();
    int play = 0;
    System.out.println("Welcome to my casino! You now have $" + total + "\nGood Luck!");
    do {
      System.out.println("Which game do you wanna choose?");
      System.out.println("Enter 1 for war, 2 for blackjack, 3 to quit: ");
      // Let 1, 2 represent the game the user choose, 3 for the sign to quit
      do{
        play = console.nextInt();
      }while(!(play == 1 || play == 2 || play == 3));
      if ( play == 1 ){
        total = war(console, total);
      } else if (play == 2){
        total = blackJack(console, total);
      }

      // If the user's total reached to 0, game stop
      if (total <= 0){
        System.out.println("You have run out of all your money! Get out of here! ");
      }

    } while(play != 3 && total > 0);
  }

  public static double war(Scanner console, double t){
    System.out.println("In this game, we will compare the value of the cards we pick");
    System.out.println("If mine is larger, I win, and you will lose your bet");
    System.out.println("If yours is larger, you win, and you will get your bet");
    System.out.println("If its a tie, lets play again.");
    // yOrN is to store if the user want to play again
    String yOrN;
    Deck d = new Deck();
    do {
      int bet = bet(console, t);
      System.out.println("Game Starts! ");
      Card c1 = d.drawNextCard();
      Card c2 = d.drawNextCard();
      int value1 = c1.getValue();
      int value2 = c2.getValue();
      System.out.println("Your Card: " + c1);
      System.out.println("My Card: " + c2);
      int win;
      // In this program, we consider Ace is the largest card
      // Denote 1 as the user win, denote -1 as the computer win, denote 0 as the tie
      if ( value1 == 1 && value2 != 1){
        win = 1;
      } else if ( value2 == 1 && value1 != 1){
        win = -1;
      } else if ( value1 > value2){
        win = 1;
      } else if (value1 == value2){
        win = 0;
      } else{
        win = -1;
      }
      // Use this method to print the result
      printResult(win);
      // Discard these two cards
      d.discard(c1);
      d.discard(c2);
      t = t + bet*win;
      if (t == 0){
        return t;
      }
      System.out.println("Now your total is " + t + "\n");
      System.out.println("Do you wanna play again?");
      yOrN = console.next();
    } while ((yOrN.startsWith("Y")||yOrN.startsWith("y"))&&(t > 0));

    // If the user choose not to play again, return the new total to the main method
    return t;

  }

  public static double blackJack(Scanner console, double t){
    System.out.println("Now we are playing blackJack.");
    System.out.println("Make your cards sum as near with 21 as possible! ");
    System.out.println("Don't get over!");
    String yOrN;
    Deck d = new Deck();
    do {
      int win;
      int bet = bet(console, t);
      // Use two array to store the cards picked.  Integers to store the total value
      Card[] user = new Card[5];
      Card[] computer = new Card[5];
      int value1;
      int value2;

      // Draw 2 cards for both computer and user
      for (int i = 0; i < 2; i ++){
        user[i] = d.drawNextCard();
        computer[i] = d.drawNextCard();
      }
      // The user's turn
      // cardNum1 is to store how many cards the user has chose
      int cardNum1 = 2;
      // If the user still want to hit, continue to pic card
      boolean hit = true;
      System.out.println("Your turn: ");

      do{
        System.out.println("Your current hand: ");
        value1 = 0;
        int adValue;
        // This forloop is to print the current hand and add up value at the same time
        for (int i = 0; i < 5; i ++){
          if (user[i] != null){
            System.out.print( user[i] +"; ");
            if (user[i].getValue() <= 10){
              adValue = user[i].getValue();
              // If the value is larger than 10, add it as 10
            } else {
              adValue = 10;
            }
            value1 += adValue;
          }
        }
        System.out.println("\nYou current value is: " + value1);

        // To store the hit or stand for user, if the value is over 21, this turn stop and store value as -1
        int choice;
        if (value1 <= 21){
          System.out.println("Do you want to stand or hit? (1 for stand, 2 for hit)");
          choice = console.nextInt();
          if (choice == 1){
            hit = false;
          } else if (choice == 2){
            cardNum1 ++;
            user[cardNum1 - 1] = d.drawNextCard();
          }
        } else {
          System.out.println("It is over 21.");
          value1 = -1;
        }

      } while(cardNum1 <= 5 && value1 != -1 && hit);
      if (value1 != -1){
        System.out.println("Your final value is " + value1);
      }
      // Discard the cards of user
      for (int i = 0; i < cardNum1; i++){
        d.discard(user[i]);
      }

      // Computer's turn
      int cardNum2 = 2;
      System.out.println("Now it's my turn");

      do{
        value2 = 0;
        System.out.println("My current hand: ");
        int adValue;
        // Print the current hand of the computer and add up the value
        for (int i = 0; i < 5; i ++){
          if (computer[i] != null){
            System.out.print( computer[i] +"; ");
            if (computer[i].getValue() <= 10){
              adValue = computer[i].getValue();
            } else {
              adValue = 10;
            }
            value2 += adValue;
          }
        }
        System.out.println("\nMy current value is: " + value2);
        if (value2 <= 21){
          //If the hand has a value greater than 16, the dealer stands.
          if (value2 <= 16){
            System.out.println("I will hit");
            cardNum2++;
            computer[cardNum2 - 1] = d.drawNextCard();
          } else {
            System.out.println("I will stand.");
          }
        } else {
          // if the value is over 21, this turn stop and change the value to -1
          value2 = -1;
          System.out.println("It is over 21");
        }
      }while(value2 <= 16 && value2 != -1 && cardNum2 <= 5);

      if (value1 > value2 ){
        win = 1;
      } else if (value1 < value2 ){
        win = -1;
      } else {
        win = 0;
      }
      printResult(win);
      // Discard the cards of user
      for (int i = 0; i < cardNum2; i++){
        d.discard(computer[i]);
      }

      t = t + bet*win;
      if (t == 0){
        return t;
      }
      System.out.println("Now your total is " + t + "\n");
      System.out.println("Do you wanna play again?");
      yOrN = console.next();
  } while ((yOrN.startsWith("Y")||yOrN.startsWith("y"))&&(t > 0));

    return t;
  }

  // This method is to ask user the bet he or she wants to make
  public static int bet(Scanner console, double t){
    int bet;
    do {
      // If the user has not enough money, ask user to input again
      System.out.println("What's your bet? ");
      bet = console.nextInt();
      if ( bet > t){
        System.out.println("Well you don't have enough money.");
      }
    } while (bet > t);
    return bet;
  }

  // This method is to print the result of every turn
  public static void printResult(int win){
    if (win == 1){
      System.out.println("Well you won. ");
    } else if (win == -1){
      System.out.println("I won :)");
    } else {
      System.out.println("It's a tie.");
    }
  }

}
