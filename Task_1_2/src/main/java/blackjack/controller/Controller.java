package blackjack.controller;

import blackjack.customio.*;
import blackjack.gameenvironment.*;

import java.util.Properties;

/**
 * Class that manages the game.
 */
public class Controller {

    private int roundsAmount;
    private int playerWin;
    private int dealerWin;
    private Participant player;
    private Participant dealer;
    private Deck deck;

    private Properties local;
    private StdIn in;

    public Controller(Properties local, StdIn in) {
        this.local = local;
        this.in = in;
    }

    /**
     * Implement game logic.
     */
    public void play() {
        this.gameStart(in);
        if (this.roundsAmount <= 0) {
            StdOut.println(local.getProperty("gameTermd"));
        } else {
            this.initGame();
            for (int round = 1; round <= this.roundsAmount; round++) {
                this.playRound(round);
            }
            StdOut.println(local.getProperty("gameEnd"));
            StdOut.printf(local.getProperty("counting"), this.playerWin, this.dealerWin);
            if (this.dealerWin == this.playerWin) {
                StdOut.println(local.getProperty("noTotalWinners"));
            } else if (this.playerWin > this.dealerWin) {
                StdOut.println(local.getProperty("plrTotalWinner"));
            } else {
                StdOut.println(local.getProperty("dlrTotalWinner"));
            }
        }
    }

    private void gameStart(StdIn in) {
        StdOut.println(local.getProperty("greeting"));
        StdOut.println(local.getProperty("fstStep"));
        StdOut.println(local.getProperty("inInstr"));
        StdOut.print(local.getProperty("roundsAmount"));
        this.roundsAmount = in.scanInt();
    }

    private void initGame() {
        this.player = new Player(this.in);
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.playerWin = 0;
        this.dealerWin = 0;
    }

    private void playRound(int round) {
        StdOut.printf(local.getProperty("roundStart"), round);
        StdOut.println(local.getProperty("partsReady"));
        this.gettingCards();
        StdOut.println(local.getProperty("dealerState"));
        Judge.ask(dealer, local);
        StdOut.println(local.getProperty("playerState"));
        Judge.ask(player, local);
        if (player.sayCardsSum() == 21) {
            StdOut.println(local.getProperty("plrBckJck"));
            this.playerWin++;
            return;
        }
        StdOut.println(local.getProperty("playerNtBckJck"));
        StdOut.println(local.getProperty("playersStep"));
        StdOut.println(local.getProperty("steps"));
        this.player.step(this.deck, this.local);
        if (player.sayCardsSum() == 21) {
            StdOut.println(local.getProperty("plrBckJck"));
            this.playerWin++;
            return;
        }
        if (player.sayCardsSum() > 21) {
            StdOut.println(local.getProperty("plBigger21"));
            this.dealerWin++;
            return;
        }
        StdOut.println(local.getProperty("dealerStep"));
        StdOut.println(local.getProperty("dealerOpenCard"));
        dealer.openLastCard();
        Judge.ask(dealer, local);
        dealer.step(this.deck, this.local);
        if (dealer.sayCardsSum() == 21) {
            StdOut.println(local.getProperty("dlrBckJck"));
            this.dealerWin++;
            return;
        }
        if (dealer.sayCardsSum() > 21) {
            StdOut.println(local.getProperty("dlBigger21"));
            this.playerWin++;
            return;
        }
        this.roundResults();
    }

    private void gettingCards() {
        this.player.prepare();
        this.dealer.prepare();
        this.deck.prepareDeck();
        StdOut.println(local.getProperty("dealPrepar"));
        this.dealer.takeCard(this.deck);
        this.dealer.openLastCard();
        this.dealer.takeCard(this.deck);
        StdOut.println(local.getProperty("dealerActs"));
        for (int i = 1; i <= 2; i++) {
            this.player.takeCard(this.deck);
            this.player.openLastCard();
        }
    }

    private void roundResults() {
        StdOut.println(local.getProperty("rndFinal"));
        StdOut.println(local.getProperty("playerState"));
        Judge.ask(this.player, local);
        StdOut.println(local.getProperty("dealerState"));
        Judge.ask(this.dealer, local);
        StdOut.println(local.getProperty("rndResult"));
        if (this.player.sayCardsSum() > this.dealer.sayCardsSum()) {
            StdOut.println(local.getProperty("plrWinRnd"));
            this.playerWin++;
        } else if (player.sayCardsSum() < dealer.sayCardsSum()) {
            StdOut.println(local.getProperty("dlrWinRnd"));
            this.dealerWin++;
        } else {
            StdOut.println(local.getProperty("noWinners"));
        }
    }
}