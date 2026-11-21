package it.unicam.cs.mpgc.rpg129072.managers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg129072.records.EnemyDefinition;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class EnemyRegistry {

    private static final Type ENEMY_LIST_TYPE = new TypeToken<List<EnemyDefinition>>() {}.getType();

    private final Map<String, EnemyDefinition> enemies = new HashMap<>();

    public EnemyRegistry(String resourcePath) {
        InputStream in = getClass().getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IllegalStateException("Resources not found: " + resourcePath);
        }

        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            List<EnemyDefinition> list = new Gson().fromJson(reader, ENEMY_LIST_TYPE);

            if (list == null) {
                throw new IllegalStateException("Empty file: " + resourcePath);
            }

            for (EnemyDefinition e : list) {
                validate(e);
                if (enemies.putIfAbsent(e.id(), e) != null) {
                    throw new IllegalStateException("EnemyId duplicated: " + e.id());
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException("Impossible load enemies", ex);
        } catch (JsonParseException ex) {
            throw new IllegalStateException("Invalid JSON in " + resourcePath, ex);
        }

        if (enemies.isEmpty()) {
            throw new IllegalStateException("No enemy defined in " + resourcePath);
        }
    }

    private void validate(EnemyDefinition e) {
        if (e == null || e.id() == null || e.id().isBlank() || e.name() == null) {
            throw new IllegalStateException("Enemy with missing id or name: " + e);
        }
        if (e.health() <= 0 || e.attack() < 0) {
            throw new IllegalStateException("Stats not valid for: " + e.id());
        }
        if (getClass().getResource(e.spritePath()) == null) {
            throw new IllegalStateException("Missing sprite: " + e.spritePath());
        }
    }

    public EnemyDefinition get(String id) {
        EnemyDefinition e = enemies.get(id);
        if (e == null) {
            throw new IllegalArgumentException("Unkown enemy: " + id);
        }
        return e;
    }

    public Collection<EnemyDefinition> all() {
        return Collections.unmodifiableCollection(enemies.values());
    }

    public EnemyDefinition random(Random rng) {
        List<EnemyDefinition> list = new ArrayList<>(enemies.values());
        return list.get(rng.nextInt(list.size()));
    }
}