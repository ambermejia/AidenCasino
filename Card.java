/* Name: Zhanming Zhang
   Email: zmzhang@brandeis.edu
   Date: 2 Dec 2016
   PA7 - Card Class - This program will represent a single card in a deck.
   Every card has three properties. User can use getValue etc to get some property
   Bugs: none
*/

public class Card{
  private int value;
  private String suit;

  // Assign the parameters the values from client code
  public Card (int value, String suit){
    this.value = value;
    this.suit = suit;
  }

  public int getValue(){
    return value;
  }

  // Return the color based on the suit
  public String getColor(){
    if( suit.equals("Hearts")||suit.equals("Diamonds")){
      return "red";
    } else {
      return "black";
    }
  }

  public String getSuit(){
    return suit;
  }

  public String toString(){
    // Use if statement to assign the name string, then return the String of the object
    String name;
    if (value == 1) {
      name = "Ace";
    } else if (value == 11) {
      name = "Jack";
    } else if (value == 12) {
      name = "Queen";
    } else if (value == 13) {
      name = "King";
    } else {
      name = "" + value;
    }
    return name + " of " + suit;
  }
}
