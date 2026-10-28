import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The class in which the created methods are called and the json file is created and saved.
 */
public class Main {
    /**
     * It creates a file named output in the folder in the root directory. Generates random students with Generator.
     * Random students created are transferred to a list in a certain department and in number.
     * In a loop, these students are saved as a .json file in the output file one by one.
     *
     * @param args an array of command-line arguments for the application
     * @throws IOException if stream to file cannot be written to or closed.
     */
    public static void main(String[] args) throws IOException {
        Department department = initializeDepartment("input.json");
        Files.createDirectories(Paths.get("./output/"));
        RandomStudentGenerator generator = new RandomStudentGenerator();
        ArrayList<Student> randomStudents = generator.createStudents(department, 1000);
        for (Student student : randomStudents) {
            JSONCreator jsonCreator = new JSONCreator(student, "./output/" + student.getStudentID() + ".json");
            jsonCreator.saveToFile();
        }
        JSONCreator jsonCreator = new JSONCreator(department, "./output/department_output_" + department.getCurrentSemester() + ".json");
        jsonCreator.saveToFile();
    }

    public static Department initializeDepartment(String inputFile) {
        try (FileReader reader = new FileReader(inputFile)) {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(reader);
            JSONObject data = (JSONObject) object;
            Department department = new Department(data.get("departmentName").toString(), data.get("departmentId").toString(), data.get("faculty").toString());
            department.setNumberOfLectureDays(Integer.parseInt(data.get("numberOfLectureDays").toString()));
            department.setNumberOfLectureHours(Integer.parseInt(data.get("numberOfLectureHours").toString()));
            department.setCurrentSemester(data.get("currentSemester").toString());
            department.setCourseTakeLimit(Integer.parseInt(data.get("courseTakeLimit").toString()));
            department.setLabCapacityLimit(Integer.parseInt(data.get("labCapacityLimit").toString()));
            department.setElectiveCapacityLimit(Integer.parseInt(data.get("electiveCapacityLimit").toString()));
            department.setDepartmentNumber(Integer.parseInt(data.get("departmentNumber").toString()));
            department.setMaximumCredits(Integer.parseInt(data.get("maximumCredits").toString()));
            // Add lecturers
            for (Object o : (JSONArray) data.get("lecturers")) {
                JSONObject lecturer = (JSONObject) o;
                if (lecturer.get("department") == null) {
                    department.addLecturer(lecturer.get("name").toString(), null);
                } else if (lecturer.get("department").toString().equals(department.getId())) {
                    department.addLecturer(lecturer.get("name").toString(), department);
                }

            }
            // Add teaching assistants
            for (Object o : (JSONArray) data.get("teachingAssistants")) {
                JSONObject teachingAssistant = (JSONObject) o;
                if (teachingAssistant.get("department").toString().equals(department.getId())) {
                    department.addTeachingAssistant(teachingAssistant.get("name").toString(), department);
                }
            }
            // Add courses
            for (Object o : (JSONArray) data.get("courses")) {
                JSONObject course = (JSONObject) o;
                String courseId = course.get("courseId").toString();
                String courseName = course.get("courseName").toString().trim();
                String courseType = course.get("type").toString();
                int credits = Integer.parseInt(course.get("credits").toString());
                int lectureHours = Integer.parseInt(course.get("lectureHours").toString());
                int semester = 0;
                if (course.get("semester") != null) {
                    semester = Integer.parseInt(course.get("semester").toString());
                }
                /*
         Generate a random number for elective course capacity
         Minimum capacity is 10, maximum is the number of declared in input file
         */
                int randomCapacity = (int) (Math.random() * department.getElectiveCapacityLimit()) + 1;
                DepartmentCourse courseObject = department.createCourse(courseId, courseName, courseType, credits, lectureHours, semester, randomCapacity);
                if (course.get("minCompletedCredits") != null) {
                    courseObject.setMinCompletedCredits(Integer.parseInt(course.get("minCompletedCredits").toString()));
                }
                department.addCourse(courseObject);
                int labHours = Integer.parseInt(course.get("labHours").toString());
                if (course.get("lecturer") != null) {
                    try {
                        int lecturerId = Integer.parseInt(course.get("lecturer").toString());
                        Lecturer lecturer = department.getLecturer(lecturerId - 1);
                        courseObject.setLecturer(lecturer);
                        lecturer.addCourse(courseObject);
                    } catch (Exception e) {
                        continue;
                    }
                }
                if (course.get("teachingAssistants") != null) {
                    for (Object teachingAssistant : (JSONArray) course.get("teachingAssistants")) {
                        int assistantId = Integer.parseInt(teachingAssistant.toString());
                        courseObject.addTeachingAssistant(department.getTeachingAssistant(assistantId - 1));
                    }
                }
                if (course.get("schedule") != null) {
                    for (Object courseSchedule : (JSONArray) course.get("schedule")) {
                        JSONArray courseScheduleArray = (JSONArray) courseSchedule;
                        int day = Integer.parseInt(courseScheduleArray.get(0).toString());
                        int hour = Integer.parseInt(courseScheduleArray.get(1).toString());
                        courseObject.addLectureHour(day, hour);
                    }
                }
                if (labHours > 0) {
                    courseObject.setLabHours(labHours);
                    // Generate a random number for lab capacity
                    int randomSectionCapacity = (int) (Math.random() * department.getLabCapacityLimit()) + 1;
                    if (course.get("labSections") != null) {
                        for (Object labSection : (JSONArray) course.get("labSections")) {
                            JSONArray labSectionArray = (JSONArray) labSection;
                            /*
                            @TODO: May be we can add random assistants to lab sections
                             */
                            LabCourse labCourse = new LabCourse(courseObject);
                            for (Object labSectionObject : labSectionArray) {
                                JSONArray labSectionHourArray = (JSONArray) labSectionObject;
                                int day = Integer.parseInt(labSectionHourArray.get(0).toString());
                                int hour = Integer.parseInt(labSectionHourArray.get(1).toString());
                                labCourse.addLectureHour(day, hour);
                            }
                            labCourse.setCapacity(randomSectionCapacity);
                            courseObject.addLabSection(labCourse);
                        }
                    }
                }
            }
            // Add course prerequisites
            for (Object o : (JSONArray) data.get("courses")) {
                JSONObject course = (JSONObject) o;
                String courseId = course.get("courseId").toString();
                if (course.get("prerequisites") != null) {
                    for (Object prerequisite : (JSONArray) course.get("prerequisites")) {
                        String prerequisiteId = prerequisite.toString();
                        department.getCourse(courseId).addPrerequisite(department.getCourse(prerequisiteId));
                    }
                }
                if (course.get("prerequisiteTo") != null) {
                    for (Object prerequisite : (JSONArray) course.get("prerequisiteTo")) {
                        String prerequisiteId = prerequisite.toString();
                        department.getCourse(courseId).addPrerequisiteTo(department.getCourse(prerequisiteId));
                    }
                }
            }
            return department;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
