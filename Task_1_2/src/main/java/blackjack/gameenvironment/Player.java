package blackjack.gameenvironment;

import blackjack.customio.*;
import java.util.ArrayList;
import java.util.Properties;

public class Player extends Participant {

    private PartState state;
    private StdIn plScan;
    private Properties local;

    public Player(StdIn in) {
        this.state = new PartState();
        this.plScan = in;
    }

    /**
     * Doing a step according to player's rools.
     *
     * @param deck - game deck
     */
    @Override
    public void step(Deck deck, Properties local) {
        Card takenCard;
        for (int decn = this.plScan.scanInt(); decn == 1; decn = this.plScan.scanInt()) {
            takenCard = this.takeCard(deck);
            this.openLastCard();
            StdOut.print(local.getProperty("plrTookCard"));
            Judge.nameCard(takenCard, local);
            StdOut.printf(local.getProperty("plrSum"), this.state.sum);
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