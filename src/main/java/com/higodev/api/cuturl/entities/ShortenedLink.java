package com.higodev.api.cuturl.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShortenedLink that = (ShortenedLink) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
