package org.example.modelSchool;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "school_director")
public class SchoolDirector {

    @Id
    @Column(name = "director_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer directorId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private Integer age;

    @OneToOne(mappedBy = "schoolDirector")
    @Cascade(value = {CascadeType.PERSIST})
    private School school;

    public SchoolDirector() {
    }

    public SchoolDirector(String fullName, Integer age, School school) {
        this.fullName = fullName;
        this.age = age;
        this.school = school;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return fullName +  ", " + age;
    }
}
