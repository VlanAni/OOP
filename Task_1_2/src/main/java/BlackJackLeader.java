import java.util.ArrayList;

class BlackJackLeader {

    private BlackJackLeader() {
    }

    static void ask(Participant player) {
        int sum = player.sayCardsSum();
        ArrayList<Card> cards = player.showCards();
        System.out.printf("Sum of opened cards: %d\n", sum);
        System.out.println("All cards:");
        for (Card card : cards) {
                nameCard(card);
        }
        System.out.println("That's all...");
    }

    static void nameCard(Card card) {
        if (!card.isOpen) {
            System.out.println("[not opened card]");
            return;
        }
        String suit = card.suit.getValue();
        String cardType = "";
        switch (card.type) {
            case CardTypes.TWO:
                cardType = "Two";
                break;
            case CardTypes.THREE:
                cardType = "Three";
                break;
            case CardTypes.FOUR:
                cardType = "Four";
                break;
            case CardTypes.FIVE:
                cardType = "Five";
                break;
            case CardTypes.SIX:
                cardType = "Six";
                break;
            case CardTypes.SEVEN:
                cardType = "Seven";
                break;
            case CardTypes.EIGHT:
                cardType = "Eight";
                break;
            case CardTypes.NINE:
                cardType = "Nine";
                break;
            case CardTypes.TEN:
                cardType = "Ten";
                break;
            case CardTypes.JACK:
                cardType = "Jack";
                break;
            case CardTypes.QUEEN:
                cardType = "Queen";
                break;
            case CardTypes.KING:
                cardType = "King";
                break;
            case CardTypes.ACE:
                cardType = "Ace";
                break;
        }
        System.out.printf("[Suit: %s | Type: %s]\n", suit, cardType);
    }
}