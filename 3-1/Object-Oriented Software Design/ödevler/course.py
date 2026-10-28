from abc import ABC


class Course(ABC):
    courseID: str
    courseSchedule = []
    __registeredStudents = []

    def get_course_id(self):
        return self.courseID

    def get_course_schedule(self):
        return self.courseSchedule

    def add_lecture_hour(self, day: int, lecture_hour: int):
        self.courseSchedule.append({day, lecture_hour})

    def add_student(self, student):
        self.__registeredStudents.append(student)
