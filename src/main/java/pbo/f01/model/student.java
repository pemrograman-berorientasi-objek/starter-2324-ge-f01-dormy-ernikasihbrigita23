package pbo.f01.model;

/**
 * 12S22013 - Christoffel Theofani Napitupulu
 * 12S22047 - Erni Kasih B. Sarumaha
 */

 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.Table;
 import javax.persistence.Id;
 import javax.persistence.ManyToOne;
 import javax.persistence.JoinColumn;

 @Entity 
 @Table(name = "students")
 public class student {
     @Id
     @Column(name = "studentId", nullable = false, length = 25)
     private String id;
 
     @Column(name = "name", nullable = false, length = 25)
     private String name;
 
     @Column(name = "entranceYear", nullable = false, length = 25)
     private int entranceYear;
 
     @Column(name = "gender", nullable = false, length = 25)
     private String gender;
 
     @ManyToOne(targetEntity = dorm.class)
     @JoinColumn(name = "dorm_name")
     private dorm dorm;
 
     public student() {
     }
 
     public student(String id, String name, int entranceYear, String gender) {
         this.id = id;
         this.name = name;
         this.entranceYear = entranceYear;
         this.gender = gender;
     }
 
     public String getid() {
         return id;
     }
 
     public void setid(String id) {
         this.id = id;
     }
 
     public String getName() {
         return name;
     }
 
     public void setName(String name) {
         this.name = name;
     }
 
     public int getEntranceYear() {
         return entranceYear;
     }
 
     public void setEntranceYear(int entranceYear) {
         this.entranceYear = entranceYear;
     }
 
     public String getGender() {
         return gender;
     }
 
     public void setGender(String gender) {
         this.gender = gender;
     }
 
     public dorm getDorm() {
         return dorm;
     }
 
     public void setDorm(dorm dorm) {
         this.dorm = dorm;
     }
 }

 