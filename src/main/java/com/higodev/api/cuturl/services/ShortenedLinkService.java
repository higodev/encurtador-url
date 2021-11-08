package com.higodev.api.cuturl.services;

import com.higodev.api.cuturl.dtos.ShortenedLinkRequestDTO;
import com.higodev.api.cuturl.dtos.ShortenedLinkResponseDTO;
import com.higodev.api.cuturl.dtos.ShortenedRedirectResponseDTO;

import java.io.IOException;
import java.util.UUID;

public interface ShortenedLinkService {

    ShortenedRedirectResponseDTO findByHash(String hash);

    ShortenedLinkResponseDTO create(final ShortenedLinkRequestDTO shortenedLinkRequestDTO, final String host) throws IOException;

}
