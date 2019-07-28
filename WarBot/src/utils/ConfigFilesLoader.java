package utils;

import models.GlobalConfig;
import models.Player;
import timer.models.TimeIntervalModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConfigFilesLoader {
    public final static String GLOBAL_CONFIG_PATH = "files/config.txt";
    public final static String PLAYERS_LIST_PATH = "files/players.txt";
    public final static String TIME_INTERVALS_LIST_PATH = "files/time-intervals.txt";

    public static GlobalConfig loadGlobalConfig() throws IOException {
        List<String> lines;
        lines = ConfigFilesLoader.readAllLines(GLOBAL_CONFIG_PATH);

        GlobalConfig configModel = new GlobalConfig();
        String[] elements;
        try {
            for (String line : lines) {
                if (line.contains("=")) {
                    elements = line.split("=", 2);
                    configModel.addProperty(elements[0], elements[1]);
                } else if (!line.isEmpty()) {
                    throw new IOException("Invalid config file format. Missing '='.");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Invalid config file format.");
        }

        return configModel;
    }

    public static List<Player> loadPlayers() throws IOException {
        List<String> lines;
        lines = ConfigFilesLoader.readAllLines(PLAYERS_LIST_PATH);

        ArrayList<Player> players = new ArrayList<>();
        String[] elements;
        try {
            for (String line : lines) {
                elements = line.trim().split(";");
                players.add(new Player(elements[0].trim(), elements[1]));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Invalid players file format.");
        }

        return players;
    }

    public static List<TimeIntervalModel> loadTimeIntervals() throws IOException {
        List<String> lines;
        lines = ConfigFilesLoader.readAllLines(TIME_INTERVALS_LIST_PATH);

        List<TimeIntervalModel> timeIntervals = new ArrayList<>();
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] elements;
        try {
            for (String line : lines) {
                if (line != null && !line.isEmpty()) {
                    elements = line.trim().split(";");
                    LocalTime begin = LocalTime.parse(elements[0].trim(), FORMATTER);
                    LocalTime end = LocalTime.parse(elements[1].trim(), FORMATTER);
                    timeIntervals.add(new TimeIntervalModel(begin, end));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Invalid time intervals file format.");
        }

        return timeIntervals;
    }

    private static List<String> readAllLines(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllLines(path);
    }
}
