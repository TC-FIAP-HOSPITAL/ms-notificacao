package com.ms.notificacao.domain.model;

import com.ms.notificacao.domain.enums.CanalEnum;
import com.ms.notificacao.domain.enums.StatusNotificacaoEnum;

import java.time.OffsetDateTime;

public class NotificacaoDomain {

    private Long idNotificacao;
    private Long idPaciente;
    private Long idConsulta;
    private CanalEnum canal;
    private StatusNotificacaoEnum status;
    private String mensagem;
    private OffsetDateTime dataAgendamentoEnvio;

    public NotificacaoDomain(){}

    public NotificacaoDomain(Long idNotificacao, Long idPaciente, Long idConsulta, CanalEnum canal, StatusNotificacaoEnum status, String mensagem, OffsetDateTime dataAgendamentoEnvio) {
        this.idNotificacao = idNotificacao;
        this.idPaciente = idPaciente;
        this.idConsulta = idConsulta;
        this.canal = canal;
        this.status = status;
        this.mensagem = mensagem;
        this.dataAgendamentoEnvio = dataAgendamentoEnvio;
    }

    public Long getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(Long idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public CanalEnum getCanal() {
        return canal;
    }

    public void setCanal(CanalEnum canal) {
        this.canal = canal;
    }

    public StatusNotificacaoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusNotificacaoEnum status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public OffsetDateTime getDataAgendamentoEnvio() {
        return dataAgendamentoEnvio;
    }

    public void setDataAgendamentoEnvio(OffsetDateTime dataAgendamentoEnvio) {
        this.dataAgendamentoEnvio = dataAgendamentoEnvio;
    }
}
