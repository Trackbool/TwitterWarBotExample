package utils;

import models.Language;
import models.configuration.GlobalConfig;
import models.war.Player;
import quotes.models.WarQuoteActions;
import timer.models.TimeIntervalModel;
import utils.file.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigFilesLoader {
    private final static String EQUAL_SYMBOL = "=";
    private final static String SEMICOLON_SYMBOL = ";";
    private final static String GLOBAL_CONFIG_FILE = "files/config.txt";
    private final static String PLAYERS_LIST_FILE = "files/players.txt";
    private final static String TIME_INTERVALS_LIST_FILE = "files/time-intervals.txt";
    private final static String LANGUAGE_PARAMETER = "{LANGUAGE}";
    private final static String TRANSLATIONS_PATH = "files/translations/" + LANGUAGE_PARAMETER;
    private final static String QUOTES_FILE = TRANSLATIONS_PATH + "/quotes.txt";
    private final static String KILL_VERBS_FILE = TRANSLATIONS_PATH + "/kill-verbs.txt";

    public static GlobalConfig loadGlobalConfig() throws IOException {
        List<String> lines = FileUtils.readAllNotEmptyLines(GLOBAL_CONFIG_FILE);

        GlobalConfig configModel = new GlobalConfig();
        String[] elements;

        for (String line : lines) {
            elements = line.split(EQUAL_SYMBOL, 2);
            if (elements.length == 2) {
                configModel.addProperty(elements[0], elements[1]);
            } else {
                throw new IOException("Invalid config file format. Missing '" + EQUAL_SYMBOL + "'.");
            }
        }

        return configModel;
    }

    public static List<Player> loadPlayers() throws IOException {
        List<String> lines = FileUtils.readAllNotEmptyLines(PLAYERS_LIST_FILE);

        ArrayList<Player> players = new ArrayList<>();
        String[] elements;

        for (String line : lines) {
            elements = line.trim().split(SEMICOLON_SYMBOL, 2);
            if (elements.length == 2) {
                players.add(new Player(elements[0].trim(), elements[1]));
            } else {
                throw new IOException("Invalid players file format.");
            }
        }

        return players;
    }

    public static List<TimeIntervalModel> loadTimeIntervals() throws IOException {
        List<String> lines = FileUtils.readAllNotEmptyLines(TIME_INTERVALS_LIST_FILE);

        List<TimeIntervalModel> timeIntervals = new ArrayList<>();
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] elements;

        for (String line : lines) {
            elements = line.trim().split(SEMICOLON_SYMBOL);
            if (elements.length == 2) {
                LocalTime begin = LocalTime.parse(elements[0].trim(), FORMATTER);
                LocalTime end = LocalTime.parse(elements[1].trim(), FORMATTER);
                timeIntervals.add(new TimeIntervalModel(begin, end));
            } else {
                throw new IOException("Invalid time intervals file format.");
            }
        }

        return timeIntervals;
    }

    public static List<String> loadKillVerbs(Language language) throws IOException {
        String verbsFile = getKillVerbsFileByLanguage(language);
        List<String> killVerbs = new ArrayList<>();
        try {
            killVerbs = FileUtils.readAllNotEmptyLines(verbsFile);
        } catch (FileNotFoundException ignored) {
        }

        return killVerbs;
    }

    public static Map<WarQuoteActions, String> loadQuotes(Language language) throws IOException {
        String quotesFile = getQuotesFileByLanguage(language);
        List<String> lines = FileUtils.readAllNotEmptyLines(quotesFile);

        Map<WarQuoteActions, String> quotes = new HashMap<>();
        String[] elements;
        try {
            for (String line : lines) {
                elements = line.trim().split(EQUAL_SYMBOL, 2);
                if (elements.length == 2) {
                    WarQuoteActions quoteStatus = WarQuoteActions.valueOf(elements[0].trim());
                    String quote = elements[1];
                    quotes.put(quoteStatus, quote);
                } else {
                    throw new IOException("No quote identifier");
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IOException("Invalid quotes '" + language.getCode() + "' file format " +
                    "or bad file encoding (UTF-8 without BOM required). " + e.getMessage());
        }

        return quotes;
    }

    private static String getKillVerbsFileByLanguage(Language language) {
        return KILL_VERBS_FILE.replace(LANGUAGE_PARAMETER, language.getCode().toLowerCase());
    }

    private static String getQuotesFileByLanguage(Language language) {
        return QUOTES_FILE.replace(LANGUAGE_PARAMETER, language.getCode().toLowerCase());
    }
}
