package com.ms.notificacao.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StatusNotificacaoEnumTest {

    @Test
    @DisplayName("Deve retornar a descrição correta para cada status")
    void getDescricao_DeveRetornarDescricaoCorreta() {
        assertEquals("PENDENTE", StatusNotificacaoEnum.PENDENTE.getDescricao());
        assertEquals("ENVIADA", StatusNotificacaoEnum.ENVIADA.getDescricao());
        assertEquals("FALHA", StatusNotificacaoEnum.FALHA.getDescricao());
        assertEquals("CANCELADA", StatusNotificacaoEnum.CANCELADA.getDescricao());
    }

    @ParameterizedTest
    @CsvSource({
            "PENDENTE, PENDENTE",
            "enviada, ENVIADA",
            "Falha, FALHA",
            "CANCELADA, CANCELADA"
    })
    @DisplayName("fromString() deve retornar o enum correto para strings válidas")
    void fromString_DeveRetornarEnum_QuandoStringEhValida(String input, StatusNotificacaoEnum expected) {
        StatusNotificacaoEnum result = StatusNotificacaoEnum.fromString(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"INEXISTENTE", "EM_PROCESSAMENTO", ""})
    @DisplayName("fromString() deve lançar exceção para strings inválidas")
    void fromString_DeveLancarExcecao_QuandoStatusInvalido(String statusInvalido) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            StatusNotificacaoEnum.fromString(statusInvalido);
        });

        assertTrue(exception.getMessage().contains("Nenhum status encontrado"));
    }

    @Test
    @DisplayName("fromString() deve lançar exceção para entrada nula")
    void fromString_DeveLancarExcecao_QuandoStatusEhNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            StatusNotificacaoEnum.fromString(null);
        });

        assertTrue(exception.getMessage().contains("Nenhum status encontrado"));
    }
}