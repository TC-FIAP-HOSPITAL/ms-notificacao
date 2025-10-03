package com.ms.notificacao.infraestrutura.dto;

import java.util.List;

public record EmailRequestDto(
        Sender sender,
        List<Recipient> to,
        String subject,
        String htmlContent,
        Recipient replyTo
) {
    public record Sender(String name, String email) {}
    public record Recipient(String name, String email) {}
}
