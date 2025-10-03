package blackjack.gameenvironment;

import blackjack.customio.InOut;

import java.util.ArrayList;

public class Dealer extends Participant {

    private PartState dealerState;

    public Dealer() {
        this.dealerState = new PartState();
    }

    /**
     * Doing a step according to dealer's rools.
     *
     * @param deck - game deck.
     */
    public void step(Deck deck) {
        Card takenCard;
        for (int delSum = this.dealerState.sum; delSum < 17; delSum = this.dealerState.sum) {
            takenCard = this.takeCard(deck);
            this.openLastCard();
            InOut.print(Strings.dlrTookCard);
            BlackJackLeader.nameCard(takenCard);
            InOut.printfInt(Strings.dlrsSum, this.dealerState.sum);
            if (this.dealerState.sum >= 21) {
                break;
            }
        }
    }

    /**
     *
     *
     * @param gameDeck - deck.
     * @return
     */
    @Override
    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.dealerState.addNewCard(newCard);
        return newCard;
    }

    /**
     * Open last taken card and update sum of opened card.
     */
    @Override
    public void openLastCard() {
        this.dealerState.updateSum();
    }

    /**
     * Give cards back and update state.
     */
    @Override
    public void prepare() {
        this.dealerState.resetState();
    }

    /**
     * @return - arrayList of cards.
     */
    @Override
    public ArrayList<Card> showCards() {
        return this.dealerState.playerCards;
    }

    /**
     * same.
     *
     * @return - points' sum.
     */
    @Override
    public int sayCardsSum() {
        return this.dealerState.sum;
    }
}