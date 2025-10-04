package blackjack.loader;

import blackjack.constructor.Constructor;

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
        Constructor.play();
    }
}