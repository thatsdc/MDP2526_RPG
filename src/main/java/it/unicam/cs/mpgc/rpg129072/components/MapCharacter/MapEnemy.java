package it.unicam.cs.mpgc.rpg129072.components.MapCharacter;

import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;

public class MapEnemy extends MapCharacter {
    public MapEnemy(EnemyType enemyType, double startX, double startY, double frameWidth, double frameHeight, int totalFrames, double scaleMultiplier) {
        super(MapEnemy.getEnemyImagePath(enemyType), startX, startY, frameWidth, frameHeight, totalFrames, scaleMultiplier);
    }

    private static String getEnemyImagePath(EnemyType enemyType){
        return switch (enemyType){
            case MERFOLK -> "/merfolk.png";
        };
    }
}
