import person


class Lecturer(person.Person):
    __givenCourses = []
    __advisedStudents = []

    def __init__(self, name, department):
        super().__init__(name, department)
        self.__givenCourses = []
        self.__advisedStudents = []

    def add_course(self, course):
        self.__givenCourses.append(course)

    def add_student(self, student):
        self.__advisedStudents.append(student)

    def get_given_courses(self):
        return self.__givenCourses

    def get_advised_students(self):
        return self.__advisedStudents