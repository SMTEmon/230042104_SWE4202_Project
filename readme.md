# Student Management System

A Java Swing desktop application for managing students, instructors, and courses in a 4-year bachelor program. Data is stored in simple `.txt` files for easy management and portability.

---

## Features

### Admin
- Register new students and instructors
- Create new courses and assign instructors
- Enroll students in courses
- View all registered students, instructors, and courses

### Instructor
- View assigned courses
- View students enrolled in their courses
- Assign and update grades

### Student
- View personal profile
- View enrolled courses and grades
- View transcript with semester-wise GPA and cumulative CGPA

---

## Project Structure

```
src/
├── data/
│   ├── courses.txt
│   ├── enrollments.txt
│   ├── instructors.txt
│   ├── login.txt
│   └── students.txt
└── studentmanagementsystem/
    ├── Main.java
    ├── model/
    │   ├── Course.java
    │   ├── Instructor.java
    │   └── Student.java
    ├── ui/
    │   ├── AdminDashboard.java
    │   ├── InstructorDashboard.java
    │   ├── LoginFrame.java
    │   └── StudentDashboard.java
    └── util/
        └── FileHandler.java
```

---

## Getting Started

### 1. Prerequisites

- Java JDK 8 or higher
- A terminal or command prompt

### 2. Compilation

Open a terminal and navigate to the project root directory (where `src/` is located):

```sh
# Compile all Java files, outputting .class files into src/
javac -d src src/studentmanagementsystem/Main.java src/studentmanagementsystem/model/*.java src/studentmanagementsystem/ui/*.java src/studentmanagementsystem/util/*.java
```

### 3. Running the Application

From the project root directory, run:

```sh
# Run the main class with src/ as the classpath
java -cp src studentmanagementsystem.Main
```

This will launch the login window.

---

## Data Files

All data is stored in the `src/data/` directory as plain text files:
- `courses.txt`
- `enrollments.txt`
- `instructors.txt`
- `login.txt`
- `students.txt`

Ensure these files exist and are formatted correctly for the application to function.

---

## Notes

- The application uses Java Swing for the GUI.
- No external libraries are required.
- For any issues, check that your working directory is set correctly and all data files are present.

---

## Author
S.M. Tahsinuzzaman Emon
ID: 230042104