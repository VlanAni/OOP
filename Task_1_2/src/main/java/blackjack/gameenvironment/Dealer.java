package blackjack.gameenvironment;

import java.util.ArrayList;

public class Dealer {

    private PartState dealerState;

    public Dealer() {this.dealerState = new PartState();}

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

    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.dealerState.addNewCard(newCard);
        return newCard;
    }

    /**
     * Open last taken card and update sum of opened card.
     */
    public void openLastCard() {
        this.dealerState.updateSum();
    }

    /**
     * Give cards back and update state.
     */
    public void prepare() {
        this.dealerState.resetState();
    }

    /**
     * @return - arrayList of cards.
     */
    public ArrayList<Card> showCards() {return this.dealerState.playerCards;}

    public int sayCardsSum() {
        return this.dealerState.sum;
    }
}