package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SleepSession {
    LocalDateTime sleepStart;
    LocalDateTime sleepFinish;
    SleepQuality sleepQuality;
    Chronotype chronotype;
    final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepSession(String session) {
        String[] sessionData = session.split(";");
        sleepStart = LocalDateTime.parse(sessionData[0], FORMATTER);
        sleepFinish = LocalDateTime.parse(sessionData[1], FORMATTER);
        sleepQuality = SleepQuality.valueOf(sessionData[2]);
        chronotype = defineChronotype();
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepFinish() {
        return sleepFinish;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public Chronotype getChronotype() {
        return chronotype;
    }

    private Chronotype defineChronotype() {
        LocalDate startDate = sleepStart.toLocalDate();
        LocalTime startTime = sleepStart.toLocalTime();
        LocalDate finishDate = sleepFinish.toLocalDate();
        LocalTime finishTime = sleepFinish.toLocalTime();
        if (startDate.equals(finishDate)) {
            if (startTime.isBefore(LocalTime.of(9, 0)) && finishTime.isAfter(LocalTime.of(9, 0))) {
                return Chronotype.Сова;
            }
        } else {
            if (startTime.isAfter(LocalTime.of(23, 0)) && finishTime.isAfter(LocalTime.of(9, 0))) {
                return Chronotype.Сова;
            } else if (startTime.isBefore(LocalTime.of(22, 0)) && finishTime.isBefore(LocalTime.of(7, 0))) {
                return Chronotype.Жаворонок;
            }
        }
        return Chronotype.Голубь;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s %s\n", sleepStart.format(FORMATTER), sleepFinish.format(FORMATTER),
                sleepQuality, chronotype);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SleepSession that = (SleepSession) o;
        return Objects.equals(sleepStart, that.sleepStart)
                && Objects.equals(sleepFinish, that.sleepFinish)
                && sleepQuality == that.sleepQuality
                && chronotype == that.chronotype;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sleepStart, sleepFinish, sleepQuality, chronotype);
    }
}
