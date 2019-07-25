package models;

public class Kill {
    private Player killer;
    private Player victim;

    public Kill(Player killer, Player victim) {
        this.killer = killer;
        this.victim = victim;
    }

    public Player getKiller() {
        return killer;
    }

    public Player getVictim() {
        return victim;
    }
}
