package com.ms.notificacao.domain.enums;

import java.util.Arrays;

public enum StatusNotificacaoEnum {

    PENDENTE("PENDENTE"),
    ENVIADA("ENVIADA"),
    FALHA("FALHA"),
    CANCELADA("CANCELADA");

    private final String descricao;

    StatusNotificacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusNotificacaoEnum fromString(String status) {
        return Arrays.stream(values())
                .filter(statusNotificacao -> statusNotificacao.descricao.equalsIgnoreCase(status)
                        || statusNotificacao.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum status encontrado : " + status));
    }
}
