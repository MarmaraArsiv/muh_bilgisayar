/**
 * The StudentCourse class that holds student course information and contains related methods
 */
public class StudentCourse {
    /**
     * Student's course grade [0, 100]
     */
    private int grade;
    /**
     * Student's course point [0, 4]
     */
    private double point;
    /**
     * Letter grade calculated from the course grade
     */
    private String letterGrade;
    /**
     * Main department course
     */
    private DepartmentCourse mainCourse;
    /**
     * Student is passed or not
     */
    private boolean passed;
    /**
     * In which semester the student took the course
     */
    private int registeredSemester;
    /**
     * Registration warning messages
     */
    private final String[] registrationMessages = {
            "Student is not eligible for course %s because prerequisite %s did not met.",
            "Advisor did not approve take of %s because he/she has another course at course time.",
            "Advisor did not approve take of %s because he/she reached credit take limit (semesters: %s).",
            "Student is could not take %s because no capacity left.",
            "Student is could not take this lab section %s because no capacity left.",
            "Advisor did not approve taking %s because he/she has another course at that time.",
            "Student cannot take %s in this semester because the course belongs to another semester.",
            "Student cannot take %s because he/she did not met minimum completed credits.",
    };

    /**
     * Register student to single course
     *
     * @param course   DepartmentCourse object
     * @param grade    Grade between [0, 100]
     * @param semester In which semester the student took the course
     */
    public StudentCourse(DepartmentCourse course, int grade, int semester) {
        this.mainCourse = course;
        setGrade(grade);
        this.registeredSemester = semester;
    }

    /**
     * Check registration rules (prerequisites, collisions and capacity) met by student
     * If all rules met, register student to course
     *
     * @param student    Student object
     * @param course     DepartmentCourse object to check student can register or not
     */
    public void checkRules(Student student, DepartmentCourse course) {
        Department department = student.getDepartment();
        String message = "";
        String courseIdName = course.getCourseId() + " " + course.getCourseName();
        int semester = course.getSemester();
        if (semester == 0 && course instanceof ElectiveCourse) {
            semester = student.getElectiveSemester((ElectiveCourse) course);
        }
        if (course.getSemester() > student.getSemester()) { // Check semester
            message = String.format(registrationMessages[6], courseIdName);
            student.addRegistrationMessage(message);
            department.addCourseRegistrationMessage(6, course);
        }
        if (!student.getFailedPrerequisites(course).isEmpty()) { // Check prerequisites
            message = String.format(registrationMessages[0], courseIdName, student.getFailedPrerequisites(course).get(0).getCourseName());
            student.addRegistrationMessage(message);
            department.addCourseRegistrationMessage(0, course);
        }
        if (student.getTimetable().courseHasCollision(course)) { // Check collisions
            message = String.format(registrationMessages[1], courseIdName);
            student.addRegistrationMessage(message);
            department.addCourseRegistrationMessage(1, course);
        }
        if (student.getTakenCredits(semester) + course.getCredits() > department.getMaximumCredits()) { // Check credits
            message = String.format(registrationMessages[2], courseIdName, course.getSemester());
            student.addRegistrationMessage(message);
            department.addCourseRegistrationMessage(2, course);
        }
        if (course.getMinCompletedCredits() > 0 && student.getCompletedCredits(semester - 1) < course.getMinCompletedCredits()) { // Check completed credits
            message = String.format(registrationMessages[7], courseIdName, course.getSemester());
            student.addRegistrationMessage(message);
            department.addCourseRegistrationMessage(7, course);
        }
        if (course instanceof ElectiveCourse && !((ElectiveCourse) course).hasCapacity()) { // Check capacity
            message = String.format(registrationMessages[3], courseIdName);
            student.addRegistrationMessage(message);
            department.addCourseRegistrationMessage(3, course);
        }
        if (!course.getLabSections().isEmpty()) {
            int random = (int) (Math.random() * course.getLabSections().size());
            LabCourse randomLabSection = course.getLabSections().get(random);
            // Check lab section capacity
            if (!randomLabSection.hasCapacity()) {
                message = String.format(registrationMessages[4], randomLabSection.getCourseId());
                student.addRegistrationMessage(message);
                department.addCourseRegistrationMessage(4, randomLabSection);
            }
            // Check lab section collisions
            if (student.getTimetable().courseHasCollision(randomLabSection)) {
                message = String.format(registrationMessages[5], randomLabSection.getCourseId());
                student.addRegistrationMessage(message);
                department.addCourseRegistrationMessage(5, randomLabSection);
            }
            if (randomLabSection.hasCapacity() && !student.getTimetable().courseHasCollision(randomLabSection) && message.isEmpty()) {
                student.getTimetable().assignCourse(randomLabSection);
                randomLabSection.addStudent(student);
            }
        }
        if (message.isEmpty()) {
            student.registerCourse(course, RandomStudentGenerator.randomGrade(), semester);
        }
    }

    /**
     * Set random grade.
     * If point is lesser than 1.5, passed sets false.
     *
     * @param grade Grade of student
     */

    public void setGrade(int grade) {
        this.grade = grade;
        setLetterGrade(grade);
        if (point < 1.5) {
            passed = false;
        }
    }

    /**
     * Get the student's letter grade for this course
     *
     * @return The student's letter grade for this course
     */
    public String getLetterGrade() {
        return letterGrade;
    }

    /**
     * Set letter grade intervals
     *
     * @param grade Grade for letter control
     */
    public void setLetterGrade(int grade) {
        if (grade >= 90) {
            letterGrade = "AA";
            point = 4;
        } else if (grade >= 85) {
            letterGrade = "BA";
            point = 3.5;
        } else if (grade >= 80) {
            letterGrade = "BB";
            point = 3;
        } else if (grade >= 75) {
            letterGrade = "CB";
            point = 2.5;
        } else if (grade >= 65) {
            letterGrade = "CC";
            point = 2;
        } else if (grade >= 55) {
            letterGrade = "DC";
            point = 1.5;
        } else if (grade >= 50) {
            letterGrade = "DD";
            point = 1;
        } else if (grade >= 45) {
            letterGrade = "FD";
            point = 0.5;
        } else {
            letterGrade = "FF";
            point = 0;
        }
    }

    /**
     * Get main course of this course
     *
     * @return Main course of this course
     */
    public DepartmentCourse getMainCourse() {
        return mainCourse;
    }

    /**
     * Get the student's registered semester for this course
     *
     * @return The student's registered semester for this course
     */
    public int getRegisteredSemester() {
        return registeredSemester;
    }

    /**
     * Get the student's point for this course
     *
     * @return The student's course point for this course
     */
    public double getPoint() {
        return point;
    }

    /**
     * Get the student's grade for this course
     *
     * @return The student's grade for this course
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Student is passed this course or not
     *
     * @return Student is passed this course or not
     */
    public boolean isPassed() {
        return passed;
    }
}
