package studentmanagementsystem.model;

public class Student {
    private String id;
    private String name;
    private String department;

    public Student(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + department;
    }
}
