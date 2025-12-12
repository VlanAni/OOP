package blackjack.gameenvironment;

import blackjack.customio.StdOut;
import java.util.ArrayList;
import java.util.Properties;

public class Dealer extends Participant {

    private PartState state;

    public Dealer() {
        this.state = new PartState();
    }

    /**
     * Doing a step according to dealer's rools.
     *
     * @param deck - game deck.
     */
    @Override
    public void step(Deck deck, Properties local) {
        Card takenCard;
        for (int delSum = this.state.sum; delSum < 17; delSum = this.state.sum) {
            takenCard = this.takeCard(deck);
            this.openLastCard();
            StdOut.print(local.getProperty("dlrTookCard"));
            Judge.nameCard(takenCard, local);
            StdOut.printf(local.getProperty("dlrsSum"), this.state.sum);
            if (this.state.sum >= 21) {
                break;
            }
        }
    }

    public void openLastCard() {
        this.state.updateSum();
    }

    public Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.state.addNewCard(newCard);
        return newCard;
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