package org.example.modelActorMovie;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @Column(name = "actor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actorId;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "age")
    private Integer age;

    @ManyToMany
    @JoinTable(
            name = "actor_movie",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
    private List<MovieNew>movies;

    public void addMovie(MovieNew movie){
        if (this.movies == null)
            this.movies = new ArrayList<>();
        this.movies.add(movie);
    }

    public Actor() {
    }

    public Actor(String actorName, Integer age) {
        this.actorName = actorName;
        this.age = age;
    }

    public Integer getActorId() {
        return actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<MovieNew> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieNew> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return actorName + " - " + age;
    }

    // генерируем данные методы, чтобы можно было удалять из списка по объекту

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(actorId, actor.actorId) && Objects.equals(actorName, actor.actorName) && Objects.equals(age, actor.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, actorName, age);
    }
}
