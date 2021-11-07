package com.higodev.api.cuturl.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "shortened_links")
public class ShortenedLink implements Serializable {

    private static final long serialVersionUID = -2780776057070915224L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private final LocalDateTime dateRegister = LocalDateTime.now();

    @Column(length = 50, unique = true, nullable = false)
    private String hash;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String linkOriginal;
}
