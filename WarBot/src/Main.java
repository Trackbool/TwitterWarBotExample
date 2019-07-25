import models.Notifier;
import models.Player;
import models.TwitterNotifier;
import timer.models.TimeIntervalModel;
import utils.ConfigFilesLoader;
import utils.GlobalConfigHolder;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Adrián Fernández Arnal - @adrianfa5
 */
public final class Main {

    public static void main(String[] args) {
        List<Player> players = null;
        List<TimeIntervalModel> timeIntervals = null;
        boolean filesLoadedSuccessfully = true;
        try {
            GlobalConfigHolder.config = ConfigFilesLoader.loadGlobalConfig();
            players = ConfigFilesLoader.loadPlayers();
            timeIntervals = ConfigFilesLoader.loadTimeIntervals();
        } catch (IOException e) {
            filesLoadedSuccessfully = false;
            System.err.println(e.getMessage());
        }

        if (filesLoadedSuccessfully) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the name of the war:");
            String warName = sc.nextLine();

            String updateStatusRateInput;
            do {
                System.out.println("Enter the rate between turns (in seconds):");
                updateStatusRateInput = sc.nextLine();
            } while (!Utils.isValidInteger(updateStatusRateInput));
            sc.close();
            int updateStatusRate = Integer.parseInt(updateStatusRateInput);

            WarSimulator simulator = new WarSimulator(warName, players);
            Notifier twitterNotifier = new TwitterNotifier(players);

            simulator.setUpdateStatusRate(updateStatusRate);
            simulator.setTimeIntervals(timeIntervals);
            simulator.setNotifier(twitterNotifier);
            simulator.start();
        }
    }
}
