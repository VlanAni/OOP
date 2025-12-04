package vanisimov.creditbook.implementation;

import vanisimov.creditbook.consts.EdLevel;

public class Student {

    private String fullName;
    private String group;
    private EdLevel edLevel;
    private boolean isBudget;

    public Student(String name, String group, EdLevel edLevel, Boolean isBudget) {
        this.fullName = name;
        this.group = group;
        this.edLevel = edLevel;
        this.isBudget = isBudget;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getGroup() {
        return this.group;
    }

    public EdLevel getEdLevel() {
        return this.edLevel;
    }

    public boolean getIsBudget() {
        return this.isBudget;
    }

    public void setFullName(String newFullName) {
        this.fullName = newFullName;
    }

    public void setGroup(String newGroup) {
        this.group = newGroup;
    }

    public void setEdLevel(EdLevel newEdLevel) {
        this.edLevel = newEdLevel;
    }

    public void setIsBudget(Boolean newIsBudget) {
        this.isBudget = newIsBudget;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- СТУДЕНТ: ").append(this.fullName).append(" --\n");
        sb.append("-- ГРУППА: ").append(this.group).append(" --\n");
        sb.append("-- СЕМЕСТР ОБУЧЕНИЯ: ").append(this.edLevel.name()).append(" --\n");
        if (this.isBudget) {
            sb.append("-- УЧИТСЯ НА БЮДЖЕТЕ --\n");
        } else {
            sb.append("-- УЧИТСЯ НА ПЛАТКЕ --\n");
        }
        return sb.toString();
    }
}