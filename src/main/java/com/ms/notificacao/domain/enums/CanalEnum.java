package com.ms.notificacao.domain.enums;

import java.util.Arrays;

public enum CanalEnum {

    SMS("SMS"),
    EMAIL("E-mail"),
    WHATSAPP("WhatsApp"),
    PUSH("Push Notification");

    private final String descricao;

    CanalEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CanalEnum fromString(String texto) {
        return Arrays.stream(values())
                .filter(canal -> canal.descricao.equalsIgnoreCase(texto) || canal.name().equalsIgnoreCase(texto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum canal encontrado para o texto: " + texto));
    }
}