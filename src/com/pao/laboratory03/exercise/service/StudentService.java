package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exceptions.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;

public class StudentService {
    private static StudentService instance;
    private List<Student> students;

    private StudentService() {
        students = new ArrayList<>();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equals(name)) {
                throw new RuntimeException("Studentul există deja!");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equals(name)) return s;
        }
        throw new StudentNotFoundException("Studentul nu a fost găsit!");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student s = findByName(studentName);
        s.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu există studenți.");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s);

            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                System.out.println("   " + entry.getKey().name() + " = " + entry.getValue());
            }
        }
    }

    public void printTopStudents() {
        System.out.println("=== Top studenți ===");

        students.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s.getName() +
                    " — media: " + String.format("%.2f", s.getAverage()));
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> result = new HashMap<>();

        for (Subject subject : Subject.values()) {
            double sum = 0;
            int count = 0;

            for (Student s : students) {
                if (s.getGrades().containsKey(subject)) {
                    sum += s.getGrades().get(subject);
                    count++;
                }
            }

            if (count > 0) {
                result.put(subject, sum / count);
            }
        }

        return result;
    }
}