import transcript
import course
import student_timetable as st
import student_course as sc
import person
from lecturer import Lecturer
from random_student_generator import RandomStudentGenerator


class Student(person.Person):
    __studentID = int
    __semester = int
    __registerYear = int
    __advisor = Lecturer
    __takenCredits = []
    __allCourses = []
    __electiveCourseChoices = {}
    __takenElectives = {}
    __newlyTakenCourses = {}
    __previousCourses = {}
    __failedCourses = {}
    __passedCourses = {}
    __transcript = transcript.Transcript
    __timeTable = st.StudentTimetable
    __educationLevel = str
    __registrationMessages = []

    # __logger = Logger

    def __init__(self, name, department):
        super().__init__(name, department)
        self.__timeTable = st.StudentTimetable(self)
        self.__newlyTakenCourses = {}
        self.__previousCourses = {}
        self.__registrationMessages = {}
        self.__allCourses = []
        self.__electiveCourseChoices = {}
        self.__takenElectives = {}
        self.__takenCredits = [0] * 8
        self.__failedCourses = {}
        self.__passedCourses = {}

    def take_mandatory_courses(self):
        for i in range(1, self.__semester):
            semester_courses = self.get_department().get_curriculum().get_semester(i)
            for course in semester_courses:
                if not isinstance(course, course.ElectiveCourse):
                    self.__allCourses.append(course)
                    # logger.info("Student " + self.get_name() + " has taken " + course.get_course_name)

    def take_elective_courses(self):
        non_technical_electives = self.get_department().get_non_technical_elective_courses()
        technical_electives = self.get_department().get_technical_elective_courses()
        faculty_electives = self.get_department().get_faculty_elective_courses()
        university_electives = self.get_department().get_university_elective_courses()

        for i in range(1, self.__semester):
            semester_courses = self.get_department().get_curriculum().get_semester(i)
            for course in semester_courses:
                if isinstance(course, course.ElectiveCourse):
                    electiveCourses: list
                    switcher: {
                        'NTE': electiveCourses.extend(dict(non_technical_electives).values()),
                        'TE': electiveCourses.extend(dict(technical_electives).values()),
                        'FTE': electiveCourses.extend(dict(faculty_electives).values()),
                        'UE': electiveCourses.extend(dict(university_electives).values())
                    }
                    random_elective_course: course.ElectiveCourse
                    random_elective_course = random.choice(electiveCourses)
                    message = ""
                    if random_elective_course in self.__takenElectives:
                        # ATTENTION
                        message = "Student could not take" + str(
                            random_elective_course.get_course_id) + "because it has already taken in previous semesters"
                        self.add_registration_message(message)
                        # logger.warning(message)
                    if random_elective_course in self.__electiveCourseChoices:
                        message = "Student could not take" + str(
                            random_elective_course.get_course_id) + "because it has already taken another" + course.get_course_id() + "course"
                        self.add_registration_message(message)
                        # logger.warning(message)
                    if not message:
                        self.__allCourses.append(random_elective_course)
                        self.__electiveCourseChoices.append(random_elective_course)
                        self.__electiveCourseChoices[random_elective_course].append(course.get_course_id, i)
                        self.__takenElectives.append(random_elective_course)
                        # logger.info("Student " + self.getName() + " has taken " + random_elective_course.getCourseId());

    def get_elective_semester(self, electiveCourse):
        semester = 0
        choice = self.__electiveCourseChoices[electiveCourse]
        if choice is None:
            for courseId in choice:
                semester = choice[courseId]
                break
        return semester

    def create_student_courses(self):
        for course in self.__allCourses:
            student_course = sc.StudentCourse(course, RandomStudentGenerator.random_grade(), course.get_semester())
            student_course.check_rules(self, course)

    def generate_transcript(self, include_new_courses):
        self.__transcript = transcript.Transcript(self, include_new_courses)
        return self.__transcript

    def register_course(self, course, grade, semester):
        student_course = sc.StudentCourse(course, grade, semester)
        if semester == self.__semester:
            if len(self.__newlyTakenCourses) == self.get_department().get_course_take_limit():
                self.add_registration_message(
                    "Student could not take " + course.get_course_id() + "because reached the course take limit.")
                return
            else:
                self.__newlyTakenCourses.append(course, student_course)
                self.__timeTable.assing_course(course)
                course.add_student(self)
        else:
            if not student_course.is_passed():
                self.__failedCourses.append(student_course.get_main_course())
            else:
                self.__passedCourses.append(student_course.get_main_course())
            self.__previousCourses.append(course, student_course)
        self.__takenCredits[semester - 1] += course.get_credits()

    def get_failed_prerequisites(self, course: course.DepartmentCourse):
        failed_prerequisites = []
        for prerequiste in course.get_prerequisites():
            if not prerequiste in self.__passedCourses:
                failed_prerequisites.append(prerequiste)
        return failed_prerequisites

    def add_registration_message(self, message):
        self.__registrationMessages.append(message)

    def get_passed_courses(self):
        return self.__passedCourses

    def add_passed_course(self, studentCourse):
        self.__passedCourses.append(studentCourse.get_main_course)

    def get_student_id(self):
        return self.__studentID

    def set_student_id(self, studentID):
        self.__studentID = studentID

    def get_semester(self):
        return self.__semester

    def set_semester(self, semester):
        self.__semester = semester

    def get_advisor(self):
        return self.__advisor

    def set_advisor(self, advisor):
        self.__advisor = advisor

    def get_time_table(self):
        return self.__timeTable

    def get_mewly_taken_courses(self):
        return self.__newlyTakenCourses

    def get_previous_courses(self):
        return self.__previousCourses

    def get_education_level(self):
        return self.__educationLevel

    def set_education_level(self, educationLevel):
        self.__educationLevel = educationLevel

    def get_register_year(self):
        return self.__registerYear

    def set_register_year(self, registerYear):
        self.__registerYear = registerYear

    def get_transcript(self):
        return self.__transcript

    def get_registration_messages(self):
        return self.__registrationMessages

    def get_all_courses(self):
        return self.__allCourses

    def get_taken_credits(self, semester):
        return self.__takenCredits[semester]

    def get_completed_credits(self, semester):
        return self.__semester
