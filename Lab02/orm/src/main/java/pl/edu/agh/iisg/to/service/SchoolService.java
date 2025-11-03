package pl.edu.agh.iisg.to.service;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;
import pl.edu.agh.iisg.to.session.SessionService;
import pl.edu.agh.iisg.to.session.TransactionService;

import javax.transaction.Transaction;
import java.util.*;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentDao studentDao;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    private final StudentRepository studentRepository;

    public SchoolService(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
        this.studentRepository = new StudentRepository(studentDao, courseDao, gradeDao);
    }

    public boolean enrollStudent(final Course course, final Student student) {
        return transactionService.doAsTransaction(() -> {
            if(course.studentSet().contains(student)) {
                return false;
            }
            course.studentSet().add(student);
            student.courseSet().add(course);
            return true;
        }).orElse(false);
    }

    public boolean removeStudent(int indexNumber) {
//        return transactionService.doAsTransaction(() -> {
//            Optional<Student> student = studentDao.findByIndexNumber(indexNumber);
//            if (student.isEmpty()) return false;
//
//            for (Course course : student.get().courseSet()) {
//                course.studentSet().remove(student.get());
//                student.get().courseSet().remove(course);
//            }
//
//            for (Grade grade : student.get().gradeSet()) {
//                gradeDao.remove(grade);
//                student.get().gradeSet().remove(grade);
//            }
//
//            studentDao.remove(student.get());
//
//            return true;
//        }).orElse(false);

        return transactionService.doAsTransaction(() -> {
            Optional<Student> student = studentDao.findByIndexNumber(indexNumber);
            if (student.isEmpty()) return false;

            studentRepository.remove(student.get());

            return true;
        }).orElse(false);
    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        return transactionService.doAsTransaction(() -> {
            // if(!course.studentSet().contains(student)) return false;
            Grade grade = new Grade(student, course, gradeValue);
            student.gradeSet().add(grade);
            course.gradeSet().add(grade);
            gradeDao.save(grade);
            return true;
        }).orElse(false);
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        Optional<Course> course = courseDao.findByName(courseName);
        if (course.isEmpty()) return Collections.emptyMap();

        // Set<Student> studentSet = course.get().studentSet();
        List<Student> studentList = studentRepository.findAllByCourseName(courseName);
        Set<Grade> gradeSet = course.get().gradeSet();

        Map<String, List<Float>> studentGrades = new HashMap<>();

        for (Student student : studentList) {
            studentGrades.put(student.fullName(), new ArrayList<>());
        }
        for (Grade grade : gradeSet) {
            studentGrades
                    .get(grade.student().fullName())
                    .add(grade.grade());
        }
        for (List<Float> grades : studentGrades.values()) {
            Collections.sort(grades);
        }

        return studentGrades;
    }
}
