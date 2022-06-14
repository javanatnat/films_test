package ru.javanatnat.springbootexample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

public class FilmDTO {
    private String name;
    private String country;
    private int year;
    private String genre;
    private long budget;

    public FilmDTO(String name, String country, int year, String genre, long budget) {
        this.name = name;
        this.country = country;
        this.year = year;
        this.genre = genre;
        this.budget = budget;
    }

    public FilmDTO() {}

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public long getBudget() {
        return budget;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", budget=" + budget +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FilmDTO filmDTO)) {
            return false;
        }

        if (year != filmDTO.year) {
            return false;
        }

        if (!name.equals(filmDTO.name)) {
            return false;
        }

        return country.equals(filmDTO.country);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + year;
        return result;
    }

    @JsonIgnore
    public Set<String> getGenreList() {
        if (genre == null || genre.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(
                genre.replaceAll("\\s+", "")
                        .split(",")
        ).collect(Collectors.toSet());
    }
}
