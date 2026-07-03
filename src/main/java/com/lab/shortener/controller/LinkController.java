package com.lab.shortener.controller;

import com.lab.shortener.model.Link;
import com.lab.shortener.service.LinkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

@RestController
public class LinkController {

    private final LinkService linkService;

    @Value("${app.environment:local}")
    private String appEnvironment;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("environment", appEnvironment);
    }

    @GetMapping("/links")
    public Collection<Link> findAll() {
        return linkService.findAll();
    }

    @PostMapping("/links")
    public ResponseEntity<Link> create(@Valid @RequestBody LinkRequest request) {
        Link created = linkService.create(request.originalUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        return linkService.findByCode(code)
                .map(link -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(link.getOriginalUrl()))
                        .<Void>build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public record LinkRequest(@NotBlank(message = "originalUrl é obrigatório") String originalUrl) {
    }
}
