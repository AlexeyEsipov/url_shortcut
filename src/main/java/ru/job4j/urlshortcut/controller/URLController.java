package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.StatisticDTO;
import ru.job4j.urlshortcut.dto.UrlDTO;
import ru.job4j.urlshortcut.model.Registration;
import ru.job4j.urlshortcut.model.URL;
import ru.job4j.urlshortcut.service.RegistrationService;
import ru.job4j.urlshortcut.service.UrlService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            URLController.class.getSimpleName());

    private final UrlService urlService;
    private final RegistrationService registrationService;

    @PostMapping("/convert")
    public ResponseEntity<UrlDTO> convert(@RequestBody @Valid UrlDTO urlDTO) {
        return new ResponseEntity<>(
                urlService.convert(urlDTO),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/redirect/{urlKey}")
    public ResponseEntity<String> redirect(@PathVariable String urlKey) {
        HttpHeaders responseHeaders = new HttpHeaders();
        URL result;
        try {
            result = urlService.findByKey(urlKey);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Key is not found. Please, check parameters.");
        }
        urlService.increaseCallsNumberByKey(urlKey);
        responseHeaders.set("REDIRECT URL", result.getUrl());
        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticDTO>> statistic() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        List<StatisticDTO> statList = new ArrayList<>();
        Registration currentUser;
        try {
            currentUser = registrationService.findByLogin(login);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }
        urlService.findAllByOwner(currentUser).forEach(
                url -> statList.add(new StatisticDTO(url.getUrl(), url.getCallsNumber())));
        return new ResponseEntity<>(statList, HttpStatus.OK);
    }
}