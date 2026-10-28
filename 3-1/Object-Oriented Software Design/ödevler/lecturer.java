import java.util.ArrayList;
import java.util.List;

/**
 * The Lecturer class extends Person class that includes lecturer informations and contains related methods
 */
public class Lecturer extends Person {
    /**
     * Courses given by Lecturer
     */
    private ArrayList<DepartmentCourse> givenCourses;
    /**
     * Advised students by Lecturer
     */
    private ArrayList<Student> advisedStudents;

    /**
     * Lecturer constructor
     *
     * @param name       Name of the lecturer to use
     * @param department Department of the lecturer to use
     */
    public Lecturer(String name, Department department) {
        super(name, department);
        givenCourses = new ArrayList<>();
        advisedStudents = new ArrayList<>();
    }

    /**
     * Add course to lecturer
     *
     * @param course Course to add
     */
    public void addCourse(DepartmentCourse course) {
        givenCourses.add(course);
    }

    /**
     * Add student to lecturer
     *
     * @param student Student to add
     */
    public void addStudent(Student student) {
        advisedStudents.add(student);
    }

    /**
     * Get given courses by lecturer
     *
     * @return Given courses by lecturer
     */
    public List<DepartmentCourse> getGivenCourses() {
        return givenCourses;
    }

    /**
     * Get advised students by lecturer
     *
     * @return Advised students by lecturer
     */
    public List<Student> getAdvisedStudents() {
        return advisedStudents;
    }
}