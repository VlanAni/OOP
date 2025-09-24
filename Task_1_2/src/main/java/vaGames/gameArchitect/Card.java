package vaGames.gameArchitect;

public class Card {
    CardTypes type;
    Suit suit;
    boolean isOpen;

    Card(CardTypes type, Suit suit) {
        this.type = type;
        this.suit = suit;
        this.isOpen = false;
    }
}