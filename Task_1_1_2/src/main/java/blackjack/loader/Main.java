package blackjack.loader;

import blackjack.controller.Controller;
import blackjack.customio.StdIn;
import blackjack.customio.StdOut;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Emulating Blackjack game.
 * BlackJackMain - the main class.
 * Card - game card class.
 * CardTypes and Suit - enum classes for cards' params.
 * Deck - game deck.
 * Participant - person who play.
 * PartState - player's data.
 */
public class Main {

    public static void main(String[] args) {
        try (StdIn in = new StdIn()) {

            StdOut.println("Choose game language: 0 - Russian, other - English");
            int choice = in.scanInt();
            String file;
            if (choice == 0) {
                file = "Messages_RU.properties";
            } else {
                file = "Messages_EN.properties";
            }

            try (InputStream strSet = Main.class.getClassLoader().getResourceAsStream(file)) {
                Properties local = new Properties();
                try (InputStreamReader reader = new InputStreamReader(strSet, StandardCharsets.UTF_8)) {
                    local.load(reader);
                    Controller controller = new Controller(local, in);
                    controller.play();
                } catch (IOException e) {
                    StdOut.println("IO-EXCEPTION");
                }
            } catch (IOException e) {
                StdOut.println("IO-EXCEPTION");
            }

        }
    }
}