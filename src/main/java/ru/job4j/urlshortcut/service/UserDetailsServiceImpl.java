package ru.job4j.urlshortcut.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Registration;
import ru.job4j.urlshortcut.repository.RegistrationRepository;
import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RegistrationRepository registrationRepository;

    public UserDetailsServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Registration reg = registrationRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));
        return new User(reg.getLogin(), reg.getPassword(), emptyList());
    }
}