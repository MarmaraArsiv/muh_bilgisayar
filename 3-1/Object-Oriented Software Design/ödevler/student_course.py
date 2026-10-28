import random
from typing import List
import student
import course
import department_course


class StudentCourse:
    __grade: int
    __point = 4.00  # Not sure about Python's float/double variable initializations
    __letterGrade: str
    __mainCourse: type(department_course.DepartmentCourse)
    __passed = True
    __registeredSemester: int
    __registrationMessages: List[str] = [
        "Student is not eligible for course {} because prerequisite {} did not met.",
        "Advisor did not approve take of {} because he/she has another course at course time.",
        "Advisor did not approve take of {} because he/she reached credit take limit (semesters: {}).",
        "Student is could not take {} because no capacity left.",
        "Student is could not take this lab section {} because no capacity left.",
        "Advisor did not approve taking {} because he/she has another course at that time.",
        "Student cannot take {} in this semester because the course belongs to another semester.",
        "Student cannot take {} because he/she did not met minimum completed credits."]

    def __init__(self, arg_course, grade, semester):
        self.__mainCourse = arg_course
        self.set_grade(grade)
        self.__registeredSemester = semester

    def check_rules(self, student: student.Student, arg_course: department_course.DepartmentCourse):
        new_department = student.get_department()
        message = ""
        course_id_name = arg_course.get_course_id() + " " + arg_course.get_course_name()
        semester = arg_course.get_semester()

        if semester == 0 and isinstance(arg_course, course.ElectiveCourse):
            semester = student.get_elective_semester(arg_course)

        if arg_course.get_semester() > student.get_semester():
            message = self.__registrationMessages[6].format(course_id_name)
            student.add_registration_message(message)
            new_department.add_course_registration_message(6, arg_course)

        if len(student.get_failed_prerequisites(arg_course)) > 0:
            message = self.__registrationMessages[0].format(course_id_name,
                                                            student.get_failed_prerequisites(arg_course)(
                                                                0).get_course_name())
            student.add_registration_message(message)
            new_department.add_course_registration_message(0, arg_course)

        if student.get_time_table().course_has_collision(arg_course):
            message = self.__registrationMessages[1].format(course_id_name)
            student.add_registration_message(message)
            new_department.add_course_registration_message(1, arg_course)

        if student.get_taken_credits(semester) + arg_course.get_credits() > new_department.get_maximum_credits():
            message = self.__registrationMessages[2].format(course_id_name, arg_course.get_semester())
            student.add_registration_message(message)
            new_department.add_course_registration_message(2, arg_course)

        if arg_course.get_min_completed_credits() > 0 and student.get_completed_credits(
                semester - 1) < arg_course.get_min_completed_credits():
            message = self.__registrationMessages[7].format(course_id_name, arg_course.get_semester())
            student.add_registration_message(message)
            new_department.add_course_registration_message(7, arg_course)

        if isinstance(arg_course, course.ElectiveCourse) and not arg_course.has_capacity():
            message = self.__registrationMessages[3].format(course_id_name)
            student.add_registration_message(message)
            new_department.add_course_registration_message(3, arg_course)

        if len(arg_course.get_lab_sections()) > 0:
            random_lab_section: course.LabCourse
            random_lab_section = random.choice(arg_course.get_lab_sections())

            if not random_lab_section.has_capacity():
                message = self.__registrationMessages[4].format(random_lab_section.get_course_id())
                student.add_registration_message(message)
                new_department.add_course_registration_message(4, random_lab_section)

            if student.get_time_table().course_has_collision(random_lab_section):
                message = self.__registrationMessages[5].format(random_lab_section.get_course_id())
                student.add_registration_message(message)
                new_department.add_course_registration_message(5, random_lab_section)

            if random_lab_section.has_capacity() and not student.get_time_table().course_has_collision(
                    random_lab_section) and len(message) == 0:
                student.get_time_table().assign_course(random_lab_section)
                random_lab_section.add_student(student)

        else:
            student.register_course(arg_course, RandomStudentGenerator.random_grade(), semester)

    def set_grade(self, grade: int):
        self.__grade = grade
        self.set_letter_grade(grade)
        if self.__point < 1.5:
            self.__passed = False

    def get_letter_grade(self):
        return self.__letterGrade

    def set_letter_grade(self, grade: int):
        if grade >= 90:
            self.__letterGrade = "AA"
            self.__point = 4.00
        elif grade >= 85:
            self.__letterGrade = "BA"
            self.__point = 3.5
        elif grade >= 80:
            self.__letterGrade = "BB"
            self.__point = 3
        elif grade >= 75:
            self.__letterGrade = "CB"
            self.__point = 2.5
        elif grade >= 65:
            self.__letterGrade = "CC"
            self.__point = 2
        elif grade >= 55:
            self.__letterGrade = "DC"
            self.__point = 1.5
        elif grade >= 50:
            self.__letterGrade = "DD"
            self.__point = 1
        elif grade >= 45:
            self.__letterGrade = "FD"
            self.__point = 0.5
        else:
            self.__letterGrade = "FF"
            self.__point = 0

    def get_main_course(self):
        return self.__mainCourse

    def get_registered_semester(self):
        return self.__registeredSemester

    def get_point(self):
        return self.__point

    def get_grade(self):
        return self.__grade

    def is_passed(self):
        return self.__passed
