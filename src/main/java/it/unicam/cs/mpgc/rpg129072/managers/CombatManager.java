package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.enums.ActionType;
import it.unicam.cs.mpgc.rpg129072.enums.TurnResult;
import it.unicam.cs.mpgc.rpg129072.records.EnemyDefinition;
import it.unicam.cs.mpgc.rpg129072.session.GameSession;

import java.util.Objects;
import java.util.Random;

public class CombatManager implements CombatState {
    private static final int BASE_PLAYER_HEALTH = 120;
    private static final int BASE_PLAYER_DAMAGE = 10;
    private static final int SCORE_OFFSET = 100;
    private static final int HEALTH_SCORE_DIVISOR = 10;
    private static final int DAMAGE_SCORE_DIVISOR = 100;

    private final String playerName;
    private final EnemyDefinition enemy;
    private final Random random;

    private final int initialPlayerHealth;
    private final int playerDamage;
    private final int initialEnemyHealth;
    private final int enemyDamage;

    private int playerHealth;
    private int enemyHealth;
    private int turnCount = 1;
    private String gameMessage;

    public CombatManager(GameSession session) {
        this(session, new Random());
    }

    CombatManager(GameSession session, Random random) {
        Objects.requireNonNull(session, "session");
        this.random = Objects.requireNonNull(random, "random");

        this.playerName = session.getPlayerName();
        this.enemy = session.getCurrentEnemy();

        int score = session.getScore();
        this.initialPlayerHealth = BASE_PLAYER_HEALTH + (score + SCORE_OFFSET) / HEALTH_SCORE_DIVISOR;
        this.playerDamage = BASE_PLAYER_DAMAGE + (score + SCORE_OFFSET) / DAMAGE_SCORE_DIVISOR;

        this.initialEnemyHealth = enemy.health();
        this.enemyDamage = enemy.attack();

        this.playerHealth = initialPlayerHealth;
        this.enemyHealth = initialEnemyHealth;

        this.gameMessage = "A wild " + enemy.name() + " appears! Choose an action.";
    }

    /**
     * Plays one turn: the enemy picks a random action and the loser of the exchange takes damage.
     * @return the outcome of the turn from the player's point of view
     */
    public TurnResult playTurn(ActionType playerAction) {
        Objects.requireNonNull(playerAction, "playerAction");
        if (isOver()) {
            throw new IllegalStateException("The combat is already over");
        }

        ActionType enemyAction = randomEnemyAction();
        TurnResult result = resolve(playerAction, enemyAction);

        StringBuilder message = new StringBuilder()
                .append(playerName).append(" uses ").append(playerAction.getDisplayName()).append('\n')
                .append(enemy.name()).append(" uses ").append(enemyAction.getDisplayName()).append('\n');

        switch (result) {
            case WIN -> {
                enemyHealth = Math.max(0, enemyHealth - playerDamage);
                message.append(enemy.name()).append(" loses ").append(playerDamage).append(" HP");
            }
            case LOSE -> {
                playerHealth = Math.max(0, playerHealth - enemyDamage);
                message.append(playerName).append(" loses ").append(enemyDamage).append(" HP");
            }
            case DRAW -> message.append("It's a draw");
        }

        gameMessage = message.toString();
        turnCount++;
        return result;
    }

    @Override
    public boolean isOver() {
        return playerHealth == 0 || enemyHealth == 0;
    }

    public boolean isPlayerWinner() {
        return enemyHealth == 0 && playerHealth > 0;
    }

    public int getReward() {
        return initialEnemyHealth;
    }

    private static TurnResult resolve(ActionType playerAction, ActionType enemyAction) {
        if (playerAction == enemyAction) return TurnResult.DRAW;
        return playerAction.beats(enemyAction) ? TurnResult.WIN : TurnResult.LOSE;
    }

    private ActionType randomEnemyAction() {
        ActionType[] actions = ActionType.values();
        return actions[random.nextInt(actions.length)];
    }

    // --- CombatState ---

    @Override public String getPlayerName() { return playerName; }
    @Override public String getEnemyName() { return enemy.name(); }
    @Override public EnemyDefinition getEnemy() { return enemy; }
    @Override public int getPlayerHealth() { return playerHealth; }
    @Override public int getInitialPlayerHealth() { return initialPlayerHealth; }
    @Override public int getEnemyHealth() { return enemyHealth; }
    @Override public int getInitialEnemyHealth() { return initialEnemyHealth; }
    @Override public String getGameMessage() { return gameMessage; }
    @Override public int getTurnCount() { return turnCount; }
}