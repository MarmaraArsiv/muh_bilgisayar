import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class test {
    @org.junit.jupiter.api.Test
    void takeMandatoryCourses() {
        Department department = Main.initializeDepartment("input.json");
        Student student = new Student("test", department);
        student.setSemester(1);
        student.takeMandatoryCourses();
        assertEquals(student.getAllCourses(), department.getCurriculum().getSemester(1), "Course registration failed.");
    }

    @org.junit.jupiter.api.Test
    void createCourse() {
        DepartmentCourse departmentCourse = new DepartmentCourse("test", "testCourse", 1, 1, 1);
        assertNotNull(departmentCourse);
        assertNotNull(departmentCourse, "course creation failed");
    }

    @org.junit.jupiter.api.Test
    void createRandomGrade() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        int randomgrade = RandomStudentGenerator.randomGrade();
        assertNotNull(randomgrade);
        assertNotNull(randomgrade, "random grade creation failed");
    }


    @Test
    public void setGrade() {
        DepartmentCourse departmentCourse = new DepartmentCourse("test", "testCourse", 1, 1, 1);
        StudentCourse sCourse = new StudentCourse(departmentCourse, 65, 1);
        sCourse.setGrade(65);
        int grade = sCourse.getGrade();
        assertEquals(65, grade);
    }


    @Test
    public void setLetterGrade() {
        DepartmentCourse departmentCourse = new DepartmentCourse("test", "testCourse", 1, 1, 1);
        StudentCourse sCourse = new StudentCourse(departmentCourse, 65, 1);
        sCourse.setLetterGrade(65);
        String letterGrade = sCourse.getLetterGrade();
        assertEquals("CC", letterGrade);
    }


    @Test
    public void notEligibleForCourse() {
        Department department = Main.initializeDepartment("input.json");
        Student stdent = new Student("test", department);
        DepartmentCourse course = new DepartmentCourse("test", "testCourse", 1, 2, 2);
        DepartmentCourse course1 = new DepartmentCourse("test1", "testCourse1", 1, 1, 1);
        StudentCourse studentCourse1 = new StudentCourse(course1, 63, 1);
        DepartmentCourse course2 = new DepartmentCourse("test2", "testCourse2", 1, 1, 1);
        StudentCourse studentCourse2 = new StudentCourse(course2, 90, 1);
        course.addPrerequisite(course1);
        course.addPrerequisite(course2);
        stdent.addPassedCourse(studentCourse1);
        stdent.addPassedCourse(studentCourse2);
        HashSet<String> passedCourses = new HashSet<String>();
        HashSet<String> failedCourses = new HashSet<String>();
        //stdent.passedCourses.add(studentCourse1.getMainCourse().getCourseId());
        //stdent.passedCourses.add(studentCourse2.getMainCourse().getCourseId());
        passedCourses.add(studentCourse1.getMainCourse().getCourseId());
        passedCourses.add(studentCourse2.getMainCourse().getCourseId());
        assertEquals(0, stdent.getFailedPrerequisites(course).size());
    }

    @Test
    public void resetCapacities() {
        DepartmentCourse course = new ElectiveCourse("test", "testCourse", 1, 1, 1, 30, "TE");
        //DepartmentCourse course1 = new MandatoryCourse("test1","testCourse1",1,1,1);
        if (course instanceof MandatoryCourse && course.getLabSections().size() > 0) {
            for (LabCourse labSection : course.getLabSections()) {
                labSection.setCapacity(40);
                assertEquals(40, labSection.getCapacity());
            }
        } else if (course instanceof ElectiveCourse) {
            ((ElectiveCourse) course).setCapacity(40);
            assertEquals(40, ((ElectiveCourse) course).getCapacity());
        }
    }

    @Test
    public void randomGrade() {
        boolean rand = false;
        int num = RandomStudentGenerator.randomGrade();
        if (num >= 10 && num <= 100)
            rand = true;
        assertTrue(rand);
    }

}
