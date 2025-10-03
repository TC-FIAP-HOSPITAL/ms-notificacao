package com.ms.notificacao.infraestrutura.dto;

import java.util.List;

public record EmailRequestDto(
        Sender sender,
        List<Recipient> to,
        String subject,
        String textContent // usando textContent ao invés de htmlContent
) {
    public record Sender(String name, String email) {}
    public record Recipient(String email, String name) {} // ordem igual à do JSON
}
