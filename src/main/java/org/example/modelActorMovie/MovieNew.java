package org.example.modelActorMovie;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movienew")
public class MovieNew {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "year_of_production")
    private Integer yearOfProduction;

    @ManyToMany(mappedBy = "movies")
    private List<Actor>actors;

    public MovieNew() {
    }

    public MovieNew(String movieName, Integer yearOfProduction) {
        this.movieName = movieName;
        this.yearOfProduction = yearOfProduction;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return movieName + " - " + yearOfProduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieNew movieNew = (MovieNew) o;
        return Objects.equals(movieId, movieNew.movieId) && Objects.equals(movieName, movieNew.movieName) && Objects.equals(yearOfProduction, movieNew.yearOfProduction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieName, yearOfProduction);
    }
}
