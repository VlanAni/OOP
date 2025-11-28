package vanisimov.creditbook.consts;

public enum Num {
    I(1), II(2), III(3), IV(4),
    V(5), VI(6), VII(7), VIII(8);

    private int value;

    private Num(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
