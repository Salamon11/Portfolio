package com.example.Portfolio.service;


import com.example.Portfolio.entity.ContactMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ContactService {

    private final List<ContactMessage> messages =
            Collections.synchronizedList(new ArrayList<>());

    public ContactMessage save(ContactMessage message) {
        messages.add(message);
        return message;
    }

    public List<ContactMessage> findAll() {
        return new ArrayList<>(messages);
    }

    public int count() {
        return messages.size();
    }
}
