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
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> submit(
            @Valid @RequestBody ContactMessage message) {

        service.save(message);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Message sent successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/messages")
    public ResponseEntity<Map<String, Object>> all() {
        Map<String, Object> response = new HashMap<>();
        response.put("messages", service.findAll());
        response.put("count", service.count());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }
}