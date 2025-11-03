package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements Repository<Student> {

    StudentDao studentDao;
    CourseDao courseDao;
    GradeDao gradeDao;

    public StudentRepository(StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        for (Course course : student.courseSet()) {
            course.studentSet().remove(student);
            student.courseSet().remove(course);
        }
        for (Grade grade : student.gradeSet()) {
            gradeDao.remove(grade);
            student.gradeSet().remove(grade);
        }
        studentDao.remove(student);
    }

    public List<Student> findAllByCourseName(String courseName) {
        Optional<Course> course = courseDao.findByName(courseName);
        List<Student> students = new ArrayList<>();

        if (course.isEmpty()) return students;

        students.addAll(course.get().studentSet());

        return students;
    }
}
