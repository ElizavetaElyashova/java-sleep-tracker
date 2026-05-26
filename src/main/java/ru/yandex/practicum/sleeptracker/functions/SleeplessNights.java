package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SleeplessNights implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        Set<LocalDateTime> sleepNights = new HashSet<>();
        sessions.stream()
                .filter(session -> session.getSleepFinish().toLocalDate().isAfter(session.getSleepStart().toLocalDate())
                        || (session.getSleepStart().toLocalTime().isAfter(LocalTime.of(0, 0)) &&
                        session.getSleepStart().toLocalTime().isBefore(LocalTime.of(6, 0))))
                .forEach(session -> sleepNights.add(LocalDateTime.of(session.getSleepFinish().toLocalDate(),
                        LocalTime.of(0, 0))));
        long allNights = Period.between(sessions.getFirst().getSleepStart().toLocalDate(),
                sessions.getLast().getSleepFinish().toLocalDate()).getDays();
        if (sessions.getFirst().getSleepStart().toLocalTime().isAfter(LocalTime.MIDNIGHT)
                && sessions.getFirst().getSleepStart().toLocalTime().isBefore(LocalTime.NOON)) {
            allNights++;
        }
        long result = allNights - sleepNights.size();
        return new SleepAnalysisResult("Количество бессонных ночей", Long.toString(result));
    }
}
