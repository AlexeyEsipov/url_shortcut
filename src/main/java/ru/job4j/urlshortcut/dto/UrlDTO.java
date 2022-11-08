package ru.job4j.urlshortcut.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UrlDTO {
    @NotBlank
    private String url;
    private String key;
}