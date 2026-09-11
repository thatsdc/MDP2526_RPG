package it.unicam.cs.mpgc.rpg129072.components.MapCharacter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

public class MapCharacter {

    private double positionX;
    private double positionY;
    private final double speed = 8;

    // Sprite sheet properties
    private final Image spriteSheet;
    private final double frameWidth;
    private final double frameHeight;
    private final double scaleMultiplier;
    private boolean isFacingLeft = false;

    // Animation state
    private int currentFrame = 0;
    private final int totalFrames;

    public MapCharacter(String imagePath, double startX, double startY, double frameWidth, double frameHeight, int totalFrames, double scaleMultiplier) {
        this.positionX = startX;
        this.positionY = startY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.scaleMultiplier = scaleMultiplier;

        // Load the sprite sheet image once
        this.spriteSheet = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
    }

    /**
     * Updates the character's position and advances the animation frame.
     */
    public void move(double deltaX, double deltaY, double mapWidth, double mapHeight) {
        this.positionX = Math.clamp(this.positionX + deltaX, 0, mapWidth - this.frameWidth);
        this.positionY = Math.clamp(this.positionY + deltaY, 0, mapHeight - this.frameHeight);

        if(deltaY == 0){
            isFacingLeft = deltaX < 0;
        }

        // Cycle through the frames
        this.currentFrame = (this.currentFrame + 1) % this.totalFrames;
    }

    /**
     * Resets the animation to the standing/idle frame.
     */
    public void stopAnimation() {
        this.currentFrame = 0;
    }

    /**
     * Extracts the correct frame from the sprite sheet and draws it on the map.
     */
    public void render(GraphicsContext gc) {
        // Calculate the exact pixel coordinates of the frame on the sprite sheet
        double sourceX = this.currentFrame * this.frameWidth;
        double sourceY = 0;

        double actualWidth = this.frameWidth * this.scaleMultiplier;
        double actualHeight = this.frameHeight * this.scaleMultiplier;

        if (this.isFacingLeft) {
            gc.drawImage(
                this.spriteSheet,
                sourceX, sourceY, this.frameWidth, this.frameHeight,
                this.positionX + actualWidth, this.positionY, -actualWidth, actualHeight
            );
        } else {
            gc.drawImage(
                this.spriteSheet,
                sourceX, sourceY, this.frameWidth, this.frameHeight,
                this.positionX, this.positionY, actualWidth, actualHeight
            );
        }
    }

    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    public double getSpeed() { return speed; }
    public double getRenderedWidth() { return frameWidth * scaleMultiplier; }
    public double getRenderedHeight() { return frameHeight * scaleMultiplier; }
}