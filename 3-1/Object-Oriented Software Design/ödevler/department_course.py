import course
import lecturer


class DepartmentCourse(course.Course):
    __courseName: str
    __registeredStudents = []
    __prerequisites = []
    __prerequisiteTo = []
    __labSections = []
    __teachingAssistants = []
    __schedule = []
    __lecturer: type(lecturer)
    __lectureHours: int
    __semester: int
    __credits: int
    __labHours: int
    __minCompletedCredits: int

    def __init__(self, course_id, course_name, course_credits, semester, lecture_hours):
        self.__schedule = None
        self.courseID = course_id
        self.__courseName = course_name
        self.__credits = course_credits
        self.__semester = semester
        self.__lectureHours = lecture_hours

    def add_prerequisite(self, course):
        self.__prerequisites.append(course)

    def add_prerequisite_to(self, course):
        self.__prerequisiteTo.append(course)

    def set_lab_hours(self, lab_hours: int):
        self.__labHours = lab_hours

    def lab_sections_count(self):
        return len(self.__labSections)

    def add_lab_section(self, lab_course):
        self.__labSections.append(lab_course)

    def add_teaching_assistant(self, teaching_assistant):
        self.__teachingAssistants.append(teaching_assistant)

    def get_teaching_assistant(self, index: int):
        if len(self.__teachingAssistants) > index:
            return self.__teachingAssistants[index]
        else:
            return None

    def set_lecturer(self, lecturer):
        self.__lecturer = lecturer

    def set_schedule(self, schedule):
        self.__schedule = schedule

    def get_course_name(self):
        return self.__courseName

    def get_prerequisites(self):
        return self.__prerequisites

    def get_lab_sections(self):
        return self.__labSections

    def get_semester(self):
        return self.__semester

    def get_credits(self):
        return self.__credits

    def get_registered_students(self):
        return self.__registeredStudents

    def get_prerequisite_to(self):
        return self.__prerequisiteTo

    def get_teaching_assistants(self):
        return self.__teachingAssistants

    def get_lecturer(self):
        return self.__lecturer

    def get_lecture_hours(self):
        return self.__lectureHours

    def get_lab_hours(self):
        return self.__labHours

    def set_semester(self, semester: int):
        self.__semester = semester

    def set_course_id(self, course_id: str):
        self.courseID = course_id

    def get_min_completed_credits(self):
        return self.__minCompletedCredits

    def set_min_completed_credits(self, min_completed: int):
        self.__minCompletedCredits = min_completed
