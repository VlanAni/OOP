import blackjack.customio.*;
import blackjack.gameenvironment.Card;
import blackjack.gameenvironment.Deck;
import blackjack.gameenvironment.Dealer;
import blackjack.gameenvironment.Player;
import org.junit.jupiter.api.Test;

class Testing {

    @Test
    void partprepareTest() {
        StdIn in = new StdIn();
        Player player = new Player(in);
        Deck deck = new Deck();
        player.takeCard(deck);
        player.openLastCard();
        player.prepare();
        int playerSum = player.sayCardsSum();

        assert (playerSum == 0);
    }

    @Test
    void deckinitTest() {
        Deck deck = new Deck();

        assert (deck.getDeckCards().size() == 52);
    }

    @Test
    void testPlayer() {
        StdIn in = new StdIn();
        Player player = new Player(in);
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);

        assert (player.takeCard(deck) == lastCard);
    }

    @Test
    void testingDealer() {
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);
        Card taken = dealer.takeCard(deck);
        dealer.openLastCard();

        assert (taken == lastCard);
    }

    @Test
    void totalTest() {
        StdIn inout = new StdIn();
        Player player = new Player(inout);
        Deck deck = new Deck();
        while (deck.getDeckCards().size() > 0) {
            player.takeCard(deck);
            player.openLastCard();
        }

        assert (player.sayCardsSum() == 340);
    }
}