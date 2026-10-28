import course
from department_course import DepartmentCourse
from teaching_assistant import TeachingAssistant
import student


class LabCourse(course.Course):
    __mainCourse: DepartmentCourse
    __lecturer: TeachingAssistant
    __sectionNumber: int
    __capacity: int

    def __init__(self, main_course: DepartmentCourse):
        self.__mainCourse = main_course

        self.__lecturer = main_course.get_teaching_assistant(main_course.lab_sections_count())
        if self.__lecturer is not None:
            self.__lecturer.assign_lab_course(self)

        self.__sectionNumber = main_course.lab_sections_count() + 1
        self.courseID = main_course.get_course_id() + "." + str(self.__sectionNumber)
        self.courseSchedule = []

    def add_student(self, student: student.Student):
        if self.__capacity > 0:
            self.__registeredStudents.append(student)
            self.__capacity = self.__capacity - 1

    def set_capacity(self, capacity: int):
        self.__capacity = capacity

    def has_capacity(self):
        return self.__capacity > 0

    def decrease_capacity(self):
        self.__capacity = self.__capacity - 1

    def get_main_course(self):
        return self.__mainCourse

    def get_lecturer(self):
        return self.__lecturer

    def get_registered_students(self):
        return self.__registeredStudents

    def get_section_number(self):
        return self.__sectionNumber

    def get_capacity(self):
        return self.__capacity
