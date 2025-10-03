package blackjack.gameenvironment;

import java.util.ArrayList;
import blackjack.customio.InOut;

/**
 * Participant - person who plays.
 * It can be player or dealer.
 */
public class Player extends Participant {

    private PartState playerState;

    /**
     * Creating perticipant.
     */
    public Player() {
        this.playerState = new PartState();
    }

    /**
     * Doing a step according to player's rools.
     *
     * @param deck - game deck
     * @param inout - user's choice
     */
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
    @Override
    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.playerState.addNewCard(newCard);
        return newCard;
    }

    /**
     * Open last taken card and update sum of opened card.
     */
    @Override
    public void openLastCard() {
        this.playerState.updateSum();
    }

    /**
     * Give cards back and update state.
     */
    @Override
    public void prepare() {
        this.playerState.resetState();
    }

    /**
     * @return - arrayList of cards.
     */
    @Override
    public ArrayList<Card> showCards() {
        return this.playerState.playerCards;
    }

    /**
     * same.
     *
     * @return - cards' sum.
     */
    @Override
    public int sayCardsSum() {
        return this.playerState.sum;
    }
}