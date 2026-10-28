import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The JSONCreator class that creates json file for students and department informations
 */
public class JSONCreator {

    private JSONObject jsonObject;
    private String outputFileName;

    /**
     * Generate a json file to save the data
     *
     * @param o              A reference of The Object Class
     * @param outputFileName Output json file name
     */
    public JSONCreator(Object o, String outputFileName) {
        this.outputFileName = outputFileName;
        if (o instanceof Department) {
            jsonObject = createJSONObject((Department) o);
        } else if (o instanceof Student) {
            jsonObject = createJSONObject((Student) o);
        }
    }

    /**
     * Create JSON object for department
     *
     * @param department Department to add
     * @return JSON object
     */
    public JSONObject createJSONObject(Department department) {
        JSONObject departmentObject = new JSONObject();
        departmentObject.put("departmentId", department.getId());
        departmentObject.put("departmentName", department.getName());
        departmentObject.put("departmentNumber", department.getDepartmentNumber());
        departmentObject.put("semester", department.getCurrentSemester());
        departmentObject.put("year", new SimpleDateFormat("yyyy").format(new Date()));
        JSONArray messages = new JSONArray();
        for (String message : department.getCourseRegistrationMessages().keySet()) {
            for (String courseId : department.getCourseRegistrationMessages().get(message).keySet()) {
                int count = department.getCourseRegistrationMessages().get(message).get(courseId);
                messages.add(String.format(message, count, courseId));
            }
        }
        departmentObject.put("messages", messages);
        return departmentObject;
    }

    /**
     * Create JSON object for the student
     *
     * @param student Student to add
     * @return JSON object
     */
    public JSONObject createJSONObject(Student student) {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("id", student.getStudentID());
        studentObject.put("semester", student.getSemester());
        studentObject.put("registerYear", student.getRegisterYear());
        studentObject.put("advisor", student.getAdvisor().getName());
        studentObject.put("educationLevel", student.getEducationLevel());
        JSONArray messages = new JSONArray();
        messages.addAll(student.getRegistrationMessages());
        studentObject.put("registrationMessages", messages);
        studentObject.put("transcriptBeforeRegister", student.generateTranscript(false).getTranscriptObject());
        studentObject.put("transcriptAfterRegister", student.generateTranscript(true).getTranscriptObject());
        return studentObject;
    }

    /**
     * Save department information to JSON file
     */
    public void saveToFile() {
        try (FileWriter file = new FileWriter(outputFileName)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
