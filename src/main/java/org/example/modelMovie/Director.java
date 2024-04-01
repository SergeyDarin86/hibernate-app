package org.example.modelMovie;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Director")
public class Director {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "director_name")
    private String directorName;

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "director",cascade = CascadeType.PERSIST)
//    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST) // еще один способ использовать каскадирование
    @Cascade(value = {
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.MERGE
    })
    private List<Movie> movies;

    public Director() {
    }

    public Director(String directorName, Integer age) {
        this.directorName = directorName;
        this.age = age;
    }

    public void addMovie(Movie movie){
        if (this.movies == null)
            this.movies = new ArrayList<>();
        this.movies.add(movie);
        movie.setDirector(this);
    }

    public Integer getId() {
        return id;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Director: " + directorName + ", age: " + age;
    }
}
