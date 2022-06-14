package ru.javanatnat.springbootexample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.javanatnat.springbootexample.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Long> {
    @Override
    List<Country> findAll();
    Optional<Country> findByNameIgnoreCase(@Param("name") String name);
}
