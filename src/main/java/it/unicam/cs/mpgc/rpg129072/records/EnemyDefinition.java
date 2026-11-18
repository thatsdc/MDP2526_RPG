package it.unicam.cs.mpgc.rpg129072.records;

public record EnemyDefinition(String id, String name, int health, int attack) {
    public String spritePath() {
        return "/characters/combat/" + id + ".png";
    }
}