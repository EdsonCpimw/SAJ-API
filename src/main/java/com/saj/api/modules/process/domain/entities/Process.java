package com.saj.api.modules.process.domain.entities;

import com.saj.api.modules.process.domain.converters.LegalAreaConverter;
import com.saj.api.modules.process.domain.converters.ProcessPriorityConverter;
import com.saj.api.modules.process.domain.converters.ProcessStatusConverter;
import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "titulo", nullable = false)
    private String title;
    @Column(name = "numero_processo", nullable = false, length = 50)
    private String numberProcess;
    @Column(name = "descricao")
    private String description;
    @Column(name = "status", nullable = false)
    private ProcessStatus status;
    @Column(name = "area_juridica", nullable = false, length = 50)
    private LegalArea legalArea;
    @Column(name = "vara")
    private String courtDivision;
    @Column(name = "tribunal")
    private String court;
    @Column(name = "prioridade", nullable = false)
    private ProcessPriority priority;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escritorio_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atribuido_para")
    private User assignerdTo;
}
