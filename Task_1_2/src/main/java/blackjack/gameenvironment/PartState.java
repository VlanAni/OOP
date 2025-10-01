package blackjack.gameenvironment;

import java.util.ArrayList;

class PartState {
    ArrayList<Card> playerCards;
    int sum;
    int size;
    int strongAceAmount;

    PartState() {
        this.playerCards = new ArrayList<Card>();
        this.resetState();
    }

    void resetState() {
        this.playerCards.clear();
        this.sum = 0;
        this.size = 0;
        this.strongAceAmount = 0;
    }

    void addNewCard(Card card) {
        this.playerCards.add(card);
        this.size += 1;
    }

    void updateSum() {
        Card card = this.playerCards.get(this.playerCards.size() - 1);
        card.Open();
        this.playerCards.set(this.size - 1, card);
        if (card.getCardType().equals(CardTypes.ACE)) {
            this.sum += 11;
            this.strongAceAmount++;
        }
        else {
            this.sum += card.getCardType().getValue();
        }
        while (this.strongAceAmount > 0 && this.sum > 21) {
            this.sum -= 10;
            this.strongAceAmount--;
        }
    }
}