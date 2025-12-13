package vanisimov.creditbook.implementation;

import lombok.Getter;
import lombok.Setter;
import vanisimov.creditbook.consts.EdLevel;

@Getter
@Setter
public class Student {

    private String fullName;
    private String group;
    private EdLevel edLevel;
    private boolean budget;

    public Student(String name, String group, EdLevel edLevel, Boolean budget) {
        this.fullName = name;
        this.group = group;
        this.edLevel = edLevel;
        this.budget = budget;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- СТУДЕНТ: ").append(this.fullName).append(" --\n");
        sb.append("-- ГРУППА: ").append(this.group).append(" --\n");
        sb.append("-- СЕМЕСТР ОБУЧЕНИЯ: ").append(this.edLevel.name()).append(" --\n");
        if (this.budget) {
            sb.append("-- УЧИТСЯ НА БЮДЖЕТЕ --\n");
        } else {
            sb.append("-- УЧИТСЯ НА ПЛАТКЕ --\n");
        }
        return sb.toString();
    }
}