package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui;

import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ClientView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides a user interface component for displaying a Tokimon card.
 * <p>
 * This class generates a visual representation of a Tokimon card with its details and provides buttons to view, edit, or delete the card.
 * </p>
 */
public class TokimonCardUi {
    private VBox tokimonCard;
    int id;
    String name;
    String type;
    int rarity;
    int healthPoint;
    String picturePath;

    /**
     * Creates a Tokimon card UI component.
     * <p>
     * This method constructs a card displaying the Tokimon's ID, name, type, rarity, health points, and picture.
     * It also includes buttons for viewing, editing, and deleting the Tokimon card.
     * </p>
     *
     * @param tokimonObject the JSON object containing Tokimon card details
     * @param clientView the client view for refreshing the card list
     * @param primaryStage the primary stage for displaying dialogs
     * @return a VBox containing the Tokimon card UI
     * @throws JSONException if there is an error processing the JSON data
     */
    public VBox createCard(JSONObject tokimonObject, ClientView clientView, Stage primaryStage) throws JSONException {
        // Extract tokimon information
        id = tokimonObject.getInt("id");
        name = tokimonObject.getString("name");
        type = tokimonObject.getString("type");
        rarity = tokimonObject.getInt("rarity");
        healthPoint = tokimonObject.getInt("healthPoint");
        picturePath = tokimonObject.getString("picture");

        // Create labels for the information
        Label idLabel = new Label("Tokimon ID: " + id);
        idLabel.getStyleClass().add("tokimon-card-label");
        VBox informationBox = new VBox(idLabel);
        informationBox.setAlignment(Pos.CENTER);
        informationBox.setPadding(new Insets(0, 12, 0, 12));
        informationBox.setSpacing(5);

        // Create image storage
        Image image = new Image(getClass().getResourceAsStream(picturePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        // Center the image within a StackPane
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPadding(new Insets(15, 15, 15, 15));

        // Create the button that allows user to delete and modify the tokimon
        HBox buttonContainer = new HBox();
        buttonContainer.getStyleClass().add("tokimon-button-container");
        buttonContainer.setAlignment(Pos.CENTER);

        // View button
        Button viewButton = new Button("View");
        viewButton.getStyleClass().add("tokimon-button");
        viewButton.setOnAction(event -> {
            try {
                new DisplaySubwindow().displayTokimonCard(String.valueOf(id));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        // Edit button
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> new UpdateForm(this, id, name, type, rarity, healthPoint, picturePath).displayEditForm());
        editButton.getStyleClass().add("tokimon-button");

        // Delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> new DeleteForm(clientView, primaryStage, id).deleteTokimon());
        deleteButton.getStyleClass().add("tokimon-button");

        buttonContainer.getChildren().addAll(viewButton, editButton, deleteButton);

        // Store the labels in a vbox acting as a card
        tokimonCard = new VBox(imageContainer, informationBox, buttonContainer);
        tokimonCard.getStyleClass().add("tokimon-card");

        return tokimonCard;
    }

    /**
     * Updates the Tokimon card UI component with new data.
     * <p>
     * This method updates the existing card with new information, including the name, type, rarity, health points, and picture.
     * </p>
     *
     * @param updateTokimonObject the JSON object containing the updated Tokimon card details
     * @throws JSONException if there is an error processing the JSON data
     */
    public void updateCard(JSONObject updateTokimonObject) throws JSONException {
        // Get the info of the tokimonObject
        name = updateTokimonObject.getString("name");
        type = updateTokimonObject.getString("type");
        rarity = Integer.parseInt(updateTokimonObject.getString("rarity"));
        healthPoint = Integer.parseInt(updateTokimonObject.getString("healthPoint"));
        picturePath = updateTokimonObject.getString("picture");

        // Update the labels and image
        VBox informationBox = (VBox) tokimonCard.getChildren().get(1);
        ((Label) informationBox.getChildren().get(0)).setText("Tokimon ID: " + id);
        ((Label) informationBox.getChildren().get(1)).setText("Name: " + name);
        ((Label) informationBox.getChildren().get(2)).setText("Type: " + type);
        ((Label) informationBox.getChildren().get(3)).setText("Rarity: " + rarity);
        ((Label) informationBox.getChildren().get(4)).setText("Health Point: " + healthPoint);

        Image image = new Image(getClass().getResourceAsStream(picturePath));
        ((ImageView) ((StackPane) tokimonCard.getChildren().get(0)).getChildren().get(0)).setImage(image);
    }
}
