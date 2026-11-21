package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.enums.ActionType;
import it.unicam.cs.mpgc.rpg129072.managers.CombatManager;
import it.unicam.cs.mpgc.rpg129072.session.GameSession;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class CombatController extends Controller {

    // Time the final turn message stays visible before leaving the combat
    private static final Duration END_OF_COMBAT_DELAY = Duration.seconds(1.5);

    private final CombatManager combatManager;
    private final GameSession session;
    private final Runnable onCombatWin;
    private final Runnable onCombatDefeat;

    public CombatController(Stage stage, CombatManager combatManager, GameSession session,
                            Runnable onCombatWin, Runnable onCombatDefeat) {
        super(stage);
        this.combatManager = Objects.requireNonNull(combatManager, "combatManager");
        this.session = Objects.requireNonNull(session, "session");
        this.onCombatWin = Objects.requireNonNull(onCombatWin, "onCombatWin");
        this.onCombatDefeat = Objects.requireNonNull(onCombatDefeat, "onCombatDefeat");
    }

    /**
     * Plays the player's action and checks whether the combat has ended.
     */
    public void playTurn(ActionType playerAction) {
        // Ignore input once the combat has ended
        if (combatManager.isOver()) return;

        combatManager.playTurn(playerAction);
        checkIfTheresWinner();
    }

    private void checkIfTheresWinner() {
        if (!combatManager.isOver()) return;

        session.endEncounter();

        if (combatManager.isPlayerWinner()) {
            // Initial enemy health is the score player earns
            session.addScore(combatManager.getReward());
            runAfterDelay(this::combatWin);
        } else {
            runAfterDelay(this::combatDefeat);
        }
    }

    private void combatWin() {
        onCombatWin.run();
    }

    private void combatDefeat() {
        onCombatDefeat.run();
    }

    private void runAfterDelay(Runnable action) {
        PauseTransition delay = new PauseTransition(END_OF_COMBAT_DELAY);
        delay.setOnFinished(event -> action.run());
        delay.play();
    }
}