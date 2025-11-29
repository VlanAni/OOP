package vanisimov.creditbook.consts;

public enum Mark {

    EXCELLENT("Отлично", 5),
    GOOD("Хорошо", 4),
    SATISFACTORY("Удовлетворительно", 3),
    CREDIT("Зачёт", 0),
    NOTEV("Неоценено", 0);

    private String value;

    private int intVal;

    private Mark(String value, int intVal) {
        this.value = value;
        this.intVal = intVal;
    }

    public String getValue() {
        return this.value;
    }

    public int getIntValue() {
        return this.intVal;
    }
}
