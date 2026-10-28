import java.util.ArrayList;

public abstract class Course {
    /**
     * id of the course
     */
    public String courseId;
    /**
     * Schedule of course
     */
    public ArrayList<int[]> courseSchedule = new ArrayList<>();
    /**
     * All registered students of lab course
     */
    public ArrayList<Student> registeredStudents = new ArrayList<>();

    /**
     * Gets course Id of the course
     *
     * @return course Id of the course
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Gets course schdeule of the course
     *
     * @return course schedule
     */
    public ArrayList<int[]> getCourseSchedule() {
        return courseSchedule;
    }

    /**
     * Creates the course calendar in days and hours
     *
     * @param day         day of course
     * @param lectureHour hours of course
     */
    public void addLectureHour(int day, int lectureHour) {
        courseSchedule.add(new int[]{day, lectureHour});
    }

    /**
     * Adds a new student to the course enrollment list
     *
     * @param student Student Object
     */
    public void addStudent(Student student) {
        registeredStudents.add(student);
    }
}