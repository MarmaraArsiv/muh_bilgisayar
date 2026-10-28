import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * The Student class that holds student information and contains related methods. This is a subtype of the class Person
 */
public class Student extends Person {
    /**
     * Id of student
     */
    private int studentID;
    /**
     * Semester of student
     */
    private int semester;
    /**
     * Registered year of student
     */
    private int registerYear;
    /**
     * Advisor of student from Lecturer class
     */
    private Lecturer advisor;
    /**
     * Taken Credits list of the student
     */
    private int[] takenCredits;
    /**
     * All courses list of the student
     */
    private ArrayList<DepartmentCourse> allCourses;
    /**
     * {HashMap} Elective course choices of the student
     */
    private HashMap<ElectiveCourse, HashMap<String, Integer>> electiveCourseChoices;
    /**
     * {HashSet} Taken Elective courses of the student
     */
    private HashSet<ElectiveCourse> takenElectives;
    /**
     * {HashMap} Newly taken courses of the student
     */
    private HashMap<DepartmentCourse, StudentCourse> newlyTakenCourses;
    /**
     * {HashMap} Previous courses of the student
     */
    private HashMap<DepartmentCourse, StudentCourse> previousCourses;
    /**
     * {HashSet} Failed courses of the student
     */
    private HashSet<DepartmentCourse> failedCourses;
    /**
     * {HashSet} Passed courses of the student
     */
    private HashSet<DepartmentCourse> passedCourses;
    /**
     * transcript of the student From Transcript class
     */
    private Transcript transcript;
    /**
     * timetable of the student From StudentTimeTable class
     */
    private StudentTimetable timetable;
    /**
     * Education level of the student.
     */
    private String educationLevel;
    /**
     * List of the Registration Messages of the students.
     */
    private ArrayList<String> registrationMessages;
    /**
     * Logger instance for logging.
     */
    private final Logger logger
            = Logger.getLogger(
            Student.class.getName());

    /**
     * Constructor of the Student
     *
     * @param name       {String}	name of the student
     * @param department {Object}	Department
     */
    public Student(String name, Department department) {
        super(name, department);
        timetable = new StudentTimetable(this);
        newlyTakenCourses = new HashMap<>();
        previousCourses = new HashMap<>();
        registrationMessages = new ArrayList<>();
        allCourses = new ArrayList<>();
        electiveCourseChoices = new HashMap<>();
        takenElectives = new HashSet<>();
        takenCredits = new int[8];
        failedCourses = new HashSet<>();
        passedCourses = new HashSet<>();
    }

    /**
     * Takes all mandatory courses until current semester
     */
    public void takeMandatoryCourses() {
        for (int i = 1; i <= semester; i++) {
            ArrayList<DepartmentCourse> semesterCourses = this.getDepartment().getCurriculum().getSemester(i);
            for (DepartmentCourse course : semesterCourses) {
                if (!(course instanceof ElectiveCourse)) {
                    allCourses.add(course);
                    logger.info("Student " + this.getName() + " has taken " + course.getCourseName());
                }
            }
        }
    }

    /**
     * Takes all elective courses until current semester
     */
    public void takeElectiveCourses() {
        HashMap<String, DepartmentCourse> nonTechnicalElectives = this.getDepartment().getNonTechnicalElectiveCourses();
        HashMap<String, DepartmentCourse> technicalElectives = this.getDepartment().getTechnicalElectiveCourses();
        HashMap<String, DepartmentCourse> facultyElectives = this.getDepartment().getFacultyElectiveCourses();
        HashMap<String, DepartmentCourse> universityElectives = this.getDepartment().getUniversityElectiveCourses();
        for (int i = 1; i <= semester; i++) {
            ArrayList<DepartmentCourse> semesterCourses = this.getDepartment().getCurriculum().getSemester(i);
            for (DepartmentCourse course : semesterCourses) {
                if (course instanceof ElectiveCourse) {
                    ArrayList<DepartmentCourse> electiveCourses = new ArrayList<>();
                    switch (((ElectiveCourse) course).getType()) {
                        case "NTE":
                            electiveCourses.addAll(nonTechnicalElectives.values());
                            break;
                        case "TE":
                            electiveCourses.addAll(technicalElectives.values());
                            break;
                        case "FTE":
                            electiveCourses.addAll(facultyElectives.values());
                            break;
                        case "UE":
                            electiveCourses.addAll(universityElectives.values());
                            break;
                    }
                    // Get random elective course from the list
                    int randomIndex = (int) (Math.random() * electiveCourses.size());
                    ElectiveCourse randomElectiveCourse = (ElectiveCourse) electiveCourses.get(randomIndex);
                    String message = "";
                    if (takenElectives.contains(randomElectiveCourse)) {
                        message = String.format("Student could not take %s because it has already taken in previous semesters", randomElectiveCourse.getCourseId());
                        addRegistrationMessage(message);
                        logger.warning(message);
                    }
                    if (electiveCourseChoices.get(randomElectiveCourse) != null) {
                        message = String.format("Student could not take %s because it has already taken another %s course", randomElectiveCourse.getCourseId(), course.getCourseId());
                        addRegistrationMessage(message);
                        logger.warning(message);
                    }
                    if (message.isEmpty()) {
                        allCourses.add(randomElectiveCourse);
                        electiveCourseChoices.put(randomElectiveCourse, new HashMap<>());
                        electiveCourseChoices.get(randomElectiveCourse).put(course.getCourseId(), i);
                        takenElectives.add(randomElectiveCourse);
                        logger.info("Student " + this.getName() + " has taken " + randomElectiveCourse.getCourseId());
                    }
                }
            }
        }
    }

    /**
     * Return which semester the student has taken the elective course
     *
     * @param electiveCourse Elective course
     * @return Semester number 1-8
     */
    public int getElectiveSemester(ElectiveCourse electiveCourse) {
        int semester = 0;
        HashMap<String, Integer> choice = electiveCourseChoices.get(electiveCourse);
        if (choice != null) {
            for (String courseId : choice.keySet()) {
                semester = choice.get(courseId);
                break;
            }
        }
        return semester;
    }

    /**
     * Creates StudentCourse objects for all courses taken by the student
     */
    public void createStudentCourses() {
        for (DepartmentCourse course : allCourses) {
            StudentCourse studentCourse = new StudentCourse(course, RandomStudentGenerator.randomGrade(), course.getSemester());
            studentCourse.checkRules(this, course);
        }
    }

    /**
     * Create a transcript for the student
     *
     * @param includeNewCourses Include courses from the current semester
     * @return Transcript object
     */
    public Transcript generateTranscript(boolean includeNewCourses) {
        transcript = new Transcript(this, includeNewCourses);
        return transcript;
    }

    /**
     * Adds a new course to the student's timetable
     *
     * @param course   Course to be added
     * @param grade    Grade of the course
     * @param semester In which semester the course was taken
     */
    public void registerCourse(DepartmentCourse course, int grade, int semester) {
        StudentCourse studentCourse = new StudentCourse(course, grade, semester);
        if (semester == this.semester) {
            if (newlyTakenCourses.size() == this.getDepartment().getCourseTakeLimit()) {
                addRegistrationMessage(String.format("Student could not take %s because reached the course take limit.", course.getCourseId()));
                return;
            } else {
                newlyTakenCourses.put(course, studentCourse);
                timetable.assignCourse(course);
                course.addStudent(this);
            }
        } else {
            if (!studentCourse.isPassed()) {
                failedCourses.add(studentCourse.getMainCourse());
            } else {
                passedCourses.add(studentCourse.getMainCourse());
            }
            previousCourses.put(course, studentCourse);
        }
        takenCredits[semester - 1] += course.getCredits();
    }

    /**
     * Checks if the student passed prerequisites for the course
     *
     * @param course Course to check prerequisites for
     * @return Failed prerequisite course id
     */
    public ArrayList<DepartmentCourse> getFailedPrerequisites(DepartmentCourse course) {
        ArrayList<DepartmentCourse> failedPrerequisites = new ArrayList<>();
        for (DepartmentCourse prerequisite : course.getPrerequisites()) {
            if (!passedCourses.contains(prerequisite)) {
                failedPrerequisites.add(prerequisite);
            }
        }
        return failedPrerequisites;
    }

    /**
     * Adds registration message if any conditions are not met
     *
     * @param message Formatted message to be added
     */
    public void addRegistrationMessage(String message) {
        registrationMessages.add(message);
    }

    /**
     * Gets Passed courses of student
     *
     * @return HashSet String passedCourses
     */
    public HashSet<DepartmentCourse> getPassedCourses() {
        return passedCourses;
    }

    /**
     * Sets Passed courses of the student
     *
     * @param studentCourse {Object}
     */
    public void addPassedCourse(StudentCourse studentCourse) {
        passedCourses.add(studentCourse.getMainCourse());
    }

    /**
     * Gets id of the student
     *
     * @return {integer} studentID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Sets id of the student.
     *
     * @param studentID {integer} id of the student
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Gets semester of the student.
     *
     * @return {integer} semester
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Sets semester of the student.
     *
     * @param semester {integer} semester of the student.
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * Gets Advisor of the student
     *
     * @return {Object} advisor
     */
    public Lecturer getAdvisor() {
        return advisor;
    }

    /**
     * Sets advisor of the student
     *
     * @param advisor {Object} advisor of the student
     */
    public void setAdvisor(Lecturer advisor) {
        this.advisor = advisor;
    }

    /**
     * Gets time table of the student.
     *
     * @return {Object} timetable
     */
    public StudentTimetable getTimetable() {
        return timetable;
    }

    /**
     * Gets newly taken courses of the student
     *
     * @return HashMap String, StudentCourse newlyTakenCourses
     */
    public HashMap<DepartmentCourse, StudentCourse> getNewlyTakenCourses() {
        return newlyTakenCourses;
    }

    /**
     * Gets previous courses of the student.
     *
     * @return HashMap String, StudentCourse previousCourses
     */
    public HashMap<DepartmentCourse, StudentCourse> getPreviousCourses() {
        return previousCourses;
    }

    /**
     * Gets Education level of the student
     *
     * @return {String} educationlevel
     */
    public String getEducationLevel() {
        return educationLevel;
    }

    /**
     * Sets education level of the student
     *
     * @param educationLevel {String}	education level of the student
     */
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    /**
     * Gets registered year of the student
     *
     * @return {integer}	registerYear
     */
    public int getRegisterYear() {
        return registerYear;
    }

    /**
     * Sets registered year of the student
     *
     * @param registerYear {integer} registered year of the student
     */
    public void setRegisterYear(int registerYear) {
        this.registerYear = registerYear;
    }

    /**
     * Gets transcript of the student
     *
     * @return {Object}	transcript
     */
    public Transcript getTranscript() {
        return transcript;
    }

    /**
     * Gets list of the Registration messages
     *
     * @return ArrayList String	registrationMessages
     */
    public ArrayList<String> getRegistrationMessages() {
        return registrationMessages;
    }

    /**
     * Gets list of the all courses belongs to the student
     * @return ArrayList StudentCourse	allCourses
     */
    public ArrayList<DepartmentCourse> getAllCourses() {
        return allCourses;
    }

    /**
     * Calculates the sum of the credits taken up to the given semester.
     *
     * @param semester {integer} semester
     * @return cumulative Credits taken up to the given semester.
     */
    public int getTakenCredits(int semester) {
        int cumulativeCredits = 0;
        for (int i = 0; i < semester; i++) {
            cumulativeCredits += takenCredits[i];
        }
        return cumulativeCredits;
    }

    /**
     * Calculates the completed of the credits taken up to the given semester.
     *
     * @param semester {integer} semester
     * @return completed of the credits taken up to the given semester.
     */
    public int getCompletedCredits(int semester) {
        int completedCredits = 0;
        for (DepartmentCourse course : allCourses) {
            if (!failedCourses.contains(course)) {
                if (course instanceof ElectiveCourse && getElectiveSemester((ElectiveCourse) course) > semester) {
                    continue;
                }
                completedCredits += course.getCredits();
            }
        }
        return completedCredits;
    }
}
