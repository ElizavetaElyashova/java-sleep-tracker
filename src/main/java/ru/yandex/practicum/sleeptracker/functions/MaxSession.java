package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MaxSession implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        Duration result = sessions.stream()
                .map(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()))
                .max(Comparator.comparing(Duration::toMinutes))
                .get();
        return new SleepAnalysisResult("Максимальная продолжительность сессии", Long.toString(result.toMinutes()));
    }
}
