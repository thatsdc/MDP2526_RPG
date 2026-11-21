package it.unicam.cs.mpgc.rpg129072.enums;

public enum ActionType {
    ATTACK("Attack"),
    DEFENSE("Defense"),
    CUNNING("Cunning");

    private final String displayName;

    ActionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Rock-paper-scissors rule: attack beats cunning, defense beats attack, cunning beats defense.
     */
    public boolean beats(ActionType other) {
        return switch (this) {
            case ATTACK -> other == CUNNING;
            case DEFENSE -> other == ATTACK;
            case CUNNING -> other == DEFENSE;
        };
    }
}