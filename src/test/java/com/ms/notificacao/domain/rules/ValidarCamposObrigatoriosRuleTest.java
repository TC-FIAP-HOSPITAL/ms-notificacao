package com.ms.notificacao.domain.rules;

import com.ms.notificacao.domain.exception.CampoObrigatorioException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ValidarCamposObrigatoriosRuleTest {

    @Test
    void validarCamposObrigatorios_sucesso() {
        assertDoesNotThrow(() -> ValidarCamposObrigatoriosRule.validarCamposObrigatorios("Teste"));
    }

    @Test
    void validarCamposObrigatorios_excecao() {
        assertThrows(CampoObrigatorioException.class, () -> ValidarCamposObrigatoriosRule.validarCamposObrigatorios(null));
    }
}
