package models;

import java.util.ArrayList;
import java.util.List;

public class War {
    private String name;
    private ArrayList<Player> players;
    private ArrayList<Player> currentPlayers;
    private ArrayList<Player> deadPlayers;
    private int currentDay;

    public War(String name) {
        this.name = name;
        players = new ArrayList<>();
        currentPlayers = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        currentDay = 1;
    }

    public War(String name, List<Player> players) {
        this.name = name;
        this.players = new ArrayList<>(players);
        this.currentPlayers = new ArrayList<>(players);
        deadPlayers = new ArrayList<>();
        currentDay = 1;
    }

    public String getName() {
        return name;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void incrementDay() {
        currentDay++;
    }

    public Player getWinnerIfThereIsOne() {
        return currentPlayers.size() == 1 ? currentPlayers.get(0) : null;
    }

    public void addPlayer(Player player) {
        players.add(player);
        currentPlayers.add(player);
    }

    public void performKill(Kill kill) {
        kill.getKiller().kill(kill.getVictim());
        currentPlayers.remove(kill.getVictim());
        deadPlayers.add(kill.getVictim());
    }

    public Player getAlivePlayer(int index) {
        if (index < 0 || index > currentPlayers.size()) {
            throw new IllegalArgumentException(
                    index + " - Inexistent player, only are " + getCurrentPlayersCount() + "remaining ");
        }
        return currentPlayers.get(index);
    }

    public void showAllPlayers() {
        showPlayers(players);
    }

    public void showCurrentPlayers() {
        showPlayers(currentPlayers);
    }

    public void showDeadPlayers() {
        showPlayers(deadPlayers);
    }

    private static void showPlayers(ArrayList<Player> players) {
        for (Player player : players) {
            System.out.println(player.toString());
        }
    }

    public int getAllPlayersCount() {
        return players.size();
    }

    public int getCurrentPlayersCount() {
        return currentPlayers.size();
    }

    public int getDeadPlayersCount() {
        return deadPlayers.size();
    }

    @Override
    public String toString() {
        return "----" + name + "----" +
                "\nDay " + currentDay +
                "\nTotal players: " + getAllPlayersCount() +
                "\nAlive players: " + getCurrentPlayersCount() +
                "\nDead players: " + getDeadPlayersCount();
    }
}
