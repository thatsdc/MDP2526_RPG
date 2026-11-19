package it.unicam.cs.mpgc.rpg129072.persistence.models;

import java.util.Arrays;
import java.util.Optional;

public enum Resolution {
    HD(1280, 720),
    FULL_HD(1920, 1080);

    private final int width;
    private final int height;

    Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public static Optional<Resolution> fromString(String value) {
        return Arrays.stream(values())
                .filter(r -> r.toString().equals(value))
                .findFirst();
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }
}