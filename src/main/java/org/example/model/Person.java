package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // помечает именно те классы, которые связаны с БД. Классы должны иметь пустой конструктор
@Table(name = "person") // в нашем случае можно не указывать, т.к. имена совпадают
public class Person {

    @Id
    @Column(name = "person_id")
    private Integer person_id;

    @Column(name = "person_name")
    private String person_name;

    @Column(name = "age")
    private Integer age;

    public Person() {
    }

    public Person(Integer person_id, String person_name, Integer age) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.age = age;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
