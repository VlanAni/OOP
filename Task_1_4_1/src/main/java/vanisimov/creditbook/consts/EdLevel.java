package vanisimov.creditbook.consts;

import lombok.Getter;

@Getter
public enum EdLevel {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4),
    FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8),
    GRADUATED(9);

    private final int value;

    private EdLevel(int value) {
        this.value = value;
    }

}
