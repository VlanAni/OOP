import org.junit.jupiter.api.Test;

class BckJckTesting {

    @Test
    void testDeck() {
        Deck deck = new Deck();
        Card lastCard = deck.getDeckCards().getLast();

        assert (lastCard == deck.extractCard());
    }

    @Test
    void testParticipant() {
        Participant player = new Participant();
        Deck deck = new Deck();
        Card lastCard = deck.getDeckCards().getLast();

        assert (player.takeCard(deck) == lastCard);
    }

    @Test
    void testParticipantSum() {
        Participant player = new Participant();
        Deck deck = new Deck();
        Card lastCard = deck.getDeckCards().getLast();
        player.takeCard(deck);
        player.openLastCard();

        assert(lastCard.type.getValue() == player.sayCardsSum());
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
}