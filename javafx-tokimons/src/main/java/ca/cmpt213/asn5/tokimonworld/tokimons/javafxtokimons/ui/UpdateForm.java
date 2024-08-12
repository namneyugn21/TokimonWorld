package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui;

import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.logic.TokimonCardButtonHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides a user interface for updating a Tokimon card.
 * <p>
 * This class displays a form that allows the user to edit details of a Tokimon card,
 * including its name, type, rarity, health points, and image path. The form includes
 * buttons for saving the changes or canceling the operation.
 * </p>
 */
public class UpdateForm {
    private final TokimonCardButtonHandler buttonHandler;
    private final TokimonCardUi parentUi;
    private final int id;
    private final String name;
    private final String type;
    private final int rarity;
    private final int healthPoint;
    private final String picturePath;

    /**
     * Constructs an UpdateForm for editing a Tokimon card.
     *
     * @param parentUi the parent UI component that will be updated
     * @param id the ID of the Tokimon card to be updated
     * @param name the current name of the Tokimon card
     * @param type the current type of the Tokimon card
     * @param rarity the current rarity of the Tokimon card
     * @param healthPoint the current health points of the Tokimon card
     * @param picturePath the current image path of the Tokimon card
     */
    public UpdateForm(TokimonCardUi parentUi, int id, String name, String type, int rarity, int healthPoint, String picturePath) {
        this.parentUi = parentUi;
        this.id = id;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.healthPoint = healthPoint;
        this.picturePath = picturePath;
        this.buttonHandler = new TokimonCardButtonHandler();
    }

    /**
     * Displays the form for editing a Tokimon card.
     * <p>
     * This method creates and shows a modal stage with input fields pre-filled with the current
     * Tokimon card's details. It includes Save and Cancel buttons.
     * </p>
     */
    public void displayEditForm() {
        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.setTitle("Edit Tokimon Card");
        editStage.setResizable(false);

        try {
            String tokimonId = String.valueOf(id);
            // Define the current value of the tokimon
            TextField nameField = new TextField(name);
            nameField.getStyleClass().add("tokimon-form-field");

            ComboBox<String> typeComboBox = new ComboBox<>();
            typeComboBox.getItems().addAll("Water", "Fire", "Grass", "Electric", "Psychic", "Earth", "Ice");
            typeComboBox.setValue(type);

            TextField rarityField = new TextField(String.valueOf(rarity));
            rarityField.getStyleClass().add("tokimon-form-field");

            TextField healthPointField = new TextField(String.valueOf(healthPoint));
            healthPointField.getStyleClass().add("tokimon-form-field");

            TextField imageField = new TextField(picturePath);
            imageField.getStyleClass().add("tokimon-form-field");

            // Create HBox for each field with labels
            HBox nameBox = new HBox(new Label("Name: "), nameField);
            HBox typeBox = new HBox(new Label("Type: "), typeComboBox);
            HBox rarityBox = new HBox(new Label("Rarity: "), rarityField);
            HBox healthPointBox = new HBox(new Label("Health: "), healthPointField);
            HBox imageBox = new HBox(new Label("Image: "), imageField);

            // Center-align items in HBox
            nameBox.setAlignment(Pos.CENTER);
            typeBox.setAlignment(Pos.CENTER);
            rarityBox.setAlignment(Pos.CENTER);
            healthPointBox.setAlignment(Pos.CENTER);
            imageBox.setAlignment(Pos.CENTER);

            // Add padding between the fields
            nameBox.setPadding(new Insets(5));
            typeBox.setPadding(new Insets(5));
            rarityBox.setPadding(new Insets(5));
            healthPointBox.setPadding(new Insets(5));
            imageBox.setPadding(new Insets(5));

            // Add the save button
            HBox buttonContainer = new HBox();
            buttonContainer.getStyleClass().add("tokimon-button-container");
            buttonContainer.setAlignment(Pos.CENTER);
            Button saveButton = new Button("Save");
            saveButton.getStyleClass().add("tokimon-button");

            // Handle the save button action
            saveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String name = nameField.getText();
                    String type = typeComboBox.getValue();
                    int rarity = Integer.parseInt(rarityField.getText());
                    int healthPoint = Integer.parseInt(healthPointField.getText());
                    String picture = imageField.getText();

                    // Create a JSONObject that will be passed to the function handling API call
                    JSONObject updateTokimonObject = new JSONObject();
                    try {
                        updateTokimonObject.put("name", name);
                        updateTokimonObject.put("type", type);
                        updateTokimonObject.put("rarity", rarity);
                        updateTokimonObject.put("healthPoint", healthPoint);
                        updateTokimonObject.put("picture", picture);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Send the update request and get the response code
                    if (buttonHandler.editTokimonCard(tokimonId, updateTokimonObject) == 200) {
                        try {
                            parentUi.updateCard(updateTokimonObject);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    editStage.close();
                }
            });

            // Add the cancel button
            Button cancelButton = new Button("Cancel");
            cancelButton.getStyleClass().add("tokimon-button");
            cancelButton.setOnAction(e -> editStage.close());
            buttonContainer.getChildren().addAll(saveButton, cancelButton);

            // Add all HBox elements to a VBox
            VBox formContainer = new VBox(10); // 10 pixels spacing between fields
            formContainer.getStyleClass().add("tokimon-form-container");
            formContainer.setAlignment(Pos.CENTER);
            formContainer.setPadding(new Insets(15));
            formContainer.getChildren().addAll(nameBox, typeBox, rarityBox, healthPointBox, imageBox, buttonContainer);

            Scene formScene = new Scene(formContainer, 400, 400);
            formScene.getStylesheets().add(getClass().getResource("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/css/styles.css").toExternalForm());
            editStage.setScene(formScene);
            editStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}