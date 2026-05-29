package com.saj.api.modules.users.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "nome", nullable = false, length = 150)
    private String name;
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @Column(name = "senha", nullable = false)
    private String password;
    @Column(name = "telefone", nullable = false)
    private String phone;
    @Column(name = "ativo", nullable = false)
    private boolean active;
    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escritorio_id", nullable = false)
    private Company company;
}
