package org.example;

import org.example.modelActorMovie.Actor;
import org.example.modelActorMovie.MovieNew;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppForActorMovie {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(MovieNew.class).addAnnotatedClass(Actor.class);
        SessionFactory factory = configuration.buildSessionFactory();

        // создаем блок try с ресурсами (try with resources)
        // у нас есть какой-то ресурс, которы будет закрыт этим блоком try
        // теперь этот блок автоматически закроет нашу factory после того как мы завершим с ним работу

        try(factory){
            Session session = factory.getCurrentSession();
            session.beginTransaction();

//            createNewActorWithNewFilm(session);
//            showMoviesForActor(1,session);
//            addExistingActorForNewMovie(7,session);
//            deleteActorFromMovie(1,session);
//            addMovieForActor(1,session);
            showActorsForMovie(2,session);
            session.getTransaction().commit();
        }

    }

    // на данный момент каскадирование не настроено, поэтому сохраняем все вручную для каждой сущности
    public static void createNewActorWithNewFilm(Session session){
        MovieNew movie = new MovieNew("Test Movie", 1999);
        Actor actor1 = new Actor("Test Actor", 99);
        Actor actor2 = new Actor("Test actress",88);

        movie.setActors(new ArrayList<>(List.of(actor1,actor2)));
        actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
        actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));

        session.persist(movie);
        session.persist(actor1);
        session.persist(actor2);
    }

    // данный метод выполнен с настроенным каскадированием и новым методом в сущности Actor
//    public static void createActorWithFilm(Session session){
//        MovieNew movie = new MovieNew("Test Movie New", 1950);
//        Actor actor1 = new Actor("Test Actor New", 100);
//
//        actor1.addMovie(movie);
//        session.persist(actor1);
//    }

    public static void showMoviesForActor(Integer actorId, Session session){
        Actor actor = session.get(Actor.class,actorId);
        System.out.println(actor.getMovies());
    }

    public static void showActorsForMovie(Integer movieId, Session session){
        MovieNew movie = session.get(MovieNew.class, movieId);
        movie.getActors().forEach(actor -> System.out.println(actor));
    }

    // в процессе выполнения sql-запросов появляется запрос "delete" - это особенность hibernate
    // он сначала удаляет строки в связанной таблице, а затем вставляет снова
    public static void addExistingActorForNewMovie(Integer actorId, Session session){
        MovieNew movie = new MovieNew("Last Movie", 1977);
        Actor actor = session.get(Actor.class,actorId);
        movie.setActors(new ArrayList<>(Collections.singletonList(actor)));

        // т.к. актер у нас уже существует и находится в состоянии "persist",
        // то для добавления нового фильма достаточно вызвать геттер и добавить в список фильмов новый фильм

        actor.getMovies().add(movie);
        session.persist(movie);
    }

    public static void deleteActorFromMovie(Integer actorId, Session session){
        Actor actor = session.get(Actor.class, actorId);
        System.out.println(actor.getMovies());

        MovieNew movieToRemove = actor.getMovies().get(0);
        actor.getMovies().remove(0);
        movieToRemove.getActors().remove(actor);
    }

    public static void addMovieForActor(Integer actorId, Session session){
        Actor actor = session.get(Actor.class, actorId);
        MovieNew movieToAdd = session.get(MovieNew.class, 1);

        actor.addMovie(movieToAdd);
        movieToAdd.getActors().add(actor);
    }










}
