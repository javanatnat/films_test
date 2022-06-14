package ru.javanatnat.springbootexample.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.javanatnat.springbootexample.model.Country;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CountryRepositoryTest {
    @Container
    private static final PostgreSQLContainer<ListsPostgreSQLContainer> postgresqlContainer
            = ListsPostgreSQLContainer.getInstance();

    @Autowired
    CountryRepository countryRepository;

    @Test
    void testSimple() {
        assertThat(postgresqlContainer.isRunning()).isTrue();
    }

    @Test
    void testFindByName() {
        Country country = countryRepository.save(new Country("Country"));
        assertThat(country).isNotNull();

        Optional<Country> findCountry = countryRepository.findByNameIgnoreCase(country.getName());
        assertThat(findCountry).isPresent();
        assertThat(findCountry.get()).isEqualTo(country);

        Optional<Country> findCountrySmall = countryRepository.findByNameIgnoreCase(
                country.getName().toLowerCase(Locale.ROOT));
        assertThat(findCountrySmall).isPresent();
        assertThat(findCountrySmall.get()).isEqualTo(country);
    }
}
