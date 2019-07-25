package models;

import twitter4j.TwitterException;
import utils.GlobalConfigHolder;
import utils.ResultsImageGenerator;
import utils.TwitterManager;

import java.io.File;
import java.util.List;

public class TwitterNotifier implements Notifier {
    private final static String RESULTS_OUTPUT_PATH = "images/results.png";
    private List<Player> players;

    public TwitterNotifier(List<Player> players){
        this.players = players;
    }

    @Override
    public void notify(String message) {
        String footMessage = GlobalConfigHolder.config.getProperty("footer_message");
        if (footMessage != null && !footMessage.isEmpty()) {
            message += "\n" + footMessage;
        }

        System.out.println(message);
        ResultsImageGenerator.generate(players, RESULTS_OUTPUT_PATH);

        try {
            TwitterManager.updateStatus(message, new File(RESULTS_OUTPUT_PATH));
        } catch (TwitterException e) {
            System.err.println("Error updating Twitter status - " + e.getMessage());
        }
    }
}
