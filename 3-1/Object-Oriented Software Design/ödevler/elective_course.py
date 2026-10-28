import department_course as dc


class ElectiveCourse(dc.DepartmentCourse):
    __capacity: int
    __type: str

    def __init__(self, course_id, course_name, course_credits, semester, lecture_hours, capacity, course_type):
        super().__init__(course_id, course_name, course_credits, semester, lecture_hours)
        self.__capacity = capacity
        self.__type = course_type

    def add_student(self, student):
        if self.__capacity > 0:
            self.add_student(student)
            self.__capacity = self.__capacity - 1

    def get_type(self):
        return self.__type

    def decrease_capacity(self):
        self.__capacity = self.__capacity - 1

    def has_capacity(self):
        return self.__capacity > 0

    def get_capacity(self):
        return self.__capacity

    def set_capacity(self, capacity: int):
        self.__capacity = capacity