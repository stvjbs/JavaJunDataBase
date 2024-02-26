package org.example.task3HW;
import org.example.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {
    private static final Random random = new Random();
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {

                insertCourses(session);

                readCourse(session, random.nextInt(1, 4));
                readCourse(session, random.nextInt(1, 4));

                updateCourse(session, random.nextInt(1, 4),
                        "English", 55);
                deleteCourse(session, random.nextInt(1, 4));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    private static List<Course> courseCreator() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.createCourse("Math", 90));
        courseList.add(Course.createCourse("Biology", 60));
        courseList.add(Course.createCourse("P.E.", 45));
        courseList.add(Course.createCourse("Physics", 75));
        return courseList;
    }
    private static void insertCourses(Session session) {
        Transaction transaction = session.beginTransaction();
        courseCreator().forEach(session::save);
        transaction.commit();
        System.out.println("Courses was successfully inserted");
    }
    private static void readCourse(Session session, int id) {
        Transaction transaction = session.beginTransaction();
        Course retrievedCourse = session.load(Course.class, id);
        System.out.println(retrievedCourse);
        transaction.commit();
        System.out.println("Course was successfully readed");
    }
    private static void updateCourse(Session session, int id, String newTitle, int newDuration) {
        Transaction transaction = session.beginTransaction();
        Course retrievedCourse = session.load(Course.class, id);
        retrievedCourse.updateTitle(newTitle);
        retrievedCourse.updateDuration(newDuration);
        session.update(retrievedCourse);
        transaction.commit();
        System.out.println("Course was successfully updated");
    }
    private static void deleteCourse(Session session, int id) {
        Transaction transaction = session.beginTransaction();
        Course retrievedCourse = session.load(Course.class, id);
        session.delete(retrievedCourse);
        transaction.commit();
        System.out.println("Course was successfully deleted");
    }
}



