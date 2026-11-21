package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.records.EnemyDefinition;

public interface CombatState {
    String getPlayerName();
    String getEnemyName();
    EnemyDefinition getEnemy();

    int getPlayerHealth();
    int getInitialPlayerHealth();
    int getEnemyHealth();
    int getInitialEnemyHealth();

    String getGameMessage();
    int getTurnCount();
    boolean isOver();
}