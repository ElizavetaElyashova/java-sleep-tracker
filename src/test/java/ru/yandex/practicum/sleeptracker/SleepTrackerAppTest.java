package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    List<SleepSession> sessions;
    static SleepSession s1;
    static SleepSession s2;
    static SleepSession s3;
    static SleepSession s4;
    static SleepSession s5;
    static SleepSession s6;

    @BeforeAll
    static void beforeAll() {
        s1 = new SleepSession("02.10.25 01:15;02.10.25 09:30;GOOD"); //сова
        s2 = new SleepSession("02.10.25 23:50;03.10.25 08:05;NORMAL"); //голубь
        s3 = new SleepSession("03.10.25 23:10;04.10.25 00:00;NORMAL"); //голубь
        s6 = new SleepSession("05.10.25 06:00;06.10.25 10:00;BAD");
        s4 = new SleepSession("29.10.25 23:40;30.10.25 10:00;BAD"); //сова
        s5 = new SleepSession("31.10.25 21:10;01.11.25 06:20;BAD"); //жаворонок
    }

    @Test
    void testNumberOfSessionsIsZero() {
        sessions = new ArrayList<>();
        SleepAnalysisResult actual = new NumberOfSessions().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Всего сессий сна", "0");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testNumberOfSessionsIsTwo() {
        sessions = new ArrayList<>(List.of(s1, s2));
        SleepAnalysisResult actual = new NumberOfSessions().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Всего сессий сна", "2");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testMinSessionUniqueResult() {
        sessions = new ArrayList<>(List.of(s1, s3));
        SleepAnalysisResult actual = new MinSession().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Минимальная продолжительность сессии", "50");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testMinSessionNotUniqueResult() {
        sessions = new ArrayList<>(List.of(s1, s2));
        SleepAnalysisResult actual = new MinSession().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Минимальная продолжительность сессии", "495");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testMaxSessionUniqueResult() {
        sessions = new ArrayList<>(List.of(s1, s3));
        SleepAnalysisResult actual = new MaxSession().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Максимальная продолжительность сессии", "495");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testMaxSessionNotUniqueResult() {
        sessions = new ArrayList<>(List.of(s1, s2));
        SleepAnalysisResult actual = new MaxSession().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Максимальная продолжительность сессии", "495");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testAverageSessionOneSession() {
        sessions = new ArrayList<>(List.of(s1));
        SleepAnalysisResult actual = new AverageSession().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Средняя продолжительность сессии", "495.00");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testAverageSessionSeveralSessions() {
        sessions = new ArrayList<>(List.of(s1, s2, s3));
        SleepAnalysisResult actual = new AverageSession().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Средняя продолжительность сессии", "346.67");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testNumberOfBadSessionsIsZero() {
        sessions = new ArrayList<>(List.of(s1, s2, s3));
        SleepAnalysisResult actual = new NumberOfBadSessions().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество сессий с плохим качеством сна", "0");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testNumberOfBadSessionsIsOne() {
        sessions = new ArrayList<>(List.of(s1, s2, s4));
        SleepAnalysisResult actual = new NumberOfBadSessions().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество сессий с плохим качеством сна", "1");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testUserChronotypeIsClear() {
        sessions = new ArrayList<>(List.of(s1, s2, s4, s5));
        SleepAnalysisResult actual = new UserChronotype().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Хронотип пользователя", Chronotype.Сова.toString());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testUserChronotypeIsUnclear() {
        sessions = new ArrayList<>(List.of(s1, s2, s3, s4, s5));
        SleepAnalysisResult actual = new UserChronotype().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Хронотип пользователя", Chronotype.Голубь.toString());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSleepLessNightsDifferentMonths() {
        sessions = new ArrayList<>(List.of(s4, s5));
        SleepAnalysisResult actual = new SleeplessNights().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", "1");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSleepLessNightsNoNights() {
        sessions = new ArrayList<>(List.of(s3));
        SleepAnalysisResult actual = new SleeplessNights().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", "0");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSleeplessNightsNoSleeplessNights() {
        sessions = new ArrayList<>(List.of(s1, s2, s3));
        SleepAnalysisResult actual = new SleeplessNights().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", "0");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSleeplessNightsSessionFinishedAtMidnight() {
        sessions = new ArrayList<>(List.of(s3));
        SleepAnalysisResult actual = new SleeplessNights().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", "0");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void testSleeplessNightsSessionFStartedAt6Am() {
        sessions = new ArrayList<>(List.of(s3, s6));
        SleepAnalysisResult actual = new SleeplessNights().apply(sessions);
        SleepAnalysisResult expected = new SleepAnalysisResult("Количество бессонных ночей", "1");
        assertEquals(expected.toString(), actual.toString());
    }

}