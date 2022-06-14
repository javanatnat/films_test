package ru.javanatnat.springbootexample.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("film")
public class Film {
    @Id
    private final Long id;
    private final String name;
    private final Long countryId;
    private final int year;
    private long budget;

    @PersistenceConstructor
    public Film(Long id, String name, Long countryId, int year, long budget) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.year = year;
        this.budget = budget;
    }

    public Film(String name, Long countryId, int year, long budget) {
        this(null, name, countryId, year, budget);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCountryId() {
        return countryId;
    }

    public int getYear() {
        return year;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                ", year=" + year +
                ", budget=" + budget +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Film film)) {
            return false;
        }

        if (year != film.year) {
            return false;
        }

        if (!Objects.equals(id, film.id)) {
            return false;
        }

        if (!name.equals(film.name)) {
            return false;
        }

        return countryId.equals(film.countryId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + countryId.hashCode();
        result = 31 * result + year;
        return result;
    }
}
