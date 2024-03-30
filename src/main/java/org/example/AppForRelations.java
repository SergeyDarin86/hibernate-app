package org.example;

import org.example.model.Customer;
import org.example.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;

public class AppForRelations {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class);

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();
            Customer customer = session.get(Customer.class,3);
            System.out.println(customer);
            //геттер должен вызываться внутри транзакции, иначе hibernate не поймет нас
//            customer.getItems().forEach(System.out::println);

            Item item = session.get(Item.class,4);
            System.out.println("-------------------------");
            System.out.println(item);

            System.out.println(item.getCustomer() );

//            createNewItemForCustomer(3, session);

            createNewCustomerWithItem(session);

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    public static void createNewItemForCustomer(Integer customerId, Session session){
        Customer customer = session.get(Customer.class,customerId);
        Item item = new Item("bed",customer);
        customer.getItems().add(item);
        //TODO: иногда нам потребуется писать код для родительской стороны
        //todo: иначе в БД изменнеия внесутся, но при вызове геттера из родительской таблицы
        //todo: hibernate выдаст нам старые, необновленные данные
        //todo: даннная строка не делает никакого sql-запроса
        //хорошей практикой считается писать изменения на обоих сторонах
        // таким образом состояние хэша Hibernate и данных в Базе будут совпадать
        session.persist(item);
    }

    public static void createNewCustomerWithItem(Session session){
        Customer customer = new Customer("Yana",30);
        Item item = new Item("shoes",customer);
        customer.setItems(new ArrayList<>(Collections.singletonList(item)));
        session.persist(customer);
        session.persist(item);
    }

}
