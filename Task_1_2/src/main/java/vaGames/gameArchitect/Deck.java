package vaGames.gameArchitect;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<Card>();
        prepareDeck();
    }

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

    public Card extractCard() {
        int length = this.cards.size();
        Card takenCard = this.cards.get(length - 1);
        this.cards.remove(length - 1);
        return takenCard;
    }

    public ArrayList<Card> getDeckCards() {
        return this.cards;
    }
}