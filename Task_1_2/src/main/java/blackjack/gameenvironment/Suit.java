package blackjack.gameenvironment;

enum Suit {
    H("Hearts"),
    D("Diamonds"),
    C("Clubs"),
    S("Spades");

    private String value;

    private Suit(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}