package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui;

import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.logic.TokimonCardButtonHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides a user interface for displaying detailed information about a Tokimon card.
 * <p>
 * This class opens a modal window showing details of a Tokimon card, including its image and attributes.
 * If the Tokimon ID is invalid, it displays an error message.
 * </p>
 */
public class DisplaySubwindow {
    TokimonCardButtonHandler buttonHandler = new TokimonCardButtonHandler();

    /**
     * Displays a modal window with details of the Tokimon card identified by the given ID.
     * <p>
     * Retrieves the Tokimon card information from the API and shows it in a pop-up window.
     * If the Tokimon ID is invalid, an error message is displayed instead.
     * </p>
     *
     * @param tokimonId the ID of the Tokimon card to display
     * @throws JSONException if there is an error processing the JSON data
     */
    public void displayTokimonCard (String tokimonId) throws JSONException {
        Stage stage = new Stage();

        // Make the API call and pass it to the JSON Object
        JSONObject tokimonObject = buttonHandler.getTokimonCard(tokimonId);
        if (tokimonObject != null) {
            // Set up the stage
            stage.setTitle("Tokimon Card");
            stage.setResizable(false);

            // Extract tokimon information
            int id = tokimonObject.getInt("id");
            String name = tokimonObject.getString("name");
            String type = tokimonObject.getString("type");
            int rarity = tokimonObject.getInt("rarity");
            int healthPoint = tokimonObject.getInt("healthPoint");
            String picturePath = tokimonObject.getString("picture");

            // Create labels for the information
            Label idLabel = new Label("Tokimon ID: " + id);
            Label nameLabel = new Label("Name: " + name);
            Label typeLabel = new Label("Type: " + type);
            Label rarityLabel = new Label("Rarity: " + rarity);
            Label healthPointLabel = new Label("Health Point: " + healthPoint);
            VBox informationBox = new VBox(idLabel, nameLabel, typeLabel, rarityLabel, healthPointLabel);
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

            VBox tokimonCard = new VBox(imageContainer, informationBox);
            tokimonCard.getStyleClass().add("tokimon-card-pop-up");
            Scene scene = new Scene(tokimonCard);
            scene.getStylesheets().add(getClass().getResource("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } else {
            // Set up stage
            stage.setTitle("ERROR");
            stage.setResizable(false);

            // Add error label
            Label errorLabel = new Label("Invalid tokimon ID, the Tokimon ID: " + tokimonId + " does not exist.");

            // Add closing button
            Button closeButton = new Button("Close");
            closeButton.getStyleClass().add("tokimon-button");
            closeButton.setOnAction(event -> stage.close());

            VBox errorBox = new VBox(errorLabel, closeButton);
            errorBox.setSpacing(20);
            errorBox.setAlignment(Pos.CENTER);
            errorBox.getStyleClass().add("error-box");
            Scene scene = new Scene(errorBox);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/css/styles.css").toExternalForm());
            stage.show();
        }


    }
}
