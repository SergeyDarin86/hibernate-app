package org.example.modelMovie;

import jakarta.persistence.*;
import org.example.modelMovie.Director;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "year_of_production")
    private Integer yearOfProduction;

    @ManyToOne
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private Director director;

    public Movie() {
    }

    public Movie(String movieName, Integer yearOfProduction) {
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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "movie: " + movieName + ", " + yearOfProduction;
    }
}
