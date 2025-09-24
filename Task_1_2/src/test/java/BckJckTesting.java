import org.junit.jupiter.api.Test;
import vaGames.gameArchitect.BlackJackLeader;
import vaGames.gameArchitect.Card;
import vaGames.gameArchitect.Deck;
import vaGames.gameArchitect.Participant;

class BckJckTesting {

    @Test
    void testDeck() {
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);

        assert (lastCard == deck.extractCard());
    }

    @Test
    void testParticipant() {
        Participant player = new Participant();
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);

        assert (player.takeCard(deck) == lastCard);
    }

    @Test
    void testBlackJackLeader() {
        Participant player = new Participant();
        Deck deck = new Deck();
        Card card = player.takeCard(deck);
        BlackJackLeader.nameCard(card);
        player.openLastCard();
        BlackJackLeader.nameCard(card);
        BlackJackLeader.ask(player);
    }

    @Test
    void preparingTest() {
        Participant player = new Participant();
        Deck deck = new Deck();
        player.takeCard(deck);
        player.openLastCard();
        player.prepare();
        int playerSum = player.sayCardsSum();

        assert (playerSum == 0);
    }

    @Test
    void testingLeader() {
        Participant player = new Participant();
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
        Participant player = new Participant();
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