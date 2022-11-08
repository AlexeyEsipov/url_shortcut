package ru.job4j.urlshortcut.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.urlshortcut.model.Registration;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    Optional<Registration> findBySite(String site);
    Optional<Registration> findByLogin(String login);
}