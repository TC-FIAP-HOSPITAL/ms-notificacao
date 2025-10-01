package com.ms.notificacao.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CanalEnumTest {

    @Test
    @DisplayName("Deve retornar a descrição correta para cada canal")
    void getDescricao_DeveRetornarDescricaoCorreta() {
        assertEquals("SMS", CanalEnum.SMS.getDescricao());
        assertEquals("E-mail", CanalEnum.EMAIL.getDescricao());
        assertEquals("WhatsApp", CanalEnum.WHATSAPP.getDescricao());
        assertEquals("Push Notification", CanalEnum.PUSH.getDescricao());
    }

    @ParameterizedTest
    @CsvSource({
            "SMS, SMS",
            "E-mail, EMAIL",
            "whatsapp, WHATSAPP",
            "Push Notification, PUSH",
            "PUSH, PUSH"
    })
    @DisplayName("fromString() deve retornar o enum correto para textos válidos")
    void fromString_DeveRetornarEnumParaTextoValido(String input, CanalEnum expected) {
        CanalEnum result = CanalEnum.fromString(input);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("fromString() deve lançar exceção para texto inválido")
    void fromString_DeveLancarExcecaoParaTextoInvalido() {
        String textoInvalido = "Telegram";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CanalEnum.fromString(textoInvalido);
        });

        assertTrue(exception.getMessage().contains("Nenhum canal encontrado"));
    }

    @Test
    @DisplayName("fromString() deve lançar exceção para texto nulo")
    void fromString_DeveLancarExcecaoParaTextoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            CanalEnum.fromString(null);
        });
    }
}