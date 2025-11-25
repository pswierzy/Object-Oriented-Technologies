package pl.edu.agh.to.school.grade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int gradeValue;

    public Grade(int value) {
        this.gradeValue = value;
    }

    public Grade() {
    }

    public int getId() {
        return id;
    }

    public int getGradeValue() {
        return gradeValue;
    }
}
