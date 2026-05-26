package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    String description;
    String result;

    public SleepAnalysisResult(String description, String result) {
        this.description = description;
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", description, result);
    }
}
