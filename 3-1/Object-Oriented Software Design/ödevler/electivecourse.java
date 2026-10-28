/**
 * The ElectiveCourse class extends DepartmentCourse class that includes elective course informations and contains related methods
 */
public class ElectiveCourse extends DepartmentCourse {
    /**
     * Capacity of elective course
     */
    private int capacity;
    /**
     * Type of elective course
     */
    private final String type;

    /**
     * ElectiveCourse constructor
     *
     * @param courseId     Id of the elective course
     * @param courseName   Name of the elective course
     * @param credits      Credits of the elective course
     * @param semester     Semester of the elective course
     * @param lectureHours Hours of the elective course
     * @param capacity     Capacity of the elective course
     * @param type         Type of the elective course (NTE, TE, etc.)
     */
    public ElectiveCourse(String courseId, String courseName, int credits, int semester, int lectureHours, int capacity, String type) {
        super(courseId, courseName, credits, semester, lectureHours);
        this.capacity = capacity;
        this.type = type;
    }

    /**
     * Add a student to elective course
     * If its capacity is not full, add it and reduce its capacity by one
     */
    public void addStudent(Student student) {
        if (this.capacity > 0) {
            super.addStudent(student);
            this.capacity--;
        }
    }

    /**
     * Get the type of elective course
     *
     * @return The type of elective course
     */
    public String getType() {
        return type;
    }

    /**
     * Decrease elective course capacity by 1
     */
    public void decreaseCapacity() {
        this.capacity--;
    }

    /**
     * Whether elective course has capacity or not
     *
     * @return Whether elective course has capacity or not
     */
    public boolean hasCapacity() {
        return this.capacity > 0;
    }

    /**
     * Get capacity of elective course
     *
     * @return Capacity of elective course
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set capacity of elective course
     *
     * @param capacity Capacity of this elective course
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}