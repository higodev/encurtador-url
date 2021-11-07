package com.higodev.api.cuturl.services;

import com.higodev.api.cuturl.dtos.ShortenedLinkRequestDTO;
import com.higodev.api.cuturl.dtos.ShortenedLinkResponseDTO;
import com.higodev.api.cuturl.dtos.ShortenedRedirectResponseDTO;
import com.higodev.api.cuturl.entities.ShortenedLink;
import com.higodev.api.cuturl.repositories.ShortenedLinkRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortenedLinkServiceImpl implements ShortenedLinkService {

    private final ShortenedLinkRepository repository;

    @Override
    public ShortenedLinkResponseDTO findById(UUID id) {

        ShortenedLink shortenedLink = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Link não encontrado"));

        return new ShortenedLinkResponseDTO(shortenedLink.getHash());

    }

    @Override
    public ShortenedRedirectResponseDTO findByHash(String hash) {

        ShortenedLink shortenedLink = repository.findByHash(hash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Link não encontrado"));

        return new ShortenedRedirectResponseDTO(shortenedLink.getLinkOriginal());

    }

    @Override
    public ShortenedLinkResponseDTO create(ShortenedLinkRequestDTO shortenedLinkRequestDTO) {

        ShortenedLink shortenedLink = new ShortenedLink();
        shortenedLink.setLinkOriginal(shortenedLinkRequestDTO.getLinkOriginal());
        String hash = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        shortenedLink.setHash(hash);
        repository.save(shortenedLink);

        return ShortenedLinkResponseDTO.builder()
                .shortenedLink(shortenedLink.getHash())
                .build();
    }
}
