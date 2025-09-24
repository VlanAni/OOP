package vagames.gamearchitect;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck stores all game cards.
 */
public class Deck {

    private ArrayList<Card> cards;

    /**
     * Creating deck.
     */
    public Deck() {
        this.cards = new ArrayList<Card>();
        prepareDeck();
    }

    /**
     * Make deck full and it's cards' order random.
     */
    public void prepareDeck() {
        this.cards.clear();
        for (Suit suit : Suit.values()) {
            for (CardTypes cardType : CardTypes.values()) {
                Card card = new Card(cardType, suit);
                this.cards.add(card);
            }
        }
        Collections.shuffle(this.cards);
    }

    Card extractCard() {
        int length = this.cards.size();
        Card takenCard = this.cards.get(length - 1);
        this.cards.remove(length - 1);
        return takenCard;
    }

    public ArrayList<Card> getDeckCards() {
        return this.cards;
    }
}