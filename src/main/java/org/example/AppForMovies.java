package org.example;

import org.example.modelMovie.Director;
import org.example.modelMovie.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

            showSingleDirectorById(2, session);
            showMoviesByDirector(2, session);
            showSingleMovie(4, session);
            showDirectorOfMovie(4,session);

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

}
