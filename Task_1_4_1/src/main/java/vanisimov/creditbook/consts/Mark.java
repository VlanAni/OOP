package vanisimov.creditbook.consts;

import lombok.Getter;

public enum Mark {

    EXCELLENT("Отлично", 5),
    GOOD("Хорошо", 4),
    SATISFACTORY("Удовлетворительно", 3),
    CREDIT("Зачёт", 0),
    NOTEV("Неоценено", 0);

    @Getter
    private String value;

    private final int intVal;

    private Mark(String value, int intVal) {
        this.value = value;
        this.intVal = intVal;
    }

    public int getIntValue() {
        return this.intVal;
    }
}
