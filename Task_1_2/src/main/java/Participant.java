import java.util.ArrayList;

class Participant {

    private PartState state;

    Participant() {
        this.state = new PartState();
    }

    Card takeCard(Deck gameDeck) {
        Card newCard = gameDeck.extractCard();
        this.state.addNewCard(newCard);
        return newCard;
    }

    void openLastCard() {
        this.state.updateSum();
    }

    void prepare() {
        this.state.resetState();
    }

    ArrayList<Card> showCards() {
        return this.state.playerCards;
    }

    int sayCardsSum() {
        return this.state.sum;
    }
}