package it.unicam.cs.mpgc.rpg129072.persistence;

public interface Persistence<T> {
    void save(T data);
    T load();
}