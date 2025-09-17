package studentmanagementsystem.model;

public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private String instructorId;

    public Course(String courseCode, String title, int credits, String instructorId) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.instructorId = instructorId;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getInstructorId() {
        return instructorId;
    }

    @Override
    public String toString() {
        return courseCode + "," + title + "," + credits + "," + instructorId;
    }
}
