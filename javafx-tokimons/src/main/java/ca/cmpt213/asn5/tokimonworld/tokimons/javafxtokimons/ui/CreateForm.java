package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui;

import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ClientView;
import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.logic.TokimonCardButtonHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Provides a user interface for creating new Tokimon cards.
 * <p>
 * This class handles the creation of a form where users can input details for a new Tokimon card
 * and submit this information to be added to the collection.
 * </p>
 */
public class CreateForm {
    TokimonCardButtonHandler buttonHandler = new TokimonCardButtonHandler();

    /**
     * Displays a form for creating a new Tokimon card.
     * <p>
     * Opens a modal window with input fields for the Tokimon card's details and buttons to submit or cancel.
     * </p>
     *
     * @param clientView the main client view that displays Tokimon cards
     * @param stage      the main stage to which the modal stage will be associated
     */

    public void createTokimonCard(ClientView clientView, Stage stage) {
        // Set up the stage
        Stage createStage = new Stage();
        createStage.initModality(Modality.APPLICATION_MODAL);
        createStage.setTitle("Edit Tokimon Card");
        createStage.setResizable(false);

        // Create the form container
        VBox formContainer = new VBox();
        formContainer.getStyleClass().add("tokimon-form-container");

        // Create the fields
        TextField nameField = new TextField();
        nameField.setPromptText("Tokimon's name");

        ComboBox<String> typeField = new ComboBox<>();
        typeField.getItems().addAll("Water", "Fire", "Grass", "Electric", "Psychic", "Earth", "Ice");
        typeField.setPromptText("Select type");

        Spinner<Integer> rarityField = new Spinner<>(1, 10, 1);
        rarityField.setEditable(true);

        Spinner<Integer> healthPointField = new Spinner<>(1, 2000, 50, 50);
        healthPointField.setEditable(true);

        TextField picturePathField = new TextField();
        picturePathField.setPromptText("Tokimon's picture");

        // Create the submit button
        Button submitButton = new Button("Add");
        submitButton.getStyleClass().add("tokimon-button");
        submitButton.setOnAction(event -> {
            // Retrieve information
            String name = nameField.getText();
            String type = typeField.getValue();
            String rarity = String.valueOf(rarityField.getValue());
            String healthPoint = String.valueOf(healthPointField.getValue());
            String picture = picturePathField.getText();

            if (buttonHandler.createTokimonCard(name, type, rarity, healthPoint, picture) == 201) {
                clientView.displayTokimonCards(stage);
                createStage.close();
            }

        });

        // Create the cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("tokimon-button");
        cancelButton.setOnAction(event -> createStage.close());

        // Add the label and the fields
        HBox nameBox = new HBox(10, new Label("Name:"), nameField);
        HBox typeBox = new HBox(10, new Label("Type:"), typeField);
        HBox rarityBox = new HBox(10, new Label("Rarity:"), rarityField);
        HBox healthPointBox = new HBox(10, new Label("Health Point:"), healthPointField);
        HBox picturePathBox = new HBox(10, new Label("Picture:"), picturePathField);

        // Add the button
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(submitButton, cancelButton);
        buttonContainer.getStyleClass().add("tokimon-button-container");

        // Add the fields and button to the form container
        formContainer.getChildren().addAll(nameBox, typeBox, rarityBox, healthPointBox, picturePathBox, buttonContainer);

        Scene createScene = new Scene(formContainer);
        createScene.getStylesheets().add(getClass().getResource("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/css/styles.css").toExternalForm());
        createStage.setScene(createScene);
        createStage.show();
    }
}
