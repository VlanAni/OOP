package blackjack.gameenvironment;

import java.util.ArrayList;
import java.util.Properties;

import blackjack.customio.*;

/**
 * BlackJackLeader - person who asks participants.
 */
public class Judge {

    private Judge() {
    }

    /**
     * Print participant's state.
     *
     * @param player - pesron who is being asked.
     */
    public static void ask(Participant player, Properties local) {
        int sum = player.sayCardsSum();
        ArrayList<Card> cards = player.showCards();
        StdOut.printf(local.getProperty("sumOpened"), sum);
        StdOut.println(local.getProperty("allCds"));
        for (Card card : cards) {
            nameCard(card, local);
        }
        StdOut.println(local.getProperty("tall"));
    }

    /**
     * Print card's information.
     *
     * @param card - card to name.
     */
    public static void nameCard(Card card,  Properties local) {
        if (!(card.getCardStatus())) {
            StdOut.println(local.getProperty("nopCard"));
            return;
        }
        String suit = card.getCardSuit().getValue();
        String cardType = card.getCardType().toString();
        StdOut.printf(local.getProperty("suitType"), suit, cardType);
    }
}