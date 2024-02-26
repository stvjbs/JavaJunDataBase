package org.example.task2;


import org.example.models.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Program {
    public static void main(String[] args) {


        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Student student = Student.create();

            session.save(student);
            System.out.println("Object student save successfully");

            Student retrievedStudent = session.get(Student.class, student.getId());
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}