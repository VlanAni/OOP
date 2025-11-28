package vanisimov.creditbook.implementation;

import vanisimov.creditbook.consts.EdLevel;

public class Student {

    private String fullName;
    private String group;
    private EdLevel edLevel;

    public Student(String name, String group, EdLevel edLevel) {
        this.fullName = name;
        this.group = group;
        this.edLevel = edLevel;
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

    public void setFullName(String newFullName) {
        this.fullName = newFullName;
    }

    public void setGroup(String newGroup) {
        this.group = newGroup;
    }

    public void setEdLevel(EdLevel newEdLevel) {
        this.edLevel = newEdLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- СТУДЕНТ: ").append(this.fullName).append(" --\n");
        sb.append("-- ГРУППА: ").append(this.group).append(" --\n");
        sb.append("-- СЕМЕСТР ОБУЧЕНИЯ: ").append(this.edLevel.name()).append(" --\n");
        return sb.toString();
    }
}