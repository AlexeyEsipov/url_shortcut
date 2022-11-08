package ru.job4j.urlshortcut.service;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.SiteDTO;
import ru.job4j.urlshortcut.dto.UserDTO;
import ru.job4j.urlshortcut.model.Registration;
import ru.job4j.urlshortcut.repository.RegistrationRepository;

@Service
public class RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            RegistrationService.class.getSimpleName());
    private final RegistrationRepository registrationRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final int stringLength;

    public RegistrationService(RegistrationRepository registrationRepository,
                               BCryptPasswordEncoder passwordEncoder,
                               @Value("${string.length}") int stringLength) {
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = passwordEncoder;
        this.stringLength = stringLength;
    }

    public UserDTO register(SiteDTO siteDTO) {
        String login = RandomString.make(stringLength);
        String password = RandomString.make(stringLength);
        UserDTO result = new UserDTO(true, login, password);
        try {
            registrationRepository.save(
                new Registration(siteDTO.getSite(), login, passwordEncoder.encode(password)));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage(), e);
            result = new UserDTO();
            result.setRegistration(false);
        }
        return result;
    }

    public Registration findByLogin(String login) {
        return registrationRepository.findByLogin(login).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("This login was not found: %s", login)));
    }
}