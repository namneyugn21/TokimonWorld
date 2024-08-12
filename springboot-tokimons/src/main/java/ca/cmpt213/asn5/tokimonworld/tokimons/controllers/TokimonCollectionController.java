package ca.cmpt213.asn5.tokimonworld.tokimons.controllers;

import ca.cmpt213.asn5.tokimonworld.tokimons.models.TokimonCard;
import ca.cmpt213.asn5.tokimonworld.tokimons.models.TokimonCollection;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * REST controller for managing TokimonCard entities in the TokimonCollection.
 * Provides endpoints to perform CRUD operations on TokimonCards.
 * <p>
 * Endpoints:
 * <ul>
 *     <li>GET /api/tokimon/all - Retrieves all TokimonCards</li>
 *     <li>GET /api/tokimon/{id} - Retrieves a TokimonCard by its ID</li>
 *     <li>POST /api/tokimon/add - Adds a new TokimonCard</li>
 *     <li>PUT /api/tokimon/edit/{id} - Updates an existing TokimonCard by its ID</li>
 *     <li>DELETE /api/tokimon/{id} - Deletes a TokimonCard by its ID</li>
 * </ul>
 * </p>
 * <p>
 * The class uses an {@link AtomicInteger} to manage the IDs for new TokimonCards and interacts with
 * the {@link TokimonCollection} service to perform data operations.
 * </p>
 * */
@RestController
@RequestMapping("/api/tokimon")
public class TokimonCollectionController {
    private AtomicInteger nextId = new AtomicInteger(1);
    private final TokimonCollection tokimonCollection;

    @Autowired
    public TokimonCollectionController(TokimonCollection tokimonCollection) {
        this.tokimonCollection = tokimonCollection;
    }

    @GetMapping("/all")
    public List<TokimonCard> getAllTokimonCards(HttpServletResponse response) {
        List<TokimonCard> tokimonCards = tokimonCollection.getCollection();
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("GET: /api/tokimon/all");
        return tokimonCards;
    }

    @GetMapping("/{id}")
    public TokimonCard getTokimonCard(@PathVariable int id, HttpServletResponse response) {
        TokimonCard tokimonCard = tokimonCollection.getTokimonCard(id);
        if (tokimonCard == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
            System.out.println("ERROR: Tokimon card " + id + " is not found");
            return null;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("GET: /api/tokimon/"+id);
        return tokimonCard;
    }

    @PostMapping("/add")
    public void addTokimonCard(@RequestBody TokimonCard card, HttpServletResponse response) {
        // Validate rarity
        if (card.getRarity() < 1 || card.getRarity() > 10) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            System.out.println("ERROR: Card rarity should be between 1 and 10");
            return;
        }
        // Validate health point
        if (card.getHealthPoint() < 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("ERROR: Card health point should be greater than 0");
            return;
        }
        card.setId(nextId.getAndIncrement());
        tokimonCollection.addTokimonCard(card);
        response.setStatus(HttpServletResponse.SC_CREATED);
        System.out.println("POST: /api/tokimon/add");
    }

    @PutMapping("/edit/{id}")
    public void editTokimonCard(@PathVariable int id, @RequestBody TokimonCard card, HttpServletResponse response) {
        // Validate rarity
        if (card.getRarity() < 1 || card.getRarity() > 10) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            System.out.println("ERROR: Card rarity should be between 1 and 10");
            return;
        }
        // Validate health point
        if (card.getHealthPoint() < 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("ERROR: Card health point should be greater than 0");
            return;
        }
        if (tokimonCollection.updateTokimonCard(id, card)) {
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("PUT: /api/tokimon/edit/"+id);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("ERROR: Tokimon card " + id + " is not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTokimonCard(@PathVariable int id, HttpServletResponse response) {
        if (tokimonCollection.removeTokimonCard(id)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            System.out.println("DELETE: /api/tokimon/"+id);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("ERROR: Tokimon card " + id + " is not found");
        }
    }
}
