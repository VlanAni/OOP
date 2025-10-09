package blackjack.gameenvironment;

import java.util.ArrayList;

abstract class Participant {

    public abstract void step(Deck deck);

    /**
     * Take closed card from the deck.
     *
     * @param gameDeck - deck.
     * @return - participant's cards.
     */
    public abstract Card takeCard(Deck gameDeck);

    /**
     * Open last taken card and update sum of opened card.
     */
    public abstract void openLastCard();

    /**
     * Give cards back and update state.
     */
    public abstract void prepare();

    /**
     * @return - arrayList of cards.
     */
    public abstract ArrayList<Card> showCards();

    /**
     * Return points.
     *
     * @return - sum of participant's points.
     */
    public abstract int sayCardsSum();
}