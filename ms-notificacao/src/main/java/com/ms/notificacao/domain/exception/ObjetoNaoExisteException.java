package com.ms.notificacao.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ObjetoNaoExisteException extends ResponseStatusException {

  public ObjetoNaoExisteException(String mensagem) {
    super(HttpStatus.NOT_FOUND, mensagem);
  }
}