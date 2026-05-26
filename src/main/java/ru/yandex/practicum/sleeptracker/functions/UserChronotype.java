package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

public class UserChronotype implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        Map<Chronotype, Integer> frequency = new HashMap<>();
        sessions.stream()
                .filter(session -> session.getSleepStart().toLocalTime().isBefore(LocalTime.NOON) ||
                        session.getSleepStart().toLocalTime().isAfter(LocalTime.of(18, 0)))
                .forEach(session -> frequency.put(session.defineChronotype(),
                        frequency.getOrDefault(session.defineChronotype(), 0) + 1));
        Optional maxFrequency = frequency.values().stream().max(Comparator.naturalOrder());
        List<Chronotype> types = frequency.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency.get())
                .map(entry -> entry.getKey())
                .toList();
        Chronotype result;
        if (types.size() > 1) {
            result = Chronotype.DOVE;
        } else {
            result = types.getFirst();
        }
        return new SleepAnalysisResult("Хронотип пользователя", result.toString());
    }
}
