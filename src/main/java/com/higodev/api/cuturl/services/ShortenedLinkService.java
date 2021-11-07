package com.higodev.api.cuturl.services;

import com.higodev.api.cuturl.dtos.ShortenedLinkRequestDTO;
import com.higodev.api.cuturl.dtos.ShortenedLinkResponseDTO;
import com.higodev.api.cuturl.dtos.ShortenedRedirectResponseDTO;

import java.util.UUID;

public interface ShortenedLinkService {

    ShortenedLinkResponseDTO findById(UUID id);

    ShortenedRedirectResponseDTO findByHash(String hash);

    ShortenedLinkResponseDTO create(ShortenedLinkRequestDTO shortenedLinkRequestDTO);

}
