package org.example;

import org.example.modelMovie.Director;
import org.example.modelMovie.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppForMovies {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Director.class)
                .addAnnotatedClass(Movie.class);

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();
            //

//            showSingleDirectorById(2, session);
//            showMoviesByDirector(2, session);
//            showSingleMovie(4, session);
//            showDirectorOfMovie(4,session);
////            createNewMovieForDirector(5,session);
//            createNewDirectorWithNewMovie(session);
//            changeDirectorForMovie(13,session);
//            deleteMovieForDirector(5,session);

//            createNewMovieForDirector(9,session);
//            createNewDirectorWithNewMovies(session);

//            showDirectorWithMoviesNew(1,session);
            showMovieWithDirectorNew(1,session);

            //
            session.getTransaction().commit();
        } finally {
            factory.close();
        }

    }

    public static void showSingleDirectorById(Integer directorId, Session session){
        Director director = session.get(Director.class, directorId);
        System.out.println(director);
    }

    public static void showMoviesByDirector(Integer directorId, Session session){
        Director director = session.get(Director.class, directorId);
        director.getMovies().forEach(movie -> System.out.println(movie));
    }

    public static void showSingleMovie(Integer movieId, Session session){
        Movie movie = session.get(Movie.class, movieId);
        System.out.println("----------------------------------------");
        System.out.println(movie);
    }

    public static void showDirectorOfMovie(Integer movieId, Session session){
        Movie movie = session.get(Movie.class, movieId);
        Director director = movie.getDirector();
        System.out.println(director);
    }

    public static void createNewMovieForDirector(Integer directorId, Session session){
        //новый способ сохранения связанных сущностей
        Director director = session.get(Director.class,directorId);
        director.addMovie(new Movie("Omen2",2012));
        session.persist(director);
    }

    //старое решение
//    public static void createNewDirectorWithNewMovie(Session session){
//        Director director = new Director("Test Director",56);
//        Movie movie = new Movie("Terminator",2000,director);
//
//        director.setMovies(new ArrayList<>(Collections.singletonList(movie)));
//        // после настройки каскадирования достаточно сохранить только сущность "director"
//        session.persist(director);
//    }

    // новый способ добавления
    public static void createNewDirectorWithNewMovies(Session session){
        Director director = new Director("Sergio Dallini", 77);
        director.addMovie(new Movie("Evil street",2020));
        director.addMovie(new Movie("Amazing day",2021));

        session.persist(director);
    }

    public static void changeDirectorForMovie(Integer movieId, Session session){
        Movie movie = session.get(Movie.class,movieId);
        Director oldDirector = session.get(Director.class,movie.getDirector().getId());
        oldDirector.getMovies().remove(movie);

        Director newDirector = session.get(Director.class,5);
        movie.setDirector(newDirector);

        newDirector.getMovies().add(movie);

        session.merge(movie);

    }

    public static void deleteMovieForDirector(Integer directorId, Session session){
        Director director = session.get(Director.class, directorId);
        Movie movie = session.get(Movie.class,13);
        director.getMovies().remove(movie);
        session.remove(movie);
    }

    // пример с загрузкой данных Lazy/Eager
    public static void showDirectorWithMoviesNew(Integer directorId, Session session){
        Director director = session.get(Director.class,directorId);
        System.out.println("Получение директора");

        // получение связанных сущностей лениво
        System.out.println(director.getMovies());
        System.out.println("Получение данных из связанной таблицы");
    }

    // пример загрузки данных Lazy/Eager

    public static void showMovieWithDirectorNew(Integer movieId, Session session){
        Movie movie = session.get(Movie.class, movieId);
        System.out.println("Получение фильма");
        System.out.println(movie.getDirector());
    }

}
