package org.example.modelSchool;

import jakarta.persistence.*;
import org.example.modelMovie.Movie;

import java.util.List;

@Entity
@Table(name = "school")
public class School {

    @Id
    @Column(name = "school_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schoolId;

    @Column(name = "school_number")
    private Integer schoolNumber;

    @OneToOne
    @JoinColumn(name = "director_id", referencedColumnName = "director_id")
    private SchoolDirector schoolDirector;

    public School() {
    }

    public School(Integer schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public Integer getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(Integer schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public SchoolDirector getSchoolDirector() {
        return schoolDirector;
    }

    public void setSchoolDirector(SchoolDirector schoolDirector) {
        this.schoolDirector = schoolDirector;
    }

    @Override
    public String toString() {
        return "School{" +
                "schoolNumber=" + schoolNumber +
                '}';
    }
}
