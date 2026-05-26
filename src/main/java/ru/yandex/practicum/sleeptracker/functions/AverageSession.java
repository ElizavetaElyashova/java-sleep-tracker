package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class AverageSession implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        OptionalDouble result = OptionalDouble.of(0);
        if (!sessions.isEmpty()) {
            result = sessions.stream()
                    .mapToLong(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()).toMinutes())
                    .average();
        }
        return new SleepAnalysisResult("Средняя продолжительность сессии",
                String.format("%.2f", result.getAsDouble()));
    }
}
