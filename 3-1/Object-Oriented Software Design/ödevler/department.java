import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Department class that holds department information and contains related methods
 */
public class Department {
    /**
     * Name of the department
     */
    private String name;
    /**
     * Id of the department
     */
    private String id;
    /**
     * Faculty of the department
     */
    private String faculty;
    /**
     * Current Semester of the department
     */
    private String currentSemester;
    /**
     * Department number of the department
     */
    private int departmentNumber;
    /**
     * Number of the lecture days of the department
     */
    private int numberOfLectureDays;
    /**
     * Number of the lecture hours of the department
     */
    private int numberOfLectureHours;
    /**
     * Maximum credits of the department
     */
    private int maximumCredits;
    /**
     * Curriculum of the department
     */
    private Curriculum curriculum;
    /**
     * All courses of the department
     */
    private HashMap<String, DepartmentCourse> courses;
    /**
     * All mandatory courses of the department
     */
    private HashMap<String, DepartmentCourse> mandatoryCourses;
    /**
     * All technical elective courses of the department
     */
    private HashMap<String, DepartmentCourse> technicalElectiveCourses;
    /**
     * All non technical elective courses of the department
     */
    private HashMap<String, DepartmentCourse> nonTechnicalElectiveCourses;
    /**
     * All faculty elective courses of the department
     */
    private HashMap<String, DepartmentCourse> facultyElectiveCourses;
    /**
     * All university elective courses of the department
     */
    private HashMap<String, DepartmentCourse> universityElectiveCourses;
    /**
     * All lecturers of the department
     */
    private ArrayList<Lecturer> lecturers;
    /**
     * All teaching assistants of the department
     */
    private ArrayList<TeachingAssistant> teachingAssistants;
    /**
     * All students of the department
     */
    private ArrayList<Student> students;
    /**
     * Course take limit of the department
     */
    private int courseTakeLimit;
    /**
     * Lab capacity limit of the department
     */
    private int labCapacityLimit;
    /**
     * Elective capacity limit of the department
     */
    private int electiveCapacityLimit;
    /**
     * Course registration messages of the department
     */
    private final HashMap<String, HashMap<String, Integer>> courseRegistrationMessages;
    /**
     * Course registration messages of the department
     */
    private final String[] registrationMessageFormats = {
            "%s students couldn't register %s because of prerequisites did not met.",
            "%s students couldn't register %s because it has collision with another course.",
            "%s students couldn't register %s because they are reached credit limit.",
            "%s students couldn't register %s because of quota problem.",
            "%s students couldn't register %s because of quota problem.",
            "%s students couldn't register %s because it has collision with another course.",
            "%s students couldn't register %s because the course belongs to another semester.",
            "%s students couldn't register %s because they did not complete minimum credits.",
    };

    /**
     * Department constructor
     */
    public Department(String name, String id, String faculty) {
        this.name = name;
        this.id = id;
        this.faculty = faculty;
        lecturers = new ArrayList<>();
        teachingAssistants = new ArrayList<>();
        students = new ArrayList<>();
        curriculum = new Curriculum();
        courses = new HashMap<>();
        mandatoryCourses = new HashMap<>();
        technicalElectiveCourses = new HashMap<>();
        nonTechnicalElectiveCourses = new HashMap<>();
        facultyElectiveCourses = new HashMap<>();
        universityElectiveCourses = new HashMap<>();
        courseRegistrationMessages = new HashMap<>();
    }

    /**
     * Create a course by taking the required data from the json file.
     *
     * @return a course that the required data taken from the json file
     */
    public DepartmentCourse createCourse(String courseId, String courseName, String courseType, int credits, int lectureHours, int semester, int capacity) {
        /*
        @TODO: Assign random schedule if it is not specified
         */
        DepartmentCourse courseObject = new DepartmentCourse();
        //The type of created course is determined with the help of switch case
        switch (courseType) {
            case "TE":
            case "UE":
            case "NTE":
            case "FTE":
                courseObject = new ElectiveCourse(courseId, courseName, credits, semester, lectureHours, capacity, courseType);
                break;
            case "M":
                courseObject = new MandatoryCourse(courseId, courseName, credits, semester, lectureHours);
                break;
        }
        return courseObject;
    }

    /**
     * Add given course to correct course list
     *
     * @param course Course to add
     */
    public void addCourse(DepartmentCourse course) {
        if (course.getSemester() == 0 && course instanceof ElectiveCourse) {
            switch (((ElectiveCourse) course).getType()) {
                case "TE":
                    technicalElectiveCourses.put(course.getCourseId(), course);
                    break;
                case "UE":
                    universityElectiveCourses.put(course.getCourseId(), course);
                    break;
                case "NTE":
                    nonTechnicalElectiveCourses.put(course.getCourseId(), course);
                    break;
                case "FTE":
                    facultyElectiveCourses.put(course.getCourseId(), course);
                    break;
            }
            this.courses.put(course.getCourseId(), course);
        } else if (course instanceof MandatoryCourse) {
            mandatoryCourses.put(course.getCourseId(), course);
            this.courses.put(course.getCourseId(), course);
            this.curriculum.addCourse(course.getSemester(), course);
        } else if (course instanceof ElectiveCourse && course.getSemester() != 0) {
            this.curriculum.addCourse(course.getSemester(), course);
        }
    }

    /**
     * Keep record of how many students failed to register courses
     *
     * @param messageFormat Formatted message
     * @param course        Course that failed to register
     */
    public void addCourseRegistrationMessage(String messageFormat, Course course) {
        if (!courseRegistrationMessages.containsKey(messageFormat)) {
            courseRegistrationMessages.put(messageFormat, new HashMap<>());
        } else {
            courseRegistrationMessages.get(messageFormat).merge(course.getCourseId(), 1, Integer::sum);
        }
    }

    /**
     * Keep record of how many students failed to register courses
     *
     * @param messageId ID of the formatted message
     * @param course    Course that failed to register
     */
    public void addCourseRegistrationMessage(int messageId, Course course) {
        String messageFormat = registrationMessageFormats[messageId];
        if (!courseRegistrationMessages.containsKey(messageFormat)) {
            courseRegistrationMessages.put(messageFormat, new HashMap<>());
        } else {
            courseRegistrationMessages.get(messageFormat).merge(course.getCourseId(), 1, Integer::sum);
        }
    }

    /**
     * Reset capacity of all courses after each semester
     */
    public void resetCapacities() {
        for (DepartmentCourse course : this.courses.values()) {
            if (course instanceof MandatoryCourse && course.getLabSections().size() > 0) {
                for (LabCourse labSection : course.getLabSections()) {
                    int randomSectionCapacity = (int) (Math.random() * labCapacityLimit) + 1;
                    labSection.setCapacity(randomSectionCapacity);
                }
            } else if (course instanceof ElectiveCourse) {
                int randomCapacity = (int) (Math.random() * electiveCapacityLimit) + 1;
                ((ElectiveCourse) course).setCapacity(randomCapacity);
            }
        }
    }

    /**
     * Get a random course from the department
     *
     * @param courses List of courses to choose from
     * @return A random course from the list
     */
    public DepartmentCourse getRandomCourse(Object[] courses) {
        int randomIndex = (int) (Math.random() * courses.length);
        return (DepartmentCourse) courses[randomIndex];
    }

    /**
     * Get the information of the selected course from the department with using HashMap
     *
     * @param courseId Id of the course to choose from
     * @return Selected course information from the list
     */
    public DepartmentCourse getCourse(String courseId) {
        return this.courses.get(courseId);
    }

    /**
     * Get name of the department
     *
     * @return Name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the department
     *
     * @param name Name of the department to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get id of the department
     *
     * @return Id of the department
     */
    public String getId() {
        return id;
    }

    /**
     * Set if of the department
     *
     * @param id Id of the department to add
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get course take limit of the department
     *
     * @return Course take limit of the department
     */
    public int getCourseTakeLimit() {
        return courseTakeLimit;
    }

    /**
     * Set course take limit of the department
     *
     * @param courseTakeLimit Course take limit of the department to set
     */
    public void setCourseTakeLimit(int courseTakeLimit) {
        this.courseTakeLimit = courseTakeLimit;
    }

    /**
     * Get current semester of the department
     *
     * @return Current semester of the department
     */
    public String getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Set current semester of the department
     *
     * @param currentSemester Current semester of the department to set
     */
    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     * Get curriculum of the department
     *
     * @return Curriculum of the department
     */
    public Curriculum getCurriculum() {
        return curriculum;
    }

    /**
     * Get lecturers of the department
     *
     * @return Lecturers of the department
     */
    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    /**
     * Get specific lecturer of the department
     * @param id lecturer id
     * @return Lecturer of the department
     */
    public Lecturer getLecturer(int id) {
        return lecturers.get(id);
    }

    /**
     * Add a lecturer to the department
     *
     * @param name       Name of the lecturer to add
     * @param department Department to add
     */
    public void addLecturer(String name, Department department) {
        lecturers.add(new Lecturer(name, department));
    }

    /**
     * Add a teaching assistant to the department
     *
     * @param name       Name of the teaching assistant to add
     * @param department Department to add
     */
    public void addTeachingAssistant(String name, Department department) {
        teachingAssistants.add(new TeachingAssistant(name, department));
    }

    /**
     * Add student to the department
     *
     * @param student Student to add
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Get faculty of the department
     *
     * @return faculty of the department
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Set faculty of the department
     *
     * @param faculty Faculty of the department to set
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * Set lab capacity limit of the department
     *
     * @param labCapacityLimit Lab capacity limit of the department to set
     */
    public void setLabCapacityLimit(int labCapacityLimit) {
        this.labCapacityLimit = labCapacityLimit;
    }

    /**
     * Set elective capacity limit of the department
     *
     * @param electiveCapacityLimit Elective capacity limit of the department to set
     */
    public void setElectiveCapacityLimit(int electiveCapacityLimit) {
        this.electiveCapacityLimit = electiveCapacityLimit;
    }

    /**
     * Get technical elective courses of the department
     *
     * @return Technical elective courses of the department
     */
    public HashMap<String, DepartmentCourse> getTechnicalElectiveCourses() {
        return technicalElectiveCourses;
    }

    /**
     * Get non technical elective courses of the department
     *
     * @return Non technical elective courses of the department
     */
    public HashMap<String, DepartmentCourse> getNonTechnicalElectiveCourses() {
        return nonTechnicalElectiveCourses;
    }

    /**
     * Get faculty elective courses of the department
     *
     * @return Faculty elective courses of the department
     */
    public HashMap<String, DepartmentCourse> getFacultyElectiveCourses() {
        return facultyElectiveCourses;
    }

    /**
     * Get university elective courses of the department
     *
     * @return University elective courses of the department
     */
    public HashMap<String, DepartmentCourse> getUniversityElectiveCourses() {
        return universityElectiveCourses;
    }

    /**
     * Get number of lecture days of the department
     *
     * @return number of lecture days of the department
     */
    public int getNumberOfLectureDays() {
        return numberOfLectureDays;
    }

    /**
     * Set the number of lecture days. If the number is greater than 7 or less than 1, set it to 5.
     *
     * @param numberOfLectureDays Number of lecture days to set
     */
    public void setNumberOfLectureDays(int numberOfLectureDays) {
        if (numberOfLectureDays > 7 || numberOfLectureDays < 1) {
            numberOfLectureDays = 5;
        }
        this.numberOfLectureDays = numberOfLectureDays;
    }

    /**
     * Get number of lecture hours of the department
     *
     * @return Number of lecture hours of the department
     */
    public int getNumberOfLectureHours() {
        return numberOfLectureHours;
    }

    /**
     * Set the number of lecture hours. If the number is greater than 24 or less than 1, set it to 15.
     *
     * @param numberOfLectureHours Number of lecture hours to set
     */
    public void setNumberOfLectureHours(int numberOfLectureHours) {
        if (numberOfLectureHours > 24 || numberOfLectureHours < 1) {
            numberOfLectureHours = 15;
        }
        this.numberOfLectureHours = numberOfLectureHours;
    }

    /**
     * Get department number of the department
     *
     * @return Department number of the department
     */
    public int getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * Set department number of the department
     *
     * @param departmentNumber Department number of the department to set
     */
    public void setDepartmentNumber(int departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    /**
     * Get all courses of the department
     *
     * @return All courses of the department
     */
    public HashMap<String, DepartmentCourse> getCourses() {
        return courses;
    }

    /**
     * Get all mandatory courses of the department
     *
     * @return All mandatory courses of the department
     */
    public HashMap<String, DepartmentCourse> getMandatoryCourses() {
        return mandatoryCourses;
    }

    /**
     * Get all teaching assistants of the department
     *
     * @return All teaching assistants of the department
     */
    public ArrayList<TeachingAssistant> getTeachingAssistants() {
        return teachingAssistants;
    }

    /**
     * Get teaching assistant with the given index
     * @param index Index of the teaching assistant
     * @return Teaching assistant with the given index
     */
    public TeachingAssistant getTeachingAssistant(int index){
        return teachingAssistants.get(index);
    }

    /**
     * Get all students of the department
     *
     * @return All students of the department
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Get lab capacity limit of the department
     *
     * @return Lab capacity limit of the department
     */
    public int getLabCapacityLimit() {
        return labCapacityLimit;
    }

    /**
     * Get elective capacity limit of the department
     *
     * @return Elective capacity limit of the department
     */
    public int getElectiveCapacityLimit() {
        return electiveCapacityLimit;
    }

    /**
     * Get course registration messages of the department
     *
     * @return Course registration messages of the department
     */
    public HashMap<String, HashMap<String, Integer>> getCourseRegistrationMessages() {
        return courseRegistrationMessages;
    }

    /**
     * Get maximum credits of the department
     *
     * @return Maximum credits of the department
     */
    public int getMaximumCredits() {
        return maximumCredits;
    }

    public void setMaximumCredits(int maximumCredits) {
        this.maximumCredits = maximumCredits;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
