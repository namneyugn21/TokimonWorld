package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons;

import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.logic.TokimonCardButtonHandler;
import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui.CreateForm;
import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui.DisplaySubwindow;
import ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.ui.TokimonCardUi;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The main class for the JavaFX client application that displays and manages Tokimon cards.
 * <p>
 * This class serves as the entry point for the JavaFX application.
 * It provides the user interface for viewing, searching, and adding Tokimon cards.
 * </p>
 */
public class ClientView extends Application {
    GridPane collectionDisplay;

    /**
     * Initializes the primary stage of the JavaFX application.
     * <p>
     * Sets up the main UI components including the title, search bar, add button,
     * and the display area for Tokimon cards. Configures the layout and applies
     * the background image and stylesheet.
     * </p>
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tokimons Collection");
        primaryStage.setResizable(true);  // Allow resizing

        // Create label
        Label collectionTitle = new Label("Tokimons Collection");
        collectionTitle.getStyleClass().add("collection-title");

        // Create add tokimon button
        Button createTokimonCard = new Button("Add Tokimon Card");
        createTokimonCard.getStyleClass().add("tokimon-add-button");
        CreateForm createForm = new CreateForm();
        createTokimonCard.setOnAction(e -> createForm.createTokimonCard(this, primaryStage));

        // Create search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Enter a Tokimon's ID to search for...");
        searchField.getStyleClass().add("search-field");
        Button findButton = new Button("Search");
        findButton.getStyleClass().add("tokimon-button");
        DisplaySubwindow displaySubwindow = new DisplaySubwindow();
        findButton.setOnAction(e -> {
            try {
                displaySubwindow.displayTokimonCard(searchField.getText());
            } catch (JSONException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        });

        // Add the search field and button to an HBox
        HBox searchBox = new HBox(10); // 10 is the spacing between elements
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchField, findButton);

        // Combine the search bar and add button together
        VBox utilitiesBox = new VBox(searchBox, createTokimonCard);
        utilitiesBox.setAlignment(Pos.CENTER);
        utilitiesBox.setSpacing(20);
        utilitiesBox.setPadding(new Insets(0, 20, 20, 20));

        // Create collection grid
        collectionDisplay = new GridPane();
        collectionDisplay.getStyleClass().add("collection-display");

        // Add the grid to scroll pane
        ScrollPane scrollPane = new ScrollPane(collectionDisplay);
        scrollPane.getStyleClass().add("collection-scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        displayTokimonCards(primaryStage);

        // Load the background image
        Image image = new Image(getClass().getResourceAsStream("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/images/background.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        Background background = new Background(backgroundImage);

        VBox mainLayout = new VBox(collectionTitle, utilitiesBox, scrollPane);
        mainLayout.getStyleClass().add("main-layout");
        mainLayout.setBackground(background);

        Scene mainScene = new Scene(mainLayout, 1180, 800);
        mainScene.getStylesheets().add(getClass().getResource("/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/css/styles.css").toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Retrieves and displays Tokimon cards in the GridPane.
     * <p>
     * This method clears the existing cards from the GridPane, retrieves the list of Tokimon cards,
     * and then creates and adds UI elements for each card to the GridPane.
     * </p>
     *
     * @param primaryStage the primary stage for this application
     */
    public void displayTokimonCards(Stage primaryStage) {
        collectionDisplay.getChildren().clear();

        try {
            TokimonCardButtonHandler buttonHandler = new TokimonCardButtonHandler();
            JSONArray jsonArray = buttonHandler.getAllTokimonCards();

            // Create the GridPane element with the counted tokimons
            for (int i = 0; i < jsonArray.length(); i++) {
                TokimonCardUi cardUi = new TokimonCardUi();
                JSONObject tokimonObject = jsonArray.getJSONObject(i);
                VBox tokimonCard = cardUi.createCard(tokimonObject, this, primaryStage);
                collectionDisplay.add(tokimonCard, i % 4, i / 4);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}