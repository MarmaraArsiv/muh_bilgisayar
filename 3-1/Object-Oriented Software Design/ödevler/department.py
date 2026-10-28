import random

from curriculum import Curriculum

import person
import course
from department_course import DepartmentCourse
from elective_course import ElectiveCourse
from mandatory_course import MandatoryCourse


class Department:
    __name = str
    __id = str
    __faculty = str
    __current_semester = str
    __department_number = int
    __number_of_lecture_days = int
    __number_of_lecture_hours = int
    __maximum_credits = int
    __curriculum = Curriculum()
    __courses = {}
    __mandatory_courses = {}
    __technical_elective_courses = {}
    __non_technical_elective_courses = {}
    __faculty_elective_courses = {}
    __university_elective_courses = {}
    __lecturers = []
    __teaching_assistants = []
    __students = []
    __course_take_limit = int
    __lab_capacity_limit = int
    __elective_capacity_limit = int
    __course_registration_messages = {}
    __registration_message_formats = [
        "%s students couldn't register {} because of prerequisites did not met.",
        "%s students couldn't register {} because it has collision with another course.",
        "%s students couldn't register {} because they are reached credit limit.",
        "%s students couldn't register {} because of quota problem.",
        "%s students couldn't register {} because of quota problem.",
        "%s students couldn't register {} because it has collision with another course.",
        "%s students couldn't register {} because the course belongs to another semester.",
        "%s students couldn't register {} because they did not complete minimum credits.",
    ]

    def __init__(self, name, id, faculty, current_semester, department_number, number_of_lecture_days,
                 number_of_lecture_hours):
        self.__name = name
        self.__id = id
        self.__faculty = faculty
        self.__current_semester = current_semester
        self.__department_number = department_number
        self.__number_of_lecture_days = number_of_lecture_days
        self.__number_of_lecture_hours = number_of_lecture_hours

    def create_course(self, course: dict):
        semester = 0
        if "semester" in course:
            semester = int(course['semester'])

        random_capacity = int((random.uniform(0, self.__elective_capacity_limit) + 1))

        if course['type'] == "M":
            course_object = MandatoryCourse(course['courseId'], course['courseName'], credits, semester,
                                            course['lectureHours'])
        else:
            course_object = ElectiveCourse(course['courseId'], course['courseName'], credits, semester,
                                           course['lectureHours'], random_capacity, course['type'])

        if course.get("minCompletedCredits") is not None:
            course_object.set_min_completed_credits(int(course['minCompletedCredits']))

        return course_object

    def add_course(self, course):
        if course.get_semester() == 0 and isinstance(course, ElectiveCourse):
            course_type = course.get_type()
            # match course_type:
            #     case 'TE':
            #         self.__technical_elective_courses.extend(course.get_course_id(), course)
            #     case 'UE':
            #         self.__university_elective_courses.extend(course.get_course_id(), course)
            #     case 'NTE':
            #         self.__non_technical_elective_courses.extend(course.get_course_id(), course)
            #     case 'FTE':
            #         self.__faculty_elective_courses.extend(course.get_course_id(), course)
            self.__courses[course.get_course_id()] = course
        elif isinstance(course, MandatoryCourse):
            self.__mandatory_courses[course.get_course_id()] = course
            self.__courses[course.get_course_id()] = course
            self.__curriculum.add_course(int(course.get_semester()), course)
        elif isinstance(course, ElectiveCourse) and course.get_semester() != 0:
            self.__curriculum.add_course(course.get_semester(), course,)

    def add_course_registration_message(self, message_id, course):
        message_format = self.__registration_message_formats[message_id]
        if message_format not in self.__course_registration_messages:
            self.__course_registration_messages.extend(message_format)
        else:
            self.__course_registration_messages.get(message_format).merge(course.get_course_id())

    def reset_capacities(self):
        for course in self.__courses:
            if isinstance(course, MandatoryCourse) and len(course.get_lab_sections()) > 0:
                for lab_section in course.get_lab_sections():
                    random_section_capacity = int(random.uniform(1, self.__lab_capacity_limit) + 1)
            elif isinstance(course, ElectiveCourse):
                random_capacity = int(random.uniform(1, self.__elective_capacity_limit) + 1)
                ElectiveCourse(course).set_capacity(random_capacity)

    def get_name(self):
        return self.__name

    def set_name(self, name):
        self.__name = name

    def get_id(self):
        return self.__id

    def set_id(self, id):
        self.__id = id

    def get_course_take_limit(self):
        return self.__course_take_limit

    def get_current_semester(self):
        return self.__current_semester

    def get_curriculum(self):
        return self.__curriculum

    def get_lecturer(self, id: int):
        return self.__lecturers[id]

    def add_lecturer(self, lecturer):
        self.__lecturers.append(lecturer)

    def add_teaching_assistant(self, assistant):
        self.__teaching_assistants.append(assistant)

    def add_student(self, student):
        self.__students.append(student)

    def get_faculty(self):
        return self.__faculty

    def get_technical_elective_courses(self):
        return self.__technical_elective_courses

    def get_non_technical_elective_courses(self):
        return self.__non_technical_elective_courses

    def get_faculty_elective_courses(self):
        return self.__faculty_elective_courses

    def get_university_elective_courses(self):
        return self.__university_elective_courses

    def get_number_of_lecture_days(self):
        return self.__number_of_lecture_days

    def get_number_of_lecture_hours(self):
        return self.__number_of_lecture_hours

    def get_department_number(self):
        return self.__department_number

    def get_courses(self):
        return self.__courses

    def get_students(self):
        return self.__students

    def get_course_registration_messages(self):
        return self.__course_registration_messages

    def get_maximum_credits(self):
        return self.__maximum_credits

    def get_elective_capacity_limit(self):
        return self.__elective_capacity_limit

    def set_course_take_limit(self, course_take_limit: int):
        self.__course_take_limit = course_take_limit

    def set_lab_capacity_limit(self, lab_capacity_limit: int):
        self.__lab_capacity_limit = lab_capacity_limit

    def set_elective_capacity_limit(self, elective_capacity_limit: int):
        self.__elective_capacity_limit = elective_capacity_limit

    def set_maximum_credits(self, maximum_credits: int):
        self.__maximum_credits = maximum_credits

    def get_lab_capacity_limit(self):
        return self.__lab_capacity_limit
        pass
