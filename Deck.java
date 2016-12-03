/* Name: Zhanming Zhang
   Email: zmzhang@brandeis.edu
   Date: 2 Dec 2016
   PA7 - Deck Class - This program is to simulate all actions needed in a real card game:
   It will creat a current desk and shuffle it, and then allow user to pick next card from it
   and discard the cards if the user want. If all cards are picked, throw the discarded cards back
   to current deck and re-shuffle it.
   Bugs: none
*/

import java.util.*;
public class Deck{
  // Initialize the fields of currentDeck, discarded cards and the index of current card
  private Card[] currentDeck;
  private Card[] discardPile;
  // This variable is to find which card have just been drawed. Initial value is -1
  private int cardNum = -1;

  // Constructor
  public Deck(){
    currentDeck = new Card[52];
    discardPile = new Card[52];
    // Initialize every cards
    int cardIndex = 0;
    // Use i to present each suit
    for (int i = 1; i < 5; i++){
      for (int j = 1; j < 14; j++){
        String suit;
        if(i == 1){
          suit = "Spade";
        } else if (i == 2){
          suit = "Hearts";
        } else if (i == 3){
          suit = "Clubs";
        } else {
          suit = "Diamonds";
        }
        currentDeck[cardIndex] = new Card(j,suit);
        cardIndex++;
      }
    }
    shuffle();
    Card[] discardPile = new Card[52];
  }

  // Use the algorithm introducted in the assignment
  public void shuffle(){

    Random rand = new Random();

    int k = 0;
    // Calculate how many cards are in the current deck in case shuffling the null cards
    while(currentDeck[k] != null && k < 51){
      k++;
    }
    // If the last card in the current deck is null
    if (currentDeck[51] != null){
      k++;
    }

    // Shuffle the cards which are not null
    for (int i = 0; i < k - 1; i++){
      int j = rand.nextInt(k - i) + i;
      Card temp = currentDeck[i];
      currentDeck[i] = currentDeck[j];
      currentDeck[j] = temp;
    }
  }

  public Card drawNextCard(){
    cardNum++;
    // if 52 cards have been draw, put the discardPile to the deck and shuffle it
    // Then return the number of cards to zero
    if (cardNum > 51){
      cardNum = 51;
      currentDeck = discardPile.clone();
      discardPile = new Card[52];
      shuffle();
      cardNum = 0;
    }
    return currentDeck[cardNum];
  }

  public void discard(Card c){
    discardPile[cardNum] = c;
  }
}
