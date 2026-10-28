import java.util.ArrayList;

/**
 * The LabCourse class implements Course class that includes lab course informations and contains related methods
 */
public class LabCourse extends Course {
    /**
     * Main course of lab course
     */
    private DepartmentCourse mainCourse;
    /**
     * Teaching assistant of lab course
     */
    private TeachingAssistant lecturer;
    /**
     * Section number of lab course
     */
    private int sectionNumber;
    /**
     * Capacity of lab course
     */
    private int capacity;

    /**
     * LabCourse constructor
     *
     * @param mainCourse Get main course to add lab lecture
     */
    public LabCourse(DepartmentCourse mainCourse) {
        this.mainCourse = mainCourse;
        /*
        It assigns a teaching assistant to the lab course as a lecturer.
        Example: For the first lab section of the main course it will assign the first TA as the lecturer.
        You can change order of assistants in input file.
         */
        this.lecturer = mainCourse.getTeachingAssistant(mainCourse.labSectionsCount());
        if (this.lecturer != null) {
            this.lecturer.assignLabCourse(this);
        }
        this.sectionNumber = mainCourse.labSectionsCount() + 1;
        this.courseId = mainCourse.getCourseId() + "." + sectionNumber;
        this.courseSchedule = new ArrayList<>();
    }

    /**
     * Add a student to elective course
     * If its capacity is not full, add it and reduce its capacity by one
     */
    public void addStudent(Student student) {
        if (this.capacity > 0) {
            registeredStudents.add(student);
            this.capacity--;
        }
    }

    /**
     * Set capacity of lab course
     *
     * @param capacity Capacity of lab course to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Whether lab course has capacity or not
     *
     * @return Whether lab course has capacity or not
     */
    public boolean hasCapacity() {
        return this.capacity > 0;
    }

    /**
     * Decrease lab course capacity by 1
     */
    public void decreaseCapacity() {
        this.capacity--;
    }

    /**
     * Get main course of lab course
     *
     * @return Main course of lab course
     */
    public DepartmentCourse getMainCourse() {
        return mainCourse;
    }

    /**
     * Get teaching assistant of lab course
     *
     * @return Teaching assistant of lab course
     */
    public TeachingAssistant getLecturer() {
        return lecturer;
    }

    /**
     * Get registered students of lab course
     *
     * @return Registered students of lab course
     */
    public ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    /**
     * Get section number of lab course
     *
     * @return Section number of lab course
     */
    public int getSectionNumber() {
        return sectionNumber;
    }

    /**
     * Get capacity of lab course
     *
     * @return Capacity of lab course
     */
    public int getCapacity() {
        return capacity;
    }
}
