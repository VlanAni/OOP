package blackjack.gameenvironment;

import java.util.ArrayList;

/**
 * Participant - person who plays.
 * It can be player or dealer.
 */
public class Player {

    private PartState playerState;

    /**
     * Creating perticipant.
     */
    public Player() {this.playerState = new PartState();}

    public void step(Deck deck, InOut inout) {
        Card takenCard;
        for (int decision = inout.scanInt(); decision == 1; decision = inout.scanInt()) {
            takenCard = this.takeCard(deck);
            this.openLastCard();
            InOut.print(Strings.plrTookCard);
            BlackJackLeader.nameCard(takenCard);
            InOut.printfInt(Strings.plrSum, this.playerState.sum);
            if (this.playerState.sum >= 21) {
                break;
            }
        }
    }

    /**
     * Take card from deck.
     *
     * @param gameDeck - deck to take card from.
     * @return - taken card.
     */
    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.playerState.addNewCard(newCard);
        return newCard;
    }

    /**
     * Open last taken card and update sum of opened card.
     */
    public void openLastCard() {
        this.playerState.updateSum();
    }

    /**
     * Give cards back and update state.
     */
    public void prepare() {
        this.playerState.resetState();
    }

    /**
     * @return - arrayList of cards.
     */
    public ArrayList<Card> showCards() {
        return this.playerState.playerCards;
    }

    public int sayCardsSum() {
        return this.playerState.sum;
    }
}