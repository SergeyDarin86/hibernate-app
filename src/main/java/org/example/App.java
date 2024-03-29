package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // создаем конфигурацию, которая автоматически найдет файл с именнем "hibernate.properties"
        // и прочитает параметры для подключения к БД
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        // здесь мы создаем фабрику и из нее получаем сессию для работы Hibernate
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try{
            session.beginTransaction();
            // указываем сущность, с которым работаем и указыаем id нашей сущности в БД
            // ищет по первичному ключу
//            Person person = session.get(Person.class, 3);
//            System.out.println(person.getPerson_name() + ", " + person.getAge());
//
//            person.setAge(44);
//            session.merge(person);  //update
////            addPersonToDB(session);
//            session.remove(person);

            Person person = new Person("Semen",23);
            session.persist(person);

            session.getTransaction().commit();  // закрываем транзакцию
            System.out.println(person.getPerson_id());
        }finally {
            // закрываем фабрику в любом случае, даже если произойдет ошибка
            sessionFactory.close();
        }

    }

    public static void addPersonToDB(Session session){
        Person person1 = new Person("Egor", 27);
        Person person2 = new Person("Gera", 45);
        Person person3 = new Person("Viktor",14);

        session.persist(person1);
        session.persist(person2);
        session.persist(person3);
    }

}
