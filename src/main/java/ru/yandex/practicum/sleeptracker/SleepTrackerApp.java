package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    static List<SleepSession> sessionsLog;
    static List<Function<List<SleepSession>, SleepAnalysisResult>> analysisFunctions;


    public static void main(String[] args) {
        try {
            readLog(args[0]);
            buildAnalysisFunctions();
            analysisFunctions.stream()
                    .forEach(function -> System.out.println(function.apply(sessionsLog)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readLog(String filePath) {
        try {
            sessionsLog = Files.lines(Path.of(filePath), StandardCharsets.UTF_8)
                    .map(str -> new SleepSession(str))
                    .toList();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void buildAnalysisFunctions() {
        analysisFunctions = new ArrayList<>();
        analysisFunctions.add(new NumberOfSessions());
        analysisFunctions.add(new MinSession());
        analysisFunctions.add(new MaxSession());
        analysisFunctions.add(new AverageSession());
        analysisFunctions.add(new NumberOfBadSessions());
        analysisFunctions.add(new SleeplessNights());
        analysisFunctions.add(new UserChronotype());
    }
}