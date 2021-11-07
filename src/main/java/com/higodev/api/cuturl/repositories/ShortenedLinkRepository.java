package com.higodev.api.cuturl.repositories;

import com.higodev.api.cuturl.entities.ShortenedLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShortenedLinkRepository extends JpaRepository<ShortenedLink, UUID> {

    Optional<ShortenedLink> findByHash(String hash);

}
