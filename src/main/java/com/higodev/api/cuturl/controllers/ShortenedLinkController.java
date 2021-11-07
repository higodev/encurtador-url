package com.higodev.api.cuturl.controllers;

import com.higodev.api.cuturl.dtos.ShortenedLinkRequestDTO;
import com.higodev.api.cuturl.dtos.ShortenedLinkResponseDTO;
import com.higodev.api.cuturl.services.ShortenedLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/cutUrl")
@RequiredArgsConstructor
public class ShortenedLinkController {

    private final ShortenedLinkService service;

    @GetMapping("/{hash}")
    public void redirectByHash(@PathVariable String hash, HttpServletResponse response) {
        String linkOriginal = service.findByHash(hash).getLinkOriginal();
        try {
            response.sendRedirect(linkOriginal);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ShortenedLinkResponseDTO create(@RequestBody ShortenedLinkRequestDTO requestDTO) {
        return service.create(requestDTO);
    }

}
