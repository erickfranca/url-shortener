package com.lab.shortener.service;

import com.lab.shortener.model.Link;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LinkService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    private final Map<String, Link> links = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    public Collection<Link> findAll() {
        return links.values();
    }

    public Optional<Link> findByCode(String code) {
        return Optional.ofNullable(links.get(code));
    }

    public Link create(String originalUrl) {
        String code = generateUniqueCode();
        Link link = new Link(code, originalUrl);
        links.put(code, link);
        return link;
    }

    private String generateUniqueCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(CODE_LENGTH);
            for (int i = 0; i < CODE_LENGTH; i++) {
                sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            }
            code = sb.toString();
        } while (links.containsKey(code));
        return code;
    }
}
