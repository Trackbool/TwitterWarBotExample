import models.Language;
import models.Notifier;
import models.TwitterNotifier;
import models.configuration.GlobalConfigHolder;
import models.war.Player;
import models.war.War;
import quotes.WarQuotes;
import quotes.models.WarQuoteActions;
import timer.models.TimeIntervalModel;
import utils.ConfigFilesLoader;
import utils.Utils;
import utils.file.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Adrián Fernández Arnal - @adrianfa5
 */
public final class Main {
    private static final String SAVE_FILE_PATH = "files/save/wargame.sav";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final File saveFile = new File(SAVE_FILE_PATH);
        boolean newGame = true;
        War war = null;
        if (saveFile.exists() && saveFile.length() > 0) {
            System.out.println("It seems that there is a saved game. Do you want to recover it? (Y/n):");
            String newGameResponse = sc.nextLine();
            if (!newGameResponse.equalsIgnoreCase("n")) {
                try {
                    war = FileUtils.deserialize(saveFile);
                    if (war.getCurrentPlayersCount() > 1) {
                        newGame = false;
                    } else {
                        throw new IllegalStateException("It seems that the saved game is over.");
                    }
                } catch (IOException e) {
                    System.err.println("Error loading the saved game - " + e.getMessage() + ".");
                } catch (ClassNotFoundException e) {
                    System.err.println("Error loading the saved game - Invalid or corrupted file.");
                } catch (Exception e) {
                    System.err.println(e.getMessage() + " Starting new game...");
                }
            }
        }

        List<Player> players = null;
        List<TimeIntervalModel> timeIntervals = null;
        List<String> killVerbs = null;
        Map<WarQuoteActions, String> quotes = null;
        boolean filesLoadedSuccessfully = false;
        try {
            GlobalConfigHolder.config = ConfigFilesLoader.loadGlobalConfig();

            Language language = new Language(GlobalConfigHolder.config.getProperty("language"), "");
            killVerbs = ConfigFilesLoader.loadKillVerbs(language);
            quotes = ConfigFilesLoader.loadQuotes(language);

            timeIntervals = ConfigFilesLoader.loadTimeIntervals();
            if (newGame) {
                players = ConfigFilesLoader.loadPlayers();
            }
            filesLoadedSuccessfully = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (filesLoadedSuccessfully) {
            if (newGame) {
                System.out.println("Enter the name of the new war:");
                String warName = sc.nextLine();
                war = new War(warName, players);
            }
            String updateStatusRateInput;
            do {
                System.out.println("Enter the rate between turns (in seconds):");
                updateStatusRateInput = sc.nextLine();
            } while (!Utils.isValidInteger(updateStatusRateInput));

            int updateStatusRate = Integer.parseInt(updateStatusRateInput);
            updateStatusRate = updateStatusRate > 0 ? updateStatusRate : 1;

            WarSimulator simulator = new WarSimulator(war, newGame, new WarQuotes(quotes, killVerbs));
            Notifier twitterNotifier = new TwitterNotifier(war.getAllPlayers());
            simulator.setUpdateStatusRate(updateStatusRate);
            simulator.setTimeIntervals(timeIntervals);
            simulator.setNotifier(twitterNotifier);

            War serializeWar = war;
            simulator.setOnTurnPerformed(() -> {
                try {
                    FileUtils.serialize(serializeWar, saveFile);
                } catch (IOException ignored) {
                }
            });
            simulator.setOnFinish(saveFile::delete);
            simulator.start();
        }
        sc.close();
    }
}
