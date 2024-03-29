package com.higodev.api.cuturl.services;

import com.higodev.api.cuturl.dtos.ShortenedLinkRequestDTO;
import com.higodev.api.cuturl.dtos.ShortenedLinkResponseDTO;
import com.higodev.api.cuturl.dtos.ShortenedRedirectResponseDTO;
import com.higodev.api.cuturl.entities.ShortenedLink;
import com.higodev.api.cuturl.repositories.ShortenedLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortenedLinkServiceImpl implements ShortenedLinkService {

    private static final int SIZE_HASH = 5;
    private static final String FILE_NAME = "/Users/higoalexandre/Development/log-error.txt";
    private static final String SEPARATOR = "################################";

    private final ShortenedLinkRepository repository;

    private String generateHash(){
        String hash = RandomStringUtils.randomAlphanumeric(SIZE_HASH).toUpperCase();
        if(repository.findByHash(hash).isPresent()){
            hash = generateHash();
        }
        return hash;
    }

    @Override
    public ShortenedRedirectResponseDTO findByHash(String hash) {

        ShortenedLink shortenedLink = repository.findByHash(hash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Link não encontrado"));

        return new ShortenedRedirectResponseDTO(shortenedLink.getLinkOriginal());

    }

    @Override
    public ShortenedLinkResponseDTO create(final ShortenedLinkRequestDTO shortenedLinkRequestDTO, final String host)
            throws IOException {

        ShortenedLink shortenedLink = new ShortenedLink();
        shortenedLink.setLinkOriginal(shortenedLinkRequestDTO.getLinkOriginal());
        shortenedLink.setHash(generateHash());

        try{
            repository.save(shortenedLink);
        }catch(Exception  e){

            StringBuilder str = new StringBuilder("");
            str.append("\n").append(SEPARATOR).append("\n");
            str.append(Files.readString(Path.of(FILE_NAME))).append("\n");
            str.append(shortenedLink.getHash()).append("\n");
            str.append(e.getMessage()).append("\n");

            log.error(str.toString());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                writer.write(str.toString());
            }

        }

        return ShortenedLinkResponseDTO.builder()
                .shortenedLink(String.format("http://%s/cutUrl/%s", host, shortenedLink.getHash()))
                .build();
    }
}
