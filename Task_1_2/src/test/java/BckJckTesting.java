import org.junit.jupiter.api.Test;

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
    void testParticipantSum() {
        Participant player = new Participant();
        Deck deck = new Deck();
        int deckSize = deck.getDeckCards().size();
        Card lastCard = deck.getDeckCards().get(deckSize - 1);
        player.takeCard(deck);
        player.openLastCard();

        assert (lastCard.type.getValue() == player.sayCardsSum());
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
    void preparingTest1() {
        PartState state = new PartState();
        Card card = new Card(CardTypes.ACE, Suit.H);
        state.addNewCard(card);
        state.resetState();

        assert (state.size == 0);
    }
}