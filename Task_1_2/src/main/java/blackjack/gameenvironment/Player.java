package blackjack.gameenvironment;

import blackjack.constructor.Strings;
import blackjack.customio.InOut;
import java.util.ArrayList;

public class Player extends Participant {

    private PartState state;
    private InOut plScan;

    public Player(InOut inout) {
        this.state = new PartState();
        this.plScan = inout;
    }

    /**
     * Doing a step according to player's rools.
     *
     * @param deck - game deck
     */
    @Override
    public void step(Deck deck) {
        Card takenCard;
        for (int decn = this.plScan.scanInt(); decn == 1; decn = this.plScan.scanInt()) {
            takenCard = this.takeCard(deck);
            this.openLastCard();
            InOut.print(Strings.plrTookCard);
            BlackJackLeader.nameCard(takenCard);
            InOut.printfInt(Strings.plrSum, this.state.sum);
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