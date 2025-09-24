package vagames.gamearchitect;

import java.util.ArrayList;

/**
 * BlackJackLeader - person who asks participants.
 */
public class BlackJackLeader {

    private BlackJackLeader() {
    }

    /**
     * Print participant's state.
     * @param player - pesron who is being asked.
     */
    public static void ask(Participant player) {
        int sum = player.sayCardsSum();
        ArrayList<Card> cards = player.showCards();
        System.out.printf("Sum of opened cards: %d\n", sum);
        System.out.println("All cards:");
        for (Card card : cards) {
            nameCard(card);
        }
        System.out.println("That's all...");
    }

    /**
     * Print card's information.
     * @param card - card to name.
     */
    public static void nameCard(Card card) {
        if (!card.isOpen) {
            System.out.println("[not opened card]");
            return;
        }
        String suit = card.suit.getValue();
        String cardType = "";
        switch (card.type) {
            case TWO:
                cardType = "Two";
                break;
            case THREE:
                cardType = "Three";
                break;
            case FOUR:
                cardType = "Four";
                break;
            case FIVE:
                cardType = "Five";
                break;
            case SIX:
                cardType = "Six";
                break;
            case SEVEN:
                cardType = "Seven";
                break;
            case EIGHT:
                cardType = "Eight";
                break;
            case NINE:
                cardType = "Nine";
                break;
            case TEN:
                cardType = "Ten";
                break;
            case JACK:
                cardType = "Jack";
                break;
            case QUEEN:
                cardType = "Queen";
                break;
            case KING:
                cardType = "King";
                break;
            case ACE:
                cardType = "Ace";
                break;
            default:
                break;
        }
        System.out.printf("[Suit: %s | Type: %s]\n", suit, cardType);
    }
}