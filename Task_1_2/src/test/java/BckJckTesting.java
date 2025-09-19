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
        Card[] cards = new Card[12];
        for (int i = 0; i < 12; i++) {
            switch (i) {
                case 0:
                    cards[i] = new Card(CardTypes.TWO, Suit.H);
                    break;
                case 1:
                    cards[i] = new Card(CardTypes.THREE, Suit.H);
                    break;
                case 2:
                    cards[i] = new Card(CardTypes.FOUR, Suit.H);
                    break;
                case 3:
                    cards[i] = new Card(CardTypes.FIVE, Suit.H);
                    break;
                case 4:
                    cards[i] = new Card(CardTypes.SIX, Suit.H);
                    break;
                case 5:
                    cards[i] = new Card(CardTypes.SEVEN, Suit.H);
                    break;
                case 6:
                    cards[i] = new Card(CardTypes.EIGHT, Suit.H);
                    break;
                case 7:
                    cards[i] = new Card(CardTypes.NINE, Suit.H);
                    break;
                case 8:
                    cards[i] = new Card(CardTypes.TEN, Suit.H);
                    break;
                case 9:
                    cards[i] = new Card(CardTypes.JACK, Suit.H);
                    break;
                case 10:
                    cards[i] = new Card(CardTypes.QUEEN, Suit.H);
                    break;
                case 11:
                    cards[i] = new Card(CardTypes.KING, Suit.H);
                    break;
                default:
                    break; 
            }
            cards[i].isOpen = true;
            BlackJackLeader.nameCard(cards[i]);
        }
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