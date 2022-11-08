package ru.job4j.urlshortcut.service;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.UrlDTO;
import ru.job4j.urlshortcut.model.Registration;
import ru.job4j.urlshortcut.model.URL;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UrlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            UrlService.class.getSimpleName());
    private final UrlRepository urlRepository;
    private final RegistrationService registrationService;
    private final int stringLength;

    public UrlService(UrlRepository urlRepository,
                      RegistrationService registrationService,
                      @Value("${string.length}") int stringLength) {
        this.urlRepository = urlRepository;
        this.registrationService = registrationService;
        this.stringLength = stringLength;
    }

    public UrlDTO convert(UrlDTO urlDTO) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        String key = RandomString.make(stringLength);
        String url = urlDTO.getUrl();
        Registration currentUser;
        try {
            currentUser = registrationService.findByLogin(login);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }
        try {
            urlRepository.save(new URL(url, key, currentUser));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage(), e);
            String keyDb = urlRepository.findByUrl(url).orElseThrow(IllegalAccessError::new).getKey();
            urlDTO.setKey(keyDb);
        }
        return urlDTO;
    }

    public URL findByKey(String key) {
        return urlRepository.findByKey(key)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("URL key \"%s\" not found!", key)));
    }

    public void increaseCallsNumberByKey(String key) {
        urlRepository.increaseCallsNumberByKey(key);
    }

    public List<URL> findAllByOwner(Registration owner) {
        return urlRepository.findAllByOwner(owner);
    }

}