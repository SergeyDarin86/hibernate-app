package org.example.modelSchool;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class SchoolDirector {

    @Id
    @Column(name = "director_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer directorId;

    @Column(name = "full_name")
    private String fullName;

    @OneToOne(mappedBy = "schoolDirector")
    @Cascade(value = {CascadeType.PERSIST, CascadeType.REMOVE})
    private School school;

}
