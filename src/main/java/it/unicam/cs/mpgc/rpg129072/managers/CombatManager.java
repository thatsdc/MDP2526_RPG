package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.enums.ActionType;
import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;
import it.unicam.cs.mpgc.rpg129072.enums.TurnResult;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CombatManager {
    private final String playerName;
    private final int playerScore;
    private final EnemyType enemyType;

    private int initialPlayerHealth;
    private int initialEnemyHealth;

    private int playerHealth;
    private int enemyHealth;

    private int playerDamage;
    private int enemyDamage;

    private int turnCount = 1;

    private String gameMessage = "";

    public CombatManager(String playerName, int playerScore, EnemyType enemyType) {
        setPlayerInitialHealthAndDamage(playerScore);
        setEnemyInitialHealthAndDamage(enemyType);

        this.playerHealth = initialPlayerHealth;
        this.enemyHealth = initialEnemyHealth;

        this.playerName = playerName;
        this.playerScore = playerScore;
        this.enemyType = enemyType;
    }

    private void setPlayerInitialHealthAndDamage(int playerScore){
        this.initialPlayerHealth = 120 + ((playerScore + 100) / 10);
        this.playerDamage = 10 + ((playerScore + 100) / 100);
    }

    private void setEnemyInitialHealthAndDamage(EnemyType enemyType){
        switch (enemyType){
            case MERFOLK -> {
                this.initialEnemyHealth = 80;
                this.enemyDamage = 15;
            }
            case ZOMBIE -> {
                this.initialEnemyHealth = 100;
                this.enemyDamage = 10;
            }
            case SKELETON -> {
                this.initialEnemyHealth = 60;
                this.enemyDamage = 20;
            }
        }
    }

    public void playTurn(ActionType playerAction){
        ActionType enemyAction = getEnemyAction();
        TurnResult turnResult = getTurnResult(playerAction, enemyAction);

        gameMessage = getPlayerName() + " uses " + playerAction + "\n" + getEnemyName() +" uses " + enemyAction;

        if (turnResult == TurnResult.WIN){
            enemyHealth -= playerDamage;
            gameMessage += "\n" + getEnemyName() + " lose " + playerDamage +" HP";
        }else if (turnResult == TurnResult.LOSE){
            playerHealth -= enemyDamage;
            gameMessage += "\n" + getPlayerName() + " lose " + enemyDamage +" HP";
        }else{
            gameMessage += "\nIt's a draw";
        }

        turnCount += 1;
    }

    private TurnResult getTurnResult(ActionType playerAction, ActionType enemyAction){
        if (playerAction == ActionType.ATTACK){
            if (enemyAction == ActionType.CUNNING) return TurnResult.WIN;
            else if (enemyAction == ActionType.DEFENSE) return TurnResult.LOSE;
            else return TurnResult.DRAW;
        }
        else if (playerAction == ActionType.DEFENSE){
            if (enemyAction == ActionType.ATTACK) return TurnResult.WIN;
            else if (enemyAction == ActionType.CUNNING) return TurnResult.LOSE;
            else return TurnResult.DRAW;
        }
        else if (playerAction == ActionType.CUNNING){
            if (enemyAction == ActionType.DEFENSE) return TurnResult.WIN;
            else if (enemyAction == ActionType.ATTACK) return TurnResult.LOSE;
            else return TurnResult.DRAW;
        }

        return null;
    }

    private ActionType getEnemyAction(){
        List<ActionType> items = Arrays.asList(ActionType.ATTACK, ActionType.DEFENSE, ActionType.CUNNING);

        Random random = new Random();
        int randomIndex = random.nextInt(items.size());

        return items.get(randomIndex);
    }

    public String getEnemyName(){
        String enemyTypeStr = enemyType.name();
        return enemyTypeStr.substring(0, 1).toUpperCase() + enemyTypeStr.substring(1).toLowerCase();
    }

    public String getGameMessage(){
        return gameMessage;
    }

    public int getTurnCount(){
        return turnCount;
    }

    public int getInitialPlayerHealth(){
        return initialPlayerHealth;
    }

    public int getInitialEnemyHealth(){
        return initialEnemyHealth;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getEnemyHealth() {
        return enemyHealth;
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
