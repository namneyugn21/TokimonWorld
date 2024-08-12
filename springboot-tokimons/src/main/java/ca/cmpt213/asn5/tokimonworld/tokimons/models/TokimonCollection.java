package ca.cmpt213.asn5.tokimonworld.tokimons.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of TokimonCard objects stored in a JSON file.
 * <p>
 * This class provides functionality to load, save, and manipulate TokimonCard data
 * from a specified file. It supports CRUD operations including adding, retrieving,
 * updating, and removing TokimonCard objects from the collection.
 * </p>
 */
@Component
public class TokimonCollection {
    private final String filePath = "/Users/namnguyen02/Documents/college/junior/CMPT213/Assignment 5/TokimonWorld/springboot-tokimons/src/main/resources/static/data/tokimoncards.json";
    private List<TokimonCard> collection;
    private ObjectMapper objectMapper;

    public TokimonCollection() {
        collection = new ArrayList<>();
        objectMapper = new ObjectMapper();
        loadCollection();
    }

    // Load existing cards collection from json file
    private void loadCollection() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.length() != 0) {
                    collection = objectMapper.readValue(file, new TypeReference<>() {});
                }
            } else {
                System.out.println("ERROR: Cannot locate the file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save collection to json file
    public void saveCollection() {
        try {
            objectMapper.writeValue(new File(filePath), collection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Use to get the list of all Tokimon cards
    public List<TokimonCard> getCollection() {
        return collection;
    }

    // Use to add a new Tokimon card into the collection
    public void addTokimonCard(TokimonCard card) {
        collection.add(card);
        saveCollection();
    }

    // Use to find the Tokimon card with a given ID
    public TokimonCard getTokimonCard(int cardId) {
        for (TokimonCard card : collection) {
            if (card.getId() == cardId) {
                return card;
            }
        }
        return null;
    }

    // Use to delete the Tokimon card with a given ID
    public boolean removeTokimonCard(int cardId) {
        for (TokimonCard card : collection) {
            if (card.getId() == cardId) {
                collection.remove(card);
                saveCollection();
                return true;
            }
        }
        return false;
    }

    // Use to update the Tokimon card with a given ID
    public boolean updateTokimonCard(int cardId, TokimonCard newCard) {
        for (TokimonCard card : collection) {
            if (card.getId() == cardId) {
                card.updateTokimonCard(newCard);
                saveCollection();
                return true;
            }
        }
        return false;
    }
}
