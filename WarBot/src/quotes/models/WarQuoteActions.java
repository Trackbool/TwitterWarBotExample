package quotes.models;

public enum WarQuoteActions {
    WAR_BEGIN("WAR_BEGIN"), PLAYERS_COUNT("PLAYERS_COUNT"), DAY("DAY"),
    KILL("KILL"), SUICIDE("SUICIDE"), PLAYERS_REMAINING("PLAYERS_REMAINING"),
    WIN("WIN");

    private String value;

    WarQuoteActions(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
