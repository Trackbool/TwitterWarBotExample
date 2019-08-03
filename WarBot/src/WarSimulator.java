import models.war.Kill;
import models.Notifier;
import models.war.Player;
import models.war.War;
import quotes.WarQuotes;
import timer.TimerTaskExecutor;
import timer.models.TimeIntervalModel;
import models.configuration.GlobalConfigHolder;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WarSimulator {
    private War war;
    private Runnable onTurnPerformed;
    private Runnable onFinish;
    private Notifier notifier;
    private boolean newGame;
    private int rateSeconds;
    private WarQuotes warQuotes;
    private List<TimeIntervalModel> timeIntervals;

    public WarSimulator(War war){
        this.war = war;
        this.notifier = System.out::println;
        this.newGame = true;
        this.rateSeconds = 0;
        this.timeIntervals = new ArrayList<>();
    }

    public WarSimulator(War war, WarQuotes warQuotes) {
        this(war);
        this.warQuotes = warQuotes;
    }

    public WarSimulator(War war, boolean newGame, WarQuotes warQuotes) {
        this(war, warQuotes);
        this.newGame = newGame;
    }

    public WarSimulator(War war, boolean newGame, WarQuotes warQuotes, Notifier notifier) {
        this(war, newGame, warQuotes);
        this.notifier = notifier;
    }

    public void setOnTurnPerformed(Runnable onTurnPerformed) {
        this.onTurnPerformed = onTurnPerformed;
    }

    public void setOnFinish(Runnable onFinish) {
        this.onFinish = onFinish;
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public void setUpdateStatusRate(int rateSeconds) {
        this.rateSeconds = rateSeconds;
    }

    public void setTimeIntervals(List<TimeIntervalModel> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }

    public void start() {
        if (thereIsOnlyOnePlayer()) {
            System.out.println("There are not enough players. Players: " + war.getCurrentPlayersCount());
            return;
        }

        long rateMillis = TimeUnit.SECONDS.toMillis(rateSeconds);
        long startDelayMillis = newGame ? rateMillis : 2;

        TimerTaskExecutor executor = new TimerTaskExecutor(
                this::performStep, startDelayMillis, rateMillis, timeIntervals);
        executor.setOnFinish(this::onWin);
        executor.setFinishCondition(this::thereIsOnlyOnePlayer);

        if (newGame) {
            notifier.notify(getBeginMessage());
            onTurnPerformed.run();
        }
        executor.start();
    }

    private String getBeginMessage() {
        return warQuotes.getWarBeginStatus(war.getName())
                + "\n" + warQuotes.getPlayersCountStatus(war.getAllPlayersCount()) +
                "\n---------------------";
    }

    private boolean thereIsOnlyOnePlayer() {
        return war.getCurrentPlayersCount() == 1;
    }

    private void performStep() {
        Kill kill = getRandomKillerAndVictim();
        war.performKill(kill);

        String message = warQuotes.getDayStatus(war.getCurrentDay()) + "\n" +
                "\n" + getKillStatus(kill) +
                "\n" + warQuotes.getPlayersRemainingStatus(war.getCurrentPlayersCount()) + "\n";
        message = appendCustomFooter(message);

        notifier.notify(message);

        war.incrementDay();
        onTurnPerformed.run();
    }

    private void onWin() {
        notifier.notify(getWinMessage());
        onFinish.run();
    }

    private String getWinMessage() {
        String winMessage = "";
        Player winner = war.getWinnerIfThereIsOne();
        if (winner != null) {
            winMessage = warQuotes.getWinStatus(winner);
        }
        return winMessage;
    }

    private Kill getRandomKillerAndVictim() {
        int random1 = Utils.randomNumber(0, war.getCurrentPlayersCount());
        int random2;
        int cont = 0;

        do {
            random2 = Utils.randomNumber(0, war.getCurrentPlayersCount());
            cont++;
        } while (random1 == random2 && cont <= (war.getCurrentPlayersCount() / 2) + 1);

        Player killer = war.getAlivePlayer(random1);
        Player victim = war.getAlivePlayer(random2);

        return new Kill(killer, victim);
    }

    private String getKillStatus(Kill kill) {
        String status;
        if (!kill.getKiller().equals(kill.getVictim())) {
            status = warQuotes.getKillStatus(kill.getKiller(), kill.getVictim());
        } else {
            status = warQuotes.getSuicideStatus(kill.getVictim());
        }

        return status;
    }

    private String appendCustomFooter(String message){
        String footMessage = GlobalConfigHolder.config.getProperty("footer_message");
        if (footMessage != null && !footMessage.isEmpty()) {
            message += "\n" + footMessage;
        }

        return message;
    }
}
