package ca.cmpt213.asn5.tokimonworld.tokimons.models;

/**
 * Represents a Tokimon card with various attributes.
 * <p>
 * A TokimonCard has an ID, name, type, rarity, health points, and a picture.
 * It also provides methods to get and set these attributes, as well as to update its details.
 * </p>
 */
public class TokimonCard {
    public enum Type {
        Water,
        Fire,
        Grass,
        Electric,
        Psychic,
        Earth,
        Ice
    }

    private int id;
    private String name;
    private Type type;
    private int rarity;
    private int healthPoint;
    private String picture;

    public TokimonCard() {}

    public TokimonCard(String name, Type type, int rarity, int healthPoint, String picture) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.healthPoint = healthPoint;
        this.picture = picture;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getRarity() {
        return rarity;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public String getPicture() {
        return picture;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void updateTokimonCard(TokimonCard card) {
        this.name = card.name;
        this.picture = card.picture;
        this.healthPoint = card.healthPoint;
        this.rarity = card.rarity;
        this.type = card.type;
    }
}
