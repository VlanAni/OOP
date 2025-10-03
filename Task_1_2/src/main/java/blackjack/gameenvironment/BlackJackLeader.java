package blackjack.gameenvironment;

import java.util.ArrayList;
import blackjack.customio.InOut;

/**
 * BlackJackLeader - person who asks participants.
 */
public class BlackJackLeader {

    private BlackJackLeader() {
    }

    /**
     * Print participant's state.
     *
     * @param player - pesron who is being asked.
     */
    public static void ask(Player player) {
        int sum = player.sayCardsSum();
        ArrayList<Card> cards = player.showCards();
        InOut.printfInt("Sum of opened cards: %d\n", sum);
        InOut.println("All cards:");
        for (Card card : cards) {
            nameCard(card);
        }
        InOut.println("That's all...");
    }

    public static void ask(Dealer player) {
        int sum = player.sayCardsSum();
        ArrayList<Card> cards = player.showCards();
        InOut.printfInt("Sum of opened cards: %d\n", sum);
        InOut.println("All cards:");
        for (Card card : cards) {
            nameCard(card);
        }
        InOut.println("That's all...");
    }

    /**
     * Print card's information.
     *
     * @param card - card to name.
     */
    public static void nameCard(Card card) {
        if (!(card.getCardStatus())) {
            InOut.println("[not opened card]");
            return;
        }
        String suit = card.getCardSuit().getValue();
        String cardType = card.getCardType().toString();
        InOut.printfStr("[Suit: %s | Type: %s]\n", suit, cardType);
    }
}