package com.ms.notificacao.infraestrutura.clients.brevo.dto;

import java.util.List;

public record EmailRequestDto(
        Sender sender,
        List<Recipient> to,
        String subject,
        String textContent) { }
