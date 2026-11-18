package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

public class IntroController extends Controller {

    private static final List<String> LORE_PAGES = List.of(
            "Long ago, a warrior was charged with protecting this land from unknown entities...",
            "For a thousand years there was peace and quiet, until a few months ago, "
                    + "when mysterious creatures appeared in the village's wheat fields..."
    );

    private final Runnable onIntroFinished;
    private int currentPage = 0;
    private boolean finished = false;

    public IntroController(Stage stage, Runnable onIntroFinished) {
        super(stage);
        this.onIntroFinished = Objects.requireNonNull(onIntroFinished, "onIntroFinished");
    }

    public String getCurrentPage() {
        return LORE_PAGES.get(currentPage);
    }

    /**
     * Moves to the next lore page, or ends the intro after the last one.
     * @return true if a new page is available, false if the intro has ended
     */
    public boolean advance() {
        if (currentPage < LORE_PAGES.size() - 1) {
            currentPage++;
            return true;
        }
        finish();
        return false;
    }

    private void finish() {
        // Guard against multiple inputs triggering navigation more than once
        if (finished) return;
        finished = true;
        onIntroFinished.run();
    }
}