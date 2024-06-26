package org.example;

import org.example.modelMovie.Director;
import org.example.modelMovie.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

            Director director = showDirectorWithMoviesNew(1,session);
//            showMovieWithDirectorNew(1,session);
            List<Movie>movies = director.getMovies();

            //TODO: подгружаем ленивые сущности с помощью специального метода Hibernate
//            Hibernate.initialize(movies);
            session.getTransaction().commit();

            // Если у нас загрузка Eager, то мы можем получить данные из связанной таблицы вне сессии
            // т.к. у нас уже все подгрузилось в одном запросе

            // При загрузке Lazy мы получим исключение org.hibernate.LazyInitializationException: failed to lazily initialize a collection
            // Для того, чтобы можно было работать вне сессии достаточно вызвать метод sout(director.getMovies())
            // внутри транзакции и затем уже вне ее можно вызывать тот же самый метод и ошибки не возникнте
            /**
             * если просто вызвать метод director.getMovies() без sout, то мы также получим ошибку
             * так происходит потому что Java оптимизирует код и когда она видит, что объект никем не используется
             * она может игнорировать его, в результате мы получим ошибку
             */
//            System.out.println(movies);

            // метод для открытия второй транзакции
//            showDirectorWithMoviesInSecondTransaction(director,session,factory);
            showDirectorWithMovieByHQL(director,factory);
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

    /**
     * В данном случает связь между таблицами установлена как OneToMany
     * от Director -> Movie и по умолчанию стоит ленивая загрузка, следовательно при получении
     * директора из контекста, мы не получаем данные из связанной таблицы
     *
     * при вызове метода director.getMovies() идет еще один запрос к БД
     */
    public static Director showDirectorWithMoviesNew(Integer directorId, Session session){
        Director director = session.get(Director.class,directorId);
        System.out.println("Получение директора");

        // получение связанных сущностей лениво
//        System.out.println(director.getMovies());
//        System.out.println("Получение данных из связанной таблицы");
        return director;
    }

    // Пример использования второй транзакции для получения связанных данных из сущности
    // может потребоваться тогда, когда нам не нужно сразу загружать данные
    // Например, нам нужны данные только в определенный момент
    public static void showDirectorWithMoviesInSecondTransaction(Director director, Session session, SessionFactory factory){
        session = factory.getCurrentSession();
        session.beginTransaction();

        System.out.println("Внутри второй транзакции");
        // пристегиваем наш объект ко второй транзакции с помощью метода merge()
        // с помощью него мы можем в любом месте программы подгрузить данные
        director = session.merge(director);

        System.out.println(director.getMovies());
        session.getTransaction().commit();
        System.out.println("Вне второй транзакции");
        System.out.println(director.getMovies());
    }

    // пример кода для получения данных из связанных таблиц,
    // используя HQL-запрос (он более громоздкий, но тем не менее может пригодиться)
    public static void showDirectorWithMovieByHQL(Director director, SessionFactory factory){
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        List<Movie>movies = session.createSelectionQuery("select m from Movie m where m.director.id=:directorId", Movie.class)
                        .setParameter("directorId", director.getId()).getResultList();
        System.out.println(movies);

        session.getTransaction().commit();
        // Также мы можем использовать эти полученные данные вне сессии
        System.out.println("Вне второй сессии");
        System.out.println(movies);
    }

    // пример загрузки данных Lazy/Eager
    /*
    В данном примере у нас идет загрузка Eager по умолчанию, т.к. связь таблиц ManyToOne
    от Movie -> Director. Поэтому у нас идет один запрос к БД при получении фильмов из контекста,
    где уже загружены директоры. Соответственно при вызове метода movie.getDirector() у нас не
    будет дополнительного запроса
     */
    public static void showMovieWithDirectorNew(Integer movieId, Session session){
        Movie movie = session.get(Movie.class, movieId);
        System.out.println("Получение фильма");
        System.out.println(movie.getDirector());
    }

}
