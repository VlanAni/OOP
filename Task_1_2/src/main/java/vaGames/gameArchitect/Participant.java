package vaGames.gameArchitect;

import java.util.ArrayList;

/**
 * Participant - person who plays.
 * It can be player or dealer.
 */
public class Participant {

    private PartState state;

    public Participant() {
        this.state = new PartState();
    }

    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.state.addNewCard(newCard);
        return newCard;
    }

    public void openLastCard() {
        this.state.updateSum();
    }

    public void prepare() {
        this.state.resetState();
    }

    public ArrayList<Card> showCards() {
        return this.state.playerCards;
    }

    public int sayCardsSum() {
        return this.state.sum;
    }
}