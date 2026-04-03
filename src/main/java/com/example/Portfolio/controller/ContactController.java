package com.example.Portfolio.controller;

import com.example.Portfolio.entity.ContactMessage;
import com.example.Portfolio.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> submit(
            @Valid @RequestBody ContactMessage message) {

        Map<String, Object> response = new HashMap<>();

        try {
            service.sendContactEmail(message);

            response.put("success", true);
            response.put("message", "Message sent successfully! I'll get back to you soon.");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to send message. Please try again later.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }
}