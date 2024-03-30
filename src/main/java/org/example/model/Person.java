package org.example.model;

import jakarta.persistence.*;

@Entity // помечает именно те классы, которые связаны с БД. Классы должны иметь пустой конструктор
@Table(name = "person") // в нашем случае можно не указывать, т.к. имена совпадают
public class Person {

    @Id
    @Column(name = "person_id")
    // данная аннотация говорит hibernate, чтобы он не трогал это поле,
    // потому что оно генерируется автоматически
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer person_id;

    @Column(name = "person_name")
    private String person_name;

    @Column(name = "age")
    private Integer age;

    public Person() {
    }

    public Person(String person_name, Integer age) {
        this.person_name = person_name;
        this.age = age;
    }

    public Integer getPerson_id() {
        return person_id;
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

    @Override
    public String toString() {
        return person_name + ", " + age;
    }
}
