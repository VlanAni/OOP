import blackjack.customio.InOut;
import blackjack.gameenvironment.BlackJackLeader;
import blackjack.gameenvironment.Card;
import blackjack.gameenvironment.Deck;
import blackjack.gameenvironment.Dealer;
import blackjack.gameenvironment.Player;
import org.junit.jupiter.api.Test;

class Testing {

    @Test
    void testParticipant() {
        InOut inout = new InOut();
        Player player = new Player(inout);
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);

        assert (player.takeCard(deck) == lastCard);
    }

    @Test
    void testBlackJackLeader() {
        InOut inout = new InOut();
        Player player = new Player(inout);
        Deck deck = new Deck();
        Card card = player.takeCard(deck);
        BlackJackLeader.nameCard(card);
        player.openLastCard();
        BlackJackLeader.nameCard(card);
        BlackJackLeader.ask(player);
    }

    @Test
    void preparingTest() {
        InOut inout = new InOut();
        Player player = new Player(inout);
        Deck deck = new Deck();
        player.takeCard(deck);
        player.openLastCard();
        player.prepare();
        int playerSum = player.sayCardsSum();

        assert (playerSum == 0);
    }

    @Test
    void testingLeader() {
        InOut inout = new InOut();
        Player player = new Player(inout);
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
    void testingDealer() {
        InOut.println("==Testing dealer==");
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        dealer.takeCard(deck);
        dealer.openLastCard();
        dealer.takeCard(deck);
        dealer.openLastCard();
        BlackJackLeader.ask(dealer);
        dealer.step(deck);
        BlackJackLeader.ask(dealer); 
    }

    @Test
    void totalTest() {
        InOut inout = new InOut();
        Player player = new Player(inout);
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