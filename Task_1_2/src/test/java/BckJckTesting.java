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
    void partStateTesting() {
        PartState state = new PartState();
        Card card = new Card(CardTypes.KING, Suit.H);
        state.addNewCard(card);
        state.updateSum();

        assert (state.sum == 10);
    }

    @Test
    void testingLeader1() {
        Card two = new Card(CardTypes.TWO, Suit.H);
        Card three = new Card(CardTypes.THREE, Suit.H);
        Card four = new Card(CardTypes.FOUR, Suit.H);
        Card five = new Card(CardTypes.FIVE, Suit.H);
        Card six = new Card(CardTypes.SIX, Suit.H);
        Card seven = new Card(CardTypes.SEVEN, Suit.H);
        Card eight = new Card(CardTypes.EIGHT, Suit.H);
        Card nine = new Card(CardTypes.NINE, Suit.H);
        Card ten = new Card(CardTypes.TEN, Suit.H);
        Card jack = new Card(CardTypes.JACK, Suit.H);
        Card queen = new Card(CardTypes.QUEEN, Suit.H);
        Card king = new Card(CardTypes.KING, Suit.H);
        two.isOpen = true;
        three.isOpen = true;
        four.isOpen = true;
        five.isOpen = true;
        six.isOpen = true;
        seven.isOpen = true;
        eight.isOpen = true;
        nine.isOpen = true;
        ten.isOpen = true;
        jack.isOpen = true;
        queen.isOpen = true;
        king.isOpen = true;
        BlackJackLeader.nameCard(two);
        BlackJackLeader.nameCard(three);
        BlackJackLeader.nameCard(four);
        BlackJackLeader.nameCard(five);
        BlackJackLeader.nameCard(six);
        BlackJackLeader.nameCard(seven);
        BlackJackLeader.nameCard(eight);
        BlackJackLeader.nameCard(nine);
        BlackJackLeader.nameCard(ten);
        BlackJackLeader.nameCard(jack);
        BlackJackLeader.nameCard(queen);
        BlackJackLeader.nameCard(king);
    }

    @Test
    void testingPartState1() {
        PartState state = new PartState();
        state.addNewCard(new Card(CardTypes.ACE, Suit.H));
        state.updateSum();
        state.addNewCard(new Card(CardTypes.ACE, Suit.D));
        state.updateSum();
        state.addNewCard(new Card(CardTypes.ACE, Suit.C));
        state.updateSum();
        state.addNewCard(new Card(CardTypes.ACE, Suit.D));
        state.updateSum();

        assert (state.sum == 14);
    }
}