package com.higodev.api.cuturl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedLinkRequestDTO {
    private String linkOriginal;
}
