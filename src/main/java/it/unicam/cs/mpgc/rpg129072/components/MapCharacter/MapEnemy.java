package it.unicam.cs.mpgc.rpg129072.components.MapCharacter;

import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;

public class MapEnemy extends MapCharacter {

    private EnemyType enemyType;

    public MapEnemy(EnemyType enemyType, double startX, double startY, double frameWidth, double frameHeight, int totalFrames, double scaleMultiplier) {
        super(MapEnemy.getEnemyImagePath(enemyType), startX, startY, frameWidth, frameHeight, totalFrames, scaleMultiplier);
        this.enemyType = enemyType;
    }

    private static String getEnemyImagePath(EnemyType enemyType){
        return "/characters/map/" + enemyType.name().toLowerCase() + ".png";
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }
}
