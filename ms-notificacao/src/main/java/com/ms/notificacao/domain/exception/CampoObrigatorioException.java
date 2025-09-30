package com.ms.notificacao.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CampoObrigatorioException extends ResponseStatusException {

    public CampoObrigatorioException(String nomeCampo) {
        super(HttpStatus.BAD_REQUEST, "Campo " + nomeCampo + " não foi preenchido!");
    }
}