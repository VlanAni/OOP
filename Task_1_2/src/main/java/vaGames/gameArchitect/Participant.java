package vagames.gamearchitect;

import java.util.ArrayList;

/**
 * Participant - person who plays.
 * It can be player or dealer.
 */
public class Participant {

    private PartState state;

    /**
     * Creating perticipant.
     */
    public Participant() {
        this.state = new PartState();
    }

    /**
     * Take card from deck.
     * @param gameDeck - deck to take card from.
     * @return - taken card.
     */
    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.state.addNewCard(newCard);
        return newCard;
    }

    /**
     * Open last taken card and update sum of opened card.
     */
    public void openLastCard() {
        this.state.updateSum();
    }

    /**
     * Give cards back and update state.
     */
    public void prepare() {
        this.state.resetState();
    }

    /**
     * @return - arrayList of cards.
     */
    public ArrayList<Card> showCards() {
        return this.state.playerCards;
    }

    public int sayCardsSum() {
        return this.state.sum;
    }
}