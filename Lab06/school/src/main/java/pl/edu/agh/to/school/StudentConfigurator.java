package pl.edu.agh.to.school;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.course.CourseRepository;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeRepository;
import pl.edu.agh.to.school.student.Student;
import pl.edu.agh.to.school.student.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfigurator {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;

    public StudentConfigurator(StudentRepository studentRepository, CourseRepository courseRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
    }

    @PostConstruct
    public void init(){

        gradeRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();

        if(studentRepository.count()<3){
            Student kowalski = new Student("Jan", "Kowalski", LocalDate.now(), "123456");
            Student budynek = new Student("Piotr", "Budynek", LocalDate.now(), "6547891");
            Student menczystaty = new Student("Henryk", "Menczystaty", LocalDate.now(), "848565");

            studentRepository.saveAll(List.of(kowalski, budynek, menczystaty));
            var to = new Course("Technologie obiektowe");
            var po = new Course("Programowanie obiektowe");

            to.assignStudent(kowalski);
            to.assignStudent(budynek);

            po.assignStudent(menczystaty);

            Grade grade1 = new Grade(5);
            kowalski.giveGrade(grade1);

            Grade grade2 = new Grade(4);
            kowalski.giveGrade(grade2);

            Grade grade3 = new Grade(3);
            budynek.giveGrade(grade3);

            Grade grade4 = new Grade(2);
            menczystaty.giveGrade(grade4);

            courseRepository.saveAll(List.of(to, po));
            gradeRepository.saveAll(List.of(grade1, grade2, grade3, grade4));
        }
    }
}
