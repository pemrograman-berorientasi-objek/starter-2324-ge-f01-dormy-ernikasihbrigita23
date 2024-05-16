package pbo.f01.model;

/**
 * 12S22013 - Christoffel Theofani Napitupulu
 * 12S22047 - Erni Kasih B. Sarumaha
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Dorm")
public class dorm {
    @Id
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "capacity", nullable = false, length = 25)
    private int capacity;

    @Column(name = "gender", nullable = false, length = 25)
    private String gender;

    @OneToMany(mappedBy = "dorm", targetEntity = student.class, cascade = CascadeType.ALL)
    private List<student> students = new ArrayList<>();

    // Default constructor
    public dorm() {
    }

    // Parameterized constructor
    public dorm(String name, int capacity, String gender) {
        this.name = name;
        this.capacity = capacity;
        this.gender = gender;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<student> getStudents() {
        return students;
    } 

    public boolean addStudent(student student) {
        if (students.size() < capacity && student.getGender().equals(this.gender)) {
            students.add(student);
            student.setDorm(this);
            return true;
        }
        return false;
    }
}
