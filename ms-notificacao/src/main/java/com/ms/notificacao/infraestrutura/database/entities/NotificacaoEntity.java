package com.ms.notificacao.infraestrutura.database.entities;

import com.ms.notificacao.domain.enums.CanalEnum;
import com.ms.notificacao.domain.enums.StatusNotificacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_notificacoes")
public class NotificacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Long idNotificacao;

    @Column(name = "id_paciente")
    private Long idPaciente;

    @Column(name = "id_consulta")
    private Long idConsulta;

    @Enumerated(EnumType.STRING)
    private CanalEnum canal;

    @Enumerated(EnumType.STRING)
    private StatusNotificacaoEnum status;

    private String mensagem;

    @Column(name = "data_agendamento_envio")
    private OffsetDateTime dataAgendamentoEnvio;
}
