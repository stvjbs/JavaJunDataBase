package org.example.models;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
public class Course {
    // Fields:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    int durationInMinutes;

    //Constructors:
    public Course(int id, String title, int durationInMinutes) {
        this.id = id;
        this.title = title;
        this.durationInMinutes = durationInMinutes;
    }

    public Course(String title, int durationInMinutes) {
        this.title = title;
        this.durationInMinutes = durationInMinutes;
    }

    public Course() {
    }

    // Fabric pattern
    public static Course createCourse(String title, int durationInMinutes) {
        return new Course(title, durationInMinutes);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDuration(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + durationInMinutes +
                '}';
    }
}
