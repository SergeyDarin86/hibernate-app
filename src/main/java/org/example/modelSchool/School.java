package org.example.modelSchool;

import jakarta.persistence.*;

@Entity
public class School {

    @Id
    @Column(name = "school_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schoolId;

    @OneToOne
    @JoinColumn(name = "director_id", referencedColumnName = "director_id")
    private SchoolDirector schoolDirector;

}
