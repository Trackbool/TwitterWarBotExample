package quotes;

import models.war.Player;
import quotes.models.WarQuoteActions;
import quotes.models.WarQuotesConstants;

import java.util.HashMap;
import java.util.Map;

public class WarQuotes {
    private Map<WarQuoteActions, String> quotes;

    public WarQuotes(Map<WarQuoteActions, String> quotesList) {
        quotes = new HashMap<>();
        initDefaultQuotesValues();
        quotesList.forEach((k, v) -> quotes.replace(k, v));
    }

    private void initDefaultQuotesValues() {
        quotes.put(WarQuoteActions.WAR_BEGIN, "The War " + WarQuotesConstants.WAR_NAME + " has begun");
        quotes.put(WarQuoteActions.PLAYERS_COUNT, "There are " + WarQuotesConstants.PLAYERS_COUNT + " players");
        quotes.put(WarQuoteActions.DAY, "Day " + WarQuotesConstants.DAY_NUMBER);

        quotes.put(WarQuoteActions.KILL, WarQuotesConstants.KILLER_NAME + " " +
                "(" + WarQuotesConstants.KILLER_NICKNAME + ") has killed " +
                "" + WarQuotesConstants.VICTIM_NAME + " " +
                "(" + WarQuotesConstants.VICTIM_NICKNAME + ")");

        quotes.put(WarQuoteActions.SUICIDE, WarQuotesConstants.VICTIM_NAME + " " +
                "(" + WarQuotesConstants.VICTIM_NICKNAME + ") has committed suicide");

        quotes.put(WarQuoteActions.PLAYERS_REMAINING,
                WarQuotesConstants.PLAYERS_REMAINING_COUNT + " player/s remaining");

        quotes.put(WarQuoteActions.WIN, "Winner!! " + WarQuotesConstants.PLAYER_NAME + " " +
                "(" + WarQuotesConstants.PLAYER_NICKNAME + ") has " +
                WarQuotesConstants.KILLS_COUNT + " kills and stills alive");
    }

    public String getWarBeginStatus(String warName) {
        return replaceParametersWithText(quotes.get(WarQuoteActions.WAR_BEGIN),
                WarQuotesConstants.WAR_NAME, warName);
    }

    public String getPlayersCountStatus(int playersCount) {
        return replaceParametersWithText(
                quotes.get(WarQuoteActions.PLAYERS_COUNT),
                WarQuotesConstants.PLAYERS_COUNT, String.valueOf(playersCount));
    }

    public String getDayStatus(int dayNumber) {
        return replaceParametersWithText(
                quotes.get(WarQuoteActions.DAY), WarQuotesConstants.DAY_NUMBER, String.valueOf(dayNumber));
    }

    public String getKillStatus(Player killer, Player victim) {
        String status = replaceParametersWithText(quotes.get(WarQuoteActions.KILL),
                WarQuotesConstants.KILLER_NAME, killer.getName());
        status = replaceParametersWithText(status, WarQuotesConstants.KILLER_NICKNAME, killer.getNick());
        status = replaceParametersWithText(status, WarQuotesConstants.VICTIM_NAME, victim.getName());
        status = replaceParametersWithText(status, WarQuotesConstants.VICTIM_NICKNAME, victim.getNick());

        return status;
    }

    public String getSuicideStatus(Player victim) {
        String status = replaceParametersWithText(quotes.get(WarQuoteActions.SUICIDE),
                WarQuotesConstants.VICTIM_NAME, victim.getName());
        status = replaceParametersWithText(status, WarQuotesConstants.VICTIM_NICKNAME, victim.getNick());

        return status;
    }

    public String getPlayersRemainingStatus(int playersCount) {
        return replaceParametersWithText(
                quotes.get(WarQuoteActions.PLAYERS_REMAINING), WarQuotesConstants.PLAYERS_REMAINING_COUNT,
                String.valueOf(playersCount));
    }

    public String getWinStatus(Player winner) {
        String status = replaceParametersWithText(quotes.get(WarQuoteActions.WIN),
                WarQuotesConstants.PLAYER_NAME, winner.getName());
        status = replaceParametersWithText(status, WarQuotesConstants.PLAYER_NICKNAME, winner.getNick());
        status = replaceParametersWithText(status, WarQuotesConstants.KILLS_COUNT, String.valueOf(winner.getKills()));

        return status;
    }

    private static String replaceParametersWithText(String quote, String parameterToReplace, String text) {
        return quote.replace(parameterToReplace, text);
    }

}
