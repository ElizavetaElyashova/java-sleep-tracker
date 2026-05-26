package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MinSession implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        Duration result = Duration.of(0, ChronoUnit.MINUTES);
        if (!sessions.isEmpty()) {
            result = sessions.stream()
                    .map(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()))
                    .min(Comparator.comparing(Duration::toMinutes))
                    .get();
        }
        return new SleepAnalysisResult("Минимальная продолжительность сессии", Long.toString(result.toMinutes()));
    }
}
