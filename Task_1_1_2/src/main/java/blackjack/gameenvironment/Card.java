package blackjack.gameenvironment;

/**
 * Card - game card.
 * It can be read by other classes.
 */
public class Card {

    private CardTypes type;
    private Suit suit;
    private boolean isOpen;

    Card(CardTypes type, Suit suit) {
        this.type = type;
        this.suit = suit;
        this.isOpen = false;
    }

    CardTypes getCardType() {
        return this.type;
    }

    Suit getCardSuit() {
        return this.suit;
    }

    boolean getCardStatus() {
        return this.isOpen;
    }

    void open() {
        this.isOpen = true;
    }

}