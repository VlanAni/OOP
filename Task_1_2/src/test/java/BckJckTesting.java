import org.junit.jupiter.api.Test;
import blackjack.gameenvironment.BlackJackLeader;
import blackjack.gameenvironment.Card;
import blackjack.gameenvironment.Deck;
import blackjack.gameenvironment.Player;

class BckJckTesting {

    @Test
    void testParticipant() {
        Player player = new Player();
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);

        assert (player.takeCard(deck) == lastCard);
    }

    @Test
    void testBlackJackLeader() {
        Player player = new Player();
        Deck deck = new Deck();
        Card card = player.takeCard(deck);
        BlackJackLeader.nameCard(card);
        player.openLastCard();
        BlackJackLeader.nameCard(card);
        BlackJackLeader.ask(player);
    }

    @Test
    void preparingTest() {
        Player player = new Player();
        Deck deck = new Deck();
        player.takeCard(deck);
        player.openLastCard();
        player.prepare();
        int playerSum = player.sayCardsSum();

        assert (playerSum == 0);
    }

    @Test
    void testingLeader() {
        Player player = new Player();
        BlackJackLeader.ask(player);
        Deck deck = new Deck();
        Card card = player.takeCard(deck);
        BlackJackLeader.nameCard(card);
        BlackJackLeader.ask(player);
        player.openLastCard();
        BlackJackLeader.nameCard(card);
        BlackJackLeader.ask(player);
    }

    @Test
    void totalTest() {
        Player player = new Player();
        BlackJackLeader.ask(player);
        Deck deck = new Deck();
        while (deck.getDeckCards().size() > 0) {
            player.takeCard(deck);
            BlackJackLeader.ask(player);
            player.openLastCard();
            BlackJackLeader.ask(player);
        }

        assert (player.sayCardsSum() == 340);
    }
}