package ru.javanatnat.springbootexample.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("country")
public class Country {
    @Id
    private final Long id;
    private final String name;

    @PersistenceConstructor
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country country)) {
            return false;
        }

        if (!Objects.equals(id, country.id)) {
            return false;
        }

        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }
}
