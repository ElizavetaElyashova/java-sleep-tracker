package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL("Сова"),
    DOVE("Голубь"),
    LARK("Жаворонок");

    private final String rus;

    Chronotype(String s) {
        rus = s;
    }

    @Override
    public String toString() {
        return rus;
    }
}
