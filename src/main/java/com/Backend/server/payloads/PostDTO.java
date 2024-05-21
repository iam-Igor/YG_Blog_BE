package com.Backend.server.payloads;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record PostDTO(
        @NotEmpty(message = "Il campo title non può essere vuoto")
        String title,
        @NotEmpty(message = "Il campo content non può essere vuoto")
        String content,
        @NotEmpty(message = "Il campo date non può essere vuoto")
        LocalDateTime date

) {
}
