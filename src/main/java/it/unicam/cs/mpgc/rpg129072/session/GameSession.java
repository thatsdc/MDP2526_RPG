package it.unicam.cs.mpgc.rpg129072.session;

import it.unicam.cs.mpgc.rpg129072.records.EnemyDefinition;
import it.unicam.cs.mpgc.rpg129072.records.Position;

import java.util.Optional;

public class GameSession {

    private String playerName;
    private int score;
    private Position playerPosition;
    private EnemyDefinition currentEnemy;

    public void startNewGame(String playerName) {
        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("Il nome del giocatore non può essere vuoto");
        }
        this.playerName = playerName;
        this.score = 0;
        this.playerPosition = null;
        this.currentEnemy = null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        if (points < 0) throw new IllegalArgumentException("Punti negativi: " + points);
        score += points;
    }

    public Optional<Position> getPlayerPosition() {
        return Optional.ofNullable(playerPosition);
    }

    public void savePlayerPosition(double x, double y) {
        this.playerPosition = new Position(x, y);
    }

    public void clearPlayerPosition() {
        this.playerPosition = null;
    }

    public void startEncounter(EnemyDefinition enemy) {
        this.currentEnemy = enemy;
    }

    public EnemyDefinition getCurrentEnemy() {
        if (currentEnemy == null) {
            throw new IllegalStateException("Nessun combattimento in corso");
        }
        return currentEnemy;
    }

    public void endEncounter() {
        this.currentEnemy = null;
    }
}