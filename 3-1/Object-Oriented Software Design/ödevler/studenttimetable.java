import java.util.ArrayList;
import java.util.HashMap;

/**
 * The StudentTimetable class that holds timetable information and contains related methods
 */
public class StudentTimetable {
    /**
     * How many days a student can attend classes.
     */
    private int numberOfDays;
    /**
     * Maximum lecture hours per day
     * They are not exact lecture hours, they just indicate order of lectures.
     */
    private int numberOfHours;
    /**
     * 2D HashMap to store the timetable.
     * First key indicates day number [1, 7]
     * Second key indicates lecture number [1, 15]
     * It can store many courses at the same lecture hour.
     */
    private HashMap<Integer, HashMap<Integer, ArrayList<Course>>> timetable;

    /**
     * Constructor of StudentTimetable
     *
     * @param student to get
     */
    public StudentTimetable(Student student) {
        this.numberOfDays = student.getDepartment().getNumberOfLectureDays();
        this.numberOfHours = student.getDepartment().getNumberOfLectureHours();
        timetable = new HashMap<>();
        for (int i = 0; i < numberOfDays; i++) {
            timetable.put(i + 1, new HashMap<>());
            for (int j = 0; j < numberOfHours; j++) {
                timetable.get(i + 1).put(j + 1, new ArrayList<>());
            }
        }
    }

    /**
     * Assigns course to timetable at given day and hour
     *
     * @param day    Integer between 1 and numberOfDays (max:7)
     * @param hour   Integer between 1 and numberOfHours (max:15)
     * @param course Course object to be assigned
     */
    public void assignCourse(int day, int hour, Course course) {
        timetable.get(day).computeIfAbsent(hour, k -> new ArrayList<>());
        timetable.get(day).get(hour).add(course);
    }

    /**
     * Assigns course to timetable
     *
     * @param course to assign
     */
    public void assignCourse(Course course) {
        for (int[] lectureHours : course.getCourseSchedule()) {
            assignCourse(lectureHours[0], lectureHours[1], course);
        }
    }

    /**
     * Checks given course causes collision with other courses in timetable
     *
     * @param course Course object to check collision
     * @return True if collision is found
     */
    public boolean courseHasCollision(Course course) {
        for (int[] lectureTime : course.getCourseSchedule()) {
            if (isCourseAssigned(lectureTime[0], lectureTime[1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if course is assigned to given day and hour
     *
     * @param day  Integer between 1 and numberOfDays (max:7)
     * @param hour Integer between 1 and numberOfHours (max:15)
     * @return Returns true if student has at least one course assigned to given day and hour
     */
    public boolean isCourseAssigned(int day, int hour) {
        return timetable.get(day).get(hour).size() > 0;
    }

    /**
     * Gets number of courses at given day and hour
     *
     * @param day  Integer between 1 and numberOfDays (max:7)
     * @param hour Integer between 1 and numberOfHours (max:15)
     * @return Returns number of courses assigned to given day and hour
     */
    public int numberOfAssignedCourses(int day, int hour) {
        return timetable.get(day).get(hour).size();
    }

    /**
     * Gets number of days
     *
     * @return {integer} number of days
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Gets number of hours
     *
     * @return    {integer} number of hours
     */
    public int getNumberOfHours() {
        return numberOfHours;
    }

    /**
     * Gets time table
     *
     * @return        {HashMap} time table
     */
    public HashMap<Integer, HashMap<Integer, ArrayList<Course>>> getTimetable() {
        return timetable;
    }
}