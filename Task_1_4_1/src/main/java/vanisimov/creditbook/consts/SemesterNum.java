package vanisimov.creditbook.consts;

import lombok.Getter;

@Getter
public enum SemesterNum {
    I(1), II(2), III(3), IV(4),
    V(5), VI(6), VII(7), VIII(8);

    private int value;

    private SemesterNum(int value) {
        this.value = value;
    }

}
