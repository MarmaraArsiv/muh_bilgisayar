import java.util.ArrayList;

/**
 * The DepartmentCourse class that holds department courses information and contains related methods
 */
public class DepartmentCourse extends Course {
    /**
     * name of the course
     */
    private String courseName;
    /**
     * List of students enrolled in the course
     */
    private ArrayList<Student> registeredStudents;
    /**
     * List of Prerequisite courses of the course
     */
    private ArrayList<DepartmentCourse> prerequisites;
    /**
     * List of Prerequisite to courses of the course
     */
    private ArrayList<DepartmentCourse> prerequisiteTo;
    /**
     * List of the lab sections of the course
     */
    private ArrayList<LabCourse> labSections;
    /**
     * List of the teaching assistant of the course
     */
    private ArrayList<TeachingAssistant> teachingAssistants;
    /**
     * In Lecturer class lecturer of the course
     */
    private Lecturer lecturer;
    /**
     * Lecture hours of the course
     */
    private int lectureHours;
    /**
     * Semester of the course
     */
    private int semester;
    /**
     * Credit number of the course
     */
    private int credits;
    /**
     * Lab hours of the course
     */
    private int labHours;
    /**
     * Minimum completed credit number of the course
     */
    private int minCompletedCredits;

    /**
     * Store the detailed data of course
     *
     * @param courseId     private ID for course
     * @param courseName   Name of the course
     * @param credits      Credit for the course
     * @param semester     The semester period to which the course belongs
     * @param lectureHours The weekly class hours the course has
     */
    public DepartmentCourse(String courseId, String courseName, int credits, int semester, int lectureHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.lectureHours = lectureHours;
        this.registeredStudents = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.prerequisiteTo = new ArrayList<>();
        this.labSections = new ArrayList<>();
        this.teachingAssistants = new ArrayList<>();
    }

    public DepartmentCourse() {
    }

    /**
     * Adds a prerequisite to the course
     *
     * @param course DepartmentCourse Object
     */
    public void addPrerequisite(DepartmentCourse course) {
        prerequisites.add(course);
    }

    /**
     * Adds a prerequisite_to to the course
     *
     * @param course DepartmentCourse Object
     */
    public void addPrerequisiteTo(DepartmentCourse course) {
        prerequisiteTo.add(course);
    }

    /**
     * Sets lab hours of course
     *
     * @param labHours Lab hours of the course
     */
    public void setLabHours(int labHours) {
        this.labHours = labHours;
    }

    /**
     * Returns the number of lab sections of the course
     *
     * @return number of lab sections
     */
    public int labSectionsCount() {
        return labSections.size();
    }

    /**
     * Adds a lab section to the course
     *
     * @param labCourse LabCourse Object
     */
    public void addLabSection(LabCourse labCourse) {
        labSections.add(labCourse);
    }

    /**
     * Adds a teaching assistant to the course
     *
     * @param teachingAssistant TeachingAssistant Object
     */
    public void addTeachingAssistant(TeachingAssistant teachingAssistant) {
        teachingAssistants.add(teachingAssistant);
    }

    /**
     * If the given index is less than size, it gives the teaching assistant.
     *
     * @param index index of list
     * @return teaching Asisstant of course
     */
    public TeachingAssistant getTeachingAssistant(int index) {
        if (teachingAssistants.size() > index) {
            return teachingAssistants.get(index);
        } else {
            return null;
        }
    }

    /**
     * Sets lab hours of course
     *
     * @param lecturer Lecturer of the Course
     */
    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    /**
     * Gets course name of the course
     *
     * @return course name of the course
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets prerequisites of the course
     *
     * @return prerequisites of the course
     */
    public ArrayList<DepartmentCourse> getPrerequisites() {
        return prerequisites;
    }

    /**
     * Gets Lab section of the course
     *
     * @return lab sections of the course
     */
    public ArrayList<LabCourse> getLabSections() {
        return labSections;
    }

    /**
     * Gets Semester of the course
     *
     * @return Semester of the course
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Gets credits of the course
     *
     * @return credits of the course
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Gets the list of registered students.
     *
     * @return list of registered students.
     */
    public ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    /**
     * Gets the list of PrerequisiteTo of the course
     *
     * @return list of PrerequisiteTo of the course
     */
    public ArrayList<DepartmentCourse> getPrerequisiteTo() {
        return prerequisiteTo;
    }

    /**
     * Gets list of Teaching Assistant of the course
     *
     * @return list of Teaching Assistant of the course
     */
    public ArrayList<TeachingAssistant> getTeachingAssistants() {
        return teachingAssistants;
    }

    /**
     * Gets lecturer of the course
     *
     * @return lecturer of the course
     */
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * Gets lecture hours of the course
     *
     * @return lecture hours of the course
     */
    public int getLectureHours() {
        return lectureHours;
    }

    /**
     * Gets lab hours of the course
     *
     * @return lab hours of the course
     */
    public int getLabHours() {
        return labHours;
    }

    /**
     * Sets semester of the course
     *
     * @param semester The semester the course is in
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * Sets course id of the course
     *
     * @param courseId Private id of the course
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets Minimum number of completed credits
     *
     * @return Minimum number of completed credits
     */
    public int getMinCompletedCredits() {
        return minCompletedCredits;
    }

    /**
     * Sets Minimum number of completed credits
     *
     * @param minCompletedCredits number of completed credits minimum
     */
    public void setMinCompletedCredits(int minCompletedCredits) {
        this.minCompletedCredits = minCompletedCredits;
    }
}