package org.example;

import org.example.modelSchool.School;
import org.example.modelSchool.SchoolDirector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AppForSchool {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(SchoolDirector.class).addAnnotatedClass(School.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();

//        showSchoolDirector(4, session);

//        showSchoolWithDirector(3,session);
//        createNewDirectorWithSchool(session);

//        changeDirectorForSchool(3,session);

//        createNewSchoolForExistingDirector(1,session);

        changeSchoolNumberForDirector(1,session);

        session.getTransaction().commit();
        factory.close();
    }

    public static void showSchoolDirector(Integer directorId, Session session){
        SchoolDirector director = session.get(SchoolDirector.class,directorId);
        System.out.println(director.getFullName() + ", " + director.getAge());

        System.out.println(director.getSchool());
    }

    public static void showSchoolWithDirector(Integer schoolId, Session session){
        School school = session.get(School.class, schoolId);
        System.out.println(school.getSchoolNumber());

        System.out.println(school.getSchoolDirector());
    }

    public static void createNewDirectorWithSchool(Session session){
        School school = new School(66);
        SchoolDirector director = new SchoolDirector("Test2 Director",101,school);
        school.setSchoolDirector(director);
        session.persist(director);
    }

    public static void changeDirectorForSchool(Integer schoolId, Session session){
        School school = session.get(School.class, 3);
        SchoolDirector director = new SchoolDirector("Changed Director", 22,school);
        session.persist(director);
        school.setSchoolDirector(director);
    }

    // With error
    // ERROR: ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности "school_director_id_key"
    public static void createNewSchoolForExistingDirector(Integer directorId, Session session){
        SchoolDirector director = session.get(SchoolDirector.class, directorId);
        School school = new School(99);
        school.setSchoolDirector(director);
        session.persist(school);
    }

    public static void changeSchoolNumberForDirector(Integer directorId, Session session){
        SchoolDirector director = session.get(SchoolDirector.class, directorId);
        director.getSchool().setSchoolNumber(333);
    }

}
