package com.higodev.api.cuturl.controllers;

import com.higodev.api.cuturl.dtos.ShortenedLinkRequestDTO;
import com.higodev.api.cuturl.dtos.ShortenedLinkResponseDTO;
import com.higodev.api.cuturl.services.ShortenedLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/cutUrl")
@RequiredArgsConstructor
public class ShortenedLinkController {

    private final ShortenedLinkService service;

    @GetMapping("/{hash}")
    @ResponseStatus(HttpStatus.FOUND)
    public void redirectByHash(@PathVariable String hash, HttpServletResponse response) {
        String linkOriginal = service.findByHash(hash).getLinkOriginal();
        try {
            response.sendRedirect(linkOriginal);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortenedLinkResponseDTO create(@RequestBody ShortenedLinkRequestDTO requestDTO, ServletRequest servletRequest)
            throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String host = request.getHeader(HttpHeaders.HOST);
        return service.create(requestDTO, host);
    }

}
