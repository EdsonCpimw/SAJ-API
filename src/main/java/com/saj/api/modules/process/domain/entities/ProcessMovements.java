package com.saj.api.modules.process.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.process.domain.enums.TypeMovements;
import com.saj.api.modules.users.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processo_movimentacoes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProcessMovements {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "tipo", nullable = false)
    private TypeMovements type;
    @Column(name = "status_processo", nullable = false)
    private ProcessStatus status;
    @Column(name = "titulo", nullable = false)
    private String title;
    @Column(name = "descricao")
    private String description;
    @Column(name = "importante", nullable = false)
    private boolean important;
    @Column(name = "data_evento")
    private LocalDateTime dateEvent;
    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id")
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User user;
}
