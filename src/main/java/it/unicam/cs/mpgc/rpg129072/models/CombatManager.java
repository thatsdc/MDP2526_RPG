package it.unicam.cs.mpgc.rpg129072.models;

public class CombatManager {

    private String playerName;
    private int playerHealth;
    private String enemyName;
    private int enemyHealth;

    public CombatManager(String playerName,  int playerHealth, String enemyName, int enemyHealth) {
        this.playerName = playerName;
        this.playerHealth = playerHealth;
        this.enemyName = enemyName;
        this.enemyHealth = enemyHealth;
    }
}
