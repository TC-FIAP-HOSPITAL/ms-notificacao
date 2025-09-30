package com.ms.notificacao.domain.rules;

import com.ms.notificacao.domain.exception.CampoObrigatorioException;

public class ValidarCamposObrigatoriosRule {

    public static void validarCamposObrigatorios(String mensagem) {
        if (isNuloOuVazio(mensagem)) {
            throw new CampoObrigatorioException("Existem campos obrigatorios que não foram preenchidos");
        }
    }

    private static boolean isNuloOuVazio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}
