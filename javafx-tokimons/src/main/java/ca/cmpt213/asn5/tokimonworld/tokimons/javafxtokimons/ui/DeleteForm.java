package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui;

import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ClientView;
import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.logic.TokimonCardButtonHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Provides a user interface for deleting a Tokimon card.
 * <p>
 * This class displays a confirmation dialog that asks the user to confirm the deletion of a Tokimon card.
 * </p>
 */
public class DeleteForm {
    private final int id;
    private final ClientView clientView;
    private final Stage primaryStage;
    private final TokimonCardButtonHandler buttonHandler;

    /**
     * Constructs a DeleteForm for a specific Tokimon card.
     *
     * @param clientView   the main client view that displays Tokimon cards
     * @param primaryStage the main stage to which the modal stage will be associated
     * @param id           the ID of the Tokimon card to be deleted
     */
    public DeleteForm(ClientView clientView, Stage primaryStage, int id) {
        this.id = id;
        this.clientView = clientView;
        this.primaryStage = primaryStage;
        this.buttonHandler = new TokimonCardButtonHandler();
    }

    /**
     * Displays the delete confirmation dialog.
     * <p>
     * Opens a modal window asking the user to confirm the deletion of the Tokimon card with the given ID.
     * The user can choose to either confirm the deletion or cancel the action.
     * </p>
     */
    public void deleteTokimon() {
        Stage deleteStage = new Stage();
        deleteStage.initModality(Modality.APPLICATION_MODAL);
        deleteStage.setTitle("Delete Tokimon Card");
        deleteStage.setResizable(false);

        // Create prompt
        Label prompt = new Label("Are you sure you want to delete this tokimon?");
        prompt.getStyleClass().add("deletion-prompt");

        // Create choices
        HBox choicesBox = new HBox();
        choicesBox.setAlignment(Pos.CENTER);
        choicesBox.setSpacing(10);
        choicesBox.getStyleClass().add("tokimon-button-container");

        // Create the buttons
        Button yesButton = new Button("Yes");
        yesButton.getStyleClass().add("tokimon-button");
        Button noButton = new Button("No");
        noButton.getStyleClass().add("tokimon-button");

        // Add event handlers for the buttons
        yesButton.setOnAction(e -> {
            if (buttonHandler.deleteTokimonCard(String.valueOf(id)) == 204) {
                clientView.displayTokimonCards(primaryStage);
            }
            deleteStage.close();
        });

        noButton.setOnAction(e -> deleteStage.close());

        // Add buttons to the choices box
        choicesBox.getChildren().addAll(yesButton, noButton);

        // Add the prompt and choices to the stage
        VBox deleteContainer = new VBox();
        deleteContainer.setPadding(new Insets(20, 20, 20, 20));
        deleteContainer.setSpacing(10);
        deleteContainer.setAlignment(Pos.CENTER);
        deleteContainer.getChildren().addAll(prompt, choicesBox);

        Scene deleteScene = new Scene(deleteContainer);
        deleteScene.getStylesheets().add(getClass().getResource("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/css/styles.css").toExternalForm());
        deleteStage.setScene(deleteScene);
        deleteStage.show();
    }
}
