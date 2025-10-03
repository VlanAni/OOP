package blackjack.constructor;

import blackjack.customio.InOut;
import blackjack.gameenvironment.BlackJackLeader;
import blackjack.gameenvironment.Deck;
import blackjack.gameenvironment.Dealer;
import blackjack.gameenvironment.Strings;
import blackjack.gameenvironment.Player;

/**
 * Class that manages the game.
 */
public class Constructor {

    private static int roundsAmount;
    private static int playerWin;
    private static int dealerWin;
    private static Player player;
    private static Dealer dealer;
    private static Deck deck;
    private static InOut inout;

    private Constructor() {}

    /**
     * Implement game logic.
     */
    public static void play() {

        Constructor.gameStart();
        if (Constructor.roundsAmount <= 0) {
            InOut.println(Strings.gameTermd);
            inout.closeStdIn();
            return;
        }

        Constructor.initGame();

        for (int round = 1; round <= Constructor.roundsAmount; round++) {

            InOut.printfInt(Strings.roundStart, round);
            InOut.println(Strings.partsReady);

            Constructor.gettingCards();

            InOut.println(Strings.dealerState);
            BlackJackLeader.ask(dealer);
            InOut.println(Strings.playerState);
            BlackJackLeader.ask(player);

            if (player.sayCardsSum() == 21) {
                System.out.println(Strings.plrBckJck);
                Constructor.playerWin++;
                continue;
            }

            InOut.println(Strings.playerNtBckJck);
            InOut.println(Strings.playersStep);
            InOut.println(Strings.steps);

            Constructor.player.step(Constructor.deck, Constructor.inout);

            if (player.sayCardsSum() == 21) {
                InOut.println(Strings.plrBckJck);
                Constructor.playerWin++;
                continue;
            }
            if (player.sayCardsSum() > 21) {
                InOut.println(Strings.plBigger21);
                Constructor.dealerWin++;
                continue;
            }

            InOut.println(Strings.dealerStep);
            InOut.println(Strings.dealerOpenCard);

            dealer.openLastCard();
            BlackJackLeader.ask(dealer);
            dealer.step(deck);

            if (dealer.sayCardsSum() == 21) {
                InOut.println(Strings.dlrBckJck);
                Constructor.dealerWin++;
                continue;
            }
            if (dealer.sayCardsSum() > 21) {
                InOut.println(Strings.dlBigger21);
                Constructor.playerWin++;
                continue;
            }

            Constructor.roundResults();

        }

        System.out.println(Strings.gameEnd);
        System.out.printf(Strings.counting, Constructor.playerWin, Constructor.dealerWin);
        if (Constructor.dealerWin == Constructor.playerWin) {
            System.out.println(Strings.noTotalWinners);
        } else if (Constructor.playerWin > Constructor.dealerWin) {
            System.out.println(Strings.plrTotalWinner);
        } else {
            System.out.println(Strings.dlrTotalWinner);
        }

        Constructor.inout.closeStdIn();

    }

    private static void gameStart() {
        InOut.println(Strings.greeting);
        InOut.println(Strings.fstStep);
        InOut.println(Strings.inInstr);
        InOut.print(Strings.roundsAmount);
        Constructor.inout = new InOut();
        Constructor.inout.openStdIn();
        Constructor.roundsAmount = Constructor.inout.scanInt();
    }

    private static void initGame() {
        Constructor.player = new Player();
        Constructor.dealer = new Dealer();
        Constructor.deck = new Deck();
        Constructor.playerWin = 0;
        Constructor.dealerWin = 0;
    }

    private static void gettingCards() {
        Constructor.player.prepare();
        Constructor.dealer.prepare();
        Constructor.deck.prepareDeck();
        InOut.println(Strings.dealPrepar);
        Constructor.dealer.takeCard(Constructor.deck);
        Constructor.dealer.openLastCard();
        Constructor.dealer.takeCard(Constructor.deck);
        InOut.println(Strings.dealerActs);
        for (int i = 1; i <= 2; i++) {
            Constructor.player.takeCard(Constructor.deck);
            Constructor.player.openLastCard();
        }
    }

    private static void roundResults() {
        InOut.println(Strings.rndFinal);
        InOut.println(Strings.playerState);
        BlackJackLeader.ask(Constructor.player);
        InOut.println(Strings.dealerState);
        BlackJackLeader.ask(Constructor.dealer);
        InOut.println(Strings.rndResult);
        if (Constructor.player.sayCardsSum() > Constructor.dealer.sayCardsSum()) {
            InOut.println(Strings.plrWinRnd);
            Constructor.playerWin++;
        } else if (player.sayCardsSum() < dealer.sayCardsSum()) {
            InOut.println(Strings.dlrWinRnd);
            Constructor.dealerWin++;
        } else {
            System.out.println(Strings.noWinners);
        }
    }
}