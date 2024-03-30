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

//            createNewCustomerWithItem(session);

//            deleteItemsForCustomer(3,session);

//            deleteCustomerById(2,session);

            updateCustomerForItem(4, session);

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

    public static void deleteItemsForCustomer(Integer customerId, Session session){
        Customer customer = session.get(Customer.class,customerId);
        customer.getItems().stream().forEach(item -> session.remove(item));
        // снова применяем хорошую практику, чтобы было правильное состояние кэша
        customer.getItems().clear();
    }

    public static void deleteCustomerById(Integer customerId, Session session){
        Customer customer = session.get(Customer.class, customerId);
        session.remove(customer);
        customer.getItems().forEach(item -> item.setCustomer(null));
    }

    public static void updateCustomerForItem(Integer updatedCustomerId, Session session){
        Customer customer = session.get(Customer.class, updatedCustomerId);
        Item item = session.get(Item.class, 5);

        // удаление старого владельца у товара (если он есть)
        //        item.getCustomer().getItems().remove(item);

        item.setCustomer(customer);
        customer.getItems().add(item);
    }

}
