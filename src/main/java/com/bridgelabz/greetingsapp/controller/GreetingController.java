package com.bridgelabz.greetingsapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bridgelabz.greetingsapp.service.GreetingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/greetings") // Defines the base path for all endpoints in this controller
public class GreetingController {

    private final GreetingService greetingService;

    /**
     * Constructor for GreetingController.
     * Spring automatically injects an instance of GreetingService.
     * @param greetingService The service layer for handling greeting business logic.
     */ 
    
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /**
     * Handles GET requests to /api/greetings.
     * - If no firstName/lastName params: Returns "Hello World".
     * - With firstName/lastName params: Returns a personalized greeting.
     * @param firstName Optional first name.
     * @param lastName Optional last name.
     * @return A ResponseEntity containing a map with the greeting message and HTTP status.
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> getGreeting(
            @RequestParam(required = false) String firstName, // Optional first name
            @RequestParam(required = false) String lastName) {  // Optional last name
        Map<String, String> response = new HashMap<>();
        // Calls the service to get the appropriate greeting
        String message = greetingService.getGreeting(firstName, lastName);
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles POST requests to /api/greetings.
     * Creates and saves a new greeting message to the database.
     * @param payload A map containing a "name" key to personalize the greeting.
     * @return A ResponseEntity containing details of the created greeting and HTTP status.
     */
    // UC4: Modified POST to save the greeting
    @PostMapping
    public ResponseEntity<Map<String, Object>> createGreeting(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        // Determine the greeting message; if name is null, use "Hello World" from service
        String greetingMessage = (name != null && !name.isEmpty()) ? "Hello " + name : greetingService.getHelloGreeting();

        // Save the greeting to the repository via the service
        Greeting savedGreeting = greetingService.saveGreeting(greetingMessage);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Greeting created and saved");
        response.put("id", savedGreeting.getId()); // Include the ID of the saved greeting
        response.put("content", savedGreeting.getMessage()); // Include the content of the saved greeting
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Handles GET requests to /api/greetings/{id}.
     * Retrieves a specific greeting by its ID from the database.
     * @param id The ID of the greeting to retrieve.
     * @return A ResponseEntity containing the greeting details or NOT_FOUND status.
     */
    // UC4: Get greeting by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getGreetingById(@PathVariable Long id) {
        // Retrieve the greeting from the service
        Greeting greeting = greetingService.getGreetingById(id);
        if (greeting == null) {
            // If greeting is not found, return 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", greeting.getId());
        response.put("message", greeting.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles GET requests to /api/greetings/all.
     * Retrieves all greetings stored in the database.
     * @return A ResponseEntity containing a list of all Greeting entities and HTTP status.
     */
    // UC4: Get all greetings
    @GetMapping("/all")
    public ResponseEntity<List<Greeting>> getAllGreetings() {
        // Retrieve all greetings from the service
        List<Greeting> greetings = greetingService.getAllGreetings();
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    /**
     * Handles PUT requests to /api/greetings/{id}.
     * Updates an existing greeting by its ID.
     * Note: This method currently only returns a message indicating an update.
     * Actual update logic in the service/repository would need to be implemented.
     * @param id The ID of the greeting to update.
     * @param payload A map containing the new "name" for the greeting.
     * @return A ResponseEntity with an update message and HTTP status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateGreeting(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String newName = payload.get("name");
        String greetingMessage = "Hello " + (newName != null && !newName.isEmpty() ? newName : "World");

        // TODO: Implement actual update logic in GreetingService and GreetingRepository
        // Example:
        // Greeting existingGreeting = greetingService.getGreetingById(id);
        // if (existingGreeting != null) {
        //     existingGreeting.setMessage(greetingMessage);
        //     greetingService.saveGreeting(existingGreeting.getMessage()); // Or a dedicated update method
        // } else {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Greeting ID " + id + " updated to: " + greetingMessage + " (update logic placeholder)");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles DELETE requests to /api/greetings/{id}.
     * Deletes a greeting by its ID from the database.
     * Note: This method currently only returns a NO_CONTENT status.
     * Actual delete logic in the service/repository would need to be implemented.
     * @param id The ID of the greeting to delete.
     * @return A ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        // TODO: Implement actual delete logic in GreetingService and GreetingRepository
        // Example:
        // greetingService.deleteGreeting(id); // You would add a delete method in your service

        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
    }
}
