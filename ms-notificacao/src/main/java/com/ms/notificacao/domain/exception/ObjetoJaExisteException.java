package com.ms.notificacao.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ObjetoJaExisteException extends ResponseStatusException {

  public ObjetoJaExisteException(String mensagem) {
    super(HttpStatus.CONFLICT, mensagem);
  }
}