package it.unicam.cs.mpgc.rpg129072.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONPersistence<T> implements Persistence<T> {

    static private final String STORAGE_PATH = "storage/";

    private final String filePath;
    private final Gson gson;
    private final Class<T> dataType;

    public JSONPersistence(String filePath, Class<T> dataType) {
        this.filePath = STORAGE_PATH + filePath;
        this.dataType = dataType;

        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void save(T data) {
        try {
            Path path = Paths.get(this.filePath);

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (Writer fileWriter = new FileWriter(this.filePath)) {
                this.gson.toJson(data, fileWriter);
            }

        } catch (IOException exception) {
            System.err.println("Error while saving data to JSON file: " + exception.getMessage());
        }
    }

    @Override
    public T load() {
        if (!Files.exists(Paths.get(this.filePath))) {
            System.out.println("No existing save file found at: " + this.filePath);
            return null;
        }

        try (Reader fileReader = new FileReader(this.filePath)) {
            return this.gson.fromJson(fileReader, this.dataType);
        } catch (IOException exception) {
            System.err.println("Error while loading data from JSON file: " + exception.getMessage());
            return null;
        }
    }
}