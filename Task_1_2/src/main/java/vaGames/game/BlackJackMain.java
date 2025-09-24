package vagames.game;

import java.util.Scanner;
import vagames.gamearchitect.BlackJackLeader;
import vagames.gamearchitect.Card;
import vagames.gamearchitect.Deck;
import vagames.gamearchitect.Participant;

/**
 * Emulating Blackjack game.
 * BlackJackMain - the main class.
 * Card - game card class.
 * CardTypes and Suit - enum classes for cards' params.
 * Deck - game deck.
 * Participant - person who play.
 * PartState - player's data.
 */
public class BlackJackMain {

    public static void main(String[] args) {
        System.out.println("Hello! It's BlackJack game");
        System.out.println("First step: please, enter rounds' amount");
        System.out.println("If your input is zero, the program stop. Otherwise, we will start");
        System.out.print("Rounds' amount: ");
        Scanner usrIn = new Scanner(System.in);
        int roundsAmount;
        roundsAmount = usrIn.nextInt();
        if (roundsAmount == 0) {
            System.out.println("The game finished...");
            return;
        }
        Participant player = new Participant();
        Participant dealer = new Participant();
        Deck deck = new Deck();
        int playerWins = 0;
        int dealerWins = 0;
        for (int round = 1; roundsAmount > 0 & round <= roundsAmount; round++) {
            System.out.printf("===Round [%d] started===\n", round);
            System.out.println("Participants are getting ready...");
            player.prepare();
            dealer.prepare();
            deck.prepareDeck();
            System.out.println("Now dealer taking cards...");
            dealer.takeCard(deck);
            dealer.openLastCard();
            dealer.takeCard(deck);
            System.out.println("Dealer took cards. Now he is going to give you two cards");
            for (int i = 1; i <= 2; i++) {
                player.takeCard(deck);
                player.openLastCard();
            }
            System.out.println("==Dealer's cards now==");
            BlackJackLeader.ask(dealer);
            System.out.println("==Your cards now==");
            BlackJackLeader.ask(player);
            if (player.sayCardsSum() == 21) {
                System.out.println("You reached Blackjack! You are winner!");
                playerWins++;
                continue;
            }
            System.out.println("You didn't reached Blackjack...");
            System.out.println("--You turn to take cards--");
            System.out.println("[1 - take card | 0 - stop my step]");
            Card takenCard;
            for (int decision = usrIn.nextInt(); decision > 0; decision = usrIn.nextInt()) {
                takenCard = player.takeCard(deck);
                player.openLastCard();
                System.out.print("You took the card: ");
                BlackJackLeader.nameCard(takenCard);
                System.out.printf("Now your points are: %d\n", player.sayCardsSum());
                if (player.sayCardsSum() >= 21) {
                    break;
                }
            }
            if (player.sayCardsSum() == 21) {
                System.out.println("You reached Blackjack! You're winner!");
                playerWins++;
                continue;
            }
            if (player.sayCardsSum() > 21) {
                System.out.println("You reached sum bigger then 21! Dealer's winner");
                dealerWins++;
                continue;
            }
            System.out.println("--Diler's turn to take cards--");
            System.out.println("Diler opened closed card");
            dealer.openLastCard();
            BlackJackLeader.ask(dealer);
            for (int delSum = dealer.sayCardsSum(); delSum < 17; delSum = dealer.sayCardsSum()) {
                takenCard = dealer.takeCard(deck);
                dealer.openLastCard();
                System.out.print("Dealer took card: ");
                BlackJackLeader.nameCard(takenCard);
                System.out.printf("Now his points are: %d\n", dealer.sayCardsSum());
                if (dealer.sayCardsSum() >= 21) {
                    break;
                }
            }
            if (dealer.sayCardsSum() == 21) {
                System.out.println("Dealer reached Blackjack! Dealer's winner!");
                dealerWins++;
                continue;
            }
            if (dealer.sayCardsSum() > 21) {
                System.out.println("Dealer reached sum bigger then 21! You're winner");
                playerWins++;
                continue;
            }
            System.out.println("==All players finished their steps==\n");
            System.out.println("==Yours cards==");
            BlackJackLeader.ask(player);
            System.out.println("==Dealer's cards==");
            BlackJackLeader.ask(dealer);
            System.out.println("=The result of the round=");
            if (player.sayCardsSum() > dealer.sayCardsSum()) {
                System.out.println("You're the winner!");
                playerWins++;
            } else if (player.sayCardsSum() < dealer.sayCardsSum()) {
                System.out.println("Dealer is the winner!");
                dealerWins++;
            } else {
                System.out.println("No winners!");
            }
        }
        System.out.println("====GENERAL RESULT====");
        System.out.printf("Yours: %d Dealer's: %d\n", playerWins, dealerWins);
        if (dealerWins == playerWins) {
            System.out.println("You an dealer have the same foutune");
        } else if (playerWins > dealerWins) {
            System.out.println("You are the total winner");
        } else {
            System.out.println("Dealer is the total winner");
        }
    }
}