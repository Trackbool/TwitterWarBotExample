package models;

import java.util.Objects;

public class Player {
    private String name;
    private String nick;
    private boolean dead;
    private int kills;

    public Player(String name, String nick) {
        this.name = name;
        this.nick = nick;
        this.dead = false;
        this.kills = 0;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public void die() {
        this.dead = true;
    }

    public void kill(Player victim) {
        if (dead && victim.isDead()) {
            throw new IllegalStateException("Killer and victim are dead. You should check if " +
                    "players are alive before.");
        } else if (dead) {
            throw new IllegalStateException("Killer is dead. You should check if killer is alive before.");
        } else if (victim.isDead()) {
            throw new IllegalStateException("Victim is dead. You should check if victim is alive before.");
        }

        victim.die();
        if (!this.equals(victim)) {
            kills++;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public int getKills() {
        return kills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return dead == player.dead &&
                kills == player.kills &&
                name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dead, kills);
    }

    @Override
    public String toString() {
        return "The player " + name + " (" + nick + ") has " + kills + " "
                + ((kills != Math.abs(1)) ? "kills" : "kill") + " and " + (dead ? "is dead" : "is still alive");
    }
}
