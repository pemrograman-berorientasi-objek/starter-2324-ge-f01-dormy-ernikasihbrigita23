package pbo.f01;


/**
 * 12S22013 - Christoffel Theofani Napitupulu
 * 12S22047 - Erni Kasih B. Sarumaha
 */

import pbo.f01.model.*;
import javax.persistence.*; 
import java.util.List;
import java.util.Scanner;

public class App {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dormy_pu");
    private static final EntityManager em = emf.createEntityManager();
    
    public static void main(String[] _args) {
        cleanDb();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if (command.equals("---")) {
                break;
            }
            processCommand(command);
        }

        scanner.close();
        em.close();
        emf.close();
    }

    private static void cleanDb() {
        String[] jpqa = { "DELETE FROM student", "DELETE FROM dorm" };

        for (String query : jpqa) {
            em.getTransaction().begin();
            em.createQuery(query).executeUpdate();
            em.flush();
            em.getTransaction().commit();
        }
    }

    private static void processCommand(String command) {
        if (command.startsWith("dorm-add#")) {
            addDorm(command);
     } else if (command.startsWith("student-add#")){
        addStudent(command);
     } else if (command.startsWith("assign#")) {
        assignStudent(command);
     } else if (command.equals("display-all")){
        displayAll();
     }
    }
    private static void addDorm(String command) {
        String[] parts = command.split("#");
        if (parts.length == 4) {
            String name = parts[1];
            int capacity = Integer.parseInt(parts[2]);
            String gender = parts[3];

            em.getTransaction().begin();
            dorm dorm = new dorm(name, capacity, gender);
            em.persist(dorm);
            em.getTransaction().commit();
        }
    }

    private static void addStudent(String command) {
    String[] parts = command.split("#");
    if (parts.length == 5) {
        String id = parts[1];
        String name = parts[2];
        int year = Integer.parseInt(parts[3]);
        String gender = parts[4];

        // Check if student already exists
        student existingStudent = em.find(student.class, id);
        if (existingStudent == null) {
            em.getTransaction().begin();
            student student = new student(id, name, year, gender);
            em.persist(student);
            em.getTransaction().commit();
        }
    }
}

    private static void assignStudent(String command) {
        String[] parts = command.split("#");
        if (parts.length == 3) {
            String studentId = parts[1];
            String dormName = parts[2];

            student student = em.find(student.class, studentId);
            dorm dorm = em.find(dorm.class, dormName);
            
            if (student != null && dorm != null && student.getGender().equals(dorm.getGender()) && dorm.getCapacity() > dorm.getStudents().size()) {
                em.getTransaction().begin();
                dorm.getStudents().add(student);
                em.merge(dorm);
                em.merge(student);
                em.flush();
                em.getTransaction().commit();
            } 
        }
    }

    private static void displayAll() {
        String query = "SELECT d FROM dorm d ORDER BY d.name";
        em.getTransaction().begin();
        List<dorm> dorms =  em.createQuery(query, dorm.class).getResultList();

        for (dorm dorm : dorms) {
            System.out.printf("%s|%s|%d|%d%n", dorm.getName(), dorm.getGender(), dorm.getCapacity(),
                dorm.getStudents().size());
            dorm.getStudents().stream()
                .sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
                .forEach(student -> System.out.printf("%s|%s|%d%n", student.getid(), student.getName(),
                    student.getEntranceYear()));
        }
    }
}
