package ru.javanatnat.javafxexample;

public class Film {
    private final String name;
    private final String country;
    private final int year;
    private String genre;
    private long budget;

    public Film(String name, String country, String genre, long budget, int year) {
        this.name = name;
        this.country = country;
        this.genre = genre;
        this.budget = budget;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public long getBudget() {
        return budget;
    }

    public int getYear() {
        return year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBudget(long budget) {
        this.budget = budget;
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

        if (!name.equals(film.name)) {
            return false;
        }

        return country.equals(film.country);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", budget=" + budget +
                '}';
    }
}
