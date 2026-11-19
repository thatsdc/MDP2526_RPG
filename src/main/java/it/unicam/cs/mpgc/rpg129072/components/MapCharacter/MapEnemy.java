package it.unicam.cs.mpgc.rpg129072.components.MapCharacter;

public class MapEnemy extends MapCharacter {

    private String enemyId;

    public MapEnemy(String enemyId, double startX, double startY, double frameWidth, double frameHeight, int totalFrames, double scaleMultiplier) {
        super(MapEnemy.getEnemyImagePath(enemyId), startX, startY, frameWidth, frameHeight, totalFrames, scaleMultiplier);
        this.enemyId = enemyId;
    }

    private static String getEnemyImagePath(String enemyId){
        return "/characters/map/" + enemyId + ".png";
    }

    public String getEnemyId() {
        return enemyId;
    }
}
