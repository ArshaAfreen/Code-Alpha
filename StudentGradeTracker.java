import java.util.ArrayList;
import java.util.Scanner;

class Student{
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("==== Student Grade Tracker ====");
        System.out.print("Enter number of students: ");
        int count = scanner.nextInt();
        scanner.nextLine();  // consume newline

        // Input data
        for (int i = 0; i < count; i++) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter grade for " + name + ": ");
            double grade = scanner.nextDouble();
            scanner.nextLine();  // consume newline

            students.add(new Student(name, grade));
        }

        // Calculate statistics
        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        Student topStudent = null;
        Student bottomStudent = null;

        for (Student s : students) {
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s;
            }

            if (s.grade < lowest) {
                lowest = s.grade;
                bottomStudent = s;
            }
        }

        double average = total / students.size();

        // Display summary report
        System.out.println("\n==== Grade Summary Report ====");
        for (Student s : students) {
            System.out.println(s.name + " - Grade: " + s.grade);
        }

        System.out.printf("\nAverage Grade: %.2f\n", average);
        System.out.println("Highest Grade: " + highest + " (by " + topStudent.name + ")");
        System.out.println("Lowest Grade: " + lowest + " (by " + bottomStudent.name + ")");
    }
}
