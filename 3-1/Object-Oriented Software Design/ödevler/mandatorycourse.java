/**
 * The MandatoryCourse class that holds mandatory class information and contains related methods. This is a subtype of the class department course
 */
public class MandatoryCourse extends DepartmentCourse {

    /**
     * Store the detailed data of mandatory course
     *
     * @param courseId     course id for mandatory course
     * @param courseName   course name for mandatory course
     * @param credits      credit value for mandatory course
     * @param semester     semester for mandatory course is in
     * @param lectureHours lecture hours for mandatory course
     */
    public MandatoryCourse(String courseId, String courseName, int credits, int semester, int lectureHours) {
        super(courseId, courseName, credits, semester, lectureHours);
    }
}
