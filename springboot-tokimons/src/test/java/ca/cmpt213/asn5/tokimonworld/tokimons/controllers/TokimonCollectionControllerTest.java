package ca.cmpt213.asn5.tokimonworld.tokimons.controllers;

import ca.cmpt213.asn5.tokimonworld.tokimons.models.TokimonCard;
import ca.cmpt213.asn5.tokimonworld.tokimons.models.TokimonCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link TokimonCollectionController}.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class TokimonCollectionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokimonCollection tokimonCollection;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        TokimonCard card1 = new TokimonCard("Pikachu", TokimonCard.Type.Electric, 5, 120, "null");
        card1.setId(1);
        TokimonCard card2 = new TokimonCard("Bulbasaur", TokimonCard.Type.Grass, 8, 520, "null");
        card2.setId(2);
        TokimonCard card3 = new TokimonCard("Charizard", TokimonCard.Type.Fire, 5, 820, "null");
        card3.setId(3);

        // Setup mock behavior
        when(tokimonCollection.getCollection()).thenReturn(List.of(card1, card2, card3));
        when(tokimonCollection.getTokimonCard(1)).thenReturn(card1);
        when(tokimonCollection.getTokimonCard(2)).thenReturn(card2);
        when(tokimonCollection.getTokimonCard(3)).thenReturn(card3);
    }

    @Test
    void contextLoads() {
        assertThat(tokimonCollection).isNotNull();
    }

    @Test
    void testAddTokimonCard() throws Exception {
        TokimonCard newCard = new TokimonCard("Raichu", TokimonCard.Type.Electric, 10, 500, "null");
        String json = objectMapper.writeValueAsString(newCard);

        // Perform POST request
        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testAddTokimonCardWithInvalidRarity() throws Exception {
        TokimonCard invalidCard = new TokimonCard("InvalidCard", TokimonCard.Type.Fire, 11, 100, "null"); // Invalid rarity
        String json = objectMapper.writeValueAsString(invalidCard);

        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    @Test
    void testAddTokimonCardWithInvalidHealthPoint() throws Exception {
        TokimonCard invalidCard = new TokimonCard("InvalidCard", TokimonCard.Type.Electric, 5, 0, "null"); // Invalid health point
        String json = objectMapper.writeValueAsString(invalidCard);

        this.mockMvc.perform(
                        post("/api/tokimon/add")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    @Test
    void testGetAllTokimonCards() throws Exception {
        this.mockMvc.perform(
                        get("/api/tokimon/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"name\":\"Pikachu\",\"type\":\"Electric\",\"rarity\":5,\"healthPoint\":120,\"picture\":\"null\"}," +
                                "{\"id\":2,\"name\":\"Bulbasaur\",\"type\":\"Grass\",\"rarity\":8,\"healthPoint\":520,\"picture\":\"null\"}," +
                                "{\"id\":3,\"name\":\"Charizard\",\"type\":\"Fire\",\"rarity\":5,\"healthPoint\":820,\"picture\":\"null\"}]"));
    }

    @Test
    void testGetTokimonCardById() throws Exception {
        this.mockMvc.perform(
                        get("/api/tokimon/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":2,\"name\":\"Bulbasaur\",\"type\":\"Grass\",\"rarity\":8,\"healthPoint\":520,\"picture\":\"null\"}"));
    }

    @Test
    void testGetTokimonCardByNonExistingId() throws Exception {
        this.mockMvc.perform(
                        get("/api/tokimon/999") // ID that does not exist
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Expect 404 Not Found
    }

    @Test
    void testEditTokimonCard() throws Exception {
        TokimonCard updatedCard = new TokimonCard("Pikachu", TokimonCard.Type.Electric, 9, 520, "null");
        when(tokimonCollection.updateTokimonCard(anyInt(), any(TokimonCard.class))).thenReturn(true);

        mockMvc.perform(put("/api/tokimon/edit/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedCard)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(tokimonCollection, times(1)).updateTokimonCard(eq(1), any(TokimonCard.class));
    }

    @Test
    void testEditTokimonCardWithInvalidRarity() throws Exception {
        TokimonCard updateCard = new TokimonCard("Bulbasaur", TokimonCard.Type.Grass, 11, 1520, "null"); // Invalid rarity
        updateCard.setId(2);

        this.mockMvc.perform(
                        put("/api/tokimon/edit/2")
                                .content(objectMapper.writeValueAsString(updateCard))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    @Test
    void testEditTokimonCardWithInvalidHealthPoint() throws Exception {
        TokimonCard updateCard = new TokimonCard("Bulbasaur", TokimonCard.Type.Grass, 8, 0, "null"); // Invalid health point
        updateCard.setId(2);

        this.mockMvc.perform(
                        put("/api/tokimon/edit/2")
                                .content(objectMapper.writeValueAsString(updateCard))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Expect 400 Bad Request
    }

    @Test
    void testDeleteTokimonCardById() throws Exception {
        // Setup mock behaviour for delete
        doReturn(true).when(tokimonCollection).removeTokimonCard(1);

        // Perform the DELETE request
        this.mockMvc.perform(
                        delete("/api/tokimon/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify the card has been deleted
        verify(tokimonCollection, times(1)).removeTokimonCard(1);
    }

    @Test
    void testDeleteTokimonCardByNonExistingId() throws Exception {
        // Setup mock behavior for delete with non-existing card
        doReturn(false).when(tokimonCollection).removeTokimonCard(999);

        this.mockMvc.perform(
                        delete("/api/tokimon/999") // ID that does not exist
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Expect 404 Not Found

        verify(tokimonCollection, times(1)).removeTokimonCard(999);
    }
}
