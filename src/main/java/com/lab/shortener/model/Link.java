package com.lab.shortener.model;

import jakarta.validation.constraints.NotBlank;

public class Link {

    private String code;

    @NotBlank(message = "originalUrl é obrigatório")
    private String originalUrl;

    public Link() {
    }

    public Link(String code, String originalUrl) {
        this.code = code;
        this.originalUrl = originalUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
