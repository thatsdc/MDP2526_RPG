package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapCharacter;
import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapEnemy;

public interface MapState {
    int getColumns();
    int getRows();
    int getTileSize();
    boolean isWheatCrushed(int x, int y);

    MapCharacter getPlayer();
    MapEnemy getVisibleEnemy();

    String getPlayerName();
    int getPlayerScore();
}