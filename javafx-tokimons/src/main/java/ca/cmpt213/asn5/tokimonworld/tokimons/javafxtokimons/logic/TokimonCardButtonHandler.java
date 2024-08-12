package ca.cmpt213.asn5.tokimonworld.tokimons.javafxtokimons.logic;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Handles HTTP requests related to Tokimon cards.
 * <p>
 * This class provides methods to interact with the REST API for managing Tokimon cards,
 * including retrieving, creating, editing, and deleting cards.
 * </p>
 */
public class TokimonCardButtonHandler {
    /**
     * Retrieves all Tokimon cards from the server.
     * <p>
     * Sends a GET request to the server to fetch all Tokimon cards as a JSON array.
     * </p>
     *
     * @return a {@link JSONArray} containing all Tokimon cards, or {@code null} if an error occurs
     */
    public JSONArray getAllTokimonCards() {
        try {
            URI uri = new URI("http://localhost:8080/api/tokimon/all");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonOutput = reader.readLine(); // Convert the json output into String
            connection.disconnect();
            return new JSONArray(jsonOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Edits a Tokimon card on the server.
     * <p>
     * Sends a PUT request to the server to update an existing Tokimon card with the given ID.
     * </p>
     *
     * @param tokimonId     the ID of the Tokimon card to be edited
     * @param tokimonObject a {@link JSONObject} containing the updated Tokimon card information
     * @return the HTTP response code from the server
     */
    public int editTokimonCard(String tokimonId, JSONObject tokimonObject) {
        int responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        try {
            URI uri = new URI("http://localhost:8080/api/tokimon/edit/" + tokimonId);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Get the info of the tokimonObject
            String name = tokimonObject.getString("name");
            String type = tokimonObject.getString("type");
            String rarity = tokimonObject.getString("rarity");
            String healthPoint = tokimonObject.getString("healthPoint");
            String picture = tokimonObject.getString("picture");

            // Write JSON data to the output stream
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"rarity\":\"" + rarity + "\",\"healthPoint\":\"" + healthPoint + "\",\"picture\":\"" + picture + "\"" + "}");
            writer.flush();
            writer.close();

            responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    /**
     * Deletes a Tokimon card from the server.
     * <p>
     * Sends a DELETE request to the server to remove a Tokimon card with the given ID.
     * </p>
     *
     * @param tokimonId the ID of the Tokimon card to be deleted
     * @return the HTTP response code from the server
     */
    public int deleteTokimonCard(String tokimonId) {
        int responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        try {
            URI uri = new URI("http://localhost:8080/api/tokimon/" + tokimonId);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }


    /**
     * Creates a new Tokimon card on the server.
     * <p>
     * Sends a POST request to the server to add a new Tokimon card with the provided details.
     * </p>
     *
     * @param name        the name of the Tokimon card
     * @param type        the type of the Tokimon card
     * @param rarity      the rarity of the Tokimon card
     * @param healthPoint the health point of the Tokimon card
     * @param picture     the picture URL of the Tokimon card
     * @return the HTTP response code from the server
     */
    public int createTokimonCard(String name, String type, String rarity, String healthPoint, String picture) {
        int responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        try {
            URI uri = new URI("http://localhost:8080/api/tokimon/add");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Write JSON data to the output stream
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"rarity\":\"" + rarity + "\",\"healthPoint\":\"" + healthPoint + "\",\"picture\":\"" + picture + "\"" + "}");
            writer.flush();
            writer.close();

            responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    /**
     * Retrieves a specific Tokimon card from the server.
     * <p>
     * Sends a GET request to the server to fetch a Tokimon card with the given ID.
     * </p>
     *
     * @param tokimonId the ID of the Tokimon card to retrieve
     * @return a {@link JSONObject} representing the Tokimon card, or {@code null} if an error occurs
     */
    public JSONObject getTokimonCard(String tokimonId) {
        JSONObject tokimonObject = null;
        try {
            URI uri = new URI("http://localhost:8080/api/tokimon/" + tokimonId);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonOutput = reader.readLine(); // Convert the json output into String

            tokimonObject = new JSONObject(jsonOutput);
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("TokimonCardButtonHandler: getTokimonCard() failed. Tokimon ID could not be found.");
            System.out.println("Error: " + e.getMessage());
        }
        return tokimonObject;
    }
}
