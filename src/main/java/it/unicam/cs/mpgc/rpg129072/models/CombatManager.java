package it.unicam.cs.mpgc.rpg129072.models;

import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;

public class CombatManager {
    private final String playerName;
    private final int playerScore;
    private final EnemyType enemyType;

    public CombatManager(String playerName, int playerScore, EnemyType enemyType) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.enemyType = enemyType;
    }
    
    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }
}
