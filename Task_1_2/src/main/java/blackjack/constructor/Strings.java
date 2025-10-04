package blackjack.constructor;

/**
 * String constants.
 */
public class Strings {

    /* greetings, start of the game */
    public static final String greeting = "Hello! It's BlackJack game";
    public static final String fstStep = "First step: please, enter rounds' amount";
    public static final String inInstr = "If your input is zero, the program stop. "
            + "Otherwise, we will start";
    public static final String roundsAmount = "Rounds' amount: ";
    public static final String gameTermd = "The game is terminated";

    /* Round beginning */
    public static final String roundStart = "===Round [%d] started===\n";
    public static final String partsReady = "Participants are getting ready...";
    public static final String dealPrepar = "Now dealer taking cards...";
    public static final String dealerActs = "Dealer took cards. "
            + "Now he is going to give you two cards";

    /* participants' states */
    public static final String dealerState = "==Dealer's cards now==";
    public static final String playerState = "==Yours cards now==";
    public static final String plrSum = "Now your points are: %d\n";
    public static final String dlrsSum = "Now his points are: %d\n";

    /* Player's step results */
    public static final String plrBckJck = "You reached Blackjack! You are winner!";
    public static final String playerNtBckJck = "You didn't reached Blackjack...";
    public static final String plBigger21 = "You reached sum bigger then 21! Dealer's winner";

    /* participants steps */
    public static final String playersStep = "--You turn to take cards--";
    public static final String steps = "[1 - take card | 0 - stop my step]";
    public static final String plrTookCard = "You took the card: ";
    public static final String dealerStep = "--Diler's turn to take cards--";
    public static final String dealerOpenCard = "Diler opened closed card";
    public static final String dlrTookCard = "Dealer took the card: ";

    /* dealer's step result */
    public static final String dlrBckJck = "Dealer reached Blackjack! Dealer's winner!";
    public static final String dlBigger21 = "Dealer reached sum bigger then 21! You're winner";

    /* round result */
    public static final String rndFinal = "==All players finished their steps==\n";
    public static final String rndResult = "=The result of the round=";
    public static final String plrWinRnd = "You're the winner!";
    public static final String dlrWinRnd = "Dealer is the winner!";
    public static final String noWinners = "No winners!";

    /* total result */
    public static final String gameEnd = "====GENERAL RESULT====";
    public static final String counting = "Yours: %d Dealer's: %d\n";
    public static final String noTotalWinners = "You an dealer have the same fourtune";
    public static final String plrTotalWinner = "You are the total winner";
    public static final String dlrTotalWinner = "Dealer is the total winner";

    private Strings() {}
}